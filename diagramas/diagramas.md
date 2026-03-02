# Diagramas — mso-ventas-liverpool

> Microservicio de gestión de ventas construido con Spring Boot 4 y Arquitectura Hexagonal.

---

## 1. Diagrama de Secuencia — Flujo de negocio

Muestra el proceso completo: alta de cliente → alta de dirección de entrega → generación de pedido.
Cada paso atraviesa las capas de la Arquitectura Hexagonal: REST → Aplicación → Puerto de salida → MongoDB.

```mermaid
sequenceDiagram
    actor C as Cliente / Consumidor API
    participant CTR as Controller (Adaptador In)
    participant SVC as Service (Aplicación)
    participant PORT as RepositoryPort (Puerto Out)
    participant DB as MongoDB

    %% ─── PASO 1: Alta de Cliente ───────────────────────────────
    Note over C,DB: PASO 1 — Alta de Cliente

    C->>+CTR: POST /api/v1/customers<br/>{ firstName, lastName, motherLastName, email }
    CTR->>CTR: Valida @Valid CreateCustomerRequest
    CTR->>+SVC: CustomerService.create(customer)
    SVC->>+PORT: repositoryPort.save(customer)
    PORT->>+DB: customerRepository.save(CustomerDocument)
    DB-->>-PORT: CustomerDocument guardado
    PORT-->>-SVC: Customer (modelo de dominio)
    SVC-->>-CTR: Result.success(customer)
    CTR-->>-C: 201 Created · CustomerResponse { id, firstName, ... }

    %% ─── PASO 2: Alta de Dirección de Entrega ──────────────────
    Note over C,DB: PASO 2 — Alta de Dirección de Entrega

    C->>+CTR: POST /api/v1/deliveries<br/>{ customerId, alias, street, colony, city, state, zipCode, country }
    CTR->>CTR: Valida @Valid CreateDeliveryRequest
    CTR->>+SVC: DeliveryService.create(delivery)
    SVC->>+PORT: repositoryPort.save(delivery)
    PORT->>+DB: deliveryRepository.save(DeliveryDocument)
    DB-->>-PORT: DeliveryDocument guardado
    PORT-->>-SVC: Delivery (modelo de dominio)
    SVC-->>-CTR: Result.success(delivery)
    CTR-->>-C: 201 Created · DeliveryResponse { id, alias, ... }

    %% ─── PASO 3: Generar Pedido ─────────────────────────────────
    Note over C,DB: PASO 3 — Generar Pedido

    C->>+CTR: POST /api/v1/orders<br/>{ customerId, deliveryId, items[], estimatedDeliveryDate }
    CTR->>CTR: Valida @Valid CreateOrderRequest
    CTR->>CTR: Construye Order con lista de OrderItem
    CTR->>+SVC: OrderService.create(order)
    SVC->>SVC: Calcula total = Σ (precio × cantidad)
    SVC->>SVC: Asigna status = PENDING y createdAt = now()
    SVC->>+PORT: repositoryPort.save(order)
    PORT->>PORT: Normaliza displayName<br/>(minúsculas, sin acentos, sin puntuación)
    PORT->>+DB: orderRepository.save(OrderDocument)
    DB-->>-PORT: OrderDocument guardado
    PORT-->>-SVC: Order (modelo de dominio)
    SVC-->>-CTR: Result.success(order)
    CTR-->>-C: 201 Created · OrderResponse { id, total, status: PENDING, ... }
```

---

## 2. Diagrama ER — Colecciones MongoDB

MongoDB no tiene claves foráneas reales; las relaciones son **referencias lógicas** mediante el campo `_id`.
`ORDER_ITEMS` es un **documento embebido** dentro de `ORDERS` (no una colección separada).

```mermaid
erDiagram
    CUSTOMERS {
        string _id        PK "ObjectId — generado por MongoDB"
        string firstName
        string lastName
        string motherLastName
        string email         "Único (índice único)"
    }

    DELIVERIES {
        string _id        PK "ObjectId — generado por MongoDB"
        string customerId FK "Referencia lógica → customers._id"
        string alias         "Ej: Casa, Trabajo, Oficina"
        string street
        string colony
        string city
        string state
        string zipCode
        string country
    }

    ORDERS {
        string   _id                  PK "ObjectId — generado por MongoDB"
        string   customerId           FK "Referencia lógica → customers._id"
        string   deliveryId           FK "Referencia lógica → deliveries._id"
        decimal  total                   "Calculado: Σ (precio × cantidad)"
        string   status                  "PENDING | CONFIRMED | SHIPPED | DELIVERED | CANCELLED"
        date     estimatedDeliveryDate
        datetime createdAt
    }

    ORDER_ITEMS {
        string  productCode
        string  displayName
        string  displayNameNormalized   "Para búsqueda flexible (sin acentos, minúsculas)"
        int     quantity
        decimal price
    }

    CUSTOMERS  ||--o{ DELIVERIES  : "tiene (1 cliente → N direcciones)"
    CUSTOMERS  ||--o{ ORDERS      : "realiza (1 cliente → N pedidos)"
    DELIVERIES ||--o{ ORDERS      : "recibe (1 dirección → N pedidos)"
    ORDERS     ||--|{ ORDER_ITEMS : "contiene — documento embebido"
```
