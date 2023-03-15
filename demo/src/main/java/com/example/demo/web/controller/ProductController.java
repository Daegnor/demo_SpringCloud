package com.example.demo.web.controller;

import com.example.demo.web.dao.ProductDao;
import com.example.demo.web.model.Product;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;

@RestController
public class ProductController {
    private final String BASE_URL = "/products";
    private final String PRODUCT_ENDPOINT = "/{id}";
    private final String PRODUCT_ROUTE = BASE_URL + PRODUCT_ENDPOINT;
    private final ProductDao productDao;

    public ProductController(ProductDao productDao) {
        this.productDao = productDao;
    }

    @GetMapping(BASE_URL)
    public List<Product> listeProduits(){
        return productDao.findAll();
    }

    @GetMapping(PRODUCT_ROUTE)
    public Product displayProduct(@PathVariable int id){
        return productDao.findById(id);
    }

    @PostMapping(BASE_URL)
    public ResponseEntity<Product> addProduct(@RequestBody Product product){
        Product added = productDao.save(product);
        if(added == null)
            return ResponseEntity.noContent().build();

        URI location = ServletUriComponentsBuilder
                .fromCurrentRequest()
                .path(PRODUCT_ROUTE)
                .buildAndExpand(added.getId())
                .toUri();

        return ResponseEntity.created(location).build();
    }
}
