[![CI](https://github.com/OKaluzny/servletApp/actions/workflows/ci.yml/badge.svg)](https://github.com/OKaluzny/servletApp/actions/workflows/ci.yml)
![Java](https://img.shields.io/badge/Java-17-orange?logo=openjdk&logoColor=white)
![Jakarta EE](https://img.shields.io/badge/Jakarta_Servlet-6.0-blue?logo=jakartaee&logoColor=white)
![JSP](https://img.shields.io/badge/JSP-3.1-blue)
![PostgreSQL](https://img.shields.io/badge/PostgreSQL-15-336791?logo=postgresql&logoColor=white)
![WildFly](https://img.shields.io/badge/WildFly-29-yellow?logo=jboss&logoColor=white)
![Maven](https://img.shields.io/badge/Maven-3-C71A36?logo=apachemaven&logoColor=white)
![Docker](https://img.shields.io/badge/Docker_Compose-2496ED?logo=docker&logoColor=white)
![JUnit](https://img.shields.io/badge/JUnit-6-25A162?logo=junit5&logoColor=white)

# servletApp

Навчальний проєкт на Java, який демонструє основні можливості **Servlet API**.
Застосунок — простий CRUD для сутності Employee (працівник) з веб-інтерфейсом на JSP.

## Що вивчається у цьому проєкті

| Технологія | Де подивитися | Що робить |
|---|---|---|
| **Servlets** | `servlet/` | Обробляють HTTP-запити (GET, POST). Кожен сервлет — окрема сторінка або дія |
| **Filters** | `filter/` | Перехоплюють запити _до_ того, як вони потраплять у сервлет. Використовуються для авторизації та логування |
| **Listeners** | `listener/` | Реагують на події: старт застосунку, створення/видалення сесії, вхідний запит |
| **JSP + JSTL** | `webapp/WEB-INF/views/` | Шаблони HTML-сторінок. JSTL (`<c:forEach>`, `<c:if>`) дозволяє використовувати цикли та умови прямо в HTML |
| **RequestDispatcher** | `ViewServlet.java` та ін. | `forward()` — передає запит із сервлета в JSP для відмальовування. `jsp:include` — вставляє спільний header/footer |
| **Error Pages** | `webapp/WEB-INF/error/` | Кастомні сторінки помилок 404 та 500, налаштовані у `web.xml` |
| **File Upload** | `FileUploadServlet.java` | Завантаження файлів через `@MultipartConfig` |
| **web.xml** | `webapp/WEB-INF/web.xml` | Дескриптор розгортання — конфігурація застосунку (параметри, error pages) |

## Структура проєкту

```
src/main/java/com/example/demo/
│
├── auth/                         # Автентифікація
│   ├── LoginServlet.java         #   Сторінка входу (GET — форма, POST — перевірка логіна)
│   └── LogoutServlet.java        #   Вихід — видаляє сесію та cookie
│
├── filter/                       # Фільтри (перехоплюють УСІ запити)
│   ├── AuthenticationFilter.java #   Перевіряє, чи авторизований користувач
│   └── RequestLoggingFilter.java #   Логує кожен запит у консоль
│
├── listener/                     # Слухачі подій
│   ├── AppContextListener.java   #   Викликається при старті/зупинці застосунку
│   ├── SessionListener.java      #   Викликається при створенні/видаленні сесії
│   └── RequestListener.java      #   Викликається при кожному HTTP-запиті
│
├── model/
│   └── Employee.java             # Модель даних (id, name, email, country)
│
├── repository/
│   └── EmployeeRepository.java   # Робота з БД через JDBC (SQL-запити)
│
├── servlet/                      # Основні сервлети
│   ├── ViewServlet.java          #   Список усіх працівників
│   ├── ViewByIDServlet.java      #   Деталі одного працівника
│   ├── SaveServlet.java          #   Створення нового працівника
│   ├── EditEmployeeServlet.java  #   Редагування працівника
│   ├── PutServlet.java           #   Збереження змін
│   ├── DeleteServlet.java        #   Видалення працівника
│   └── FileUploadServlet.java    #   Завантаження файлів
│
└── util/
    └── DatabaseCheck.java        # Перевірка підключення до БД

src/main/webapp/WEB-INF/
├── views/                        # JSP-сторінки
│   ├── header.jsp                #   Спільна шапка (підключається через jsp:include)
│   ├── footer.jsp                #   Спільний підвал
│   ├── login.jsp                 #   Форма входу
│   ├── employeeList.jsp          #   Таблиця всіх працівників
│   ├── employeeDetail.jsp        #   Картка працівника
│   ├── employeeForm.jsp          #   Форма створення/редагування
│   └── upload.jsp                #   Форма завантаження файлу
├── error/
│   ├── 404.jsp                   #   Сторінка «Не знайдено»
│   └── 500.jsp                   #   Сторінка «Помилка сервера»
└── web.xml                       # Конфігурація застосунку
```

## Як це працює (для початківців)

### Шлях запиту

```
Браузер → Filter → Servlet → Repository → БД
                       ↓
                 RequestDispatcher.forward()
                       ↓
                    JSP-сторінка → HTML → Браузер
```

1. Користувач відкриває URL, наприклад `/demo/viewServlet`
2. **Filter** перехоплює запит — перевіряє авторизацію, логує
3. **Servlet** отримує запит, звертається до **Repository** за даними з БД
4. Servlet кладе дані у `request.setAttribute()` і викликає `forward()` на JSP
5. **JSP** генерує HTML, використовуючи дані з request, і надсилає відповідь у браузер

### Авторизація

- При спробі зайти на захищену сторінку без авторизації — редирект на `/demo/loginServlet`
- Публічні сторінки (перегляд списку та деталей працівника) доступні без входу
- Для редагування, створення, видалення та завантаження файлів потрібно увійти

## Сторінки застосунку

| Сторінка | URL | Потрібен логін? |
|---|---|---|
| Список працівників | `/demo/viewServlet` | Ні |
| Деталі працівника | `/demo/viewByIDServlet?id=1` | Ні |
| Створити працівника | `/demo/saveServlet` | Так |
| Редагувати | `/demo/editEmployee?id=1` | Так |
| Видалити | `/demo/deleteServlet?id=1` | Так |
| Завантаження файлу | `/demo/uploadServlet` | Так |
| Вхід | `/demo/loginServlet` | Ні |
| Вихід | `/demo/logoutServlet` | — |

**Логін:** `admin` **Пароль:** `password`

> Облікові дані налаштовуються у файлі `web.xml` (параметри `app.auth.user` та `app.auth.password`).

## Запуск через Docker (рекомендовано)

Найпростіший спосіб — Docker піднімає і базу даних, і сервер автоматично.

### Що потрібно встановити

1. **Java 17+** — [завантажити](https://adoptium.net/)
2. **Maven** — [завантажити](https://maven.apache.org/download.cgi)
3. **Docker Desktop** — [завантажити](https://www.docker.com/products/docker-desktop/)

### Покроковий запуск

**Крок 1.** Клонуйте репозиторій:
```bash
git clone https://github.com/OKaluzny/servletApp.git
cd servletApp
```

**Крок 2.** Зберіть проєкт (створить файл `target/demo.war`):
```bash
mvn clean package
```

**Крок 3.** Запустіть контейнери:
```bash
docker-compose up -d
```

Ця команда підніме два контейнери:
- **PostgreSQL** — база даних на порту `5432`
- **WildFly** — сервер застосунків на порту `8080`

**Крок 4.** Зачекайте ~15 секунд і перевірте, що все запустилося:
```bash
docker-compose ps
```

Обидва контейнери мають бути у статусі `Up`.

**Крок 5.** Відкрийте у браузері:
```
http://localhost:8080/demo/viewServlet
```

> Або скористайтеся скриптом, який виконає кроки 2-4 автоматично:
> ```bash
> # Linux/Mac
> ./docker-build.sh
>
> # Windows
> docker-build.bat
> ```

### Корисні команди Docker

```bash
# Переглянути логи сервера (Ctrl+C щоб вийти)
docker-compose logs -f wildfly

# Зупинити все
docker-compose down

# Перезібрати після змін у коді
mvn clean package && docker-compose restart wildfly
```

## Запуск без Docker (вручну)

Якщо Docker не встановлено, можна налаштувати все вручну.

### Що потрібно встановити

1. **Java 17+**
2. **Maven**
3. **PostgreSQL** — [завантажити](https://www.postgresql.org/download/)
4. **WildFly 29** — [завантажити](https://www.wildfly.org/downloads/)

### Крок 1. Створіть базу даних

Відкрийте термінал PostgreSQL (`psql`) і виконайте:

```sql
CREATE DATABASE employee;

\c employee;

CREATE TABLE IF NOT EXISTS public.users
(
    id      SERIAL PRIMARY KEY,
    name    VARCHAR(255),
    email   VARCHAR(255),
    country VARCHAR(255)
);
```

### Крок 2. Зберіть проєкт

```bash
mvn clean package
```

### Крок 3. Запустіть WildFly

Перейдіть до теки WildFly та запустіть сервер:

```bash
cd wildfly-29.0.1.Final/bin
./standalone.sh     # Linux/Mac
standalone.bat      # Windows
```

### Крок 4. Розгорніть застосунок

В іншому терміналі, з теки проєкту:

```bash
mvn org.wildfly.plugins:wildfly-maven-plugin:4.2.0.Final:deploy
```

### Крок 5. Відкрийте у браузері

```
http://localhost:8080/demo/viewServlet
```

## Підключення IDE до бази даних

Щоб IDE підказувала SQL-запити, підключіть її до PostgreSQL.

### IntelliJ IDEA

1. **View** → **Tool Windows** → **Database**
2. Натисніть **+** → **Data Source** → **PostgreSQL**
3. Заповніть:
   - **Host:** `localhost`
   - **Port:** `5432`
   - **Database:** `employee`
   - **User:** `postgres`
   - **Password:** `postgres`
4. Натисніть **Test Connection**, потім **OK**

### Інші IDE

Використовуйте JDBC URL:
```
jdbc:postgresql://localhost:5432/employee
```
Логін: `postgres`, пароль: `postgres`

## Технології

- Java 17
- Jakarta Servlet API 6.0
- JSP 3.1 + JSTL 3.0
- PostgreSQL 15
- WildFly 29
- Maven
- Docker Compose
- JUnit 6
