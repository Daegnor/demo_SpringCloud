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

/**
 * Controller pour gérer les produits
 * On précise l'annotation RestController, ceci permet au framework de savoir que non seulement notre classe est un controlleur,
 * mais qu'en plus ses output doivent être formaté en JSON
 * De ce fait, le framework s'occupe tout seul de formater nos retour, tant que c'est possible (il ne pourra pas transformer un type de base en JSON par exemple)
 * Les routes sont indiqués par les annotations GetMapping, PostMapping, ...
 */
@RestController
public class ProductController {
    private final String BASE_URL = "/products";
    private final String PRODUCT_ENDPOINT = "/{id}";
    private final String PRODUCT_ROUTE = BASE_URL + PRODUCT_ENDPOINT;
    private final String FILTER_GREATER_THAN_ENDPOINT = "/greaterThan/{limit}";
    private final String FILTER_GREATER_THAN_ROUTE = BASE_URL + FILTER_GREATER_THAN_ENDPOINT;
    private final String FILTER_EXPENSIVE_ENDPOINT = "/expensive/{limit}";
    private final String FILTER_EXPENSIVE_ROUTE = BASE_URL + FILTER_EXPENSIVE_ENDPOINT;

    /**
     * SpringBoot fonctionne par injection de dépendance (Inversion de controle),
     * c'est à dire que le framework va initialiser les objects dont notre classe a besoin, et on va juste les récupérer
     * Ici, on indique que le controller a besoin du DAO pour la table Products (via l'annotation Autowired).
     * SpringBoot va donc créer la dépendance (appelée bean) et la donner au constructeur
     * @param productDao
     */
    @Autowired
    private final ProductDao productDao;

    public ProductController(ProductDao productDao) {
        this.productDao = productDao;
    }

    /**
     * Cette méthode permet de récupérer tous les produits.
     * Normalement, retourner productDao.findAll() suffirait, mais ici on en fait un peu plus
     * Ici, on va en + filtrer les données de nos produits. En effet, par défaut spring boot renvoie en json l'intégralité des données de nos produits
     * Cependant, dans les cas où on veut omettre certaines données (ici, prixAchat), on peut passer par des filtres
     * MappingJacksonValue va permettre de récupérer une liste d'éléments, et de construire le json en applicant nos filtres (filtrage dynamique)
     * @return
     */
    @GetMapping(BASE_URL)
    public MappingJacksonValue listeProduits(){
        Iterable<Product> products = productDao.findAll();

        SimpleBeanPropertyFilter myFilter = SimpleBeanPropertyFilter.serializeAllExcept("prixAchat");
        FilterProvider listFilters = new SimpleFilterProvider().addFilter(Product.KEY_DYNAMIC_FILTER, myFilter);

        MappingJacksonValue filteredProducts = new MappingJacksonValue(products);
        filteredProducts.setFilters(listFilters);

        return filteredProducts;
    }

    /**
     * Ici, on renvoie juste notre produit
     * Spring Boot va, par lui même, construire le json
     * (pas de 404 s'il ne trouve pas, il faudrait définir une responseEntity pour ça)
     * PathVariable permet d'indiquer que le paramètre se trouve dans la route
     * @param id
     * @return
     */
    @GetMapping(PRODUCT_ROUTE)
    public Product displayProduct(@PathVariable int id){
        return productDao.findById(id);
    }

    @GetMapping(FILTER_GREATER_THAN_ROUTE)
    public List<Product> filterByPrice(@PathVariable int limit){
        return productDao.findByPrixGreaterThan(limit);
    }

    @GetMapping(FILTER_EXPENSIVE_ROUTE)
    public List<Product> findExpensive(@PathVariable int limit){
        return productDao.findExpensiveProduct(limit);
    }

    /**
     * Ici, on renvoie une ResponseEntity
     * Par défaut, Spring Boot gère tout seul les codes de retour, en fonction de si ça plante ou si tout passe / on renvoie des données
     * ResponseEntity permet de définir explicitement ce qu'on renvoie
     * @param product l'annotation RequestBody permet de préciser que l'object est dans le body de la requête
     *                spring boot créé automatiquement la produit, en le remplissant soit avec les données de la requête,
     *                soit les valeurs par défaut si la donnée n'est pas présente
     * @return
     */
    @PostMapping(BASE_URL)
    public ResponseEntity<Product> addProduct(@RequestBody Product product){
        // On sauvegarde le produit en db
        Product added = productDao.save(product);

        // On créer un nouveau URL. Ce dernier part de notre url actuel (/products), ajoute notre endpoint pour les id,
        // puis le construit avec l'id de notre nouveau produit
        URI location = ServletUriComponentsBuilder
                .fromCurrentRequest()
                .path(PRODUCT_ENDPOINT)
                .buildAndExpand(added.getId())
                .toUri();

        // Ici, on indique qu'il faut renvoyer un code CREATED, et on précise un URL de redirection (la page du produit créé)
        return ResponseEntity.created(location).build();
    }

    @DeleteMapping(PRODUCT_ROUTE)
    public void deleteProduct(@PathVariable int id){
        productDao.deleteById(id);
    }

    @PutMapping(BASE_URL)
    public void putProduct(@RequestBody Product product){
        // La fonction save permet l'ajout comme l'update d'un produit
        productDao.save(product);
    }
}
