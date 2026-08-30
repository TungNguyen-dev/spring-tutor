package tungnn.tutor.java.spring.tool.crawler.config;

import java.nio.file.Path;
import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties(prefix = "crawler")
public record CrawlerConfig(Storage storage, Dir dir, Pool pool) {

  public Path baseDir() {
    return Path.of(storage.base());
  }

  public Path inputDir() {
    return baseDir().resolve(dir.input());
  }

  public Path outputDir() {
    return baseDir().resolve(dir.output());
  }

  public Path doneDir() {
    return baseDir().resolve(dir.done());
  }

  public int poolSize() {
    return pool.size();
  }

  public record Storage(String base) {}

  public record Dir(String input, String output, String done) {}

  public record Pool(int size) {}
}
