# Remember Debts - API de Gestión de Deudas

## Descripción

Remember Debts es una aplicación backend desarrollada en Java con Spring Boot que permite a los usuarios gestionar y controlar sus deudas mensuales, tales como créditos con proveedores, impuestos, préstamos bancarios, entre otros. La aplicación facilita el registro, seguimiento, alertas y confirmación de pagos para evitar olvidos y mejorar la administración financiera.

---

## Funcionalidades principales

- Registro y autenticación de usuarios.
- Gestión completa de deudas: creación, edición, eliminación y visualización.
- Registro y seguimiento de pagos realizados.
- Alertas y recordatorios para deudas próximas a vencer.
- Visualización de deudas en formato listado y calendario.
- Filtrado de deudas por estado (pendiente, pagada, vencida).
- Historial y resumen de pagos por mes.

---

## Tecnologías utilizadas

- Java 21
- Spring Boot 3.4.x
- Spring Data JPA / Hibernate
- PostgreSQL (base de datos relacional)
- Lombok
- Maven (gestión de dependencias)
- Jakarta Persistence API
- Validación con Hibernate Validator

---

## Estructura del proyecto

- `model.entity` - Entidades JPA que representan las tablas de la base de datos (`User`, `Deuda`, `Pago`, `CategoriaDeuda`, `Alerta`).
- `model.enums` - Enumeraciones para estados y frecuencias.
- `repository` - Interfaces para acceso a datos con Spring Data JPA.
- `service` - Lógica de negocio y servicios.
- `controller` - Endpoints REST para la API.
- `resources` - Configuración y scripts SQL (como `data-test.sql`).

---

## Configuración y ejecución

### Requisitos previos

- Java 21 instalado
- PostgreSQL configurado y en ejecución
- Maven instalado

### Pasos para ejecutar

1. Clonar el repositorio:
   git clone <URL_DEL_REPOSITORIO>
   cd remember-debts
2. Configurar conexión a base de datos en `src/main/resources/application.properties` o `application.yml`:
   spring.datasource.url=jdbc:postgresql://localhost:5432/rememberdebtsdb
   spring.datasource.username=tu_usuario
   spring.datasource.password=tu_contraseña

    spring.jpa.hibernate.ddl-auto=update
    spring.jpa.show-sql=true
3. Crear la base de datos en PostgreSQL (si no existe):
   CREATE DATABASE rememberdebtsdb;
4. Ejecutar el proyecto con Maven:
   mvn spring-boot:run

5. La API estará disponible en: `http://localhost:8080/api/v1/`

---

## Datos de prueba

Se incluye un archivo `data-test.sql` con datos iniciales para usuarios, categorías, deudas, pagos y alertas. Este script se ejecuta automáticamente al iniciar la aplicación si está configurado en `application.properties`.

---

## Endpoints principales (ejemplos)

- `POST /api/v1/auth/register` - Registrar nuevo usuario
- `POST /api/v1/auth/login` - Iniciar sesión
- `GET /api/v1/deudas` - Listar deudas del usuario
- `POST /api/v1/deudas` - Registrar nueva deuda
- `PUT /api/v1/deudas/{id}` - Editar deuda
- `DELETE /api/v1/deudas/{id}` - Eliminar deuda
- `POST /api/v1/pagos` - Confirmar pago
- `GET /api/v1/alertas` - Ver alertas próximas

---

## Buenas prácticas y recomendaciones

- Las contraseñas se almacenan de forma segura con hashing (no en texto plano).
- Validar entradas para evitar datos inválidos.
- Manejar errores y respuestas HTTP adecuadas.
- Implementar paginación para listados grandes.
- Configurar seguridad (JWT o sesiones) para proteger la API.

---

## Contribuciones

Este proyecto es para fines académicos y está abierto a mejoras y sugerencias. Si quieres contribuir, por favor abre un issue o pull request.

---

## Licencia

Este proyecto está bajo la licencia UPAO.