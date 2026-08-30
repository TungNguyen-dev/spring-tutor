package tungnn.tutor.java.spring.tool.crawler.model;

import java.util.List;

public record CrawlRequest(List<Course> courses) {

  public record Course(String courseName, List<String> articleUrls) {}
}
