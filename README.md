# 🛋️ ms_furniture - Microservicio Spring Boot

Microservicio construido con **Spring Boot**, usando la plantilla de **arquitectura limpia de Bancolombia**, orquestado con Docker Compose. Incluye RabbitMQ y DynamoDB Local como dependencias.

---

## 📋 Requisitos

- [Docker](https://docs.docker.com/get-docker/)
- [Docker Compose](https://docs.docker.com/compose/)
- (Opcional) JDK 21+ y Gradle 8+ si deseas ejecutar sin contenedores

---

## 📂 Estructura del proyecto




---

## 🚀 Ejecución con Docker

### 1. Clonar el repositorio

```bash
    git clone https://github.com/alejo221/mueblessas
    cd mueblessas
```
### 2. Construir el proyecto

- Desde la raíz del proyecto, ejecutar.

```bash
  ./gradlew clean build
```
- Esto debe generar el archivo: ms_furniture.jar en la ubicación applications/app-service/build/libs/ms_furniture.jar

-  Levantar contenedores (Dynamo, RabbitMQ y Jar)
```bash
  docker-compose -f deployment/docker-compose.yml up --build
```
** Se puede demorar unos segundos para conectarse a los servicios.

- Para detener 
```bash
    docker-compose -f deployment/docker-compose.yml down -v
```
