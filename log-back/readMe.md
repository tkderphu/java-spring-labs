`loggger` is used for logging message about state of system
Spring allow  developer can write log of application easy
only create file ``logback.xml, logback-spring.xml`` this two file
will override default log of application.

``Example: logback.xml``
````
<?xml version="1.0" encoding="UTF-8"?>
<configuration>

  <appender name="STDOUT" class="ch.qos.logback.core.ConsoleAppender">
    <encoder>
      <pattern>
        %d{dd-MM-yyyy HH:mm:ss.SSS} %magenta([%thread]) %highlight(%-5level) %logger{36}.%M - %msg%n
      </pattern>
    </encoder>
  </appender>

  <root level="info">
    <appender-ref ref="STDOUT"/>
  </root>
 
</configuration>
````

``Explain``
````
ConsoleAppender: Write log at console
%d: time write log
%thread: write thread name
$-5level: write level log(trace>debug>info>warn>error)
%n: line break
%logger{36}: package class name where log is created. 
Number 36 it mean write 36 character when package too long

%M: method name where log is created
%magenta(): fill color message in round brackets
highlight(): fill color message in round brackets
````
````
In case level is 'info' so all of level < info also is created log, 
such as level: info so level warn, error also is created if it happend
````

``Write log sepecific place``

````
1, Write log with specific class
<logger name="com.lankydan.service.MyServiceImpl"  additivity="false" level="debug">
  <appender-ref ref="STDOUT" />
</logger>    

2, You can also write with specific package
<logger name="com.lankydan.controller" additivity="false"  level="debug">
  <appender-ref ref="STDOUT" />
</logger>    

additivity="false": write distinct message
````

``Logback attribute``
````
<property name="LOG_PATH" value="logs"/>    
attribute is used for log to file
````
``
Log to file using: FileAppender -> all log is written to only a file -> file will too much``
``
````
<appender name="SAVE-TO-FILE" class="ch.qos.logback.core.FileAppender">
  
  <file>${LOG_PATH}/log.log</file>

  <encoder class="ch.qos.logback.classic.encoder.PatternLayoutEncoder">
    <Pattern>
      %d{dd-MM-yyyy HH:mm:ss.SSS} [%thread] %-5level %logger{36}.%M - %msg%n
    </Pattern>
  </encoder>

</appender>  
<logger name="com.lankydan.service.MyServiceImpl" additivity="false" level="debug">
  <appender-ref ref="SAVE-TO-FILE"/>
</logger>  
````

``
RollingFileAppender: write log allow day, month, and can delete them with sepcific date or day
``

````
<appender name="SAVE-TO-FILE" class="ch.qos.logback.core.rolling.RollingFileAppender">
  
  <file>${LOG_PATH}/log.log</file>
  
  <encoder class="ch.qos.logback.classic.encoder.PatternLayoutEncoder">
    <Pattern>
      %d{dd-MM-yyyy HH:mm:ss.SSS} [%thread] %-5level %logger{36}.%M - %msg%n
    </Pattern>
  </encoder>

  <rollingPolicy class="ch.qos.logback.core.rolling.TimeBasedRollingPolicy">
    <fileNamePattern>
      ${LOG_PATH}/archived/log_%d{dd-MM-yyyy}.log
    </fileNamePattern>
    <maxHistory>10</maxHistory>
    <totalSizeCap>100MB</totalSizeCap>
  </rollingPolicy>
</appender>
````
````
maxHistory: specific number of day file log will be deleted
totalSizeCap: limit length of file, it must follow with maxHistory
````
````
<logger name="com.lankydan.service.MyServiceImpl" additivity="false" level="debug">
  <appender-ref ref="STDOUT"/>
  <appender-ref ref="SAVE-TO-FILE"/>
</logger>    
````

``Logback different profile``
````
<?xml version="1.0" encoding="UTF-8"?>
<configuration>

  <!-- config for STDOUT and SAVE-TO-FILE -->

  <springProfile name="dev">
    <root level="info">
      <appender-ref ref="STDOUT"/>
      <appender-ref ref="SAVE-TO-FILE"/>
    </root>
    <logger name="com.lankydan.service.MyServiceImpl" additivity="false" level="debug">
      <appender-ref ref="STDOUT"/>
      <appender-ref ref="SAVE-TO-FILE"/>
    </logger>
  </springProfile>

  <springProfile name="prod">
    <root level="info">
      <appender-ref ref="SAVE-TO-FILE"/>
    </root>
    <logger name="com.lankydan.service.MyServiceImpl" additivity="false" level="error">
      <appender-ref ref="SAVE-TO-FILE"/>
    </logger>
  </springProfile>

</configuration>    

````
