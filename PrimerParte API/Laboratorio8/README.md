# Laboratorio 8 - GTICS - API RESTful Northwind

## Descripción del Proyecto

Este proyecto implementa un API RESTful para gestionar productos de la base de datos Northwind. Está desarrollado con Spring Boot 3.5.7 y utiliza MySQL como base de datos.

## Requisitos Previos

- Java 17 o superior
- Maven 3.6 o superior
- MySQL 8.0 o superior
- Git (opcional)

## Instalación y Configuración

### 1. Clonar/Descargar el Proyecto

```bash
cd C:\Users\cs\Desktop\Laboratorio8
```

### 2. Configurar la Base de Datos

1. **Importar la base de datos Northwind:**

```bash
mysql -u root -p < C:\Users\cs\Downloads\Northwind.MySQL.sql
```

2. **Verificar que se creó correctamente:**

```bash
mysql -u root -p
mysql> SHOW DATABASES;
mysql> USE northwind;
mysql> SHOW TABLES;
```

### 3. Configurar la Conexión en application.properties

El archivo `src/main/resources/application.properties` ya está configurado con:

```properties
spring.datasource.url=jdbc:mysql://localhost:3306/northwind
spring.datasource.username=root
spring.datasource.password=root
```

**Nota:** Si tu usuario/contraseña de MySQL son diferentes, actualiza estos valores.

### 4. Compilar y Ejecutar el Proyecto

```bash
# Compilar
mvn clean install

# Ejecutar
mvn spring-boot:run
```

Deberías ver en la consola:
```
Started Laboratorio8Application in 3.5 seconds
```

## Estructura del Proyecto

```
Laboratorio8/
├── src/
│   ├── main/
│   │   ├── java/com/example/laboratorio8/
│   │   │   ├── controller/
│   │   │   │   └── ProductController.java
│   │   │   ├── entity/
│   │   │   │   └── Product.java
│   │   │   ├── repository/
│   │   │   │   └── ProductRepository.java
│   │   │   ├── dto/
│   │   │   │   ├── ProductRequest.java
│   │   │   │   ├── SuccessResponse.java
│   │   │   │   └── ErrorResponse.java
│   │   │   └── Laboratorio8Application.java
│   │   └── resources/
│   │       └── application.properties
│   └── test/
├── pom.xml
├── API_DOCUMENTATION.md
└── README.md
```

## Endpoints Disponibles

### GET - Obtención de Datos

- **`GET /product`** - Listar todos los productos
- **`GET /product/{id}`** - Obtener un producto por ID

### POST - Crear Datos

- **`POST /product`** - Crear un nuevo producto

### PUT - Actualizar Datos

- **`PUT /product`** - Actualizar un producto existente

### DELETE - Eliminar Datos

- **`DELETE /product/{id}`** - Eliminar un producto

## Documentación Completa

Para más detalles sobre cada endpoint, incluyendo:
- Parámetros de entrada
- Estructura de respuestas
- Códigos HTTP
- Ejemplos de uso

Consulta el archivo: **[API_DOCUMENTATION.md](./API_Documentacion)**

## Ejemplos de Uso Rápido

### Listar todos los productos
```bash
curl -X GET http://localhost:8080/product
```

### Obtener producto con ID 1
```bash
curl -X GET http://localhost:8080/product/1
```

### Crear un nuevo producto
```bash
curl -X POST http://localhost:8080/product \
  -H "Content-Type: application/json" \
  -d '{
    "productName": "Mi Producto",
    "supplierId": 1,
    "categoryId": 1,
    "unitPrice": 100,
    "unitsInStock": 50
  }'
```

### Actualizar un producto
```bash
curl -X PUT http://localhost:8080/product \
  -H "Content-Type: application/json" \
  -d '{
    "productId": 80,
    "productName": "Producto Actualizado",
    "unitPrice": 150
  }'
```

### Eliminar un producto
```bash
curl -X DELETE http://localhost:8080/product/80
```

## Probando con Postman

1. Descarga [Postman](https://www.postman.com/downloads/)
2. Abre Postman
3. Crea solicitudes para cada endpoint
4. Los ejemplos de uso están documentados en `API_DOCUMENTATION.md`

## Tecnologías Utilizadas

- **Framework:** Spring Boot 3.5.7
- **Lenguaje:** Java 17
- **ORM:** Spring Data JPA / Hibernate
- **Base de Datos:** MySQL 8.0
- **Build Tool:** Maven 3.6
- **API REST:** RESTful con JSON

## Dependencias Principales

```xml
<dependency>
    <groupId>org.springframework.boot</groupId>
    <artifactId>spring-boot-starter-web</artifactId>
</dependency>

<dependency>
    <groupId>org.springframework.boot</groupId>
    <artifactId>spring-boot-starter-data-jpa</artifactId>
</dependency>

<dependency>
    <groupId>com.mysql</groupId>
    <artifactId>mysql-connector-j</artifactId>
    <version>8.0.33</version>
</dependency>
```

## Ejercicios Implementados

### ✅ Ejercicio 1: Endpoints de Obtención de Datos (4 puntos)
- [x] GET /product - Listar todos los productos (1 punto)
- [x] GET /product/{id} - Obtener producto por ID (3 puntos)

### ✅ Ejercicio 2: Endpoints de Manipulación de Datos (5 puntos)
- [x] POST /product - Crear producto (2 puntos)
- [x] PUT /product - Actualizar producto (2 puntos)
- [x] DELETE /product/{id} - Eliminar producto (1 punto)

### ✅ Ejercicio 3: Crear Documentación (2 puntos)
- [x] Documentación en Markdown (API_DOCUMENTATION.md)

## Notas Importantes

1. **Validaciones:** Todos los endpoints validan los datos de entrada
2. **Manejo de Errores:** Se retornan códigos HTTP apropiados y mensajes descriptivos
3. **Respuestas JSON:** Todos los endpoints retornan respuestas en formato JSON
4. **Sin Autenticación:** En esta versión no se implementa autenticación

## Solución de Problemas

### Error de conexión a MySQL
```
Unable to build Hibernate SessionFactory
```
**Solución:** Verifica que:
- MySQL esté corriendo (`services.msc` en Windows)
- La contraseña en `application.properties` sea correcta
- La base de datos `northwind` exista

### Error de tipos de datos
```
Schema-validation: wrong column type encountered
```
**Solución:** Asegúrate de que `spring.jpa.hibernate.ddl-auto=none` en `application.properties`

## Autor

Desarrollado para el Laboratorio 8 - GTICS

## Versión

**1.0** - 2 de Noviembre de 2025

## Licencia

Este proyecto es de uso educativo.
