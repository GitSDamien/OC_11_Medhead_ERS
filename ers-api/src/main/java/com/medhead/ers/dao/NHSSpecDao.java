package com.medhead.ers.dao;

import com.medhead.ers.model.NHSSpec;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface NHSSpecDao extends JpaRepository<NHSSpec, Integer> {
    NHSSpec findById(int id);

    // List<NHSSpec> findByPrixGreaterThan(int prixLimit);
    // https://docs.spring.io/spring-data/jpa/docs/current/api/org/springframework/data/jpa/repository/JpaRepository.html

}
