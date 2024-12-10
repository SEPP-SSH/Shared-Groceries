# Dependencies
---
See below for an explanation of the key dependencies used by the SSH Cloud product prototype, and what role each dependency fulfils in the project:

| Dependency | Role |
| ---------- | ---- |
| Hibernate | Hibernate is being used for Object/Relational Mapping (ORM) to simplify implementing the database within the Java project, allowing the team to avoid writing _both_ class definitions for each object that corresponds to a table in the database and SQL commands for database management, getting us to the demonstration of project viabilitiy quicker. |
| MySQL | MySQL is the database server platform being used to implement the datbaase for SSH Cloud. |
| JUnit | JUnit is the unit testing platform for SSH Cloud, and is also exploited in a client-server context to run integration tests for the SSH Cloud server, its corresponding database sever, and a client instance. |
| Javax Persistence API | The Javax Persistence API is being used, in conjunction with Hibernate, to utilise ORM (as described above). |
| Lombok | Lombok is being used as an annotation processor for the ORM-related annotations in the prototype. |