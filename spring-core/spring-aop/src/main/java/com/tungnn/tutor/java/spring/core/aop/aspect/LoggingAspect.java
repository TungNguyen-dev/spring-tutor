package com.tungnn.tutor.java.spring.core.aop.aspect;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.*;
import org.springframework.stereotype.Component;

@Aspect
@Component
public class LoggingAspect {

  @Before("execution(* com.tungnn.tutor.java.spring.core.aop.service.BeanService.*(..))")
  public void logDetailsBefore(JoinPoint joinPoint) {
    // Gets method name
    String methodName = joinPoint.getSignature().getName();
    // Gets method arguments
    Object[] args = joinPoint.getArgs();

    String message = "[@Before        ] Method: " + methodName + " will execute. ";
    StringBuilder methodArgs = new StringBuilder("Arguments: ");
    if (args.length > 0) {
      for (Object arg : args) {
        methodArgs.append(arg).append(", ");
      }
    } else {
      methodArgs.append("None");
    }
    message += methodArgs;

    System.out.println(message);
  }

  @AfterReturning(
      pointcut = "execution(* com.tungnn.tutor.java.spring.core.aop.service.BeanService.*(..))",
      returning = "result")
  public void logDetailsAfterReturning(JoinPoint joinPoint, Object result) {
    // Gets method name
    String methodName = joinPoint.getSignature().getName();
    String message = "[@AfterReturning] Method: " + methodName + " executed successfully";

    // Log the return value
    if (result != null) {
      message += " and returned: " + result;
    } else {
      message += " and returned: null";
    }
    System.out.println(message);
  }

  @AfterThrowing(
      pointcut = "execution(* com.tungnn.tutor.java.spring.core.aop.service.BeanService.*(..))",
      throwing = "exception")
  public void logDetailsAfterThrowing(JoinPoint joinPoint, Exception exception) {
    // Gets method name
    String methodName = joinPoint.getSignature().getName();
    String message = "[@AfterThrowing] Method: " + methodName + " threw an exception.";
    message += " Exception Message: " + exception.getMessage();
    System.out.println(message);
  }

  @After("execution(* com.tungnn.tutor.java.spring.core.aop.service.BeanService.*(..))")
  public void logDetailsAfter(JoinPoint joinPoint) {
    // Gets method name
    String methodName = joinPoint.getSignature().getName();
    System.out.println("[@After         ] Method: " + methodName + " has finished execution");
  }
  
  @Around("execution(* com.tungnn.tutor.java.spring.core.aop.service.BeanService.*(..))")
  public Object logDetailsAround(ProceedingJoinPoint joinPoint) throws Throwable {
    long startTime = System.currentTimeMillis();
    String methodName = joinPoint.getSignature().getName();

    System.out.println("[@Around        ] Method: " + methodName + " - execution started");

    Object result = joinPoint.proceed();

    long endTime = System.currentTimeMillis();
    System.out.println("[@Around        ] Method: " + methodName + " - execution completed in "
        + (endTime - startTime) + "ms");

    return result;
  }
}
