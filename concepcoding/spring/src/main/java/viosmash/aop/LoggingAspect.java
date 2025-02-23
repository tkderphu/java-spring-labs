package viosmash.aop;


import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.springframework.stereotype.Component;

@Component
@Aspect //create bean and this class for logging operation before method is executed
public class LoggingAspect {
    /**
     * Wildcard * will match any single item
     * Wildcard .. will matches one or more than item
     */

    /**
     * Match any method in LoggingController with return type is String
     */
//    @Before("execution(String viosmash.aop.LoggingController.*(*))")
//    public void beforeMethod() {
//        System.out.println("Do something before method is executed");
//    }

    /**
     * Matches any method in LogginController with has one parameter with type String
     */
//    @Before("execution(* viosmash.aop.LoggingController.*(String))")
//    public void beforeMethod() {
//        System.out.println("Do something before method is executed");
//    }

    /**
     * Matches any method in LoggingController
     */
//    @Before("execution(* viosmash.aop.LoggingController.*())")
//    public void beforeMethod() {
//        System.out.println("Do something before method is executed");
//    }
//
//
//    @Before("args(String, Integer)")
//    public void matchArguments() {
//        System.out.println("Do something before method is executed");
//    }


}
