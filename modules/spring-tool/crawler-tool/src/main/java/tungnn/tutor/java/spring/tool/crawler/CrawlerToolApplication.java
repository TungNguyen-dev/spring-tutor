package tungnn.tutor.java.spring.tool.crawler;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.ConfigurationPropertiesScan;

@SpringBootApplication(
    scanBasePackages = {
      "tungnn.tutor.java.spring.tool.crawler",
      "tungnn.tutor.java.spring.infrastructure.genai.llm"
    })
@ConfigurationPropertiesScan(
    basePackages = {
      "tungnn.tutor.java.spring.tool.crawler.config",
    })
public class CrawlerToolApplication {

  public static void main(String[] args) {
    SpringApplication.run(CrawlerToolApplication.class, args);
  }
}
