# servletApp

Для того что бы запустить это приложение, необходимо:

1. Сделать Fork данного проекта (приоритет) или клонировать репозиторий

```bash
git clone https://github.com/OKaluzny/demoServlet.git
```

2. Собрать это приложение с помощью maven 

```bash
mvn clean install
```
3. Скачать и установить WildFly https://www.wildfly.org/. Запустить WildFly. Перейти в каталог /bin и вызвать standalone
   далее задеплоить приложение с помощью команды

```bash
mvn org.wildfly.plugins:wildfly-maven-plugin:2.0.2.Final:deploy
```
4. Скачать и установить клиент запросов Postman
   
5. Скачать и установить базу данных PostgreSQL
```bash
create table users
(
    id      serial not null
        constraint users_pk
            primary key,
    name    varchar,
    email   varchar,
    country varchar
);

alter table users
    owner to postgres;
```
