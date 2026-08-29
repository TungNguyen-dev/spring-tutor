package tungnn.tutor.java.spring.infrastructure.genai.llm.service;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class GeminiServiceImplTests {

  @Autowired private GeminiService geminiService;

  private record CityInfo(String name, String country) {}

  @Test
  @DisplayName("Real API Test: generateAnswer")
  void generateAnswer_RealApi_ShouldReturnNonEmptyString() {
    // Given
    String prompt = "Trả lời ngắn gọn: 1 + 1 bằng mấy?";

    // When
    String result = geminiService.generateAnswer(prompt);

    // Then
    assertNotNull(result);
    assertFalse(result.isBlank());
    System.out.println(">>> Answer Result:\n" + result);
  }

  @Test
  @DisplayName("Real API Test: generateStructuredAnswer")
  void generateStructuredAnswer_RealApi_ShouldReturnMappedObject() {
    // Given
    String prompt = "Thủ đô của Việt Nam là gì?";

    // When
    CityInfo result = geminiService.generateStructuredAnswer(prompt, CityInfo.class);

    // Then
    assertNotNull(result);
    assertNotNull(result.name());
    System.out.println(">>> Structured Result: " + result);
  }

  @Test
  @DisplayName("Real API Test: generateStreamAnswer")
  void generateStreamAnswer_RealApi_ShouldEmitTokens() {
    String prompt = "Đếm từ 1 đến 3.";

    geminiService.generateStreamAnswer(prompt).doOnNext(System.out::print).blockLast();
  }
}
