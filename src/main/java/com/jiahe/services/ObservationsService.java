package com.jiahe.services;

import com.jiahe.models.Observation;
import com.jiahe.repositories.ObservationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ObservationsService {
    @Autowired
    private ObservationRepository observationRepository;

    public List<Observation> findByItemId(int itemId) {
        return observationRepository.findByItemId(itemId);
    }
}
