# cloud-storage (Still in development)

[EN](#english-version) | [RU](#русская-версия)

## English Version

## Description

Cloud file storage. It's a multiuser web-application on Spring Boot. Made
to proceed basic operations with files and folders in cloud-storage: Upload,
rename, delete

## Sequence diagram

![Sequence diagram](docs/sequence-diagram.png)

## Component diagram

![Component diagram](docs/component-diagram.png)

## End-points

![End-points](docs/end-points.png)

## Tech stack

### Back-end:

- Java 17
- Spring Boot 3.2.5:
    * Web
    * Security
    * Session
    * Data JPA
- Thymeleaf
- PostgreSQL

### Front-end:

- HTML
- CSS
- Bootstrap 4
- JavaScript

### Testing:

- JUnit 5
- Testcontainers
- Mockito

### Deploy

- Docker

## Implementation

DDD pattern

## Installation and Running

1. Clone the repository: git clone https://github.com/Darfik43/cloud-storage
2. Open Terminal 
3. cd [path-to-repository]/cloud-storage
4. docker compose build 
5. docker compose up 
6. Open browser and go localhost:8080/

## Authors

Author: Darfik43

## Contact

darfik43@gmail.com

## Русская версия

# cloud-storage

## Описание

Облачное хранилище файлов. Многопользовательское веб-приложение построенное с
использованием
Spring boot. Обладает всей базовой функциональностю облака:
загрузить, переименовать, удалить

## Диаграмма последовательности

![Sequence diagram](docs/sequence-diagram.png)

## Диаграмма компонентов

![Component diagram](docs/component-diagram.png)

## Энд-поинты API

![End-points](docs/end-points.png)

## Стэк технологий

### Back-end:

- Java 17
- Spring Boot 3.2.5:
    * Web
    * Security
    * Session
    * Data JPA
- Thymeleaf
- JUnit
- PostgreSQL

### Front-end:

- HTML
- CSS
- Bootstrap 4
- JavaScript

### Testing:

- JUnit 5
- Testcontainers
- Mockito

### Deploy

- Docker

## Реализация

Шаблон DDD

## Установка и запуск

1. Клонировать репозиторий с проектом: git clone https://github.com/Darfik43/cloud-storage
2. Открыть терминал
3. cd [path-to-repository]/cloud-storage
4. docker compose build
5. docker compose up
6. Приложение доступно на localhost:8080/

## Автор

Darfik43

## Контакты

darfik43@gmail.com
