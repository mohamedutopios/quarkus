package net.utopios.service;


import jakarta.enterprise.context.ApplicationScoped;
import jakarta.transaction.Transactional;
import net.utopios.entity.DataContent;
import net.utopios.repository.DataRepository;

import java.util.Arrays;
import java.util.List;

@ApplicationScoped
public class DataService {

    private final DataRepository dataRepository;

    public DataService(DataRepository dataRepository) {
        this.dataRepository = dataRepository;
    }

    public List<DataContent> getData() {
        return dataRepository.listAll();
    }


    @Transactional
    public DataContent post(DataContent dataContent) {
        dataRepository.persistAndFlush(dataContent);
        return dataContent;
    }

    public boolean isLive() {
        return true;
    }
}
