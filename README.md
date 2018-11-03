# Budget Manager

Just an application for managing personal budget and expenses. 
Nothing really works for now `¯\_(ツ)_/¯`

### Prerequisites

* JDK 11+
* PostgreSQL 10+

### Database setup
1. Create Postgres user **openpf**
1. Create databases **opfdb** and **opfdb_test** with **openpf** user as the owner

or
1. Provide your own user and databases
2. Change *application\*.conf* files accordingly
3. Change credentials in *build.grdale* (needed for Flyway migrations)  

### Running tests

* Run only unit test (faster): `./gradlew test` 
* Run all tests (slower): `./gradlew check`
* Run all tests with coverage: `./gradlew check jacocoTestReport`

### Build and run
* Run: `./gradlew bootRun`
* Build UberJAR: `./gradlew bootJar`  

### Flyway
All Flyway commands are available as Gradle tasks and will affect dev database, 
except the **flywayCleanTestDB** task, which will clean a test database.

Migrations are run automatically on application startup.  