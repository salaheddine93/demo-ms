package com.example.demoms;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import org.springframework.data.rest.core.config.RepositoryRestConfiguration;
import org.springframework.web.bind.annotation.*;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.websocket.server.PathParam;
import java.util.List;

@SpringBootApplication
public class DemoMsApplication implements CommandLineRunner {

    public static void main(String[] args) {
        SpringApplication.run(DemoMsApplication.class, args);
    }

    @Autowired
    private ProduitRepository produitRepository;

    @Autowired
    RepositoryRestConfiguration restConfiguration;

    @Override
    public void run(String... args) throws Exception {

        restConfiguration.exposeIdsFor(Produit.class);

        produitRepository.save(new Produit(null,"Laptop",16000,7));
        produitRepository.save(new Produit(null,"Printer",32000,14));
        produitRepository.save(new Produit(null,"Monitor",64000,21));

        produitRepository.findAll().forEach(p -> {
            System.out.println(p.getName());
        });
    }
}

@Entity
@Data @NoArgsConstructor @AllArgsConstructor
class Produit{
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    private double price;
    private double quantity;
}

@RepositoryRestResource
interface ProduitRepository extends JpaRepository<Produit,Long> {
}
/*
@RestController
class ProduitRestController{
    @Autowired
    private ProduitRepository produitRepository;

    @GetMapping(path = "/produits")
    public List<Produit> getAllProducts(){
        return produitRepository.findAll();
    }

    @GetMapping(path = "/produits/{id}")
    public Produit getProductById(@PathVariable Long id){
        return produitRepository.findById(id).get();
    }

    @PostMapping(path = "/produits")
    public Produit saveProduct(@RequestBody Produit produit){
        return produitRepository.save(produit);
    }

    @DeleteMapping(path = "/produits/{id}")
    public void deleteProduct(@PathVariable Long id){
        produitRepository.deleteById(id);
    }

    @PutMapping(path = "/produits/{id}")
    public Produit updateProduct(@PathVariable Long id,@RequestBody Produit produit){
        produit.setId(id);
        return produitRepository.save(produit);
    }
}*/

