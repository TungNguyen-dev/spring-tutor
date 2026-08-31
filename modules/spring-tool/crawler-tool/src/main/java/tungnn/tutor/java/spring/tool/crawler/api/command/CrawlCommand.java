package tungnn.tutor.java.spring.tool.crawler.api.command;

import org.springframework.shell.core.command.annotation.Command;
import org.springframework.shell.core.command.annotation.CommandGroup;
import org.springframework.stereotype.Component;
import tungnn.tutor.java.spring.tool.crawler.service.CrawlService;

@Component
@CommandGroup(name = "Crawl Command")
public class CrawlCommand {

  private final CrawlService crawlService;

  public CrawlCommand(CrawlService crawlService) {
    this.crawlService = crawlService;
  }

  @Command(
      description =
          "Crawl content from target URLs listed in input files and generate Markdown notes.",
      help =
          """
          Usage:
            crawl

          Example:
            shell:> crawl
          """)
  public void crawl() {
    crawlService.executeCrawl();
  }
}
