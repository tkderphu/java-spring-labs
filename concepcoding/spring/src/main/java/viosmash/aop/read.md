Aspect allow development can be logging operation of client before it execution

Such as when user login to system,
we want to log that action before
login execution, how can we do that?

using aspect for interceptor(do something before it is executed)

Some define with aspect programming

``Pointcut``: its an expression, which tell where an ADVICE should be applied

``ADVICE``: where code is written and run for log operation of method is specific
in pointcut, along with 

``@Before:`` run before method is executed, 

``@After:`` run after method is executed, 

``@Around: ``(its surrounds the method execution before and after)

Types of pointcut: 
1. Execution: matches a particular method in a particular class
    Example: @Link LoggingAspect
2. Within: matches all method within  class or  package
3. @within: matches any method in a class which has annotation
4. @annotation: matches any method has annotation
5. Args: matches any method with particular arguments
6. @Args: matches any method with particular parameters and that parameter class
        is annotated with particular annotation
   7. Target: matches any method with instance of particular class

    
