package com.example.demo.web.model;

import com.fasterxml.jackson.annotation.JsonFilter;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;

//@JsonIgnoreProperties({"prixAchat", "id"})
//@JsonFilter(Product.KEY_DYNAMIC_FILTER)
@Entity
public class Product {
    public static final String KEY_DYNAMIC_FILTER = "dynamicFilter";
    @Id
    private int id;
    private String name;
    private int prix;
    private int prixAchat;

    public Product() {

    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getPrix() {
        return prix;
    }

    public void setPrix(int prix) {
        this.prix = prix;
    }

    public int getPrixAchat() {
        return prixAchat;
    }

    public void setPrixAchat(int prixAchat) {
        this.prixAchat = prixAchat;
    }

    @Override
    public String toString() {
        return "Product{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", prix=" + prix +
                '}';
    }
}
