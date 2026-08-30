package tungnn.tutor.java.spring.tool.crawler.config;

import org.openqa.selenium.WebDriver;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import tungnn.tutor.java.selenium.driver.ChromeWebDriverFactory;
import tungnn.tutor.java.selenium.driver.WebDriverFactory;
import tungnn.tutor.java.selenium.driver.options.ChromeOptionsFactory;
import tungnn.tutor.java.tool.crawler.core.ContentCrawler;
import tungnn.tutor.java.tool.crawler.core.SimpleContentCrawler;

@Configuration
public class AppConfig {

  @Bean
  public WebDriverFactory driverFactory() {
    return new ChromeWebDriverFactory(new ChromeOptionsFactory());
  }

  @Bean
  public WebDriver driver(WebDriverFactory driverFactory) {
    return driverFactory.getWebDriver("profile_0");
  }

  @Bean
  public ContentCrawler contentCrawler(WebDriver driver) {
    return new SimpleContentCrawler(driver);
  }
}
