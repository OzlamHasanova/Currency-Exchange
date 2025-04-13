CURRENCY EXCHANGE API

Project Description
The Currency Exchange API provides an interface for retrieving, storing, and managing official exchange rates of the Azerbaijani Manat (AZN) against foreign currencies. This project fetches exchange rate data from the Central Bank of Azerbaijan (CBA) and allows users to view exchange rates for specific dates, as well as manage the data in the system.

Features

Data Collection: Official exchange rates are fetched from the CBA XML bulletin for the selected date and stored in the database.

Data Management: If the rates for a specific date already exist in the database, no changes are made, and a corresponding response is returned.

Data Deletion: Exchange rates stored in the database can be deleted based on a specific date.

Authentication: Token-based authentication is required for importing exchange rates.

Basic Authentication: Used for retrieving AZN exchange rates.

Technologies Used

Spring Boot: For building the REST API.

Spring Security: For implementing token and basic authentication.

Spring Data JPA: For database interactions.

Flyway: For database migrations.

PostgreSQL: As the database to store exchange rates.

JUnit & Mockito: For unit testing and mocking dependencies.

Endpoints

POST /api/exchange-rates/import:
Import exchange rates for a specific date.
Authentication: Token-based.
Request Parameter: date (format: yyyy-MM-dd).

GET /api/exchange-azn/reverse-rate:
Retrieve the reverse exchange rate for a specific currency on a specific date.
Authentication: Basic Authentication.
Request Parameters: code (currency code), date (yyyy-MM-dd).

GET /api/exchange-azn/reverse-rates:
Retrieve all reverse exchange rates for a specific date.
Authentication: Basic Authentication.
Request Parameter: date (yyyy-MM-dd).

GET /api/exchange-azn/reverse-rates/by-currency:
Retrieve all reverse exchange rates for a specific currency over all available dates.
Authentication: Basic Authentication.
Request Parameter: currencyCode.


Vido Guide: https://drive.google.com/file/d/13TOO7g0GiNo3B6Pu8U97SlCf-0HwbTxW/view?usp=sharing
Mail:ozlamhasanova@gmail.com
