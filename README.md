# crypto-price

This is a Spring Boot application which is used to get current localized price of a cryptocurrency.

# About the Service

The service is just a REST service where user need to select one specific cryptocurrency from drop-down box and also user can provide optional value for IP adree in form of IPv4. After selecting one specific cryptocurrency user can get the current localized price of that cryptocurrency by pressing Submit button.

The localize information is determined by the IP address provided in the input and if the IP address is not provided in the input then the localized information is determined from the request.

Here is what this little application demonstrates:

- Full integration with the Spring Framework: inversion of control, dependency injection, etc.
- Packaging as a single jar with embedded container (tomcat): No need to install a container separately on the host just run using the java -jar command
- Annotations are used to writing the service; HTTP GET method is used to in the service.
- Demonstrates MockMVC test framework with associated libraries

Below is the endpoint need to call to access this service:

http://localhost:8080/

# Development

- Java 8
- Spring Boot 2.3.0.M1
- Maven 2.22.0
- Thymeleaf

# Build

- mvn clean install

# Test

- mvn test

# Run

- mvn spring-boot:run OR
- java -jar target/com.crypto-0.0.1-SNAPSHOT.jar

# Resources
   
- Cryptocurrency information : https://api.coingecko.com/api/v3/coins/markets?vs_currency={$currency}
- Localized information : https://www.iplocate.io/api/lookup/{IP Address}

# Assumptions

- For undetermined countries or country codes (ISO 3136), 'US' is used as country/country code and price is represent in USD currency.
- For undetermined currency code are (ISO 4217), USD (US dollar) is used to represent price.
- For undetermined currency symbol, replaced with currency code (ISO 4217) as prefix of the price for Cryptocurrency.
