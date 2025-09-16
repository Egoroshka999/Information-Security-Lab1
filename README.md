## Описание проекта

Учебный проект по информационной безопасности на **Spring Boot (Kotlin)**.  
Реализован REST API с регистрацией, аутентификацией через JWT и базовой моделью пользователей.

**Основные возможности:**
- Регистрация и логин пользователей
- Генерация JWT-токенов
- Защита всех эндпоинтов кроме `/api/auth/login` и `/api/auth/register`
- Взаимодействие с PostgreSQL через Hibernate/JPA

---

## API

| Метод | Эндпоинт         | Описание                                          | Доступ           |
|-------|------------------|---------------------------------------------------|----------------|
| POST  | `/auth/register` | Регистрация нового пользователя                   | Public         |
| POST  | `/auth/login`    | Логин и получение JWT                             | Public         |
| GET   | `/api/data`      | Получение списка зарегистрированных пользователей | Authenticated  |

---
 
## Реализованные меры защиты
### SQL Injection (SQLi)

 - Используется Hibernate (JPA) → запросы параметризованы.
 - Нет конкатенации строк.
 - Репозитории (UserRepository) безопасно обращаются к базе.

### XSS (Cross-Site Scripting)

Все выводимые данные экранируются через:

```kotlin
HtmlUtils.htmlEscape(u.username)
```

Вредоносный ввод (\<script\>) не выполнится на клиенте.

### Broken Authentication

Аутентификация через JWT-токены:
 - При логине генерируется токен (JwtUtil).
 - Все защищённые эндпоинты проверяют токен (JwtFilter).
 - Пароли хранятся только в хешированном виде:
 - Используется BCrypt (PasswordEncoder).

---

## CI/CD и безопасность

### Проект настроен на GitHub Actions:
 - Build – сборка проекта
 - SAST (SpotBugs) – статический анализ кода.
   - Отчёт: build/reports/spotbugs/spotbugs.html.
 - SCA (OWASP Dependency-Check) – проверка зависимостей на CVE. 
   - Отчёт: build/reports/dependency-check-report.html.

---

## Отчеты анализа безопасности

- [SpotBugs Report](reports/spotbugs.html)
- [Dependency-Check Report](reports/dependency-check-report.html)