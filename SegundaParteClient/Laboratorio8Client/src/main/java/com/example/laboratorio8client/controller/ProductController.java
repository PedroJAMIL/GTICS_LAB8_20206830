package com.example.laboratorio8client.controller;

import com.example.laboratorio8client.dto.Product;
import com.example.laboratorio8client.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@Controller
public class ProductController {

    @Autowired
    private ProductService productService;
   
    @GetMapping("/")
    public String index(Model model) {
        List<Product> products = productService.getAllProducts();
        model.addAttribute("products", products);
        return "index";
    }

    @GetMapping("/buscar")
    public String buscarProducto(@RequestParam(required = false) Integer id, Model model) {
        List<Product> products = productService.getAllProducts();
        model.addAttribute("products", products);

        if (id == null) {
            model.addAttribute("error", "Por favor ingrese un ID de producto");
            return "index";
        }

        Product product = productService.getProductById(id);

        if (product != null) {
            model.addAttribute("productoBuscado", product);
            model.addAttribute("success", "Producto encontrado exitosamente");
        } else {
            model.addAttribute("error", "El producto con ID " + id + " no existe o el ID no es válido");
        }

        return "index";
    }
}
