# RangeManager

## Проект написан в рамках тестового задания для поступления в ШИФТ Лаб 2024

### Автор: Ледин Даниил Александрович

## Функциональные характеристики:
1. Получать по REST API массив с интервалами цифр или букв в формате JSON, объединять все пересекающиеся интервалы и сохранять массив из непересекающихся интервалов в in-memory SQL базу данных H2.
2. Выдавать по REST API минимальный интервал в формате JSON.

## Требования к функциональным характеристикам:
1. Допускается в БД хранить пересекающиеся интервалы, если они были отправлены разными запросами. 
2. Запрос минимального интервала делать на стороне БД, то есть SQL запросом.
3. Валидация входных данных

## Использованные технологии:
* Java 17
* Gradle 8.5
* Spring boot 3
* Spring Data JPA
* Spring Web
* Swagger API
* Actuator
* H2 Database
* Flyway
* JUnit 5 и Mockito
* Lombok
* Hibernate

## Сборка, запуск и работа с приложением:
* Склонируйте репозиторий: 
  ```
  git clone https://github.com/MacIT54/range-manager.git
  ``` 
* Для сборки проекта перейдите в корневую папку и выполните команду: 
  ```
  ./gradlew build
  ```
* Для сборки и запуска: 
  ```
  ./gradlew bootRun
  ```
* Запускайте проект средствами IDE (точка входа в приложение находится в классе RangeManagerApplication), <br>
  либо командой: 
  ```
  java -jar build/libs/range-manager-0.0.1.jar
  ```
  (предварительно соберите проект командой "./gradlew build")
* Для конфигурирования используется файл application.yaml

## Endpoints:
#### POST http://localhost:8080/api/v1/intervals/merge?kind=digits - добавление числовых интервалов
#### POST http://localhost:8080/api/v1/intervals/merge?kind=letters - добавление буквенных интервалов
#### GET http://localhost:8080/api/v1/intervals/min?kind=digits - запрос минимального числового интервала
#### GET http://localhost:8080/api/v1/intervals/min?kind=letters - запрос минимального буквенного интервала

<br/>

### После запуска приложение доступно по адресу: http://localhost:8080/api/v1/intervals/
### Консоль для базы данных H2 доступна по адресу: http://localhost:8080/h2-console/
* JDBC URL: jdbc:h2:mem:rangemanager
* Login: sa
* Password: password
### Документация Swagger API доступна по адресу: http://localhost:9090/actuator/swagger-ui/index.html#/
### Postman-коллекция находится в корневой папке








