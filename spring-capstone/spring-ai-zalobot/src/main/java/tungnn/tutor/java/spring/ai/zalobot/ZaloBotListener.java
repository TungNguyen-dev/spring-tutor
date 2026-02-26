package tungnn.tutor.java.spring.ai.zalobot;

import dev.linhvu.zalobot.client.ZaloBotClient;
import dev.linhvu.zalobot.core.model.GetUpdatesResult;
import dev.linhvu.zalobot.core.model.SendMessage;
import dev.linhvu.zalobot.core.model.SendMessageResult;
import dev.linhvu.zalobot.listener.UpdateListener;
import org.springframework.ai.chat.client.ChatClient;
import org.springframework.stereotype.Component;

import java.util.Objects;

@Component
public class ZaloBotListener implements UpdateListener {

  private final ZaloBotClient zaloBotClient;
  private final ChatClient chatClient;

  public ZaloBotListener(ZaloBotClient zaloBotClient, ChatClient.Builder builder) {
    this.zaloBotClient = zaloBotClient;
    this.chatClient = builder.build();
  }

  @Override
  public void onUpdate(GetUpdatesResult update) {
    if (update.isTextMessage()) {
      String chatId = update.message().chat().id();
      String text = update.message().text();

      String systemPrompt =
          """
          You're an assistant. Your answers MUST:
          * NEVER be over 1500 characters
          """;
      System.out.println(systemPrompt);
      System.out.println(text);

      String content = this.chatClient.prompt().system(systemPrompt).user(text).call().content();
      System.out.println(content);
      Objects.requireNonNull(content, "content");

      zaloBotClient
          .sendMessage()
          .body(new SendMessage(chatId, content))
          .retrieve()
          .call(SendMessageResult.class);
    }
  }
}
