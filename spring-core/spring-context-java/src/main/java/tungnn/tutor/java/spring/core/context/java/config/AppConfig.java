package tungnn.tutor.java.spring.core.context.java.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import tungnn.tutor.java.spring.core.context.java.bean.BeanSample;
import tungnn.tutor.java.spring.core.context.java.bean.impl.BeanSampleImpl;

@Configuration
public class AppConfig {

  @Bean
  public BeanSample beanSample() {
    return new BeanSampleImpl();
  }

  @Bean("beanSampleNamed")
  public BeanSample beanSampleNamed() {
    return new BeanSampleImpl();
  }
}
