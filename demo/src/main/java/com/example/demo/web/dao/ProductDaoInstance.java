package com.example.demo.web.dao;

import com.example.demo.web.model.Product;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;

@Repository
public class ProductDaoInstance implements ProductDao{
    public static List<Product> products = new ArrayList<>();

    static {
        products.add(new Product(1, "Test1", 300, 120));
        products.add(new Product(2, "Test2", 500, 200));
        products.add(new Product(3, "Test3", 700, 400));
    }

    @Override
    public List<Product> findAll() {
        return products;
    }

    @Override
    public Product findById(int id) {
        return products.stream().filter(product -> product.getId() == id).findAny().orElse(null);
    }

    @Override
    public Product save(Product product) {
        products.add(product);
        return product;
    }
}
