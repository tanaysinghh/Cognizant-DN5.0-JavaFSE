# SLF4J Logging

**Exercise 1: Logging Error Messages and Warning Levels**

`LoggingExample.java` logs at info, warn, and error levels using SLF4J.

## Maven dependencies (pom.xml)
```xml
<dependency>
    <groupId>org.slf4j</groupId>
    <artifactId>slf4j-api</artifactId>
    <version>2.0.13</version>
</dependency>
<dependency>
    <groupId>ch.qos.logback</groupId>
    <artifactId>logback-classic</artifactId>
    <version>1.5.6</version>
</dependency>
```
SLF4J is the logging API; Logback is the actual implementation that prints the logs.
