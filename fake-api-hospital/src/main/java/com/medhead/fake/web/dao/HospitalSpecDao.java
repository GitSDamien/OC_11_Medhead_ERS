package com.medhead.fake.web.dao;

import com.medhead.fake.model.HospitalSpec;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface HospitalSpecDao extends JpaRepository<HospitalSpec, Integer> {
    HospitalSpec findById(int id);
}
