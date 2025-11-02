# Documentación API RESTful - Laboratorio 8 GTICS

Este documento detalla todos los endpoints del API RESTful desarrollado para gestionar productos de la base de datos Northwind. La API permite realizar operaciones CRUD (Create, Read, Update, Delete) sobre la tabla `Products`.

**Base URL:** `http://localhost:8080`


## Tabla de Contenidos

1. [Endpoints GET - Obtención de Datos](#endpoints-get---obtención-de-datos)
2. [Endpoints POST - Creación de Datos](#endpoints-post---creación-de-datos)
3. [Endpoints PUT - Actualización de Datos](#endpoints-put---actualización-de-datos)
4. [Endpoints DELETE - Eliminación de Datos](#endpoints-delete---eliminación-de-datos)
5. [Estructura de Respuestas](#estructura-de-respuestas)
6. [Códigos de Estado HTTP](#códigos-de-estado-http)
7. [Ejemplos de Uso](#ejemplos-de-uso)

---

## Endpoints GET - Obtención de Datos

### 1. Listar Todos los Productos

**Descripción:** Retorna una lista de todos los productos disponibles en la base de datos.

#### Detalles Técnicos

| Propiedad | Valor |
|-----------|-------|
| **Método HTTP** | GET |
| **URL** | `/product` |
| **Autenticación** | No requerida |
| **Parámetros de Ruta** | Ninguno |
| **Parámetros de Query** | Ninguno |

#### Argumentos de Entrada

**Ninguno**

#### Argumentos de Salida

**Tipo:** Array de objetos `Product`

```json
[
  {
    "productId": 1,
    "productName": "Chai",
    "supplierId": 1,
    "categoryId": 1,
    "quantityPerUnit": "10 boxes x 20 bags",
    "unitPrice": 18.0,
    "unitsInStock": 39,
    "unitsOnOrder": 0,
    "reorderLevel": 10,
    "discontinued": false
  },
  {
    "productId": 2,
    "productName": "Chang",
    "supplierId": 1,
    "categoryId": 1,
    "quantityPerUnit": "24 - 12 oz bottles",
    "unitPrice": 19.0,
    "unitsInStock": 17,
    "unitsOnOrder": 40,
    "reorderLevel": 25,
    "discontinued": false
  }
]
```

#### Códigos de Estado HTTP

| Código | Descripción |
|--------|-------------|
| **200** | OK - Lista de productos obtenida exitosamente |
| **500** | Error interno del servidor |


### 2. Obtener un Producto por ID

**Descripción:** Retorna los detalles de un producto específico según su ID.

#### Detalles Técnicos

| Propiedad | Valor |
|-----------|-------|
| **Método HTTP** | GET |
| **URL** | `/product/{id}` |
| **Autenticación** | No requerida |
| **Parámetro de Ruta** | `id` (Integer) - ID del producto |

#### Argumentos de Entrada

| Parámetro | Tipo | Ubicación | Obligatorio | Descripción |
|-----------|------|-----------|------------|-------------|
| `id` | Integer | Ruta URL | Sí | ID único del producto |

**Validaciones:**
- El `id` debe ser un número entero válido
- Si no es número válido, retorna error 400

#### Argumentos de Salida (Exitoso)

**Tipo:** Objeto `Product`

```json
{
  "productId": 1,
  "productName": "Chai",
  "supplierId": 1,
  "categoryId": 1,
  "quantityPerUnit": "10 boxes x 20 bags",
  "unitPrice": 18.0,
  "unitsInStock": 39,
  "unitsOnOrder": 0,
  "reorderLevel": 10,
  "discontinued": false
}
```

#### Argumentos de Salida (Error - ID No Válido)

```json
{
  "status": 400,
  "message": "El ID del producto debe ser un número válido",
  "timestamp": 1762103447336
}
```

#### Argumentos de Salida (Error - Producto No Existe)

```json
{
  "status": 400,
  "message": "El producto con ID 999 no existe",
  "timestamp": 1762103447336
}
```

#### Códigos de Estado HTTP

| Código | Descripción |
|--------|-------------|
| **200** | OK - Producto obtenido exitosamente |
| **400** | Bad Request - ID no válido o producto no existe |
| **500** | Error interno del servidor |

#### Ejemplo de Uso

```bash
# Obtener producto con ID 1
curl -X GET http://localhost:8080/product/1

# Intentar obtener producto con ID inexistente (retorna 400)
curl -X GET http://localhost:8080/product/999

# Intentar con ID no numérico (retorna 400)
curl -X GET http://localhost:8080/product/abc
```

---

## Endpoints POST - Creación de Datos

### 3. Crear un Nuevo Producto

**Descripción:** Crea un nuevo producto en la base de datos.

#### Detalles Técnicos

| Propiedad | Valor |
|-----------|-------|
| **Método HTTP** | POST |
| **URL** | `/product` |
| **Autenticación** | No requerida |
| **Content-Type** | application/json |

#### Argumentos de Entrada

**Ubicación:** Cuerpo de la solicitud (Body - JSON)

| Campo | Tipo | Obligatorio | Descripción |
|-------|------|------------|-------------|
| `productName` | String | Sí | Nombre del producto |
| `supplierId` | Integer | No | ID del proveedor |
| `categoryId` | Integer | No | ID de la categoría |
| `quantityPerUnit` | String | No | Cantidad por unidad |
| `unitPrice` | Double | No | Precio unitario |
| `unitsInStock` | Integer | No | Unidades en stock |
| `unitsOnOrder` | Integer | No | Unidades en pedido |
| `reorderLevel` | Integer | No | Nivel de reorden |
| `discontinued` | Boolean | No | ¿Producto descontinuado? (default: false) |

**Ejemplo de Body:**

```json
{
  "productName": "Laptop Gaming",
  "supplierId": 1,
  "categoryId": 1,
  "quantityPerUnit": "1 unit",
  "unitPrice": 1500,
  "unitsInStock": 5,
  "unitsOnOrder": 0,
  "reorderLevel": 1,
  "discontinued": false
}
```

#### Argumentos de Salida (Exitoso)

```json
{
  "success": true,
  "message": "Producto creado exitosamente",
  "data": {
    "productId": 80,
    "productName": "Laptop Gaming",
    "supplierId": 1,
    "categoryId": 1,
    "quantityPerUnit": "1 unit",
    "unitPrice": 1500.0,
    "unitsInStock": 5,
    "unitsOnOrder": 0,
    "reorderLevel": 1,
    "discontinued": false
  },
  "timestamp": 1762104617334
}
```

#### Argumentos de Salida (Error)

```json
{
  "status": 400,
  "message": "Error al crear el producto: [detalle del error]",
  "timestamp": 1762104617334
}
```

#### Códigos de Estado HTTP

| Código | Descripción |
|--------|-------------|
| **201** | Created - Producto creado exitosamente |
| **400** | Bad Request - Error en los datos de entrada |
| **500** | Error interno del servidor |

#### Ejemplo de Uso (Postman)

```
Método: POST
URL: http://localhost:8080/product
Headers: Content-Type: application/json
Body (raw JSON):
{
  "productName": "Laptop Gaming",
  "supplierId": 1,
  "categoryId": 1,
  "quantityPerUnit": "1 unit",
  "unitPrice": 1500,
  "unitsInStock": 5,
  "unitsOnOrder": 0,
  "reorderLevel": 1,
  "discontinued": false
}
```

#### Ejemplo de Uso (curl)

```bash
curl -X POST http://localhost:8080/product \
  -H "Content-Type: application/json" \
  -d '{
    "productName": "Laptop Gaming",
    "supplierId": 1,
    "categoryId": 1,
    "quantityPerUnit": "1 unit",
    "unitPrice": 1500,
    "unitsInStock": 5,
    "unitsOnOrder": 0,
    "reorderLevel": 1,
    "discontinued": false
  }'
```

---

## Endpoints PUT - Actualización de Datos

### 4. Actualizar un Producto Existente

**Descripción:** Actualiza los datos de un producto existente en la base de datos.

#### Detalles Técnicos

| Propiedad | Valor |
|-----------|-------|
| **Método HTTP** | PUT |
| **URL** | `/product` |
| **Autenticación** | No requerida |
| **Content-Type** | application/json |

#### Argumentos de Entrada

**Ubicación:** Cuerpo de la solicitud (Body - JSON)

| Campo | Tipo | Obligatorio | Descripción |
|-------|------|------------|-------------|
| `productId` | Integer | **Sí** | ID del producto a actualizar |
| `productName` | String | No | Nuevo nombre del producto |
| `supplierId` | Integer | No | Nuevo ID del proveedor |
| `categoryId` | Integer | No | Nuevo ID de la categoría |
| `quantityPerUnit` | String | No | Nueva cantidad por unidad |
| `unitPrice` | Double | No | Nuevo precio unitario |
| `unitsInStock` | Integer | No | Nuevas unidades en stock |
| `unitsOnOrder` | Integer | No | Nuevas unidades en pedido |
| `reorderLevel` | Integer | No | Nuevo nivel de reorden |
| `discontinued` | Boolean | No | Nuevo estado de discontinuación |

**Ejemplo de Body:**

```json
{
  "productId": 80,
  "productName": "Laptop Gaming - ACTUALIZADO",
  "supplierId": 1,
  "categoryId": 1,
  "quantityPerUnit": "1 unit",
  "unitPrice": 2000,
  "unitsInStock": 10,
  "unitsOnOrder": 0,
  "reorderLevel": 1,
  "discontinued": false
}
```

#### Validaciones

- El `productId` es **obligatorio** y debe ser válido (> 0)
- El producto debe existir en la base de datos

#### Argumentos de Salida (Exitoso)

```json
{
  "success": true,
  "message": "Producto actualizado exitosamente",
  "data": {
    "productId": 80,
    "productName": "Laptop Gaming - ACTUALIZADO",
    "supplierId": 1,
    "categoryId": 1,
    "quantityPerUnit": "1 unit",
    "unitPrice": 2000.0,
    "unitsInStock": 10,
    "unitsOnOrder": 0,
    "reorderLevel": 1,
    "discontinued": false
  },
  "timestamp": 1762104666986
}
```

#### Argumentos de Salida (Error - ID No Proporcionado)

```json
{
  "status": 400,
  "message": "El ID del producto es obligatorio y debe ser válido",
  "timestamp": 1762104666986
}
```

#### Argumentos de Salida (Error - Producto No Existe)

```json
{
  "status": 400,
  "message": "El producto con ID 999 no existe",
  "timestamp": 1762104666986
}
```

#### Argumentos de Salida (Error - Excepción)

```json
{
  "status": 400,
  "message": "Error al actualizar el producto: [detalle del error]",
  "timestamp": 1762104666986
}
```

#### Códigos de Estado HTTP

| Código | Descripción |
|--------|-------------|
| **200** | OK - Producto actualizado exitosamente |
| **400** | Bad Request - ID no proporcionado, producto no existe, o error en datos |
| **500** | Error interno del servidor |

#### Ejemplo de Uso (Postman)

```
Método: PUT
URL: http://localhost:8080/product
Headers: Content-Type: application/json
Body (raw JSON):
{
  "productId": 80,
  "productName": "Laptop Gaming - ACTUALIZADO",
  "unitPrice": 2000,
  "unitsInStock": 10
}
```

#### Ejemplo de Uso (curl)

```bash
curl -X PUT http://localhost:8080/product \
  -H "Content-Type: application/json" \
  -d '{
    "productId": 80,
    "productName": "Laptop Gaming - ACTUALIZADO",
    "supplierId": 1,
    "categoryId": 1,
    "quantityPerUnit": "1 unit",
    "unitPrice": 2000,
    "unitsInStock": 10,
    "unitsOnOrder": 0,
    "reorderLevel": 1,
    "discontinued": false
  }'
```

---

## Endpoints DELETE - Eliminación de Datos

### 5. Eliminar un Producto

**Descripción:** Elimina un producto de la base de datos según su ID.

#### Detalles Técnicos

| Propiedad | Valor |
|-----------|-------|
| **Método HTTP** | DELETE |
| **URL** | `/product/{id}` |
| **Autenticación** | No requerida |
| **Body** | Ninguno |

#### Argumentos de Entrada

| Parámetro | Tipo | Ubicación | Obligatorio | Descripción |
|-----------|------|-----------|------------|-------------|
| `id` | Integer | Ruta URL | Sí | ID del producto a eliminar |

**Validaciones:**
- El `id` debe ser un número entero válido
- Si no es número válido, retorna error 400
- El producto debe existir en la base de datos

#### Argumentos de Salida (Exitoso)

```json
{
  "success": true,
  "message": "Producto eliminado exitosamente",
  "data": null,
  "timestamp": 1762104715387
}
```

#### Argumentos de Salida (Error - ID No Válido)

```json
{
  "status": 400,
  "message": "El ID del producto debe ser un número válido",
  "timestamp": 1762104715387
}
```

#### Argumentos de Salida (Error - Producto No Existe)

```json
{
  "status": 400,
  "message": "El producto con ID 999 no existe",
  "timestamp": 1762104715387
}
```

#### Argumentos de Salida (Error - Excepción)

```json
{
  "status": 400,
  "message": "Error al eliminar el producto: [detalle del error]",
  "timestamp": 1762104715387
}
```

#### Códigos de Estado HTTP

| Código | Descripción |
|--------|-------------|
| **200** | OK - Producto eliminado exitosamente |
| **400** | Bad Request - ID no válido o producto no existe |
| **500** | Error interno del servidor |

#### Ejemplo de Uso (Postman)

```
Método: DELETE
URL: http://localhost:8080/product/80
Headers: (ninguno especial)
Body: (vacío)
```

#### Ejemplo de Uso (curl)

```bash
# Eliminar producto con ID 80
curl -X DELETE http://localhost:8080/product/80

# Intentar eliminar producto no existente (retorna 400)
curl -X DELETE http://localhost:8080/product/999

# Intentar con ID no numérico (retorna 400)
curl -X DELETE http://localhost:8080/product/abc
```

---

## Estructura de Respuestas

### Respuestas Exitosas (GET, POST, PUT, DELETE)

Las respuestas exitosas varían según el endpoint:

#### GET - Lista de Productos
```json
[
  {
    "productId": 1,
    "productName": "Chai",
    ...
  }
]
```

#### GET - Producto Individual
```json
{
  "productId": 1,
  "productName": "Chai",
  ...
}
```

#### POST - Crear Producto
```json
{
  "success": true,
  "message": "Producto creado exitosamente",
  "data": { /objeto Product/ },
  "timestamp": 1762104617334
}
```

#### PUT - Actualizar Producto
```json
{
  "success": true,
  "message": "Producto actualizado exitosamente",
  "data": { /objeto Product/ },
  "timestamp": 1762104666986
}
```

#### DELETE - Eliminar Producto
```json
{
  "success": true,
  "message": "Producto eliminado exitosamente",
  "data": null,
  "timestamp": 1762104715387
}
```

### Respuestas de Error

Todas las respuestas de error siguen este formato:

```json
{
  "status": 400,
  "message": "Descripción del error",
  "timestamp": 1762104715387
}
```

### Objeto Product

```json
{
  "productId": 1,
  "productName": "Chai",
  "supplierId": 1,
  "categoryId": 1,
  "quantityPerUnit": "10 boxes x 20 bags",
  "unitPrice": 18.0,
  "unitsInStock": 39,
  "unitsOnOrder": 0,
  "reorderLevel": 10,
  "discontinued": false
}
```

**Descripción de campos:**
- `productId`: Identificador único del producto (auto-generado)
- `productName`: Nombre del producto
- `supplierId`: ID del proveedor
- `categoryId`: ID de la categoría
- `quantityPerUnit`: Descripción de la cantidad por unidad
- `unitPrice`: Precio unitario en dólares
- `unitsInStock`: Unidades disponibles en almacén
- `unitsOnOrder`: Unidades pendientes de recibir
- `reorderLevel`: Nivel mínimo para reordenar
- `discontinued`: Indicador de si el producto está descontinuado

---

## Códigos de Estado HTTP

| Código | Nombre | Descripción |
|--------|--------|-------------|
| **200** | OK | La solicitud fue exitosa |
| **201** | Created | El recurso fue creado exitosamente |
| **400** | Bad Request | Error en los datos de entrada o validación |
| **500** | Internal Server Error | Error interno del servidor |

---

## Notas Adicionales

- **Autenticación:** En esta versión, no se requiere autenticación.
- **Rate Limiting:** No hay limitación de velocidad implementada.
- **CORS:** Debe configurarse si el API se consume desde dominios diferentes.
- **Base de Datos:** Se utiliza MySQL con la base de datos Northwind.
- **Framework:** Spring Boot 3.5.7 con Spring Data JPA.

---



Documentación  para el Laboratorio 8 - GTICS

**Fecha:** 2 de Noviembre de 2025

