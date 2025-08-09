package tungnn.tutor.java.spring.core.context.xml;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import tungnn.tutor.java.spring.core.context.xml.bean.BeanSample;

public class XMLBasedContextApplication {

  public static void main(String[] args) {
    ApplicationContext context = new ClassPathXmlApplicationContext("application-beans.xml");
    BeanSample beanSample = context.getBean("beanSample", BeanSample.class);
    System.out.printf("%-20s %s%n", "beanSample:", beanSample);
  }
}
