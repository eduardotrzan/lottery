# Lottery

This is a Lottery concept for ESignLive.

## Local use

### Running application (one of the following)
1 - mvn spring-boot:run
    
2 - run as java application the class: com.system.lottery.view.boot.Application.java

3 - [WIP] mvn clean install
    java -jar target/dependency/webapp-runner-8.0.33.0.jar --port 8080 target/lottery-1.0.0.war
    
## Testing Payload

### Ticket WS
### JSON
```
{
  "number": 33,
  "name": "Eduardo",
  "drawOn": "2017-01-21"
}
```