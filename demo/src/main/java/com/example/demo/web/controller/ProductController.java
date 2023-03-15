package com.example.demo.web.controller;

import com.example.demo.web.dao.ProductDao;
import com.example.demo.web.model.Product;
import com.fasterxml.jackson.databind.ser.FilterProvider;
import com.fasterxml.jackson.databind.ser.impl.SimpleBeanPropertyFilter;
import com.fasterxml.jackson.databind.ser.impl.SimpleFilterProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.json.MappingJacksonValue;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;

@RestController
public class ProductController {
    private final String BASE_URL = "/products";
    private final String PRODUCT_ENDPOINT = "/{id}";
    private final String PRODUCT_ROUTE = BASE_URL + PRODUCT_ENDPOINT;
    private final String FILTER_GREATER_THEN_ENDPOINT = "/greaterThan/{limit}";
    private final String FILTER_GREATER_THEN_ROUTE = BASE_URL + FILTER_GREATER_THEN_ENDPOINT;
    @Autowired
    private final ProductDao productDao;

    public ProductController(ProductDao productDao) {
        this.productDao = productDao;
    }

    @GetMapping(BASE_URL)
    public MappingJacksonValue listeProduits(){
        Iterable<Product> products = productDao.findAll();
        SimpleBeanPropertyFilter myFilter = SimpleBeanPropertyFilter.serializeAllExcept("prixAchat");
        FilterProvider listFilters = new SimpleFilterProvider().addFilter(Product.KEY_DYNAMIC_FILTER, myFilter);
        MappingJacksonValue filteredProducts = new MappingJacksonValue(products);
        filteredProducts.setFilters(listFilters);
        return filteredProducts;
    }

    @GetMapping(PRODUCT_ROUTE)
    public Product displayProduct(@PathVariable int id){
        return productDao.findById(id);
    }

    @GetMapping(FILTER_GREATER_THEN_ROUTE)
    public List<Product> filterByPrice(@PathVariable int limit){
        return productDao.findByPrixGreaterThan(limit);
//        return productDao.findExpensiveProduct(limit);
    }

    @PostMapping(BASE_URL)
    public ResponseEntity<Product> addProduct(@RequestBody Product product){
        Product added = productDao.save(product);

        URI location = ServletUriComponentsBuilder
                .fromCurrentRequest()
                .path(PRODUCT_ENDPOINT)
                .buildAndExpand(added.getId())
                .toUri();

        return ResponseEntity.created(location).build();
    }

    @DeleteMapping(PRODUCT_ROUTE)
    public void deleteProduct(@PathVariable int id){
        productDao.deleteById(id);
    }

    @PutMapping(BASE_URL)
    public void putProduct(@RequestBody Product product){
        productDao.save(product);
    }
}
