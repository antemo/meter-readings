meter-readings test project
---
Test project used to provide data read from remote meters

### Initial setup

Application uses Java 14 and Maven 3.6.3 which should be installed on system. Current application configuration expects PostgreSQL database `meter` to be available at `localhost:5432` with user
 `postgres` having password `postgres`, but all this can be changed in `application.yml` configuration file.

### Running application

Application can be started using following command:
>mvn spring-boot:run

Initial data for testing is preloaded in database using migrations scripts during first application startup. Examples of requests can be found in Postman collection in `config` folder.