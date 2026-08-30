package tungnn.tutor.java.spring.infrastructure.genai.llm.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.when;

import java.util.List;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Answers;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.ai.chat.client.ChatClient;
import reactor.core.publisher.Flux;
import tungnn.tutor.java.spring.infrastructure.genai.llm.service.impl.GeminiServiceImpl;

@ExtendWith(MockitoExtension.class)
class GeminiServiceTests {

  // RETURNS_DEEP_STUBS giúp tự động mock chuỗi: prompt() -> user() -> call() -> content()/entity()
  @Mock(answer = Answers.RETURNS_DEEP_STUBS)
  private ChatClient chatClient;

  @InjectMocks private GeminiServiceImpl geminiService;

  @Test
  @DisplayName("Mock ChatClient: generateAnswer")
  void generateAnswer_ShouldReturnStringContent() {
    // Given
    String prompt = "Trả lời ngắn gọn: 1 + 1 bằng mấy?";
    String expectedAnswer = "2";

    when(chatClient.prompt().user(prompt).call().content()).thenReturn(expectedAnswer);

    // When
    String result = geminiService.generateAnswer(prompt);

    // Then
    assertNotNull(result);
    assertEquals(expectedAnswer, result);
  }

  @Test
  @DisplayName("Mock ChatClient: generateStructuredAnswer")
  void generateStructuredAnswer_ShouldReturnMappedEntity() {
    // Given
    String prompt = "Thủ đô của Việt Nam là gì?";
    CityInfo expectedCity = new CityInfo("Hà Nội", "Việt Nam");

    when(chatClient.prompt().user(prompt).call().entity(CityInfo.class)).thenReturn(expectedCity);

    // When
    CityInfo result = geminiService.generateStructuredAnswer(prompt, CityInfo.class);

    // Then
    assertNotNull(result);
    assertEquals("Hà Nội", result.name());
    assertEquals("Việt Nam", result.country());
  }

  @Test
  @DisplayName("Mock ChatClient: generateStreamAnswer")
  void generateStreamAnswer_ShouldEmitFluxContent() {
    // Given
    String prompt = "Đếm từ 1 đến 3.";
    Flux<String> expectedFlux = Flux.just("1", ", ", "2", ", ", "3");

    when(chatClient.prompt().user(prompt).stream().content()).thenReturn(expectedFlux);

    // When
    List<String> tokens = geminiService.generateStreamAnswer(prompt).collectList().block();

    // Then
    assertNotNull(tokens);
    assertEquals(5, tokens.size());
    assertEquals("1, 2, 3", String.join("", tokens));
  }

  private record CityInfo(String name, String country) {}
}
