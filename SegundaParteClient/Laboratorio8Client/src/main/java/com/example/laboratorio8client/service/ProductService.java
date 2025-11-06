package com.example.laboratorio8client.service;

import com.example.laboratorio8client.dto.Product;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;

import java.util.Arrays;
import java.util.List;

@Service
public class ProductService {

    @Autowired
    private RestTemplate restTemplate;

    @Value("${api.base.url}")
    private String apiBaseUrl;

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

    public Product getProductById(Integer id) {
        try {
            String url = apiBaseUrl + "/product/" + id;
            return restTemplate.getForObject(url, Product.class);
        } catch (HttpClientErrorException e) {
            System.err.println("Producto no encontrado: " + e.getMessage());
            return null;
        } catch (Exception e) {
            System.err.println("Error al obtener producto: " + e.getMessage());
            return null;
        }
    }
}
