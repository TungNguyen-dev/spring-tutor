package tungnn.tutor.java.spring.tool.crawler.model;

import java.nio.file.Path;
import java.util.List;

public record CrawlResult(List<Course> courses, boolean success) {

  public record Course(String courseName, List<Path> articlePaths, boolean success) {}
}
