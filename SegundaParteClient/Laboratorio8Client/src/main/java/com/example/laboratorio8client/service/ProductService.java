package com.example.laboratorio8client.service;

import com.example.laboratorio8client.dto.Product;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;

import java.util.Arrays;
import java.util.List;

/**
 * Servicio que consume el API REST de productos.
 * Utiliza RestTemplate para hacer las peticiones HTTP.
 */
@Service
public class ProductService {

    @Autowired
    private RestTemplate restTemplate;

    @Value("${api.base.url}")
    private String apiBaseUrl;

    /**
     * Obtiene todos los productos del API.
     * Consume: GET http://localhost:8080/product
     * 
     * @return Lista de productos
     */
    public List<Product> getAllProducts() {
        try {
            String url = apiBaseUrl + "/product";
            Product[] products = restTemplate.getForObject(url, Product[].class);
            return products != null ? Arrays.asList(products) : List.of();
        } catch (Exception e) {
            System.err.println("Error al obtener productos: " + e.getMessage());
            return List.of();
        }
    }

    /**
     * Obtiene un producto por su ID del API.
     * Consume: GET http://localhost:8080/product/{id}
     * 
     * @param id ID del producto
     * @return Producto encontrado o null si no existe
     */
    public Product getProductById(Integer id) {
        try {
            String url = apiBaseUrl + "/product/" + id;
            return restTemplate.getForObject(url, Product.class);
        } catch (HttpClientErrorException e) {
            // Si el API retorna 400 o 404, retornamos null
            System.err.println("Producto no encontrado: " + e.getMessage());
            return null;
        } catch (Exception e) {
            System.err.println("Error al obtener producto: " + e.getMessage());
            return null;
        }
    }
}
