package tungnn.tutor.java.spring.infrastructure.genai.llm.config;

import org.springframework.ai.chat.client.ChatClient;
import org.springframework.beans.factory.annotation.Configurable;
import org.springframework.context.annotation.Bean;

@Configurable
public class AiConfig {

  @Bean
  public ChatClient chatClient(ChatClient.Builder builder) {
    return builder.build();
  }
}
