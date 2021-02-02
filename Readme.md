# Calculator server

TCP server providing a service for performing simple arithmetic calculations

Build
```bash
./gradlew build
```

Run application on default port (4567 port)
```bash
./gradlew run
```

Run application on custom port (for example, 9999 port)
```bash
./gradlew run --args='--tcp.server.port=9999' 
```

Run tests
```bash
./gradlew test
```

Run application using only jar file on default port (4567 port)
```bash
java -jar ./build/libs/CalculatorServer-1.0.jar 
```

Run application using only jar file on custom port (for example, 9999 port)
```bash
java -jar ./build/libs/CalculatorServer-1.0.jar --tcp.server.port=9999
```

### Technologies

* Java 11
* Gradle
* Spring Boot
* Spring Integration  
* log4j 2

### Mathematical library 
mXparser
http://mathparser.org

### Supported mathematical functions

* sin
* cos
* tan
* ctan
* power (^)

For a full description, see the link https://github.com/mariuszgromada/MathParser.org-mXparser

    