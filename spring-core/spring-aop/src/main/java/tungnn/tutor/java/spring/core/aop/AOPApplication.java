package tungnn.tutor.java.spring.core.aop;

import tungnn.tutor.java.spring.core.aop.service.BeanService;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.EnableAspectJAutoProxy;

@ComponentScan
@EnableAspectJAutoProxy
public class AOPApplication {

  public static void main(String[] args) {
    ApplicationContext context = new AnnotationConfigApplicationContext(AOPApplication.class);
    BeanService beanService = context.getBean(BeanService.class);
    beanService.doWork();
  }
}
