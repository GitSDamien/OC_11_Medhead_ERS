package com.medhead.ers.controller;

import com.fasterxml.jackson.databind.ser.FilterProvider;
import com.fasterxml.jackson.databind.ser.impl.SimpleBeanPropertyFilter;
import com.fasterxml.jackson.databind.ser.impl.SimpleFilterProvider;
import com.medhead.ers.model.NHSSpec;
import com.medhead.ers.dao.NHSSpecDao;
import com.medhead.ers.exceptions.NHSSpecIntrouvableException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.json.MappingJacksonValue;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;

@RestController
public class NHSSpecController {

    @Autowired
    private NHSSpecDao nHSSpecDao;

    @RequestMapping(value = "/NHSSpec", method = RequestMethod.GET)
    public MappingJacksonValue getList() {
        Iterable<NHSSpec> specs = nHSSpecDao.findAll();
        SimpleBeanPropertyFilter monFiltre = SimpleBeanPropertyFilter.serializeAllExcept("prixAchat");
        FilterProvider listDeNosFiltres = new SimpleFilterProvider().addFilter("monFiltreDynamique", monFiltre);
        MappingJacksonValue specsFiltres = new MappingJacksonValue(specs);
        specsFiltres.setFilters(listDeNosFiltres);
        return specsFiltres;
    }

    @GetMapping(value = "/NHSSpec/{id}")
    public NHSSpec afficher(@PathVariable int id) {
        NHSSpec spec = nHSSpecDao.findById(id);
        if(spec==null) throw new NHSSpecIntrouvableException("L'id " + id + " est INTROUVABLE.");
        return spec;
    }


    @PostMapping(value = "/NHSSpec")
    public ResponseEntity<Void> ajouter(@RequestBody NHSSpec spec) {
        NHSSpec added =  nHSSpecDao.save(spec);
        if (added == null)
            return ResponseEntity.noContent().build();
        URI location = ServletUriComponentsBuilder
                .fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(added.getId())
                .toUri();
        return ResponseEntity.created(location).build();
    }

    @PutMapping (value = "/NHSSpec/{id}")
    public void update(@RequestBody NHSSpec spec)
    {
        nHSSpecDao.save(spec);
    }

    @DeleteMapping (value = "/NHSSpec/{id}")
    public void supprimer(@PathVariable int id) {
        nHSSpecDao.deleteById(id);
    }

}
