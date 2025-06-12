# 📦 Microservicio de Productos

Microservicio encargado de la gestión de productos. Forma parte de un sistema distribuido que se comunica bajo el estándar [JSON:API](https://jsonapi.org). Expone endpoints para consultar y registrar productos, documentados con Swagger, protegidos por API Key, y contiene health check y pruebas automatizadas.

---

## ⚙️ Tecnologías

- **Java 17**
- **Spring Boot 3**
- **Maven**
- **RESTful API (JSON:API)**
- **Swagger / OpenAPI 3**
- **Autenticación por API Key**
- **Pruebas: JUnit 5 + Mockito**
- **Docker**

---

## 🚀 Endpoints

### Base URL

http://localhost:8081/api/products

### API pública

| Método | Endpoint         | Descripción                          |
|--------|------------------|--------------------------------------|
| GET    | `/api/products`  | Listar todos los productos           |
| POST   | `/api/products`  | Crear un nuevo producto              |
| GET    | `/api/products/{id}` | Listar producto por id|
| GET    | `/health`        | Health check manual                  |

> ⚠️ Todos los endpoints requieren el header:  
> `X-API-KEY: my-secret-key`

---
# 🧪 Pruebas
```bash
mvn clean test
```

---

## 🔐 Seguridad por API Key

La autenticación se realiza mediante una API Key enviada en el header `X-API-KEY`.  
La clave actual está configurada en `application.yml`:

```yaml
api:
  key: my-secret-key
```
---

# Productos Service

Servicio REST para gestión de productos desarrollado con Spring Boot.

## 📄 Documentación Swagger

Una vez el servicio está corriendo:
* UI Swagger: 👉 `http://localhost:8081/swagger-ui.html`
* OpenAPI JSON: 👉 `http://localhost:8081/v3/api-docs`

## 🩺 Health Check

* Endpoint: `GET /health`
* Respuesta esperada:

```json
{
  "status": "UP",
  "timestamp": "2025-06-11T20:00:00Z"
}
```

## 🐳 Docker

### Construir imagen

```bash
docker build -t productos-service .
```

### Ejecutar contenedor

```bash
docker run -p 8080:8080 productos-service
```

## 🧭 Cómo ejecutar localmente

```bash
mvn spring-boot:run
```

O compilar y correr el jar:

```bash
mvn clean package
java -jar target/productos-*.jar
```

## 📦 Estructura del proyecto

```
src/
├── main/
│   ├── java/com/linktic/productos
│   │   ├── controller
│   │   ├── dto
│   │   ├── service
│   │   │   │──Impl  
│   │   ├── entity
│   │   └── config
│   │   └── exception
│   └── resources
│       ├── application.yml
│       └── ...
└── test/
    └── java/com/linktic/productos
```

## 🧠 Notas adicionales

* Se sigue el estándar JSON:API en todas las respuestas.
* La validación de entrada se maneja con `@Valid`.
* La documentación Swagger se genera automáticamente con `springdoc-openapi`.

## 👨‍💻 Autor

Desarrollado por [Jose Mulato]  
Email: jose.mulato@linktic.com  
GitHub: @jose.mulato