package com.example.demo.web.dao;

import com.example.demo.web.model.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

/**
 * Un DAO permet la communication avec une base de donnée
 * Un des points forts de Spring Boot est qu'il gère tout seul la communication avec une DB
 * De plus, il peut également développer par lui même des fonctions, grâce aux conventions d'écriture
 * De ce fait, pas besoin d'implémenter notre interface, la définition suffit
 */
public interface ProductDao extends JpaRepository<Product, Integer> {
    /**
     * Ici, on indique a spring boot que notre méthode doit trouver (find) un unique produit via sa propriété Id
     * @param id
     * @return
     */
    Product findById(int id);

    /**
     * Même chose ici avec le prix, mais on précise une condition where > prixLimit
     * @param prixLimit
     * @return
     */
    List<Product> findByPrixGreaterThan(int prixLimit);

    /**
     * Dans le cas où on a besoin d'une requête complexe (non définissable par convention), il est possible de l'indiquer via l'annotation Query
     * @param limit
     * @return
     */
    @Query("SELECT p FROM Product p WHERE p.prix > ?1")
    List<Product> findExpensiveProduct(int limit);
}
