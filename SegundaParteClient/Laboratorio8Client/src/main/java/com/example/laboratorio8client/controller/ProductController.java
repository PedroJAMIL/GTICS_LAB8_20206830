package com.example.laboratorio8client.controller;

import com.example.laboratorio8client.dto.Product;
import com.example.laboratorio8client.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

/**
 * Controlador Web que maneja las vistas HTML con Thymeleaf.
 * No retorna JSON, retorna nombres de plantillas HTML.
 */
@Controller
public class ProductController {

    @Autowired
    private ProductService productService;

    /**
     * Página principal que lista todos los productos.
     * También muestra el formulario de búsqueda.
     * 
     * @param model Modelo para pasar datos a la vista
     * @return Nombre de la plantilla Thymeleaf
     */
    @GetMapping("/")
    public String index(Model model) {
        // Obtener todos los productos del API
        List<Product> products = productService.getAllProducts();
        
        // Añadir la lista al modelo para que la vista pueda acceder
        model.addAttribute("products", products);
        
        return "index"; // Retorna la vista index.html
    }

    /**
     * Endpoint para buscar un producto por ID.
     * Se activa cuando el usuario envía el formulario de búsqueda.
     * 
     * @param id ID del producto a buscar
     * @param model Modelo para pasar datos a la vista
     * @return Nombre de la plantilla Thymeleaf
     */
    @GetMapping("/buscar")
    public String buscarProducto(@RequestParam(required = false) Integer id, Model model) {
        // Primero, obtenemos todos los productos para la tabla
        List<Product> products = productService.getAllProducts();
        model.addAttribute("products", products);

        // Si no se proporcionó un ID, solo mostramos la tabla
        if (id == null) {
            model.addAttribute("error", "Por favor ingrese un ID de producto");
            return "index";
        }

        // Buscar el producto por ID
        Product product = productService.getProductById(id);

        if (product != null) {
            // Si se encontró, añadirlo al modelo
            model.addAttribute("productoBuscado", product);
            model.addAttribute("success", "Producto encontrado exitosamente");
        } else {
            // Si no se encontró, mostrar mensaje de error
            model.addAttribute("error", "El producto con ID " + id + " no existe o el ID no es válido");
        }

        return "index"; // Retorna la misma vista
    }
}
