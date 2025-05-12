# API Bancaria Microservicios
## Descripción del Proyecto
Este proyecto está basado en una arquitectura de microservicios, donde se implementan funcionalidades de CRUD y gestión de cuentas y movimientos bancarios. El objetivo es gestionar clientes, cuentas y movimientos a través de un API Restful con Spring Boot, Docker y PostgreSQL.

## 🚀Tecnologías Utilizadas

- **Java 17** con **Spring Boot**.
- **JPA** para el manejo de entidades y base de datos relacional.
- **PostgreSQL** como base de datos relacional.
- **Docker** para el despliegue de contenedores.
- **JUnit** para las pruebas unitarias.
- **Postman** para la validación de los endpoints.
- 
## Endpoints

### Cliente Controller (`/api/v1/mantenimiento/clientes`)

#### 1. **Registrar un cliente**
- **URL**: `/api/v1/mantenimiento/clientes/crear`
- **Método**: `POST`
- **Descripción**: Crea un nuevo cliente en el sistema.
- **Cuerpo de la solicitud**:
    ```json
    {
      "identificacion": "123456789",
      "nombre": "Juan Pérez",
      "genero": "Masculino",
      "edad": "30",
      "direccion": "Calle Ficticia 123",
      "telefono": "123456789"
    }
    ```
- **Respuesta**:
    ```json
    {
      "mensaje": "Cliente creado correctamente.",
      "fechaEjecucion": "2025-01-28T12:30:00",
      "cliente": { /* Información del cliente creado */ }
    }
    ```

#### 2. **Listar todos los clientes**
- **URL**: `/api/v1/mantenimiento/clientes/listar`
- **Método**: `GET`
- **Descripción**: Devuelve una lista de todos los clientes registrados en el sistema.
- **Respuesta**:
    ```json
    [
      {
        "identificacion": "123456789",
        "nombre": "Juan Pérez",
        "genero": "Masculino",
        "edad": "30",
        "direccion": "Calle Ficticia 123",
        "telefono": "123456789"
      },
      // Más clientes
    ]
    ```

#### 3. **Eliminar un cliente**
- **URL**: `/api/v1/mantenimiento/clientes/eliminar/{identificacion}`
- **Método**: `DELETE`
- **Descripción**: Elimina un cliente según su número de identificación.
- **Respuesta**:
    ```json
    {
      "mensaje": "Cliente eliminado correctamente.",
      "fechaEjecucion": "2025-01-28T12:35:00"
    }
    ```

#### 4. **Actualizar información de un cliente**
- **URL**: `/api/v1/mantenimiento/clientes/actualizar/{identificacion}`
- **Método**: `PUT`
- **Descripción**: Actualiza los datos de un cliente existente.
- **Cuerpo de la solicitud**:
    ```json
    {
      "nombre": "Juan Pérez Actualizado",
      "genero": "Masculino",
      "edad": "31",
      "direccion": "Calle Actualizada 456",
      "telefono": "987654321"
    }
    ```
- **Respuesta**:
    ```json
    {
      "mensaje": "Cliente actualizado correctamente.",
      "fechaEjecucion": "2025-01-28T12:40:00",
      "cliente": { /* Información del cliente actualizado */ }
    }
    ```

#### 5. **Consultar un cliente por identificación**
- **URL**: `/api/v1/mantenimiento/clientes/consultarPersonaPorIdentificacion/{identificacion}`
- **Método**: `GET`
- **Descripción**: Consulta la información de un cliente usando su identificación.
- **Respuesta**:
    ```json
    {
      "identificacion": "123456789",
      "nombre": "Juan Pérez",
      "genero": "Masculino",
      "edad": "30",
      "direccion": "Calle Ficticia 123",
      "telefono": "123456789"
    }
    ```

### Cuenta Controller (`/api/v1/operaciones/cuentas`)

#### 1. **Crear cuenta bancaria**
- **URL**: `/api/v1/operaciones/cuentas/crear`
- **Método**: `POST`
- **Descripción**: Crea una nueva cuenta bancaria.
- **Cuerpo de la solicitud**:
    ```json
    {
      "identificacionCliente": "123456789",
      "tipoCuenta": "Ahorro",
      "saldoInicial": 1000.0
    }
    ```
- **Respuesta**:
    ```json
    {
      "mensaje": "Cuenta creada correctamente.",
      "fechaEjecucion": "2025-01-28T12:45:00",
      "cuenta": { /* Información de la cuenta creada */ }
    }
    ```

#### 2. **Listar todas las cuentas**
- **URL**: `/api/v1/operaciones/cuentas/listar`
- **Método**: `GET`
- **Descripción**: Devuelve una lista de todas las cuentas bancarias registradas.
- **Respuesta**:
    ```json
    [
      {
        "numeroCuenta": "1234567890",
        "tipoCuenta": "Ahorro",
        "saldoInicial": 1000.0,
        "saldoDisponible": 1000.0,
        "estado": true,
        "identificacionCliente": "123456789",
        "nombreCliente": "Juan Pérez"
      },
      // Más cuentas
    ]
    ```

#### 3. **Eliminar cuenta bancaria**
- **URL**: `/api/v1/operaciones/cuentas/eliminar/{numeroCuenta}`
- **Método**: `DELETE`
- **Descripción**: Elimina una cuenta bancaria utilizando su número de cuenta.
- **Respuesta**:
    ```json
    {
      "mensaje": "Cuenta eliminada correctamente.",
      "fechaEjecucion": "2025-01-28T12:50:00"
    }
    ```

#### 4. **Actualizar cuenta bancaria**
- **URL**: `/api/v1/operaciones/cuentas/actualizar/{numeroCuenta}`
- **Método**: `PUT`
- **Descripción**: Actualiza los datos de una cuenta bancaria existente.
- **Cuerpo de la solicitud**:
    ```json
    {
      "tipoCuenta": "Corriente",
      "saldoInicial": 1500.0,
      "estado": true
    }
    ```
- **Respuesta**:
    ```json
    {
      "mensaje": "Cuenta actualizada correctamente.",
      "fechaEjecucion": "2025-01-28T12:55:00",
      "cuenta": { /* Información de la cuenta actualizada */ }
    }
    ```

### Movimiento Controller (`/api/v1/operaciones/movimientos`)

*Los movimientos bancarios también pueden ser gestionados con endpoints similares para registrar, listar y actualizar transacciones.*

## Requisitos

- Java 17 o superior
- Spring Boot 2.6.x
- Maven
- PostgreSQL (o cualquier otra base de datos compatible)

## Instalación

1. Clonar el repositorio.
2. Configurar la base de datos en `application.properties`.
3. Ejecutar el proyecto con el siguiente comando:
    ```bash
    mvn spring-boot:run
    ```
4. Acceder a la API a través de los endpoints definidos en este README.

## Contribuciones

¡Las contribuciones son bienvenidas! Si tienes alguna mejora o bug, por favor abre un *pull request* o *issue*.

---
## Autor:
<span style="color:DodgerBlue">**Leonardo Villagran**</span> | <span style="color:yellow">**le**</span><span style="color:DodgerBlue">**vi**</span><span style="color:red">**Dev**</span>

- [LinkedIn] https://www.linkedin.com/in/lvillagrans

Este archivo `README.md` proporciona información básica sobre los endpoints y cómo interactuar con la API. ¡Espero que te sea útil!
