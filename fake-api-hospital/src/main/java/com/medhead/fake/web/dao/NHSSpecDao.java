package com.medhead.fake.web.dao;

import com.medhead.fake.model.NHSSpec;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface NHSSpecDao extends JpaRepository<NHSSpec, Integer> {
    NHSSpec findById(int id);
}
