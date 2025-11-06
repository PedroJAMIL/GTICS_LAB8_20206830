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
        Integer productoId;
        try {
            productoId = Integer.parseInt(id);
        } catch (NumberFormatException e) {
            ErrorResponse error = new ErrorResponse(400, "El ID del producto debe ser un número válido");
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(error);
        }

        Optional<Product> producto = productRepository.findById(productoId);

        if (producto.isPresent()) {
            return ResponseEntity.ok(producto.get());
        } else {
            ErrorResponse error = new ErrorResponse(400, "El producto con ID " + productoId + " no existe");
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(error);
        }
    }

    @PostMapping
    public ResponseEntity<?> crearProducto(@RequestBody ProductRequest productRequest) {
        try {
            Product nuevoProducto = new Product();
            nuevoProducto.setProductName(productRequest.getProductName());
            nuevoProducto.setSupplierId(productRequest.getSupplierId());
            nuevoProducto.setCategoryId(productRequest.getCategoryId());
            nuevoProducto.setUnit(productRequest.getUnit());
            nuevoProducto.setPrice(productRequest.getPrice());

            Product productoGuardado = productRepository.save(nuevoProducto);

            SuccessResponse respuesta = new SuccessResponse(true, "Producto creado exitosamente", productoGuardado);
            return ResponseEntity.status(HttpStatus.CREATED).body(respuesta);

        } catch (Exception e) {
            ErrorResponse error = new ErrorResponse(400, "Error al crear el producto: " + e.getMessage());
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(error);
        }
    }

    @PutMapping
    public ResponseEntity<?> actualizarProducto(@RequestBody ProductRequest productRequest) {
        if (productRequest.getProductId() == null || productRequest.getProductId() <= 0) {
            ErrorResponse error = new ErrorResponse(400, "El ID del producto es obligatorio y debe ser válido");
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(error);
        }

        try {
            Optional<Product> productoExistente = productRepository.findById(productRequest.getProductId());

            if (productoExistente.isEmpty()) {
                ErrorResponse error = new ErrorResponse(400, "El producto con ID " + productRequest.getProductId() + " no existe");
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(error);
            }

            Product producto = productoExistente.get();
            producto.setProductName(productRequest.getProductName());
            producto.setSupplierId(productRequest.getSupplierId());
            producto.setCategoryId(productRequest.getCategoryId());
            producto.setUnit(productRequest.getUnit());
            producto.setPrice(productRequest.getPrice());

            Product productoActualizado = productRepository.save(producto);

            SuccessResponse respuesta = new SuccessResponse(true, "Producto actualizado exitosamente", productoActualizado);
            return ResponseEntity.ok(respuesta);

        } catch (Exception e) {
            ErrorResponse error = new ErrorResponse(400, "Error al actualizar el producto: " + e.getMessage());
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(error);
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> eliminarProducto(@PathVariable String id) {
        Integer productoId;
        try {
            productoId = Integer.parseInt(id);
        } catch (NumberFormatException e) {
            ErrorResponse error = new ErrorResponse(400, "El ID del producto debe ser un número válido");
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(error);
        }

        try {
            Optional<Product> productoExistente = productRepository.findById(productoId);

            if (productoExistente.isEmpty()) {
                ErrorResponse error = new ErrorResponse(400, "El producto con ID " + productoId + " no existe");
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(error);
            }

            productRepository.deleteById(productoId);

            SuccessResponse respuesta = new SuccessResponse(true, "Producto eliminado exitosamente");
            return ResponseEntity.ok(respuesta);

        } catch (Exception e) {
            ErrorResponse error = new ErrorResponse(400, "Error al eliminar el producto: " + e.getMessage());
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(error);
        }
    }
}
