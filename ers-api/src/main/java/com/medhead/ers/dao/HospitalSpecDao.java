package com.medhead.ers.dao;

import com.medhead.ers.model.HospitalSpec;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface HospitalSpecDao extends JpaRepository<HospitalSpec, Integer> {

    List<HospitalSpec> findByIdHospital(int id);

}
