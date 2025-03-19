# Invoices Management System

## Features
- Create, view, and manage invoices
- Pay invoices partially or fully
- Process overdue invoices with late fees
- RESTful API endpoints for all operations

## Technology Stack
- **Languages**: Java
- **Frameworks**: Spring Boot
- **Build Tool**: Maven
- **Database**: PostgreSQL
- **Containerization**: Docker, Docker Compose

## Backend
The backend is built using Spring Boot and provides RESTful APIs for managing invoices. It uses PostgreSQL as the database and is containerized using Docker.

### Steps to Clone the Repository
1. Open your terminal.
2. Clone the repository:
    ```sh
    git clone https://github.com/your-username/invoices-management-system.git
    ```
3. Navigate to the project directory:
    ```sh
    cd invoices-management-system
    ```

### Steps to Setup the Backend
1. Ensure you have Docker and Docker Compose installed on your machine. 
2. Build and start the Docker containers:
    ```sh
    docker-compose up --build
    ```
3. The application will be available at `http://localhost:8080`.

## Usage
You can use the following HTTP requests to interact with the application:

- **Create an Invoice**:
    ```http
    POST http://localhost:8080/invoices
    Content-Type: application/json

    {
      "amount": 600.0,
      "dueDate": "2025-03-12"
    }
    ```

- **Get All Invoices**:
    ```http
    GET http://localhost:8080/invoices
    ```

- **Pay an Invoice**:
    ```http
    POST http://localhost:8080/invoices/{id}/payments
    Content-Type: application/json

    {
      "amount": 200.0
    }
    ```

- **Process Overdue Invoices**:
    ```http
    POST http://localhost:8080/invoices/processoverdue?lateFee=500.0&overdueDays=10
    ```

## Contribution
Contributions are welcome! Please follow these steps to contribute:

1. Fork the repository.
2. Create a new branch:
    ```sh
    git checkout -b feature-branch
    ```
3. Make your changes and commit them:
    ```sh
    git commit -m "Description of changes"
    ```
4. Push to the branch:
    ```sh
    git push origin feature-branch
    ```
5. Open a pull request on GitHub.

Thank you for contributing!
