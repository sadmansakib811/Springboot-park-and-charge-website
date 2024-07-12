<<<<<<< HEAD
# Park and Charge Station By Infinite Loopers

## Project Overview

The Station and Booking Management System is a microservice-based application designed to manage electric vehicle (EV) charging stations and their bookings. It allows users to register as car owners or station owners, create and manage charging stations, and handle bookings. The system was developed using Spring Boot, Spring MVC, REST, and Thymeleaf for the user interface.


## Technologies Used

- **Spring Boot**: For creating microservices.
- **Spring MVC**: For handling web requests and responses.
- **REST**: For communication between microservices.
- **Thymeleaf**: For creating the user interface.
- **H2 Database**: For storing data.
- **Resilience4j**: For implementing circuit breaker patterns.
- **Jackson**: For handling JSON data.
- **JUnit**: For testing the application.
- **Maven**: For project management and dependency management.
- **Eureka**: Netflix Service Discovery Server and Client
- **Spring-cloud-config-server**: For getting the config from Cloud like GitHub public repo

## Microservices

### User Service

- Handles user registration, authentication, and management.

### Station Service

- Manages the creation, editing, deletion, and listing of EV charging stations.

### Booking Service

- Handles the creation, management, and listing of bookings for EV charging stations.

### Eureka Server

- Provides service discovery, allowing microservices to find and communicate with each other.

### Config Server

- Provides centralized configuration management for all microservices.

## Setup Instructions

### Prerequisites

- Java 17
- Maven
- Git

### Clone the Repository



git clone <repository-url>



# Build and Run Each Microservice
## Navigate to each microservice directory and build the project using Maven:
### Config-server



cd config-server
mvn spring-boot:run



### Eureka-server



cd eureka-server
mvn spring-boot:run



### User-service



cd user-service
mvn clean install
mvn spring-boot:run



### Station-service



cd station-service
mvn clean install
mvn spring-boot:run



### Booking-service



cd booking-service
mvn clean install
mvn spring-boot:run



### Accessing the Services



* User Service: Runs on http://localhost:8081/login
* Station Service: Runs on http://localhost:8082/stations
* Booking Service: Runs on http://localhost:8083/bookings



## Using Mock Data

### User Service

Mock data for users is preloaded during application startup. The following users are available:

#### Car Owner
* Username: sadman
* Password: 1234

#### Station Owner
* Username: Farhana
* Password: 1234


## Running the Application
Start the User Service: Navigate to the user-service directory and run the application.
Start the Station Service: Navigate to the station-service directory and run the application.
Start the Booking Service: Navigate to the booking-service directory and run the application.

# Testing the Application

### Eureka-Server:

* Run Eureka Server

### Config-Server:

* Run Config Server

### Logging In:

* Go to the User Service: http://localhost:8081
* Log in as Alice (Car Owner): Username: farhana, Password: 1234
* Log in as Bob (Station Owner): Username: sakib, Password: 1234

### Managing Stations:
* Go to the Station Service: http://localhost:8082/stations
* Create a new Station: Fill in the station details and submit the form.
* Edit an existing Station: Click the "Edit" button next to a station, modify the details, and save.
* Delete a Station: Click the "Delete" button next to a station.

### Managing Bookings:
* Go to the Booking Service: http://localhost:8083/bookings
* Create a new Booking: Fill in the booking details and submit the form.
* View Bookings: View the list of bookings and their details.
* Update Booking Status: Accept or reject a booking.

### User Service
Controller: Handles HTTP requests and responses.
Service: Contains business logic.
Repository: Handles data persistence.
Model: Defines the user entity.

### Station Service
Controller: Handles HTTP requests and responses.
Service: Contains business logic.
Repository: Handles data persistence.
Model: Defines the station entity.

### Booking Service
Controller: Handles HTTP requests and responses.
Service: Contains business logic.
Repository: Handles data persistence.
Model: Defines the booking entity.



## Conclusion
This project demonstrates the implementation of a microservice-based application using Spring Boot and related technologies. Both Sadman Sakib and Farhana Binta Shaheed contributed equally to the development and completion of this project. We hope this readme file helps you set up and run the application smoothly. If you encounter any issues, feel free to reach out to us.

=======
# Springboot-park-and-charge-website
The Station and Booking Management System is a SpringBoot based microservice-based application designed to manage electric vehicle (EV) charging stations and their bookings
>>>>>>> origin/main
