package tungnn.tutor.java.spring.infrastructure.genai.llm.service;

import reactor.core.publisher.Flux;

public interface GeminiService {

  String generateAnswer(String prompt);

  <T> T generateStructuredAnswer(String prompt, Class<T> responseType);

  Flux<String> generateStreamAnswer(String prompt);
}
