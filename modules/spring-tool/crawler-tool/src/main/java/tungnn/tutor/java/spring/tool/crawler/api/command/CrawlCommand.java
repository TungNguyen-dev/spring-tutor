package tungnn.tutor.java.spring.tool.crawler.api.command;

import java.io.IOException;
import java.io.UncheckedIOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardCopyOption;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.shell.core.command.annotation.Command;
import org.springframework.shell.core.command.annotation.CommandGroup;
import org.springframework.stereotype.Component;
import tungnn.tutor.java.core.lib.io.filesystem.FileUtil;
import tungnn.tutor.java.spring.tool.crawler.config.CrawlerConfig;
import tungnn.tutor.java.spring.tool.crawler.model.CrawlRequest;
import tungnn.tutor.java.spring.tool.crawler.model.CrawlResult;
import tungnn.tutor.java.spring.tool.crawler.service.ArticleCrawlService;

@Component
@CommandGroup(name = "Crawl Command")
public class CrawlCommand {

  private static final Logger LOGGER = LoggerFactory.getLogger(CrawlCommand.class);

  private final ArticleCrawlService articleCrawlService;
  private final CrawlerConfig crawlerConfig;

  public CrawlCommand(ArticleCrawlService articleCrawlService, CrawlerConfig crawlerConfig) {
    this.articleCrawlService = articleCrawlService;
    this.crawlerConfig = crawlerConfig;
  }

  @Command(
      description =
          "Crawl content from target URLs listed in input files and generate Markdown notes.",
      help =
          """
          Usage:
            crawl

          Example:
            shell:> crawl
          """)
  public void crawl() {
    List<CourseSourceFile> sourceFiles = loadCourseSourceFiles();

    if (sourceFiles.isEmpty()) {
      LOGGER.info("No course sources found in: {}", crawlerConfig.inputDir());
      return;
    }

    LOGGER.info("Starting crawl for {} course file(s)...", sourceFiles.size());

    List<CrawlRequest.Course> courses =
        sourceFiles.stream()
            .map(source -> new CrawlRequest.Course(source.fileName(), source.targetUrls()))
            .toList();

    CrawlResult result = articleCrawlService.crawlArticles(new CrawlRequest(courses));

    Set<String> successfulCourseNames =
        result.courses().stream()
            .filter(CrawlResult.Course::success)
            .map(CrawlResult.Course::courseName)
            .collect(Collectors.toSet());

    List<CourseSourceFile> successfulFiles =
        sourceFiles.stream()
            .filter(source -> successfulCourseNames.contains(source.fileName()))
            .toList();

    List<CourseSourceFile> failedFiles =
        sourceFiles.stream()
            .filter(source -> !successfulCourseNames.contains(source.fileName()))
            .toList();

    successfulFiles.forEach(
        source -> {
          try {
            moveSourceToDoneDir(source.path());
          } catch (Exception e) {
            LOGGER.error("Failed to move file to done dir: {}", source.path(), e);
          }
        });

    LOGGER.info(
        "Crawling completed. Moved {} successful file(s) to done directory.",
        successfulFiles.size());

    if (!failedFiles.isEmpty()) {
      failedFiles.forEach(
          source -> {
            try {
              Path outputPath = crawlerConfig.outputDir().resolve(source.fileName());
              if (Files.exists(outputPath)) {
                FileUtil.deleteRecursively(outputPath);
              }
            } catch (Exception e) {
              LOGGER.error("Failed to cleanup failed file: {}", source.fileName(), e);
            }
          });

      LOGGER.warn(
          "{} course file(s) failed or contained failed articles and remain in input directory: {}",
          failedFiles.size(),
          failedFiles.stream().map(CourseSourceFile::fileName).toList());
    }
  }

  private List<CourseSourceFile> loadCourseSourceFiles() {
    if (!Files.exists(crawlerConfig.inputDir())) {
      return List.of();
    }

    return FileUtil.walk(crawlerConfig.inputDir()).stream()
        .filter(Files::isRegularFile)
        .map(this::parseCourseSourceFile)
        .toList();
  }

  private CourseSourceFile parseCourseSourceFile(Path sourcePath) {
    List<String> targetUrls =
        FileUtil.readString(sourcePath)
            .lines()
            .map(String::trim)
            .filter(line -> !line.isBlank())
            .toList();

    return new CourseSourceFile(sourcePath, targetUrls);
  }

  private void moveSourceToDoneDir(Path sourcePath) {
    try {
      Path doneDir = FileUtil.createDirectories(crawlerConfig.doneDir());
      Path destinationPath = doneDir.resolve(sourcePath.getFileName().toString());

      Files.move(sourcePath, destinationPath, StandardCopyOption.REPLACE_EXISTING);
    } catch (IOException e) {
      LOGGER.error("Failed to move file to done directory: {}", sourcePath, e);
      throw new UncheckedIOException(
          "Failed to move completed file to done directory: " + sourcePath, e);
    }
  }

  private record CourseSourceFile(Path path, List<String> targetUrls) {
    private String fileName() {
      return path.getFileName().toString();
    }
  }
}
