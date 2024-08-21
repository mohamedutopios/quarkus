package org.agoncal.quarkus.microservices.book.resource;


import jakarta.inject.Inject;
import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.*;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import org.agoncal.quarkus.microservices.book.entity.Personne;
import org.agoncal.quarkus.microservices.book.service.PersonneService;

import java.util.List;
import java.util.Optional;

@Path("/personnes")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class PersonneResource {

    @Inject
    PersonneService personneService;

    @POST
    public Response creerPersonne(Personne personne) {
        Personne nouvellePersonne = personneService.creerPersonne(personne.getNom(), personne.getPrenom(), personne.getAge());
        return Response.ok(nouvellePersonne).status(Response.Status.CREATED).build();
    }

    @GET
    public List<Personne> toutesLesPersonnes() {
        return personneService.toutesLesPersonnes();
    }

    @GET
    @Path("/{id}")
    public Response trouverPersonneParId(@PathParam("id") Long id) {
        Optional<Personne> personne = personneService.trouverParId(id);
        return personne.map(value -> Response.ok(value).build())
                .orElseGet(() -> Response.status(Response.Status.NOT_FOUND).build());
    }

    @GET
    @Path("/nom/{nom}")
    public List<Personne> trouverParNom(@PathParam("nom") String nom) {
        return personneService.trouverParNom(nom);
    }

    @DELETE
    @Path("/{id}")
    public Response supprimerPersonne(@PathParam("id") Long id) {
        personneService.supprimerPersonne(id);
        return Response.noContent().build();
    }

    @PUT
    @Path("/{id}")
    public Response mettreAJourPersonne(@PathParam("id") Long id, Personne personne) {
        Personne miseAJour = personneService.mettreAJourPersonne(id, personne.getNom(), personne.getPrenom(), personne.getAge());
        if (miseAJour == null) {
            return Response.status(Response.Status.NOT_FOUND).build();
        }
        return Response.ok(miseAJour).build();
    }
}
