# Instruction
- Step1: Download the project to your local machine
- Step2: Navigate to the project directory from terminal
- Step3: Ensure you have a local Docker installed and running
- Step4: Type the following commands to build and run
>$ ./mvnw spring-boot:build-image
>
>$ docker run -it -p8080:8080 appointment:0.0.1-SNAPSHOT
