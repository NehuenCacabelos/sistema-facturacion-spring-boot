# Sistema de Facturación con Spring Boot

Sistema de gestión de clientes, productos y facturación desarrollado con **Spring Boot 3**, **Java 17**, **MySQL** y **Thymeleaf**.

## 🚀 Tecnologías Utilizadas

- **Java 17**
- **Spring Boot 3.4.x**
  - Spring Data JPA / Hibernate
  - Spring Web (REST Controllers & MVC)
  - Thymeleaf
- **MySQL 8.0**
- **Docker & Docker Compose**
- **Maven**

## 📋 Características

- Gestión de Clientes (CRUD y paginación).
- Gestión de Productos y catálogo.
- Creación y gestión de Facturas con ítems y cálculo de totales.
- Endpoints REST para consumo externo / APIs.
- Vistas web dinámicas integradas con Thymeleaf.

## 🛠️ Ejecución Local

### 1. Iniciar la base de datos (Docker Compose)

En la raíz del proyecto, ejecuta:

```bash
docker compose up -d
```

Esto levantará una instancia de MySQL en el puerto `3306` con la base de datos `db_springboot`.

### 2. Ejecutar la aplicación

Ingresa al directorio de la aplicación y ejecuta:

```bash
cd demo
./mvnw spring-boot:run
```

La aplicación iniciará en `http://localhost:8080`.

