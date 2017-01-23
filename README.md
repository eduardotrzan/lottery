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

### Verify Result
Verifies if a given Ticket was a winner considering its draw date provided.

#### Method
```java
LotteryResultWS verifyResult(@RequestBody TicketWS ticketWS)
```

#### Endpoint :: POST
http://localhost:8080/lottery/verifyResult

##### Payload
```json
{
  "number": 33,
  "name": "Eduardo",
  "drawOn": "2017-01-21"
}
```

##### Result Example
```json
{
  "winners": [
    {
      "position": 0,
      "name": "Eduardo",
      "value": 20
    }
  ],
  "drawOn": "2017-01-21"
}
```

### Verify Results
Verifies if list of Tickets has a max of 3 winners, considering its draw date provided.

#### Method
```java
LotteryResultWS verifyResults(@RequestBody List<TicketWS> ticketWSs)
```

#### Endpoint :: POST
http://localhost:8080/lottery/verifyResults

##### Payload
```json
[
	{
	  "number": 20,
	  "name": "Eduardo",
	  "drawOn": "2017-01-21"
	},
	{
	  "number": 43,
	  "name": "Mary",
	  "drawOn": "2017-01-21"
	},
	{
	  "number": 41,
	  "name": "Louis",
	  "drawOn": "2017-01-21"
	},
	{
	  "number": 33,
	  "name": "Noah",
	  "drawOn": "2017-01-21"
	},
	{
	  "number": 16,
	  "name": "Tim",
	  "drawOn": "2017-01-21"
	}
]
```

##### Result Example
```json
{
  "winners": [
    {
      "position": 0,
      "name": "Eduardo",
      "value": 219.105
    },
    {
      "position": 1,
      "name": "Mary",
      "value": 43.821
    },
    {
      "position": 2,
      "name": "Tim",
      "value": 29.214
    }
  ],
  "drawOn": null
}
```


### Verify Result on Date
Verifies if a given Ticket was a winner on a given provided draw date.

#### Method
```java
LotteryResultWS verifyResultOnDate(@RequestBody TicketWS ticketWS, @RequestParam @DateTimeFormat(pattern=DATE_PATTERN) Date drawOn)
```

#### Endpoint :: POST
http://localhost:8080/lottery/verifyResultOnDate?drawOn=2017-01-21

##### Payload
```json
{
  "number": 15,
  "name": "Eduardo",
  "drawOn": "2017-01-21"
}
```

##### Result Example
```json
{
  "winners": [
    {
      "position": 0,
      "name": "Eduardo",
      "value": 277.62
    }
  ],
  "drawOn": "2017-01-21"
}
```

### Verify Results on Date
Verifies if list of Tickets has a max of 3 winners, on a given provided draw date..

#### Method
```java
LotteryResultWS verifyResultsOnDate(@RequestBody List<TicketWS> ticketWSs, @RequestParam @DateTimeFormat(pattern=DATE_PATTERN) Date drawOn)
```

#### Endpoint :: POST
http://localhost:8080/lottery/verifyResultsOnDate?drawOn=2017-01-21

##### Payload
```json
[
	{
	  "number": 15,
	  "name": "Eduardo",
	  "drawOn": "2017-01-21"
	},
	{
	  "number": 43,
	  "name": "Mary",
	  "drawOn": "2017-01-21"
	},
	{
	  "number": 3,
	  "name": "Louis",
	  "drawOn": "2017-01-21"
	},
	{
	  "number": 5,
	  "name": "Noah",
	  "drawOn": "2017-01-21"
	},
	{
	  "number": 16,
	  "name": "Tim",
	  "drawOn": "2017-01-21"
	}
]
```

##### Result Example
```json
{
  "winners": [
    {
      "position": 0,
      "name": "Eduardo",
      "value": 277.62
    },
    {
      "position": 1,
      "name": "Louis",
      "value": 55.52
    },
    {
      "position": 2,
      "name": "Noah",
      "value": 37.02
    }
  ],
  "drawOn": null
}
```

### Purchase a Ticket
Purchases a ticket by providing a name.

#### Method
```java
TicketWS purchaseTicket(@RequestParam String name)
```

#### Endpoint :: POST
http://localhost:8080/lottery/purchaseTicket?name=Eduardo

##### Payload
None

##### Result Example
```json
{
  "number": 21,
  "name": "Eduardo",
  "drawOn": "2017-02-01"
}
```

### Start a Draw
Starts a draw for the current month.

#### Method
```java
LotteryDrawWS startDraw()
```

#### Endpoint :: POST
http://localhost:8080/lottery/startDraw

##### Payload
None

##### Result Example
```json
{
  "drawOn": "2017-01-22",
  "prize": 228.3,
  "combination": [
    45,
    2,
    36
  ]
}
```


### Start a Draw for Date
Starts a draw for a month of a given date.

#### Method
```java
LotteryDrawWS startDrawForDate(Date drawOn)
```

#### Endpoint :: POST
http://localhost:8080/lottery/startDrawForDate?drawOn=2017-02-22

##### Payload
None

##### Result Example
```json
{
  "drawOn": "2017-02-22",
  "prize": 228.3,
  "combination": [
    45,
    2,
    36
  ]
}
```