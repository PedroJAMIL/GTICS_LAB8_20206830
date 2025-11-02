package com.example.laboratorio8.controller;

import com.example.laboratorio8.dto.ErrorResponse;
import com.example.laboratorio8.dto.ProductRequest;
import com.example.laboratorio8.dto.SuccessResponse;
import com.example.laboratorio8.entity.Product;
import com.example.laboratorio8.repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;


@RestController
@RequestMapping("/product")
public class ProductController {

    @Autowired
    private ProductRepository productRepository;

    @GetMapping
    public ResponseEntity<List<Product>> listarProductos() {
        List<Product> productos = productRepository.findAll();
        return ResponseEntity.ok(productos);
    }


    @GetMapping("/{id}")
    public ResponseEntity<?> obtenerProductoPorId(@PathVariable String id) {
        
        // Paso 1: Validar que el ID sea un número entero válido
        Integer productoId;
        try {
            productoId = Integer.parseInt(id);
        } catch (NumberFormatException e) {
            // Si no es un número, retornar error 400
            ErrorResponse error = new ErrorResponse(
                400,
                "El ID del producto debe ser un número válido"
            );
            return ResponseEntity
                .status(HttpStatus.BAD_REQUEST)
                .body(error);
        }

        // Paso 2: Buscar el producto en la BD
        Optional<Product> producto = productRepository.findById(productoId);

        // Paso 3: Si existe, retornar con status 200
        if (producto.isPresent()) {
            return ResponseEntity.ok(producto.get());
        } else {
            // Si no existe, retornar error 400
            ErrorResponse error = new ErrorResponse(
                400,
                "El producto con ID " + productoId + " no existe"
            );
            return ResponseEntity
                .status(HttpStatus.BAD_REQUEST)
                .body(error);
        }
    }

    @PostMapping
    public ResponseEntity<?> crearProducto(@RequestBody ProductRequest productRequest) {
        try {
            // Crear nueva instancia de Product a partir del request
            Product nuevoProducto = new Product();
            nuevoProducto.setProductName(productRequest.getProductName());
            nuevoProducto.setSupplierId(productRequest.getSupplierId());
            nuevoProducto.setCategoryId(productRequest.getCategoryId());
            nuevoProducto.setQuantityPerUnit(productRequest.getQuantityPerUnit());
            nuevoProducto.setUnitPrice(productRequest.getUnitPrice());
            nuevoProducto.setUnitsInStock(productRequest.getUnitsInStock());
            nuevoProducto.setUnitsOnOrder(productRequest.getUnitsOnOrder());
            nuevoProducto.setReorderLevel(productRequest.getReorderLevel());
            nuevoProducto.setDiscontinued(productRequest.getDiscontinued());

            // Guardar el producto en la BD
            Product productoGuardado = productRepository.save(nuevoProducto);

            // Retornar respuesta exitosa con status 201 (Created)
            SuccessResponse respuesta = new SuccessResponse(
                true,
                "Producto creado exitosamente",
                productoGuardado
            );

            return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(respuesta);

        } catch (Exception e) {
            // Si hay error, retornar error 400
            ErrorResponse error = new ErrorResponse(
                400,
                "Error al crear el producto: " + e.getMessage()
            );
            return ResponseEntity
                .status(HttpStatus.BAD_REQUEST)
                .body(error);
        }
    }


    @PutMapping
    public ResponseEntity<?> actualizarProducto(@RequestBody ProductRequest productRequest) {
        
        // Paso 1: Validar que se proporcione un ID
        if (productRequest.getProductId() == null || productRequest.getProductId() <= 0) {
            ErrorResponse error = new ErrorResponse(
                400,
                "El ID del producto es obligatorio y debe ser válido"
            );
            return ResponseEntity
                .status(HttpStatus.BAD_REQUEST)
                .body(error);
        }

        try {
            // Paso 2: Buscar el producto en la BD
            Optional<Product> productoExistente = productRepository.findById(productRequest.getProductId());

            if (productoExistente.isEmpty()) {
                // Si no existe, retornar error 400
                ErrorResponse error = new ErrorResponse(
                    400,
                    "El producto con ID " + productRequest.getProductId() + " no existe"
                );
                return ResponseEntity
                    .status(HttpStatus.BAD_REQUEST)
                    .body(error);
            }

            // Paso 3: Actualizar los datos del producto
            Product producto = productoExistente.get();
            producto.setProductName(productRequest.getProductName());
            producto.setSupplierId(productRequest.getSupplierId());
            producto.setCategoryId(productRequest.getCategoryId());
            producto.setQuantityPerUnit(productRequest.getQuantityPerUnit());
            producto.setUnitPrice(productRequest.getUnitPrice());
            producto.setUnitsInStock(productRequest.getUnitsInStock());
            producto.setUnitsOnOrder(productRequest.getUnitsOnOrder());
            producto.setReorderLevel(productRequest.getReorderLevel());
            producto.setDiscontinued(productRequest.getDiscontinued());

            // Paso 4: Guardar cambios en la BD
            Product productoActualizado = productRepository.save(producto);

            // Retornar respuesta exitosa con status 200 (OK)
            SuccessResponse respuesta = new SuccessResponse(
                true,
                "Producto actualizado exitosamente",
                productoActualizado
            );

            return ResponseEntity.ok(respuesta);

        } catch (Exception e) {
            ErrorResponse error = new ErrorResponse(
                400,
                "Error al actualizar el producto: " + e.getMessage()
            );
            return ResponseEntity
                .status(HttpStatus.BAD_REQUEST)
                .body(error);
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> eliminarProducto(@PathVariable String id) {
        
        // Paso 1: Validar que el ID sea un número válido
        Integer productoId;
        try {
            productoId = Integer.parseInt(id);
        } catch (NumberFormatException e) {
            ErrorResponse error = new ErrorResponse(
                400,
                "El ID del producto debe ser un número válido"
            );
            return ResponseEntity
                .status(HttpStatus.BAD_REQUEST)
                .body(error);
        }

        try {
            // Paso 2: Buscar el producto en la BD
            Optional<Product> productoExistente = productRepository.findById(productoId);

            if (productoExistente.isEmpty()) {
                // Si no existe, retornar error 400
                ErrorResponse error = new ErrorResponse(
                    400,
                    "El producto con ID " + productoId + " no existe"
                );
                return ResponseEntity
                    .status(HttpStatus.BAD_REQUEST)
                    .body(error);
            }

            // Paso 3: Eliminar el producto de la BD
            productRepository.deleteById(productoId);

            // Retornar respuesta exitosa con status 200 (OK)
            SuccessResponse respuesta = new SuccessResponse(
                true,
                "Producto eliminado exitosamente"
            );

            return ResponseEntity.ok(respuesta);

        } catch (Exception e) {
            ErrorResponse error = new ErrorResponse(
                400,
                "Error al eliminar el producto: " + e.getMessage()
            );
            return ResponseEntity
                .status(HttpStatus.BAD_REQUEST)
                .body(error);
        }
    }
}
