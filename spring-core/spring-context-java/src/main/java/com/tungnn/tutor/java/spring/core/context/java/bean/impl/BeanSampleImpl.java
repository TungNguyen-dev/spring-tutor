package com.tungnn.tutor.java.spring.core.context.java.bean.impl;

import com.tungnn.tutor.java.spring.core.context.java.bean.BeanSample;

public class BeanSampleImpl implements BeanSample {

  @Override
  public void sayHello() {
    System.out.println("Hello from BeanServiceImpl!");
  }
}
