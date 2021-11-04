package com.jiahe.app.repositories;

import com.jiahe.app.models.Observation;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ObservationRepository extends JpaRepository<Observation, Long> {
    @Query("""
            SELECT 
                o 
            FROM Observation o 
            WHERE o.itemId = :item_id
            """)
    List<Observation> findByItemId(@Param("item_id") int itemId);
}
