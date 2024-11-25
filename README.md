# Study Spots Backend

## Development

- Clone this repo `git clone https://github.com/brennenho/study-spots-backend.git`
- Update mySQL credentials
  - Copy `src/main/resources/application.properties.example` to `src/main/resources/application.properties`
  - Update `spring.datasource.username` and `spring.datasource.password` with your local mySQL credentials
- Install dependencies `./mvnw clean install`
- Run `./mvnw spring-boot:run`
- Server runs on `localhost:8080`
