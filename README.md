# Technologies
- Java
- Spring Boot
- H2
- Maven
- Junit
- Docker
# How To Build And Run
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
