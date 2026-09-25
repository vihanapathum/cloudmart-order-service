# CloudMart — Order Service

## Project Description

Handles order placement for CloudMart. Backed by MongoDB (non-relational
database requirement). Every order placed is also written as a lightweight
event record to Firestore, so the required Firestore resource is genuinely
exercised rather than just provisioned and left empty.

## Technology Stack

- Java 25
- Spring Boot 4.0.7 (Spring Web MVC)
- Spring Data MongoDB
- Google Cloud Firestore (order audit events)
- Spring Cloud Eureka Client + Config Client
- PM2 (process management on the deployed VM)

## API

| Method | Path               | Description       |
| ------ | ------------------ | ----------------- |
| GET    | `/api/orders`      | List all orders   |
| GET    | `/api/orders/{id}` | Get one order     |
| POST   | `/api/orders`      | Place a new order |
| DELETE | `/api/orders/{id}` | Delete an order   |

## Setup / Getting Started

### Prerequisites

- Java 25 JDK, Maven 3.9+
- A MongoDB instance reachable locally, or a MongoDB Atlas connection string
- GCP Application Default Credentials for the Firestore write (optional
  locally - order creation still works and just skips the audit log if
  credentials aren't found)

### Run locally

```bash
mvn clean package
java -jar target/order-service.jar
```

## Student Information

- **Student Name:** A.G.Vihana Pathum Piyasiri
- **Student Number:** 2301692038
- **Slack Handle:** vihana_piyasiri
- **GCP Project ID:** project-f45a7f6e-0370-44ea-b74
