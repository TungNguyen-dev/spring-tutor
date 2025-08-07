package com.tungnn.tutor.java.spring.core.context.java;

import com.tungnn.tutor.java.spring.core.context.java.bean.BeanSample;
import com.tungnn.tutor.java.spring.core.context.java.bean.ControllerSample;
import com.tungnn.tutor.java.spring.core.context.java.bean.RepositorySample;
import com.tungnn.tutor.java.spring.core.context.java.bean.ServiceSample;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.ComponentScan;

@ComponentScan
public class JavaBasedContextApplication {

  public static void main(String[] args) {
    ApplicationContext context =
        new AnnotationConfigApplicationContext(JavaBasedContextApplication.class);

    BeanSample beanSample = context.getBean("beanSample", BeanSample.class);
    System.out.printf("%-20s %s%n", "beanSample:", beanSample);

    BeanSample beanSampleNamed = context.getBean("beanSampleNamed", BeanSample.class);
    System.out.printf("%-20s %s%n", "beanSampleNamed:", beanSampleNamed);

    ControllerSample controllerSample = context.getBean("controllerSample", ControllerSample.class);
    System.out.printf("%-20s %s%n", "controllerSample:", controllerSample);

    RepositorySample repositorySample = context.getBean("repositorySample", RepositorySample.class);
    System.out.printf("%-20s %s%n", "repositorySample:", repositorySample);

    ServiceSample serviceSample = context.getBean("serviceSample", ServiceSample.class);
    System.out.printf("%-20s %s%n", "serviceSample:", serviceSample);
  }
}
