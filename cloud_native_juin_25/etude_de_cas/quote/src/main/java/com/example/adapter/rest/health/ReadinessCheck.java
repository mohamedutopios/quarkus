package com.example.adapter.rest.health;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.persistence.EntityManager;
import org.eclipse.microprofile.health.HealthCheck;
import org.eclipse.microprofile.health.HealthCheckResponse;
import org.eclipse.microprofile.health.Readiness;

@Readiness
@ApplicationScoped
public class ReadinessCheck implements HealthCheck {

    @Inject
    EntityManager entityManager;
    @Override
    public HealthCheckResponse call() {
        try {
            entityManager.createNativeQuery("SELECT 1").getSingleResult();
            return HealthCheckResponse.up("Application is Ready");
        }catch (Exception ex) {
            return HealthCheckResponse.down(ex.getMessage());
        }
    }
}
