package tungnn.tutor.java.spring.tool.crawler.domain;

public interface WebCrawler {

  boolean supports(CrawlRequest request);

  CrawlResult crawl(CrawlRequest crawlRequest);
}
