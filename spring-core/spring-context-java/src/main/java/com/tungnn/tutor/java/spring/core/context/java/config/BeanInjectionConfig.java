package com.tungnn.tutor.java.spring.core.context.java.config;

import com.tungnn.tutor.java.spring.core.context.java.bean.BeanSample;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class BeanInjectionConfig {

  /*
   * Field inject
   */
  @Autowired private BeanSample beanSample;

  {
    System.out.println("Field inject: " + beanSample);
  }

  /*
   * Method inject
   */
  @Bean
  public BeanSample beanInjectedMethod(@Qualifier("beanSample") BeanSample beanSample) {
    System.out.println("beanInjectedMethod: " + beanSample);
    return beanSample;
  }

  private BeanSample beanInjectedConstructor;

  public BeanInjectionConfig(@Qualifier("beanSample") BeanSample beanInjectedConstructor) {
    System.out.println("Constructor inject: " + beanInjectedConstructor);
  }
}
