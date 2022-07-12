package com.medhead.ers.model;


import javax.persistence.Entity;
import javax.persistence.Id;

@Entity
public class HospitalSpec {
    @Id
    //@GeneratedValue
    private int id;

    private int idHospital;

    private int idSpec;

    public HospitalSpec() { }

    public HospitalSpec(int id, int idHospital, int idSpec) {
        this.id = id;
        this.idHospital = idHospital;
        this.idSpec = idSpec;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getIdHospital() {
        return idHospital;
    }

    public void setIdHospital(int idHospital) {
        this.idHospital = idHospital;
    }

    public int getIdSpec() {
        return idSpec;
    }

    public void setIdSpec(int idSpec) {
        this.idSpec = idSpec;
    }

    @Override
    public String toString() {
        return "HospitalSpec{" +
                "id=" + id +
                ", idHospital=" + idHospital +
                ", idSpec=" + idSpec +
                '}';
    }
}

