# Technologies
- Java
- Spring Boot
- H2
- Maven
- Junit
- Docker
# How to Build and Run
## Step1 
  Download the project to your local machine.
## Step2 
  Navigate into the project directory from your terminal.
## Step3
  Type the following commands in your terminal to build and run.
```
$ ./mvnw clean install
$ ./mvnw spring-boot:run
```
  If you wish to run on Docker, ensure you have a local Docker installed and running.
  
  Type the following commands to build and run
```
$ ./mvnw spring-boot:build-image
$ docker run -it -p8080:8080 appointment:0.0.1-SNAPSHOT
```
# APIs
## GET /appointments
Sample Response:
```
[{
    "id": 1,
    "customer": "TBD",
    "carVIN": "TBD",
    "price": 0.0,
    "time": "2021-08-01T16:36:44Z",
    "status": "New"
}]
```
## GET /appointments/{id}
Sample Response:
```
{
    "id": 1,
    "customer": "TBD",
    "carVIN": "TBD",
    "price": 0.0,
    "time": "2021-08-01T16:36:44Z",
    "status": "New"
}
```

## GET /appointments/{dateFrom}/{dateTo}
Date Format: yyyy-MM-dd

## POST /appointments
Sample Request:
```
[{
    "id": 1000,
    "customer": "TBD",
    "carVIN": "TBD",
    "price": 0.0,
    "time": "2021-08-01T16:36:44Z",
    "status": "New"
}]
```
## PUT /appointments
Sample Request:
```
{
    "id": 1,
    "customer": "John",
    "carVIN": "DAGHEOU32532FDS",
    "price": 120.0,
    "time": "2021-08-01T16:36:44Z",
    "status": "New"
}
```
## PUT /appointments/{id}/{status}

## DELETE /appointments/{id}
## DELETE /appointments
Sample Request: [1, 2, 3]
