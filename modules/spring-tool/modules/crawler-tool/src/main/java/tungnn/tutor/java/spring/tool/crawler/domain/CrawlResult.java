package tungnn.tutor.java.spring.tool.crawler.domain;

public sealed interface CrawlResult {

  record Success(String url, String title, String content) implements CrawlResult {}

  record Failure(Exception exception) implements CrawlResult {}
}
