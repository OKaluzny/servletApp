# Multi-stage build for Maven application
FROM maven:3.9.4-openjdk-17 AS build

# Set working directory
WORKDIR /app

# Copy pom.xml and download dependencies
COPY pom.xml .
RUN mvn dependency:go-offline -B

# Copy source code and build
COPY src ./src
RUN mvn clean package -DskipTests

# Runtime stage - use WildFly
FROM quay.io/wildfly/wildfly:29.0.1.Final-jdk17

# Copy the built WAR file to WildFly deployments directory
COPY --from=build /app/target/demo.war /opt/jboss/wildfly/standalone/deployments/

# Create admin user for WildFly management
RUN /opt/jboss/wildfly/bin/add-user.sh admin admin --silent

# Expose ports
EXPOSE 8080 9990

# Start WildFly
CMD ["/opt/jboss/wildfly/bin/standalone.sh", "-b", "0.0.0.0", "-bmanagement", "0.0.0.0"]