# servletApp



Для того что бы запустить это приложение, необходимо:

1. Сделать Fork данного проекта (приоритет) или клонировать репозиторий

```bash
git clone https://github.com/OKaluzny/servletApp.git
```

2. Собрать это приложение с помощью maven 

```bash
mvn clean install -Dmaven.plugin.validation=VERBOSE
```
3. Скачать и установить WildFly https://www.wildfly.org/. Запустить WildFly. Перейти в каталог /bin и вызвать standalone
   далее задеплоить приложение с помощью команды

```bash
mvn org.wildfly.plugins:wildfly-maven-plugin:4.2.0.Final:deploy
```
4. Скачать и установить клиент запросов Postman
   
5. Скачать и установить базу данных PostgreSQL, создать базу данных - Employee
```bash

DROP DATABASE Employee;

CREATE DATABASE Employee;

USE Employee;
CREATE TABLE if not exists public.users
(
    id      serial 
            primary key,
    name    varchar(255),
    email   varchar(255),
    country varchar(255)
);
```
