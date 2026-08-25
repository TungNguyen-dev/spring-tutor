package tungnn.tutor.java.spring.core.aop.service;

import org.springframework.stereotype.Service;

@Service
public class BeanService {

  public void doWork() {
    System.out.println("[BeanService    ] Method: doWork actually processing.");
  }
}
