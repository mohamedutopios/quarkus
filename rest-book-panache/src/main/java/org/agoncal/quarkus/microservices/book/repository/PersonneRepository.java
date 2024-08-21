package org.agoncal.quarkus.microservices.book.repository;


import io.quarkus.hibernate.orm.panache.PanacheRepository;
import io.quarkus.panache.common.Parameters;
import io.quarkus.panache.common.Sort;
import jakarta.enterprise.context.ApplicationScoped;
import org.agoncal.quarkus.microservices.book.entity.Personne;

import java.util.List;
import java.util.Optional;

@ApplicationScoped
public class PersonneRepository implements PanacheRepository<Personne> {

    // 1. Recherche par un seul champ
    public List<Personne> findByNom(String nom) {
        return find("nom", nom).list();
    }

    // 2. Recherche par plusieurs champs
    public List<Personne> findByNomAndPrenom(String nom, String prenom) {
        return find("nom = ?1 and prenom = ?2", nom, prenom).list();
    }

    // 3. Utilisation de paramètres nommés
    public List<Personne> findByNomAndPrenomNomme(String nom, String prenom) {
        return find("nom = :nom and prenom = :prenom",
                Parameters.with("nom", nom).and("prenom", prenom)).list();
    }

    // 4. Recherche dynamique
    public List<Personne> findWithDynamicQuery(String nom, Integer age) {
        String query = "";
        if (nom != null) {
            query += "nom = :nom ";
        }
        if (age != null) {
            if (!query.isEmpty()) {
                query += "and ";
            }
            query += "age = :age";
        }

        return find(query, Parameters.with("nom", nom).and("age", age)).list();
    }

    // 5. Utilisation des opérateurs de comparaison
    public List<Personne> findByAgeGreaterThan(int age) {
        return find("age > ?1", age).list();
    }

    public List<Personne> findByAgeBetween(int minAge, int maxAge) {
        return find("age >= ?1 and age <= ?2", minAge, maxAge).list();
    }

    // 6. Utilisation de fonctions SQL
    public List<Personne> findByNomStartingWith(String prefix) {
        return find("lower(nom) like lower(?1)", prefix + "%").list();
    }

    public List<Personne> findByNomEndingWith(String suffix) {
        return find("nom like ?1", "%" + suffix).list();
    }

    // Trier par nom ascendant
    public List<Personne> findAndSortByNom() {
        return find("age > ?1 order by nom asc", 18).list();
    }

    // Trier par nom descendant
    public List<Personne> findAndSortByNomDesc() {
        return find("age > ?1 order by nom desc", 18).list();
    }

    // 8. Pagination des résultats
    public List<Personne> findPaged(int page, int pageSize) {
        return find("age > ?1", 18).page(page, pageSize).list();
    }

    // 9. Recherche d'un seul résultat
    public Personne findByExactNom(String nom) {
        return find("nom", nom).firstResult();
    }

    public Optional<Personne> findOptionalByExactNom(String nom) {
        return find("nom", nom).firstResultOptional();
    }

    // 10. Utilisation de sous-requêtes
    public List<Personne> findWithSubQuery() {
        return find("age > (select avg(age) from Personne)").list();
    }
}

