# 🛋️ Muebles SAS - Microservicio Spring Boot

Microservicio construido con **Spring Boot**, usando la plantilla de **arquitectura limpia de Bancolombia**, orquestado con Docker Compose. Incluye RabbitMQ y DynamoDB Local como dependencias.

---

##  Requisitos

- [Docker](https://docs.docker.com/get-docker/)
- [Docker Compose](https://docs.docker.com/compose/)
- JDK 21+ y Gradle 8+

---

##  Ejecución con Docker

### 1. Clonar el repositorio

```bash
    git clone https://github.com/alejo221/mueblessas
    cd mueblessas
```
### 2. Ejecutar el servicio
El endpoint se organizó lo suficente para ser levantado con pocos comandos y ninguna configuración adicional, el mismo levanta el rabbit y el dynamo con sus recursos.

2.1 Desde la raíz del proyecto, ejecutar.

```bash
  ./gradlew clean build
```
- Esto debe generar el archivo: ms_furniture.jar en la ubicación applications/app-service/build/libs/ms_furniture.jar


2.2  Levantar contenedores (Dynamo, RabbitMQ y JAR)
```bash
  docker-compose -f deployment/docker-compose.yml up --build
```
** Se puede demorar unos segundos para conectarse a los servicios.

- Para detener 
```bash
    docker-compose -f deployment/docker-compose.yml down -v
```
---
### 3. Probar el endpoint


### 📄 Descripción

Este endpoint permite registrar estadísticas.
Nota: Se maneja unos valores en español por la necesidad, sin embargo, internamente se hace la gestión en inglés.


POST http://localhost:8080/api/v1/stats

---

### 3.1 Caso ideal
Body 

```json
{
  "totalContactoClientes": 250,
  "motivoReclamo": 25,
  "motivoGarantia": 10,
  "motivoDuda": 100,
  "motivoCompra": 100,
  "motivoFelicitaciones": 7,
  "motivoCambio": 8,
  "hash": "5484062a4be1ce5645eb414663e14f59"
}
```
Respuesta
```json
{
  "success": true,
  "message": "Registered",
  "data": {
    "totalCustomerContacts": 250,
    "complaintReason": 25,
    "warrantyReason": 10,
    "questionReason": 100,
    "purchaseReason": 100,
    "praiseReason": 7,
    "exchangeReason": 8,
    "hash": "5484062a4be1ce5645eb414663e14f59",
    "timestamp": "2025-06-24T11:11:35.467386240Z"
  },
  "timestamp": "2025-06-24T11:11:35.488902403Z",
  "code": 201,
  "errors": null
}
```
### 3.1 Caso no ideal 1 - Hash no válido

```json
{
  "totalContactoClientes": 250,
  "motivoReclamo": 25,
  "motivoGarantia": 10,
  "motivoDuda": 100,
  "motivoCompra": 100,
  "motivoFelicitaciones": 7,
  "motivoCambio": 8,
  "hash": "5484062a4be1ce565eb414663e14ffas"
}
```
Respuesta
```json
{
  "success": false,
  "message": "The hash is not valid",
  "data": null,
  "timestamp": "2025-06-24T11:18:38.907892099Z",
  "code": 400,
  "errors": null
}
```

### 3.2 Caso no ideal 2 - Campos con tipo incorrecto

```json
{
  "totalContactoClientes": 250,
  "motivoReclamo": 25,
  "motivoGarantia": 10,
  "motivoDuda": "uno",
  "motivoCompra": 100,
  "motivoFelicitaciones": "dos",
  "motivoCambio": 8,
  "hash": "5484062a4be1ce565eb414663e14ffas"
}
```
Respuesta
```json
{
  "success": false,
  "message": "400 BAD_REQUEST \"Failed to read HTTP message\"",
  "data": null,
  "timestamp": "2025-06-24T11:19:04.805906321Z",
  "code": 400,
  "errors": null
}
```
### 3.3 Caso no ideal 3 - Campos con valores inválidos

```json
{
  "totalContactoClientes": 250,
  "motivoReclamo": "",
  "motivoGarantia": 10,
  "motivoDuda": -50,
  "motivoCompra": 100,
  "motivoFelicitaciones": 7,
  "motivoCambio": 8,
  "hash": "5484062a4be1ce565eb414663e14f59"
}

```
Respuesta
```json
{
  "success": false,
  "message": "Validación fallida",
  "data": null,
  "timestamp": "2025-06-24T11:20:31.000387752Z",
  "code": 400,
  "errors": [
    {
      "field": "motivoDuda",
      "message": "El valor no es válido para un motivoDuda"
    },
    {
      "field": "motivoReclamo",
      "message": "El motivoReclamo es obligatorio"
    }
  ]
}
```

---

## 3. Pruebas
### 3.1 Unitarias

Para ejecutar pruebas unitarias, es necesario ubicarse en la raíz del proyecto y ejecutar el comando

```gradle
    ./gradlew test
```
Se ejecutarán un total de 14 pruebas

### 3.2 Integración

Para ejecutar pruebas de integración, es necesario ubicarse en 
la ruta deployment/acceptantTest y ejecutar el comando

```gradle
    gradlew clean test -i
```

Se ejecutarán un total de 6 escenarios
