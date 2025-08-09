package tungnn.tutor.java.spring.web.client.controller;

import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/spring")
public class SpringBasedController {

  @GetMapping("/param")
  public String withRequestParam(
      @RequestParam String name, @RequestParam(defaultValue = "0") int age) {
    return String.format("Name: %s, Age: %d", name, age);
  }

  @GetMapping("/header")
  public String withRequestHeader(
      @RequestHeader("User-Agent") String userAgent,
      @RequestHeader(value = "Accept-Language", defaultValue = "en") String language) {
    return String.format("User-Agent: %s, Language: %s", userAgent, language);
  }

  @GetMapping("/path/{id}/{action}")
  public String withPathVariable(@PathVariable Long id, @PathVariable String action) {
    return String.format("ID: %d, Action: %s", id, action);
  }

  @PostMapping("/body")
  public String withRequestBody(@RequestBody UserRequest request) {
    return String.format(
        "Received user data - Name: %s, Email: %s", request.getName(), request.getEmail());
  }

  public static class UserRequest {
    private String name;
    private String email;

    public String getName() {
      return name;
    }

    public void setName(String name) {
      this.name = name;
    }

    public String getEmail() {
      return email;
    }

    public void setEmail(String email) {
      this.email = email;
    }
  }
}
