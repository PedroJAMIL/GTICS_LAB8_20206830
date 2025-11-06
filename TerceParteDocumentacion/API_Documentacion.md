# Documentación API - Laboratorio 8 GTICS

Documentación de los endpoints del API REST para gestionar productos de la base de datos Northwind.

**URL Base:** `http://localhost:8080`

---

## 1. GET /product - Listar todos los productos

Obtiene la lista completa de productos de la base de datos.

**URL:** GET /product

**Respuesta exitosa (200):**
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
  }
]
```

---

## 2. GET /product/{id} - Obtener producto por ID

Obtiene un producto específico según su ID.

**URL:** GET /product/{id}

**Parámetros:**
- `id` (Integer) - ID del producto en la URL

**Validaciones:**
- El ID debe ser un número válido
- El producto debe existir en la base de datos

**Respuesta exitosa (200):**
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

**Respuesta de error (400):**
```json
{
  "status": 400,
  "message": "El producto con ID 999 no existe",
  "timestamp": 1762103447336
}
```

---

## 3. POST /product - Crear nuevo producto

Crea un nuevo producto en la base de datos.

**URL:** POST `/product`

**Content-Type:** application/json

**Body (JSON):**
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

**Respuesta exitosa (201):**
```json
{
  "success": true,
  "message": "Producto creado exitosamente",
  "data": {
    "productId": 80,
    "productName": "Laptop Gaming",
    ...
  },
  "timestamp": 1762104617334
}
```

---

## 4. PUT /product - Actualizar producto

Actualiza un producto existente.

**URL:** PUT `/product`

**Content-Type:** application/json

**Body (JSON):**
```json
{
  "productId": 80,
  "productName": "Laptop Gaming - ACTUALIZADO",
  "unitPrice": 2000,
  "unitsInStock": 10
}
```

**Nota:** El productId es obligatorio. Los demás campos son opcionales.

**Respuesta exitosa (200):**
```json
{
  "success": true,
  "message": "Producto actualizado exitosamente",
  "data": { ... },
  "timestamp": 1762104666986
}
```

---

## 5. DELETE /product/{id} - Eliminar producto

Elimina un producto de la base de datos.

**URL:** DELETE `/product/{id}`

**Parámetros:**
- `id` (Integer) - ID del producto a eliminar

**Respuesta exitosa (200):**
```json
{
  "success": true,
  "message": "Producto eliminado exitosamente",
  "data": null,
  "timestamp": 1762104715387
}
```

**Respuesta de error (400):**
```json
{
  "status": 400,
  "message": "El producto con ID 999 no existe",
  "timestamp": 1762104715387
}
```

---

## Estructura del objeto Product

Todos los endpoints que devuelven productos usan esta estructura:

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

**Campos:**
- productId: ID único del producto (generado automáticamente)
- productName: Nombre del producto
- supplierId: ID del proveedor
- categoryId: ID de la categoría
- quantityPerUnit: Cantidad por unidad
- unitPrice: Precio unitario
- unitsInStock: Unidades disponibles
- unitsOnOrder: Unidades pedidas
- reorderLevel: Nivel de reorden
- discontinued: Si está descontinuado (true/false)

---
## Notas

- No se requiere autenticación
- Todas las respuestas son en formato JSON
- Base de datos: MySQL (Northwind)

Laboratorio 8 - GTICS  
Fecha: 2 de Noviembre de 2025

