package com.medhead.ers.controller;

import com.fasterxml.jackson.databind.ser.FilterProvider;
import com.fasterxml.jackson.databind.ser.impl.SimpleBeanPropertyFilter;
import com.fasterxml.jackson.databind.ser.impl.SimpleFilterProvider;
import com.medhead.ers.dao.HospitalSpecDao;
import com.medhead.ers.model.BedAvailability;
import com.medhead.ers.model.HospitalSpec;
import com.medhead.ers.model.Hospitals;
import com.medhead.ers.model.LatLong;
import com.medhead.ers.dao.HospitalsDao;
import com.medhead.ers.exceptions.HospitalIntrouvableException;
import com.medhead.ers.service.MainService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.*;
import org.springframework.http.converter.json.MappingJacksonValue;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import org.springframework.web.util.UriComponentsBuilder;

import org.json.*;

import java.net.URI;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

@RestController
public class HospitalsController {
    @Autowired
    private HospitalsDao hospitalsDao;

    @Autowired
    private HospitalSpecDao hospitalSpecDao;

    private MainService mainService = new MainService();

    @PostMapping(value = "/BedAvailability")
    public List<Hospitals> bedAvailability(@RequestBody BedAvailability urgence) {
        LatLong urgencePosition = new LatLong(urgence.getLatitude(), urgence.getLongitude());
        List<Hospitals> rightHospitals = new ArrayList<>();
        List<Hospitals> returnHospitals = new ArrayList<>();

        // liste hopitaux, si + de 24h

        // recherche hopitaux avec spec
        List<Hospitals> listeAll = hospitalsDao.findAll();
        for( Hospitals elemAll : listeAll ){
            List<HospitalSpec> specs = hospitalSpecDao.findByIdHospital(elemAll.getId());
            boolean hfind = false;
            for (HospitalSpec spec : specs){
                if(!hfind && urgence.getSpecs().contains(spec.getIdSpec())){
                    elemAll.setDistance(Math.round(
                        MainService.distance(elemAll.getLatitude(), urgencePosition.getLatitude(), elemAll.getLongitude(), urgencePosition.getLongitude(), 0,0)
                    ));
                    rightHospitals.add(elemAll);
                    hfind = true;
                }
            }
        }

        // trie par distance (a vol d'oiseau)
        rightHospitals.sort(Comparator.comparing(Hospitals::getDistance));

        // boucle recherche dispo lit
        int limiter = 3;
        for( Hospitals elemRight : rightHospitals ){
            int nbBed = mainService.bedAvailable(elemRight.getId());
            if(nbBed > 0){
                HttpEntity<String> reponse = mainService.sendDistanceMatrix(urgencePosition, elemRight);
                JSONObject obj = new JSONObject(reponse.getBody());

                String status = obj.getString("status");
                if(status.equals("OK")){
                    elemRight.setDistancestr(
                        obj.getJSONArray("rows").getJSONObject(0)
                            .getJSONArray("elements").getJSONObject(0)
                            .getJSONObject("distance").getString("text")
                    );

                    elemRight.setDuration(
                        obj.getJSONArray("rows").getJSONObject(0)
                            .getJSONArray("elements").getJSONObject(0)
                            .getJSONObject("duration").getString("text")
                    );
                }

                returnHospitals.add(elemRight);
                limiter--;
            }
            if(limiter <= 0){
                break;
            }
        }

        return returnHospitals;
    }

    @RequestMapping(value = "/Hospital", method = RequestMethod.GET)
    public MappingJacksonValue getList() {
        Iterable<Hospitals> liste = hospitalsDao.findAll();
        SimpleBeanPropertyFilter monFiltre = SimpleBeanPropertyFilter.serializeAllExcept("distance", "distancestr", "duration");
        FilterProvider listDeNosFiltres = new SimpleFilterProvider().addFilter("filtreHospital", monFiltre);
        MappingJacksonValue listeFiltres = new MappingJacksonValue(liste);
        listeFiltres.setFilters(listDeNosFiltres);
        return listeFiltres;
    }

}
