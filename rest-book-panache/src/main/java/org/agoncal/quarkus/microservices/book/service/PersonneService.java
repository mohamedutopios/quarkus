package org.agoncal.quarkus.microservices.book.service;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;
import org.agoncal.quarkus.microservices.book.entity.Personne;
import org.agoncal.quarkus.microservices.book.repository.PersonneRepository;

import java.util.List;
import java.util.Optional;

@ApplicationScoped
public class PersonneService {

    @Inject
    PersonneRepository personneRepository;

    @Transactional
    public Personne creerPersonne(String nom, String prenom, int age) {
        Personne personne = new Personne();
        personne.setNom(nom);
        personne.setPrenom(prenom);
        personne.setAge(age);
        personneRepository.persist(personne);
        return personne;
    }

    public List<Personne> toutesLesPersonnes() {
        return personneRepository.listAll();
    }

    public Optional<Personne> trouverParId(Long id) {
        return personneRepository.findByIdOptional(id);
    }

    public List<Personne> trouverParNom(String nom) {
        return personneRepository.findByNom(nom);
    }

    @Transactional
    public void supprimerPersonne(Long id) {
        personneRepository.deleteById(id);
    }

    @Transactional
    public Personne mettreAJourPersonne(Long id, String nouveauNom, String nouveauPrenom, int nouvelAge) {
        Personne personne = personneRepository.findById(id);
        if (personne != null) {
            personne.setNom(nouveauNom);
            personne.setPrenom(nouveauPrenom);
            personne.setAge(nouvelAge);
        }
        return personne;
    }
}

