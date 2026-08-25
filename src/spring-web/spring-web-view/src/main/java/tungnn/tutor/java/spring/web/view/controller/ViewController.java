package tungnn.tutor.java.spring.web.view.controller;

import java.util.Arrays;
import java.util.List;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/view")
public class ViewController {

  @GetMapping("/welcome")
  public String welcome(Model model) {
    model.addAttribute("message", "Welcome to Thymeleaf Demo!");
    return "welcome";
  }

  @GetMapping("/items")
  public String showItems(Model model) {
    List<String> items = Arrays.asList("Item 1", "Item 2", "Item 3");
    model.addAttribute("items", items);
    return "items";
  }

  @GetMapping("/form")
  public String showForm(Model model) {
    model.addAttribute("user", new User());
    return "form";
  }

  @PostMapping("/submit")
  public String submitForm(@ModelAttribute("user") User user, Model model) {
    model.addAttribute("submittedUser", user);
    return "result";
  }

  public static class User {
    private String name;
    private String email;

    public User() {}

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
