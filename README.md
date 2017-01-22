# Lottery

This is a Lottery concept.

## Local use

### Running application (one of the following)
1 - mvn spring-boot:run
    
2 - run as java application the class: com.system.lottery.view.boot.Application.java

3 - [WIP] mvn clean install
    java -jar target/dependency/webapp-runner-8.0.33.0.jar --port 8080 target/lottery-1.0.0.war
    
## Testing Application

### Current Draw
Retrieves the current draw that happened. This means the draw in the current month.
If there is none, a new draw will automatically be generated.

#### Method
```java
LotteryDrawWS getCurrentDraw()
```

#### Endpoint :: GET
http://localhost:8080/lottery/currentDraw

##### Payload
None

##### Result Example
```json
{
  "drawOn": "2017-01-01",
  "prize": 200,
  "combination": [
    40,
    33,
    35
  ]
}
```

### Draw From
Retrieves the draw that happened to a given date. This means the draw in the history information for a the specific month.
If there is none, a new draw will automatically be generated.

#### Method
```java
LotteryDrawWS getDrawFrom(@RequestParam Date date)
```

#### Endpoint :: GET
http://localhost:8080/lottery/drawFrom?date=2016-12-17

##### Payload
None

##### Result Example
```json
{
  "drawOn": "2016-12-01",
  "prize": 200,
  "combination": [
    27,
    35,
    7
  ]
}
```

### Latest Year Draws
Retrieves last 12 months draws.

#### Method
```java
List<LotteryDrawWS> getLatestYearDraws()
```

#### Endpoint :: GET
http://localhost:8080/lottery/latestYearDraws

##### Payload
None

##### Result Example
```json
[
  {
    "drawOn": "2015-01-01",
    "prize": 200,
    "combination": [
      16,
      17,
      9
    ]
  },
  {
    "drawOn": "2015-02-01",
    "prize": 200,
    "combination": [
      42,
      15,
      41
    ]
  },
  {
    "drawOn": "2015-03-01",
    "prize": 200,
    "combination": [
      44,
      30,
      13
    ]
  },
  {
    "drawOn": "2015-04-01",
    "prize": 200,
    "combination": [
      10,
      39,
      41
    ]
  },
  {
    "drawOn": "2015-05-01",
    "prize": 200,
    "combination": [
      16,
      26,
      17
    ]
  },
  {
    "drawOn": "2015-06-01",
    "prize": 200,
    "combination": [
      10,
      29,
      16
    ]
  },
  {
    "drawOn": "2015-07-01",
    "prize": 200,
    "combination": [
      7,
      33,
      30
    ]
  },
  {
    "drawOn": "2015-08-01",
    "prize": 200,
    "combination": [
      4,
      1,
      14
    ]
  },
  {
    "drawOn": "2015-09-01",
    "prize": 200,
    "combination": [
      4,
      17,
      40
    ]
  },
  {
    "drawOn": "2015-10-01",
    "prize": 200,
    "combination": [
      40,
      16,
      21
    ]
  },
  {
    "drawOn": "2015-11-01",
    "prize": 200,
    "combination": [
      34,
      7,
      43
    ]
  },
  {
    "drawOn": "2015-12-01",
    "prize": 200,
    "combination": [
      18,
      30,
      3
    ]
  }
]
```

### Latest X Draws
Retrieves latest X months draws.

#### Method
```java
List<LotteryDrawWS> getLatestQtdDraws(@RequestParam int quantity)
```

#### Endpoint :: GET
http://localhost:8080/lottery/latestQtdDraws?quantity=3

##### Payload
None

##### Result Example
```json
[
  {
    "drawOn": "2015-01-01",
    "prize": 200,
    "combination": [
      16,
      17,
      9
    ]
  },
  {
    "drawOn": "2015-02-01",
    "prize": 200,
    "combination": [
      42,
      15,
      41
    ]
  },
  {
    "drawOn": "2015-03-01",
    "prize": 200,
    "combination": [
      44,
      30,
      13
    ]
  }
]
```