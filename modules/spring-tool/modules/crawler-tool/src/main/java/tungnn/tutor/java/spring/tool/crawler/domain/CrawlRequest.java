package tungnn.tutor.java.spring.tool.crawler.domain;

public sealed interface CrawlRequest {

  String url();

  record WebPage(String url) implements CrawlRequest {}
}
