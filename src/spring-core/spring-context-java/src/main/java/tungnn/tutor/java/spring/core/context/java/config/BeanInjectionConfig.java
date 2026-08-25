package tungnn.tutor.java.spring.core.context.java.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import tungnn.tutor.java.spring.core.context.java.bean.BeanSample;

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
   * Constructor inject
   */
  private BeanSample beanInjectedConstructor;

  public BeanInjectionConfig(@Qualifier("beanSample") BeanSample beanInjectedConstructor) {
    this.beanInjectedConstructor = beanInjectedConstructor;
    System.out.println("Constructor injected: " + this.beanInjectedConstructor);
  }

  /*
   * Method inject
   */
  @Bean
  public BeanSample beanInjectedMethod(@Qualifier("beanSample") BeanSample beanSample) {
    System.out.println("beanInjectedMethod: " + beanSample);
    return beanSample;
  }
}
