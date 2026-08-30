package tungnn.tutor.java.spring.tool.crawler.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import tungnn.tutor.java.infrastructure.pool.webdriver.PooledWebDriverPool;
import tungnn.tutor.java.infrastructure.pool.webdriver.WebDriverPool;
import tungnn.tutor.java.selenium.driver.ChromeWebDriverFactory;
import tungnn.tutor.java.selenium.driver.WebDriverFactory;
import tungnn.tutor.java.selenium.driver.options.ChromeOptionsFactory;
import tungnn.tutor.java.tool.crawler.core.ContentCrawler;
import tungnn.tutor.java.tool.crawler.core.PooledContentCrawler;

@Configuration
public class AppConfig {

  private final CrawlerConfig crawlerConfig;

  public AppConfig(CrawlerConfig crawlerConfig) {
    this.crawlerConfig = crawlerConfig;
  }

  @Bean
  public WebDriverFactory driverFactory() {
    return new ChromeWebDriverFactory(new ChromeOptionsFactory());
  }

  @Bean
  public WebDriverPool driverPool(WebDriverFactory driverFactory) {
    return new PooledWebDriverPool(driverFactory, crawlerConfig.poolSize());
  }

  @Bean
  public ContentCrawler contentCrawler(WebDriverPool driverPool) {
    return new PooledContentCrawler(driverPool);
  }
}
