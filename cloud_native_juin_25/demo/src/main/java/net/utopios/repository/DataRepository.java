package net.utopios.repository;

import io.quarkus.hibernate.orm.panache.PanacheRepository;
import jakarta.enterprise.context.ApplicationScoped;
import net.utopios.entity.DataContent;

@ApplicationScoped
public class DataRepository implements PanacheRepository<DataContent> {
}
