package tungnn.tutor.java.spring.web.client.controller;

import jakarta.servlet.http.HttpServletRequest;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/jakarta")
public class JakartaBasedController {

  @GetMapping("/do-work")
  public String doWork(HttpServletRequest request) {
    StringBuilder info = new StringBuilder();
    info.append("[JakartaBasedController] Method: doWork actually processing.\n");

    info.append("Request Parameters:\n");
    request
        .getParameterMap()
        .forEach(
            (key, value) ->
                info.append(String.format("  %s: %s%n", key, String.join(", ", value))));

    info.append("Request Headers:\n");
    java.util.Collections.list(request.getHeaderNames())
        .forEach(
            headerName ->
                info.append(
                    String.format("  %s: %s%n", headerName, request.getHeader(headerName))));

    info.append("\n");
    System.out.println(info);
    return info.toString();
  }
}
