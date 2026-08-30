package tungnn.tutor.java.spring.tool.crawler.api.command;

import java.util.ArrayList;
import java.util.List;
import org.openqa.selenium.WebDriver;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.shell.core.command.CommandContext;
import org.springframework.shell.core.command.annotation.Argument;
import org.springframework.shell.core.command.annotation.Command;
import org.springframework.shell.core.command.annotation.CommandGroup;
import org.springframework.stereotype.Component;
import tungnn.tutor.java.infrastructure.pool.webdriver.WebDriverPool;

@Component
@CommandGroup(name = "Selenium Command")
public class SeleniumCommand {

  private static final Logger LOGGER = LoggerFactory.getLogger(SeleniumCommand.class);

  private final WebDriverPool webDriverPool;

  public SeleniumCommand(WebDriverPool webDriverPool) {
    this.webDriverPool = webDriverPool;
  }

  @Command(
      description = "Khởi tạo và mở một số lượng WebDriver nhất định trong pool.",
      help =
          """
          Usage:
            open-drivers <numOfDrivers>

          Example:
            shell:> open-drivers 3
          """)
  public void openDrivers(
      CommandContext ctx, @Argument(index = 0, description = "numOfDrivers") int numOfDrivers) {

    LOGGER.info("Đang khởi tạo {} WebDrivers...", numOfDrivers);

    List<WebDriver> list = new ArrayList<>(numOfDrivers);
    for (int i = 0; i < numOfDrivers; i++) {
      list.add(webDriverPool.getDriver());
    }

    var writer = ctx.outputWriter();
    String promptMsg =
        String.format(
            "Đã mở thành công %d WebDrivers. Nhấn [ENTER] để tắt tất cả drivers...", numOfDrivers);

    // Chờ người dùng nhấn Enter từ Terminal
    try {
      ctx.inputReader().readInput(promptMsg);
    } catch (Exception e) {
      LOGGER.error("Lỗi khi chờ phản hồi từ bàn phím", e);
    } finally {
      // Đảm bảo luôn thu hồi/đóng drivers kể cả khi có ngoại lệ xảy ra
      LOGGER.info("Đang tiến hành tắt các WebDrivers...");
      for (WebDriver driver : list) {
        try {
          webDriverPool.returnDriver(driver);
        } catch (Exception e) {
          LOGGER.error("Lỗi khi trả lại WebDriver về pool", e);
        }
      }
    }

    writer.println("Đã tắt toàn bộ WebDrivers thành công.");
    writer.flush();
  }
}
