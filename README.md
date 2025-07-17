# servletApp

Java servlet application with PostgreSQL database support.

## Quick Start with Docker (Recommended)

The easiest way to run this application is using Docker Compose:

### Prerequisites
- Docker and Docker Compose installed
- Maven (for building)

### Running with Docker

1. Clone the repository:
```bash
git clone https://github.com/OKaluzny/servletApp.git
cd servletApp
```

2. Build and run with Docker:
```bash
# Linux/Mac
./docker-build.sh

# Windows
docker-build.bat
```

3. Access the application:
- Web Application: http://localhost:8080/demo
- WildFly Admin Console: http://localhost:9990 (admin/admin)
- PostgreSQL: localhost:5432 (postgres/postgres)

### Docker Commands
```bash
# Start services
docker-compose up -d

# View logs
docker-compose logs -f

# Stop services
docker-compose down

# Rebuild and restart
docker-compose up --build -d
```

## Manual Setup (Alternative)

To run this application manually, you need to:

1. Fork this project (preferred) or clone the repository

```bash
git clone https://github.com/OKaluzny/servletApp.git
```

2. Build this application using Maven

```bash
mvn clean install -Dmaven.plugin.validation=VERBOSE
```

3. Download and install WildFly from https://www.wildfly.org/. Start WildFly by going to the /bin directory and running standalone, then deploy the application using the command:

```bash
mvn org.wildfly.plugins:wildfly-maven-plugin:4.2.0.Final:deploy
```

4. Download and install Postman REST client

5. Download and install PostgreSQL database, create the Employee database:

```sql
DROP DATABASE IF EXISTS Employee;

CREATE DATABASE Employee;

\c Employee;

CREATE TABLE IF NOT EXISTS public.users
(
    id      SERIAL PRIMARY KEY,
    name    VARCHAR(255),
    email   VARCHAR(255),
    country VARCHAR(255)
);
```

## IDE Database Configuration

To enable SQL code assistance and eliminate IDE warnings about missing data sources, configure your IDE to connect to the PostgreSQL database:

> **Note**: If you see warnings like "No data sources are configured to run this SQL and provide advanced code assistance" in your IDE, follow the configuration steps below to resolve them.

### IntelliJ IDEA / DataGrip Configuration

1. **Using Docker (Recommended)**:
   - Start the application with Docker: `./docker-build.sh` or `docker-build.bat`
   - In IntelliJ IDEA, go to **View** → **Tool Windows** → **Database**
   - Click the **+** button and select **Data Source** → **PostgreSQL**
   - Configure the connection:
     - **Host**: `localhost`
     - **Port**: `5432`
     - **Database**: `employee`
     - **User**: `postgres`
     - **Password**: `postgres`
   - Test the connection and apply

2. **Manual PostgreSQL Setup**:
   - Install PostgreSQL locally
   - Create the database using the SQL commands above
   - Configure the data source with your local PostgreSQL credentials

### Other IDEs

For other IDEs (Eclipse, VS Code, etc.), configure a PostgreSQL connection using:
- **JDBC URL**: `jdbc:postgresql://localhost:5432/employee`
- **Username**: `postgres`
- **Password**: `postgres`

Once configured, your IDE will provide SQL syntax highlighting, code completion, and validation for database queries.
