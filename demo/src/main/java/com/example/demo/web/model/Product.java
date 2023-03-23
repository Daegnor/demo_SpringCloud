package com.example.demo.web.model;

import com.fasterxml.jackson.annotation.JsonFilter;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

/**
 * Ici, on définit une table (Entity) de notre DB
 * Il est possible d'indiquer des champ a ne pas ajouter lors de la convertion en JSON, soit via JsonIgnoreProperties (filtrage fixe, valable partout)
 * Soit par JsonFilter (filtrage dynamique, définit dans les méthodes utilisant l'entité)
 * Seul prob avec le filtrage dynamic : chaque utilisation nécessite de définir le filtre, sinon ça plante
 */
//@JsonIgnoreProperties({"prixAchat", "id"})
//@JsonFilter(Product.KEY_DYNAMIC_FILTER)
@Entity
public class Product {
    public static final String KEY_DYNAMIC_FILTER = "dynamicFilter";
    // On précise avec l'annotation Id qu'il s'agit de l'id du produit, et par GeneratedValue qu'il s'agit d'un serial
    @Id
//    @GeneratedValue(strategy = GenerationType.AUTO) normalement ça marche, mais h2 doit pas supporter les séquence, donc je désactive
    private int id;
    private String name;
    private int prix;
    private int prixAchat;

    // Le constructeur doit être vide, le framework construit les objects via les setter
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
