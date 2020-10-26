# Credit Card Service
- Description - This service process credit card transactions and display the transactions.
- Design considerations - Spring boot and Java 8 has been used to write this application. Spring boot makes it easy to
 quickly write the production ready applications. Also another reason is it can be extended into a restful api which
 can be exposed to clients with minimal changes required.

## Giving input to service
- You can either enter an absolute path of the file or just the file name if it is present in the same folder as jar
- e.g. c://test/input.txt or input.txt respectively

## Running service from IDE
- Run `mvn clean package`
- Run `mvn spring-boot:run` from root of the project in IDE terminal
 
## Running service from command line or terminal
- Run `mvn clean package` from project root
- Jar is generated in `target/credit-card-service-0.0.1-SNAPSHOT.jar`
- Go to the location of the jar and run `java -jar credit-card-service-0.0.1-SNAPSHOT.jar`

## Running unit tests and code coverage
- Run `mvn clean test` or `mvn test` from root of the project in terminal
- You can see the code coverage report at /target/site/index.html
- You can view the file in any browser shown in the options