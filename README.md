# mso-ventas-liverpool

Microservicio de gestión de ventas desarrollado como examen técnico para Liverpool.
Construido con **Spring Boot 4** y **Arquitectura Hexagonal (Ports & Adapters)**, expone una API REST para la gestión de **Clientes**, **Direcciones de Entrega** y **Pedidos**, con persistencia en **MongoDB**.

---

## Video de demostración

[![Ver demostración en YouTube](https://img.shields.io/badge/▶_Ver_Demo-YouTube-red?style=for-the-badge&logo=youtube)](https://www.youtube.com/watch?v=z8ZOm8tFqOk)

> El video muestra: arranque y funcionamiento de la aplicación.

![Tests](assets/miniatura_youtube.png)

---

## Tecnologías y versiones

| Tecnología | Versión |
|---|---|
| Java | 21 |
| Spring Boot | 4.0.3 |
| Spring Data MongoDB | Incluido en Spring Boot 4 |
| SpringDoc OpenAPI (Swagger) | 2.8.4 |
| Lombok | Incluido en Spring Boot 4 |
| JaCoCo | 0.8.12 |
| Checkstyle (Google Style) | 3.6.0 |
| MongoDB | 7.0 (Docker) |
| Maven Wrapper | 3.x |

---

## Prerrequisitos

Antes de clonar y ejecutar el proyecto, asegúrate de tener instalado:

| Herramienta | Versión mínima | Descarga |
|---|---|---|
| Java JDK | 21 | [adoptium.net](https://adoptium.net/) |
| Docker Desktop | Cualquier versión reciente | [docker.com](https://www.docker.com/products/docker-desktop/) |
| Git | Cualquier versión | [git-scm.com](https://git-scm.com/) |

> **MongoDB** se levanta automáticamente con Docker — no necesitas instalarlo manualmente.

---

## Levantar el proyecto paso a paso

### 1. Clonar el repositorio

```bash
git clone https://github.com/JFCI-7/mso-ventas-liverpool.git
cd mso-ventas-liverpool
```

### 2. Levantar MongoDB con Docker

```bash
docker-compose up -d
```

Esto crea un contenedor MongoDB 7.0 con:
- **Puerto**: `27017`
- **Usuario**: `admin`
- **Contraseña**: `admin123`
- **Base de datos**: `mso_ventas_db`

Verifica que el contenedor esté corriendo:

```bash
docker ps
```

### 3. Ejecutar la aplicación

```bash
./mvnw spring-boot:run
```

La aplicación arranca en `http://localhost:8080` con el perfil `dev` activo.

### 4. Verificar que funciona

Abre en tu navegador:

| Recurso | URL |
|---|---|
| Swagger UI | http://localhost:8080/swagger-ui.html |
| OpenAPI JSON | http://localhost:8080/api-docs |
| Health check | http://localhost:8080 |

---

## Ejecutar los tests

### Todos los tests + cobertura + CheckStyle

```bash
./mvnw verify
```

Este comando ejecuta en secuencia:
1. **93 tests** unitarios e integración (Mockito + MockMvc)
2. **JaCoCo** — verifica cobertura mínima del 70% de líneas
3. **Checkstyle** — valida Google Java Style (0 violaciones)

### Solo los tests

```bash
./mvnw test
```

### Solo CheckStyle

```bash
./mvnw checkstyle:check
```

### Reporte de cobertura JaCoCo

```bash
./mvnw verify
open target/site/jacoco/index.html   # macOS
# start target/site/jacoco/index.html  # Windows
```

---

## Resultados de calidad

| Métrica | Resultado |
|---|---|
| Tests ejecutados | 93 |
| Tests fallidos | 0 |
| Cobertura de líneas | ~97% (mínimo requerido: 70%) |
| Cobertura de ramas | ~91% |
| Violaciones CheckStyle | 0 |

---

## API REST — Endpoints

La URL base es `http://localhost:8080`. La documentación interactiva completa está en Swagger UI.

### Customers — `/api/v1/customers`

| Método | Endpoint | Descripción | Código éxito |
|---|---|---|---|
| `POST` | `/api/v1/customers` | Crear cliente | `201 Created` |
| `GET` | `/api/v1/customers` | Listar todos los clientes | `200 OK` |
| `GET` | `/api/v1/customers/{id}` | Obtener cliente por ID | `200 OK` |
| `PUT` | `/api/v1/customers/{id}` | Actualizar cliente | `200 OK` |
| `DELETE` | `/api/v1/customers/{id}` | Eliminar cliente | `204 No Content` |

**Ejemplo — Crear cliente:**
```json
POST /api/v1/customers
{
  "firstName": "Juan",
  "lastName": "Perez",
  "motherLastName": "Garcia",
  "email": "juan.perez@email.com"
}
```

### Deliveries — `/api/v1/deliveries`

| Método | Endpoint | Descripción | Código éxito |
|---|---|---|---|
| `POST` | `/api/v1/deliveries` | Crear dirección de entrega | `201 Created` |
| `GET` | `/api/v1/deliveries?customerId={id}` | Listar por cliente | `200 OK` |
| `GET` | `/api/v1/deliveries/{id}` | Obtener dirección por ID | `200 OK` |
| `PUT` | `/api/v1/deliveries/{id}` | Actualizar dirección | `200 OK` |
| `DELETE` | `/api/v1/deliveries/{id}` | Eliminar dirección | `204 No Content` |

**Ejemplo — Crear dirección:**
```json
POST /api/v1/deliveries
{
  "customerId": "cust_001",
  "alias": "Casa",
  "street": "Av. Insurgentes 123",
  "colony": "Roma Norte",
  "city": "Ciudad de Mexico",
  "state": "CDMX",
  "zipCode": "06700",
  "country": "Mexico"
}
```

### Orders — `/api/v1/orders`

| Método | Endpoint | Descripción | Código éxito |
|---|---|---|---|
| `POST` | `/api/v1/orders` | Crear pedido | `201 Created` |
| `GET` | `/api/v1/orders` | Listar todos los pedidos | `200 OK` |
| `GET` | `/api/v1/orders?displayName={texto}` | Buscar por nombre de artículo | `200 OK` |
| `GET` | `/api/v1/orders/{id}` | Obtener pedido por ID | `200 OK` |
| `PATCH` | `/api/v1/orders/{id}/status` | Actualizar estado del pedido | `200 OK` |
| `DELETE` | `/api/v1/orders/{id}` | Eliminar pedido | `204 No Content` |

**Ejemplo — Crear pedido:**
```json
POST /api/v1/orders
{
  "customerId": "cust_001",
  "deliveryId": "deliv_001",
  "estimatedDeliveryDate": "2026-04-15",
  "items": [
    {
      "productCode": "PROD-001",
      "displayName": "Cafetera Nespresso",
      "quantity": 2,
      "price": 1299.99
    }
  ]
}
```

> El campo `total` es calculado automáticamente por el servicio (`Σ precio × cantidad`).
> La búsqueda por `displayName` es flexible: ignora acentos, mayúsculas y puntuación.

**Estados válidos de un pedido:**
```
PENDING → CONFIRMED → SHIPPED → DELIVERED
                              ↘ CANCELLED
```

---

## Arquitectura

El proyecto implementa **Arquitectura Hexagonal (Ports & Adapters)** con tres módulos de negocio independientes y un módulo compartido.

```
com.liverpool.msoventas
├── shared/                          # Módulo transversal
│   └── domain/model/
│       ├── Result.java              # Contenedor genérico de resultado (éxito/fallo)
│       └── ErrorType.java           # Enum: NOT_FOUND | CONFLICT | VALIDATION_ERROR | INTERNAL_ERROR
│
├── customer/                        # Módulo de Clientes
│   ├── domain/
│   │   ├── model/Customer.java
│   │   └── port/
│   │       ├── in/                  # Puertos de entrada (casos de uso)
│   │       └── out/                 # Puertos de salida (repositorio)
│   ├── application/service/         # Lógica de negocio (CustomerService)
│   └── infrastructure/
│       ├── adapter/in/rest/         # Controlador REST + DTOs
│       ├── adapter/out/persistence/ # Adaptador MongoDB
│       └── config/                  # Composition Root (BeanConfig)
│
├── delivery/                        # Módulo de Direcciones de Entrega
│   └── [misma estructura que customer]
│
└── order/                           # Módulo de Pedidos
    └── [misma estructura que customer]
```

### Decisiones de diseño relevantes

- **Sin `@Service` en la capa de aplicación**: los servicios se registran como beans en `*BeanConfig` (Composition Root), manteniendo el desacoplamiento del framework.
- **Result Pattern**: todos los casos de uso retornan `Result<T>` en lugar de lanzar excepciones, haciendo explícito el manejo de errores.
- **Normalización de texto**: la búsqueda por `displayName` usa NFD normalization para ser insensible a acentos, mayúsculas y puntuación.

---

## Estructura de pruebas

| Clase de test | Tipo | Tests |
|---|---|---|
| `CustomerServiceTest` | Unitario (Mockito) | 9 |
| `CustomerMongoAdapterTest` | Unitario (Mockito) | 7 |
| `CustomerControllerTest` | Integración (MockMvc) | 13 |
| `DeliveryServiceTest` | Unitario (Mockito) | 9 |
| `DeliveryMongoAdapterTest` | Unitario (Mockito) | 6 |
| `DeliveryControllerTest` | Integración (MockMvc) | 12 |
| `OrderServiceTest` | Unitario (Mockito) | 11 |
| `OrderMongoAdapterTest` | Unitario (Mockito) | 10 |
| `OrderControllerTest` | Integración (MockMvc) | 15 |
| `MsoVentasLiverpoolApplicationTests` | Contexto Spring | 1 |
| **Total** | | **93** |

---

## Diagramas

Los diagramas Mermaid del proyecto están en [`diagramas/diagramas.md`](diagramas/diagramas.md):

- **Diagrama de Secuencia**: flujo completo de negocio — alta de cliente → alta de dirección → generación de pedido, atravesando todas las capas de la arquitectura.
- **Diagrama ER**: estructura de las colecciones MongoDB con sus relaciones lógicas.

---

## Evidencias

### Proyecto corriendo

![Proyecto corriendo](assets/proyecto_corriendo.png)

### Swagger UI

![Swagger UI](assets/swagger-ui.png)

### Tests — BUILD SUCCESS

![Tests](assets/tests-build-success.png)

### Cobertura JaCoCo

![Cobertura JaCoCo](assets/jacoco-coverage.png)

### CheckStyle — 0 violaciones

![CheckStyle](assets/checkstyle-success.png)

### Javadoc generado

![Javadoc](assets/javadoc-mso.png)

---

## Configuración de entorno

El proyecto usa perfiles de Spring Boot:

| Perfil | Archivo | Uso |
|---|---|---|
| `dev` (default) | `application-dev.properties` | Desarrollo local con Docker |
| `prod` | `application-prod.properties` | Producción |

La cadena de conexión a MongoDB se configura en `application-dev.properties`:

```properties
spring.mongodb.uri=mongodb://admin:admin123@localhost:27017/mso_ventas_db?authSource=admin
```

Para producción, se recomienda usar variables de entorno. Ver `.env.example`:

```env
MONGODB_URI=mongodb://usuario:password@host:27017/mso_ventas_db?authSource=admin
```

---

## Comandos de referencia rápida

```bash
# Levantar MongoDB
docker-compose up -d

# Ejecutar aplicación
./mvnw spring-boot:run

# Ejecutar todos los tests + verificación completa
./mvnw verify

# Solo tests
./mvnw test

# Solo CheckStyle
./mvnw checkstyle:check

# Generar Javadoc
./mvnw javadoc:javadoc
# Resultado en: target/site/apidocs/index.html

# Detener MongoDB
docker-compose down
```

---

## Autor

**Jesús Fernando Cruz Islas**
Examen técnico — Posición Backend Developer · Liverpool · 2026
