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
  private BeanSample beanInjectedConstructor;

  {
    System.out.println("Field inject: " + beanSample);
  }

  public BeanInjectionConfig(@Qualifier("beanSample") BeanSample beanInjectedConstructor) {
    System.out.println("Constructor inject: " + beanInjectedConstructor);
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
