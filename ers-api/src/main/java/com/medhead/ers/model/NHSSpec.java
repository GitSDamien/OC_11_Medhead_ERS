package com.medhead.ers.model;


import javax.persistence.Entity;
import javax.persistence.Id;
import javax.validation.constraints.Size;

@Entity
public class NHSSpec {
    @Id
    //@GeneratedValue
    private int id;
    @Size(min = 3, max = 50)
    private String groupe;

    @Size(min = 3, max = 50)
    private String spec;

    public NHSSpec(){ }

    public NHSSpec(int id, String groupe, String spec) {
        this.id = id;
        this.groupe = groupe;
        this.spec = spec;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getGroupe() {
        return groupe;
    }

    public void setGroupe(String groupe) {
        this.groupe = groupe;
    }

    public String getSpec() {
        return spec;
    }

    public void setSpec(String spec) {
        this.spec = spec;
    }

    @Override
    public String toString() {
        return "NHSSpec{" +
                "id=" + id +
                ", groupe='" + groupe + '\'' +
                ", spec='" + spec + '\'' +
                '}';
    }
}
