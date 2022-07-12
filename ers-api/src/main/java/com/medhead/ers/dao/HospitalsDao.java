package com.medhead.ers.dao;

import com.medhead.ers.model.Hospitals;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface HospitalsDao extends JpaRepository<Hospitals, Integer> {
    Hospitals findById(int id);

    List<Hospitals> findByBedGreaterThan(int indice);

}
