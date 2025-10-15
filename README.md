# WiproTalentNext2025Solutions

This repository contains practice solutions and small projects for Java fundamentals, OOPs, JDBC, multithreading, collections, I/O, XML, and more.

## Top-level folders
- [01_Java_Fundamentals/](01_Java_Fundamentals/) — assignments and mini projects for core Java basics.
- [03_OOPS_Inheritance/](03_OOPS_Inheritance/) — inheritance assignments and mini projects.
- [05_Wrapper_Classes/](05_Wrapper_Classes/) — wrapper class exercises.
- [06_IO_Streams/](06_IO_Streams/) — I/O stream tasks and mini projects.
- [07_Collection/](07_Collection/) — collection framework assignments.
- [abstraction_packages_exception_handling/](abstraction_packages_exception_handling/) — abstraction, packages, and exception handling exercises.
- [HTML/](HTML/) — HTML examples (layout tags, tables, forms).
- [JDBC/](JDBC/) — JDBC tasks and examples.
- [Junit/](Junit/) — JUnit learning and examples.
- [MultiThreading/](MultiThreading/) — multithreading tasks and simulations.
- [XML1 & XML2/](XML1 & XML2/) — XML examples and stylesheets.

## Notable files
- JDBC tasks:
  - [`com.hr.MainApplication`](JDBC/jdbc_tasks/src/com/hr/MainApplication.java) — entry point for the JDBC examples. ([JDBC/jdbc_tasks/src/com/hr/MainApplication.java](JDBC/jdbc_tasks/src/com/hr/MainApplication.java))
  - [`com.hr.DbConnection`](JDBC/jdbc_tasks/src/com/hr/DbConnection.java) — edit DB connection details here. ([JDBC/jdbc_tasks/src/com/hr/DbConnection.java](JDBC/jdbc_tasks/src/com/hr/DbConnection.java))
  - [`com.hr.Task_Startup`](JDBC/jdbc_tasks/src/com/hr/Task_Startup.java) — inspects EMPLOYEES metadata and data. ([JDBC/jdbc_tasks/src/com/hr/Task_Startup.java](JDBC/jdbc_tasks/src/com/hr/Task_Startup.java))
  - Project README: [JDBC/jdbc_tasks/README.md](JDBC/jdbc_tasks/README.md)
- Multithreading README: [MultiThreading/JavaMultithreadingAssignments/README.md](MultiThreading/JavaMultithreadingAssignments/README.md)
- XML sample: [XML1 & XML2/Student.xml](XML1 & XML2/Student.xml) (uses `Rule.xsl`) and [XML1 & XML2/Rule.xml](XML1 & XML2/Rule.xml)

## How to build & run
- Java projects: from a project folder (e.g., `MultiThreading/JavaMultithreadingAssignments/`) run:
  ```sh
  javac -d . src/com/your/package/**/*.java
  java com.your.package.MainClass
  ```
- JDBC: place appropriate `ojdbcX.jar` in `JDBC/jdbc_tasks/lib/`, update connection details in [`com.hr.DbConnection`](JDBC/jdbc_tasks/src/com/hr/DbConnection.java), compile and run [`com.hr.MainApplication`](JDBC/jdbc_tasks/src/com/hr/MainApplication.java).

## Notes
- Clean up XML whitespace if needed (e.g., in `XML1 & XML2/Student.xml`) and ensure `<?xml-stylesheet?>` href matches `Rule.xsl`.
- Add or update driver JARs in `JDBC/jdbc_tasks/lib/` before running JDBC examples.

## Contact
This README was generated from the project folder structure and key files present in the workspace.