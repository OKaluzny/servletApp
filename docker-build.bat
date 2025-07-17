@echo off

echo Building Maven project...
mvn clean package

echo Building Docker images and starting services...
docker-compose up --build -d

echo Waiting for services to be ready...
timeout /t 30 /nobreak

echo Services status:
docker-compose ps

echo.
echo Application should be available at:
echo - Web Application: http://localhost:8080/demo
echo - WildFly Admin Console: http://localhost:9990 (admin/admin)
echo - PostgreSQL: localhost:5432 (postgres/postgres)
echo.
echo To view logs: docker-compose logs -f
echo To stop services: docker-compose down

pause