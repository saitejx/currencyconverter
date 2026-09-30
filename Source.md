Build a complete **Currency Converter web application using Java Full Stack**.

## 1. Project Goal

Create a simple, practical, professional currency converter that allows users to convert an amount from one currency to another using current exchange rates.

The application should demonstrate:

* Frontend development
* Java Spring Boot backend
* REST APIs
* PostgreSQL database
* External exchange-rate API integration
* CRUD/database operations

Do NOT add AI features. Keep the project focused only on currency conversion.

---

## 2. Technology Stack

### Frontend

* HTML5
* CSS3
* Vanilla JavaScript
* Fetch API
* Do not use React unless absolutely necessary

### Backend

* Java
* Spring Boot
* Spring Web
* Spring Data JPA
* REST API
* Maven

### Database

* PostgreSQL

### External API

Use a reliable exchange-rate API to obtain current currency exchange rates.

Keep the API key/base URL in `application.properties` or environment variables. Never hardcode secrets in JavaScript.

---

## 3. UI/UX Requirements

The UI must NOT look like a modern AI-generated SaaS website.

Avoid:

* Gradients
* Glassmorphism
* Neon colors
* Glowing buttons
* Excessive rounded cards
* Floating blobs
* Excessive animations
* Huge hero sections
* Excessive icons
* AI-style futuristic designs

Instead, create a simple and realistic interface similar to a banking, finance, or university application.

### Design style

Use:

* White background
* Light gray sections
* Dark navy/blue as the primary color
* Small border radius: approximately 4–6px
* Thin borders
* Minimal shadows
* Normal-sized buttons
* Clear labels
* Clean typography
* Good spacing
* Responsive layout

The application should look like it was designed by a professional student developer, not generated from an AI SaaS template.

---

## 4. Main Page

Create a homepage containing:

### Header

Display:

Currency Converter

Navigation:

* Home
* History
* Favorites

Keep the header simple.

### Currency Conversion Form

Fields:

1. Amount
2. From Currency
3. To Currency

Example:

Amount:
[ 10000 ]

From:
[ INR - Indian Rupee ▼ ]

To:
[ USD - US Dollar ▼ ]

[ Convert ]

Add a small swap button between the From and To currencies.

---

## 5. Conversion Result

After clicking Convert, display:

Converted amount:

$119.80

Exchange rate:

1 INR = 0.01198 USD

Also display:

Last updated: [date/time]

Do not display fake exchange rates.

The result should come from the backend/exchange-rate API.

---

## 6. Supported Currencies

Initially support common currencies such as:

* INR — Indian Rupee
* USD — US Dollar
* EUR — Euro
* GBP — British Pound
* JPY — Japanese Yen
* AUD — Australian Dollar
* CAD — Canadian Dollar
* AED — UAE Dirham
* SGD — Singapore Dollar
* CHF — Swiss Franc

Structure the code so additional currencies can easily be added later.

---

## 7. Backend REST APIs

Create REST endpoints such as:

GET /api/currencies

GET /api/rates

POST /api/convert

GET /api/history

DELETE /api/history

POST /api/favorites

GET /api/favorites

DELETE /api/favorites/{id}

### POST /api/convert

Request:

{
"amount": 10000,
"fromCurrency": "INR",
"toCurrency": "USD"
}

Response:

{
"amount": 10000,
"fromCurrency": "INR",
"toCurrency": "USD",
"exchangeRate": 0.01198,
"convertedAmount": 119.80,
"timestamp": "2026-09-30T20:00:00"
}

---

## 8. PostgreSQL Database

Create a database named:

currency_converter

Create a conversion_history table containing:

* id
* from_currency
* to_currency
* amount
* exchange_rate
* converted_amount
* created_at

Create a favorites table containing:

* id
* from_currency
* to_currency
* created_at

Use JPA entities and repositories.

---

## 9. Conversion History

Whenever a successful conversion is performed, save it to PostgreSQL.

Create a History page displaying:

| Date | From | To | Amount | Exchange Rate | Result |
| ---- | ---- | -- | ------ | ------------- | ------ |

Add:

* Search/filter functionality
* Clear history button
* Responsive table

Do not create unnecessary charts or analytics.

---

## 10. Favorite Currency Pairs

Allow users to save frequently used currency pairs.

Example:

Favorites

INR → USD
INR → AED
INR → GBP

Clicking a favorite pair should automatically select those currencies on the converter page.

---

## 11. Validation

Implement proper validation.

Examples:

* Amount cannot be empty
* Amount must be greater than 0
* From and To currencies cannot be the same
* Invalid currency codes should be rejected
* API failures should display a clear error message
* Network/API timeout should be handled gracefully

Display simple messages such as:

"Please enter a valid amount."

"Please select different currencies."

"Unable to retrieve exchange rates. Please try again."

---

## 12. Error Handling

Implement backend exception handling using Spring Boot.

Return appropriate HTTP status codes:

200 - Successful request
400 - Invalid input
404 - Resource not found
500 - Server/API error

Create a consistent JSON error response.

---

## 13. Project Structure

Use a clean Spring Boot structure:

src/
└── main/
├── java/
│   └── com/example/currencyconverter/
│       ├── controller/
│       ├── service/
│       ├── repository/
│       ├── entity/
│       ├── dto/
│       ├── exception/
│       └── CurrencyConverterApplication.java
│
└── resources/
├── application.properties
└── static/
├── index.html
├── history.html
├── css/
│   └── style.css
└── js/
├── app.js
└── history.js

Use proper separation between Controller, Service, Repository, Entity and DTO layers.

---

## 14. Frontend Behavior

Use JavaScript Fetch API to communicate with the Spring Boot REST APIs.

Do not put business logic directly into HTML.

The frontend should:

1. Load available currencies.
2. Allow the user to enter an amount.
3. Allow currency selection.
4. Send the conversion request to Spring Boot.
5. Display the result.
6. Save/display conversion history.
7. Handle errors gracefully.
8. Allow favorite currency pairs.

---

## 15. Responsive Design

The application must work properly on:

* Desktop
* Laptop
* Tablet
* Mobile

Do not make the mobile version overly complicated.

---

## 16. Important Development Rules

Write clean, beginner-friendly Java code.

Do not over-engineer the application.

Do not add:

* AI chatbot
* Machine learning
* Cryptocurrency features
* Complex authentication unless required
* Payment system
* Unnecessary dashboards
* Excessive animations

Focus on making the core currency conversion functionality reliable.

Use environment variables for API keys.

Include comments only where they help explain important logic.

---

## 17. What I Need From You

Build the project step by step.

First provide:

1. Complete project architecture
2. Required software/installations
3. PostgreSQL database setup
4. Spring Boot project setup
5. Maven dependencies
6. Backend code
7. PostgreSQL/JPA configuration
8. Exchange-rate API integration
9. REST API implementation
10. Frontend HTML
11. CSS
12. JavaScript
13. How frontend communicates with backend
14. How to run the complete project
15. API testing examples using Postman
16. Common errors and their solutions

Provide complete code for every required file.

Make sure all code is compatible with a current stable version of Java and Spring Boot.

Do not skip configuration files.

At the end, provide exact commands to:

* Create the PostgreSQL database
* Start the Spring Boot application
* Test the APIs
* Open the frontend
* Verify that data is being stored in PostgreSQL

The final application should be simple, clean, functional, and suitable for demonstrating a **Java Full Stack project in a college project/viva/interview**.
