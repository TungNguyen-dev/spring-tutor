package tungnn.tutor.java.spring.infrastructure.genai.llm.service.impl;

import org.springframework.ai.chat.client.ChatClient;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import tungnn.tutor.java.spring.infrastructure.genai.llm.service.GeminiService;

@Service
public class GeminiServiceImpl implements GeminiService {

  private final ChatClient chatClient;

  public GeminiServiceImpl(ChatClient.Builder chatClientBuilder) {
    this.chatClient = chatClientBuilder.build();
  }

  @Override
  public String generateAnswer(String prompt) {
    return chatClient.prompt().user(prompt).call().content();
  }

  @Override
  public <T> T generateStructuredAnswer(String prompt, Class<T> responseType) {
    return chatClient.prompt().user(prompt).call().entity(responseType);
  }

  @Override
  public Flux<String> generateStreamAnswer(String prompt) {
    return chatClient.prompt().user(prompt).stream().content();
  }
}
