package com.medhead.fake.web.dao;

import com.medhead.fake.model.Hospitals;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface HospitalsDao extends JpaRepository<Hospitals, Integer> {
    Hospitals findById(int id);

    // List<NHSSpec> findByPrixGreaterThan(int prixLimit);
    // https://docs.spring.io/spring-data/jpa/docs/current/api/org/springframework/data/jpa/repository/JpaRepository.html

}
