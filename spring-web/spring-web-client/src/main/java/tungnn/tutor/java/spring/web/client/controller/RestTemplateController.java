package tungnn.tutor.java.spring.web.client.controller;

import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

@RestController
@RequestMapping("/rest-template")
public class RestTemplateController {

  private static final String API_URL = "https://api.example.com/users";
  private final RestTemplate restTemplate = new RestTemplate();

  @GetMapping("/get")
  public ResponseEntity<String> getExample() {
      return restTemplate.getForEntity(API_URL, String.class);
  }

  @PostMapping("/post")
  public ResponseEntity<String> postExample(@RequestBody String requestData) {
      return restTemplate.postForEntity(API_URL, requestData, String.class);
  }

  @PutMapping("/put/{id}")
  public void putExample(@PathVariable String id, @RequestBody String requestData) {
      restTemplate.put(API_URL + "/{id}", requestData, id);
  }

  @DeleteMapping("/delete/{id}")
  public void deleteExample(@PathVariable String id) {
      restTemplate.delete(API_URL + "/{id}", id);
  }

  @GetMapping("/exchange")
  public ResponseEntity<String> exchangeExample() {
      HttpHeaders headers = new HttpHeaders();
      headers.setContentType(MediaType.APPLICATION_JSON);

      HttpEntity<String> entity = new HttpEntity<>(headers);

      return restTemplate.exchange(
          API_URL,
          HttpMethod.GET,
          entity,
          String.class
      );
  }
  
}
