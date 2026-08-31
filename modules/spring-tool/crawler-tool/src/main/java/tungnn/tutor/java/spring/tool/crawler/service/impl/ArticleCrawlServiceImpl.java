package tungnn.tutor.java.spring.tool.crawler.service.impl;

import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Collection;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;
import org.springframework.stereotype.Service;
import tungnn.tutor.java.core.lib.io.filesystem.FileNameUtil;
import tungnn.tutor.java.core.lib.io.filesystem.FileUtil;
import tungnn.tutor.java.document.markdown.MarkdownWriterUtils;
import tungnn.tutor.java.spring.tool.crawler.config.CrawlerConfig;
import tungnn.tutor.java.spring.tool.crawler.model.CrawlRequest;
import tungnn.tutor.java.spring.tool.crawler.model.CrawlResult;
import tungnn.tutor.java.spring.tool.crawler.service.ArticleCrawlService;
import tungnn.tutor.java.tool.crawler.core.BatchCrawlExecutor;
import tungnn.tutor.java.tool.crawler.core.ContentCrawlRequest;
import tungnn.tutor.java.tool.crawler.core.ContentCrawlResult;
import tungnn.tutor.java.tool.crawler.obsidian.ObsidianNote;

@Service
public class ArticleCrawlServiceImpl implements ArticleCrawlService {

  private final CrawlerConfig crawlerConfig;
  private final BatchCrawlExecutor batchCrawlExecutor;

  public ArticleCrawlServiceImpl(
      CrawlerConfig crawlerConfig, BatchCrawlExecutor batchCrawlExecutor) {
    this.crawlerConfig = crawlerConfig;
    this.batchCrawlExecutor = batchCrawlExecutor;
  }

  @Override
  public CrawlResult crawlArticles(CrawlRequest request) {
    if (request == null || request.courses() == null || request.courses().isEmpty()) {
      return new CrawlResult(List.of(), true);
    }

    var resultsByUrl = executeBatchCrawl(request.courses());

    List<CrawlResult.Course> resultCourses =
        request.courses().stream().map(course -> processCourse(course, resultsByUrl)).toList();

    // Toàn bộ request được coi là thành công nếu tất cả các course đều thành công
    boolean overallSuccess = resultCourses.stream().allMatch(CrawlResult.Course::success);

    return new CrawlResult(resultCourses, overallSuccess);
  }

  private LinkedHashMap<String, ContentCrawlResult> executeBatchCrawl(
      List<CrawlRequest.Course> courses) {

    var crawlRequests =
        courses.stream()
            .map(CrawlRequest.Course::articleUrls)
            .flatMap(Collection::stream)
            .distinct()
            .map(ContentCrawlRequest::new)
            .toList();

    return batchCrawlExecutor.crawlBatch(crawlRequests).stream()
        .collect(
            Collectors.toMap(
                ContentCrawlResult::url,
                result -> result,
                (existing, _) -> existing,
                LinkedHashMap::new));
  }

  private CrawlResult.Course processCourse(
      CrawlRequest.Course course, LinkedHashMap<String, ContentCrawlResult> resultsByUrl) {

    int totalUnits = course.articleUrls().size();
    int zeroPaddingWidth = String.valueOf(totalUnits).length();
    var unitCounter = new AtomicInteger(0);

    Path courseOutputDir = crawlerConfig.outputDir().resolve(course.courseName());
    FileUtil.createDirectories(courseOutputDir);

    List<Path> generatedPaths = new ArrayList<>();
    boolean courseSuccess = true;

    for (String articleUrl : course.articleUrls()) {
      int unitNumber = unitCounter.incrementAndGet();
      var result = resultsByUrl.get(articleUrl);

      if (result == null || !result.isSuccess()) {
        System.err.printf("Crawl failed [%d/%d]: %s%n", unitNumber, totalUnits, articleUrl);
        courseSuccess = false; // Đánh dấu course thất bại nếu có ít nhất 1 bài viết fail
        continue;
      }

      Path filePath = writeResultToFile(courseOutputDir, unitNumber, result, zeroPaddingWidth);
      generatedPaths.add(filePath);
    }

    return new CrawlResult.Course(course.courseName(), generatedPaths, courseSuccess);
  }

  private Path writeResultToFile(
      Path courseOutputDir, int unitNumber, ContentCrawlResult result, int zeroPaddingWidth) {

    String sanitizedTitle = FileNameUtil.sanitize(result.title());
    String formatPattern = "%0" + zeroPaddingWidth + "d - %s.md";
    String fileName = String.format(formatPattern, unitNumber, sanitizedTitle);

    Path destinationFile = courseOutputDir.resolve(fileName);

    ObsidianNote note =
        new ObsidianNote(
            result.title(),
            MarkdownWriterUtils.convertHtmlToMarkdown(result.content()),
            List.of(result.url()));

    FileUtil.writeString(destinationFile, note.toMarkdown());
    return destinationFile;
  }
}
