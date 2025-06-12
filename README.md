# 🧩 Sistema de Gestión de Inventario y Productos

Este proyecto está compuesto por **dos microservicios** desarrollados en Java 17 con Spring Boot 3:
- 🛍️ **Productos Service**: Gestión de productos.
- 📦 **Inventario Service**: Control de inventario y eventos de stock.

Ambos servicios se comunican siguiendo el estándar [JSON:API](https://jsonapi.org) y se documentan con Swagger/OpenAPI.

---

## ⚙️ Tecnologías

- Java 17
- Spring Boot 3
- Maven
- Docker
- Swagger / OpenAPI 3
- API Key (autenticación)
- JUnit 5 + Mockito
- Kafka (solo en `inventario`)
- Jacoco (mínimo 80% de cobertura obligatoria)

---

## 🚀 Endpoints Principales

### 📍 Productos (`localhost:8081`)

| Método | Endpoint              | Descripción                  |
|--------|------------------------|------------------------------|
| GET    | `/api/products`        | Listar todos los productos   |
| POST   | `/api/products`        | Crear un nuevo producto      |
| GET    | `/api/products/{id}`   | Obtener un producto por ID   |
| GET    | `/health`              | Health check manual          |

### 📍 Inventario (`localhost:8082`)

| Método | Endpoint                  | Descripción                     |
|--------|---------------------------|---------------------------------|
| PUT    | `/api/inventory/{id}`     | Actualizar inventario           |
| POST   | `/api/inventory`          | Crear inventario                |
| GET    | `/api/inventory/{id}`     | Obtener inventario por producto |
| GET    | `/api/inventory/purchase` | Realizar Compra                 |
| GET    | `/health`                 | Health check manual             |

> 🔐 **Todos los endpoints requieren el header:**  
> `X-API-KEY: my-secret-key`

---

## 🧪 Pruebas Automatizadas

### ✅ Ejecutar pruebas con validación de cobertura

```bash
./run_with_tests.sh
```

Este script:
- Compila y ejecuta los tests de ambos microservicios.
- Valida que Jacoco tenga al menos un 80% de cobertura.
- Si no se cumple, la build falla.

Puedes ver el reporte de cobertura abriendo este archivo en tu navegador:
```
productos/target/site/jacoco/index.html
inventario/target/site/jacoco/index.html
```

---

## 🐳 Docker

Asegúrate de tener Docker Desktop abierto antes de ejecutar los scripts.

### 🔄 Levantar servicios

```bash
./start.sh
```

Este comando construye y levanta ambos microservicios (productos e inventario) con Docker Compose.

---

## 📄 Documentación Swagger

Una vez los servicios están corriendo:

| Servicio   | Swagger UI                                    | OpenAPI JSON                              |
|------------|-----------------------------------------------|-------------------------------------------|
| Productos  | http://localhost:8081/swagger-ui.html         | http://localhost:8081/v3/api-docs         |
| Inventario | http://localhost:8082/swagger-ui.html         | http://localhost:8082/v3/api-docs         |

---

## 🧭 Ejecutar manualmente

### Productos
```bash
cd productos
mvn spring-boot:run
```

### Inventario
```bash
cd inventario
mvn spring-boot:run
```

---

## 📦 Estructura del proyecto

```
├── productos/
│   └── src/main/java/com/linktic/productos
│       ├── controller
│       ├── dto
│       ├── service
│       ├── entity
│       ├── config
│       └── exception
│
├── inventario/
│   └── src/main/java/com/linktic/inventario
│       ├── controller
│       ├── dto
│       ├── service
│       ├── entity
│       ├── kafka
│       ├── config
│       └── exception
```

---

## 🧠 Notas Adicionales

- Las respuestas siguen el estándar JSON:API.
- Swagger generado automáticamente con springdoc-openapi.
- Todos los endpoints están protegidos por API Key (X-API-KEY).
- Los eventos de cambio de inventario se publican a Kafka (en inventario).
- Los scripts `run_with_tests.sh` y `start.sh` automatizan las pruebas y despliegue local.

---

## 👨‍💻 Autor

**Desarrollado por Jose Mulato**
- 📧 jose.mulato@linktic.com
- 💻 GitHub: @jose.mulato