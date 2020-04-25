db.migration is for Flyway. Flyway is a database version control tool for Maven (our build tool)

The SQL written in these version controls, is the starter data that we may want to
have in our application at boot.

For example, we may want an ADMIN to exist if we run this application as a standard worker, or
workers to exist if we test this as a manager; and so on.

Most SQL will be written using JPA => Java Classes becoming tables (think of it that way)
