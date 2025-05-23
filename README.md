````markdown
# Demonstrating Entity Relationships in JPA

This project demonstrates how to implement and work with various entity relationships in Java using JPA (Java Persistence API) with Spring Boot. It covers practical examples of common relationship types like `@OneToOne`, `@OneToMany`, `@ManyToOne`, and `@ManyToMany`.

## 🚀 Technologies Used

- Java 17
- Spring Boot 3.1.0
- Spring Data JPA
- H2 In-Memory Database
- Maven

## 🔗 Entity Relationships Covered

- **One-to-One (`@OneToOne`)**
- **One-to-Many (`@OneToMany`)**
- **Many-to-One (`@ManyToOne`)**
- **Many-to-Many (`@ManyToMany`)**

Each of these is demonstrated with proper mapping, cascading, and fetching strategies where applicable.

## 🛠️ Getting Started

### Prerequisites

- Java 17 installed
- Maven installed
- IDE like IntelliJ IDEA or Eclipse

### Installation & Run

1. Clone the repository:
   ```bash
   git clone https://github.com/karanbadhwar/demonstrating-entity-relation.git
````

2. Navigate to the project directory:

   ```bash
   cd demonstrating-entity-relation
   ```

3. Build the project:

   ```bash
   mvn clean install
   ```

4. Run the application:

   ```bash
   mvn spring-boot:run
   ```

### H2 Console

* Access it at: `http://localhost:8080/h2-console`
* JDBC URL: `jdbc:h2:mem:testdb`
* Username: `sa`
* Password: *(leave blank)*

## 📋 Features

* Demonstrates real-world mapping of entities.
* Uses in-memory H2 database for quick testing.
* Logs SQL queries to the console.
* Easy to extend and experiment with.

## 🧪 Useful Spring Boot Properties

```properties
spring.h2.console.enabled=true
spring.datasource.url=jdbc:h2:mem:testdb
spring.jpa.show-sql=true
spring.jpa.properties.hibernate.format_sql=true
logging.level.org.hibernate.SQL=DEBUG
logging.level.org.hibernate.type.descriptor.sql.BasicBinder=TRACE
```

## 🤝 Contributing

Pull requests are welcome. For major changes, please open an issue first to discuss what you would like to change.

