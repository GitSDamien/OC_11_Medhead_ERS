package com.medhead.fake.web.controller;

import com.fasterxml.jackson.databind.ser.FilterProvider;
import com.fasterxml.jackson.databind.ser.impl.SimpleBeanPropertyFilter;
import com.fasterxml.jackson.databind.ser.impl.SimpleFilterProvider;
import com.medhead.fake.model.HospitalSpec;
import com.medhead.fake.model.Hospitals;
import com.medhead.fake.model.NHSSpec;
import com.medhead.fake.web.dao.HospitalSpecDao;
import com.medhead.fake.web.dao.HospitalsDao;
import com.medhead.fake.web.dao.NHSSpecDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.converter.json.MappingJacksonValue;
import org.springframework.web.bind.annotation.*;

@RestController
public class FakeController {

    @Autowired
    private HospitalsDao hospitalsDao;

    @Autowired
    private NHSSpecDao nHSSpecDao;

    @Autowired
    private HospitalSpecDao hospitalSpecDao;

    @RequestMapping(value = "/NHSSpec", method = RequestMethod.GET)
    public MappingJacksonValue getList() {
        Iterable<NHSSpec> specs = nHSSpecDao.findAll();
        SimpleBeanPropertyFilter monFiltre = SimpleBeanPropertyFilter.serializeAllExcept("prixAchat");
        FilterProvider listDeNosFiltres = new SimpleFilterProvider().addFilter("monFiltreDynamique", monFiltre);
        MappingJacksonValue specsFiltres = new MappingJacksonValue(specs);
        specsFiltres.setFilters(listDeNosFiltres);
        return specsFiltres;
    }

    @RequestMapping(value = "/Hospital", method = RequestMethod.GET)
    public MappingJacksonValue getHospital() {
        Iterable<Hospitals> liste = hospitalsDao.findAll();
        SimpleBeanPropertyFilter monFiltre = SimpleBeanPropertyFilter.serializeAllExcept("prixAchat");
        FilterProvider listDeNosFiltres = new SimpleFilterProvider().addFilter("monFiltreDynamique", monFiltre);
        MappingJacksonValue listeFiltres = new MappingJacksonValue(liste);
        listeFiltres.setFilters(listDeNosFiltres);
        return listeFiltres;
    }

    @GetMapping(value = "/Hospital/{id}")
    public Hospitals afficher(@PathVariable int id) {
        Hospitals hospital = hospitalsDao.findById(id);
        return hospital;
    }

    @RequestMapping(value = "/HospitalSpec", method = RequestMethod.GET)
    public MappingJacksonValue getHospitalSpec() {
        Iterable<HospitalSpec> liste = hospitalSpecDao.findAll();
        SimpleBeanPropertyFilter monFiltre = SimpleBeanPropertyFilter.serializeAllExcept("prixAchat");
        FilterProvider listDeNosFiltres = new SimpleFilterProvider().addFilter("monFiltreDynamique", monFiltre);
        MappingJacksonValue listeFiltres = new MappingJacksonValue(liste);
        listeFiltres.setFilters(listDeNosFiltres);
        return listeFiltres;
    }

}
