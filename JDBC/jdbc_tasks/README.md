# JDBC Tasks Project

## Overview
This project is designed to demonstrate the use of Java Database Connectivity (JDBC) for interacting with a database. It includes various tasks such as establishing a database connection, executing SQL queries, and managing data sorting and restrictions.

## Project Structure
```
jdbc_tasks
├── src
│   └── com
│       └── hr
│           ├── MainApplication.java
│           ├── DbConnection.java
│           ├── Task_Startup.java
│           ├── Task_SelectStatement.java
│           └── Task_RestrictingSorting.java
├── lib
│   └── ojdbcX.jar
└── README.md
```

## Files Description
- **MainApplication.java**: The entry point of the application that initializes and manages the execution flow.
- **DbConnection.java**: Contains methods for establishing a connection to the database.
- **Task_Startup.java**: Handles startup tasks such as initializing resources and loading configurations.
- **Task_SelectStatement.java**: Provides methods for executing SQL SELECT statements.
- **Task_RestrictingSorting.java**: Manages sorting and restricting data based on specified criteria.
- **ojdbcX.jar**: JDBC driver required for connecting to Oracle databases.

## Setup Instructions
1. Ensure you have Java Development Kit (JDK) installed on your machine.
2. Download the `ojdbcX.jar` file and place it in the `lib` directory.
3. Compile the Java files located in the `src/com/hr` directory.
4. Run the `MainApplication` class to start the application.

## Usage Guidelines
- Modify the database connection details in `DbConnection.java` to connect to your database.
- Use the provided classes to perform various database operations as needed.

## License
This project is licensed under the MIT License.