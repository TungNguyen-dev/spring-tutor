package tungnn.tutor.java.spring.tool.crawler.service;

import java.io.IOException;
import java.io.UncheckedIOException;
import java.nio.file.DirectoryStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardCopyOption;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import tungnn.tutor.java.core.lib.io.filesystem.FileUtil;
import tungnn.tutor.java.spring.tool.crawler.config.CrawlerConfig;
import tungnn.tutor.java.spring.tool.crawler.model.CrawlRequest;
import tungnn.tutor.java.spring.tool.crawler.model.CrawlResult;

@Service
public class CrawlService {

  private static final Logger LOGGER = LoggerFactory.getLogger(CrawlService.class);

  private final ArticleCrawlService articleCrawlService;
  private final CrawlerConfig crawlerConfig;

  public CrawlService(ArticleCrawlService articleCrawlService, CrawlerConfig crawlerConfig) {
    this.articleCrawlService = articleCrawlService;
    this.crawlerConfig = crawlerConfig;
  }

  public void executeCrawl() {
    List<CourseSourceFile> sourceFiles = loadCourseSourceFiles();

    if (sourceFiles.isEmpty()) {
      LOGGER.info("No course sources found in: {}", crawlerConfig.inputDir());
      return;
    }

    LOGGER.info("Starting crawl for {} course file(s)...", sourceFiles.size());

    List<CrawlRequest.Course> courses =
        sourceFiles.stream()
            .map(source -> new CrawlRequest.Course(source.courseIdentifier(), source.targetUrls()))
            .toList();

    CrawlResult result = articleCrawlService.crawlArticles(new CrawlRequest(courses));

    Set<String> successfulCourseNames =
        result.courses().stream()
            .filter(CrawlResult.Course::success)
            .map(CrawlResult.Course::courseName)
            .collect(Collectors.toSet());

    List<CourseSourceFile> successfulFiles =
        sourceFiles.stream()
            .filter(source -> successfulCourseNames.contains(source.courseIdentifier()))
            .toList();

    List<CourseSourceFile> failedFiles =
        sourceFiles.stream()
            .filter(source -> !successfulCourseNames.contains(source.courseIdentifier()))
            .toList();

    handleSuccessfulFiles(successfulFiles);
    handleFailedFiles(failedFiles);
  }

  private void handleSuccessfulFiles(List<CourseSourceFile> successfulFiles) {
    successfulFiles.forEach(
        source -> {
          try {
            moveSourceToDoneDir(source);
          } catch (Exception e) {
            LOGGER.error("Failed to move file to done dir: {}", source.path(), e);
          }
        });

    LOGGER.info(
        "Crawling completed. Moved {} successful file(s) to done directory.",
        successfulFiles.size());
  }

  private void handleFailedFiles(List<CourseSourceFile> failedFiles) {
    if (failedFiles.isEmpty()) {
      return;
    }

    failedFiles.forEach(
        source -> {
          try {
            Path outputPath = crawlerConfig.outputDir().resolve(source.relativePath());
            if (Files.exists(outputPath)) {
              FileUtil.deleteRecursively(outputPath);
            }
          } catch (Exception e) {
            LOGGER.error("Failed to cleanup failed file: {}", source.relativePath(), e);
          }
        });

    LOGGER.warn(
        "{} course file(s) failed or contained failed articles and remain in input directory: {}",
        failedFiles.size(),
        failedFiles.stream().map(CourseSourceFile::courseIdentifier).toList());
  }

  private List<CourseSourceFile> loadCourseSourceFiles() {
    Path inputDir = crawlerConfig.inputDir();
    if (!Files.exists(inputDir)) {
      return List.of();
    }

    return FileUtil.walk(inputDir).stream()
        .filter(Files::isRegularFile)
        .map(this::parseCourseSourceFile)
        .toList();
  }

  private CourseSourceFile parseCourseSourceFile(Path sourcePath) {
    Path relativePath = crawlerConfig.inputDir().relativize(sourcePath);
    List<String> targetUrls =
        FileUtil.readString(sourcePath)
            .lines()
            .map(String::trim)
            .filter(line -> !line.isBlank())
            .toList();

    return new CourseSourceFile(sourcePath, relativePath, targetUrls);
  }

  private void moveSourceToDoneDir(CourseSourceFile source) {
    try {
      Path destinationPath = crawlerConfig.doneDir().resolve(source.relativePath());

      if (destinationPath.getParent() != null) {
        FileUtil.createDirectories(destinationPath.getParent());
      }

      Files.move(source.path(), destinationPath, StandardCopyOption.REPLACE_EXISTING);

      // Xóa các thư mục cha rỗng trong inputDir sau khi đã di chuyển file
      deleteEmptyParentDirectories(source.path().getParent());
    } catch (IOException e) {
      LOGGER.error("Failed to move file to done directory: {}", source.path(), e);
      throw new UncheckedIOException(
          "Failed to move completed file to done directory: " + source.path(), e);
    }
  }

  private void deleteEmptyParentDirectories(Path directory) {
    Path inputDir = crawlerConfig.inputDir().toAbsolutePath().normalize();
    Path current = directory != null ? directory.toAbsolutePath().normalize() : null;

    while (current != null && current.startsWith(inputDir) && !current.equals(inputDir)) {
      if (isDirectoryEmpty(current)) {
        try {
          Files.delete(current);
          LOGGER.debug("Deleted empty parent directory in inputDir: {}", current);
          current = current.getParent();
        } catch (IOException e) {
          LOGGER.warn("Could not delete directory: {}", current, e);
          break;
        }
      } else {
        // Dừng lại nếu thư mục cha vẫn còn chứa file/sub-folder khác
        break;
      }
    }
  }

  private boolean isDirectoryEmpty(Path directory) {
    try (DirectoryStream<Path> dirStream = Files.newDirectoryStream(directory)) {
      return !dirStream.iterator().hasNext();
    } catch (IOException e) {
      return false;
    }
  }

  private record CourseSourceFile(Path path, Path relativePath, List<String> targetUrls) {
    private String courseIdentifier() {
      return relativePath.toString().replace('\\', '/');
    }
  }
}
