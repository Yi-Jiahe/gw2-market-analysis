package com.jiahe.app.services;

import com.jiahe.app.models.Observation;
import com.jiahe.app.repositories.ObservationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ObservationService {
    @Autowired
    private ObservationRepository observationRepository;

    public List<Observation> findAll() {
        return observationRepository.findAll();
    }

    public List<Observation> findByItemId(int itemId) {
        return observationRepository.findByItemId(itemId);
    }
}
