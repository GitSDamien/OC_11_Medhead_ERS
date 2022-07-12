package com.medhead.ers.service;

import com.medhead.ers.dao.HospitalSpecDao;
import com.medhead.ers.dao.HospitalsDao;
import com.medhead.ers.model.BedAvailability;
import com.medhead.ers.model.HospitalSpec;
import com.medhead.ers.model.Hospitals;
import com.medhead.ers.model.LatLong;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

public class MainService {

    /**
     * Calculate distance between two points in latitude and longitude taking
     * into account height difference. If you are not interested in height
     * difference pass 0.0. Uses Haversine method as its base.
     *
     * lat1, lon1 Start point lat2, lon2 End point el1 Start altitude in meters
     * el2 End altitude in meters
     * @returns Distance in Meters
     */
    public static double distance(double lat1, double lat2, double lon1, double lon2, double el1, double el2) {
        final int R = 6371; // Radius of the earth

        double latDistance = Math.toRadians(lat2 - lat1);
        double lonDistance = Math.toRadians(lon2 - lon1);

        double a = Math.sin(latDistance / 2) * Math.sin(latDistance / 2)
                + Math.cos(Math.toRadians(lat1)) * Math.cos(Math.toRadians(lat2)) * Math.sin(lonDistance / 2)
                * Math.sin(lonDistance / 2);
        double c = 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1 - a));
        double distance = R * c * 1000; // convert to meters

        double height = el1 - el2;

        distance = Math.pow(distance, 2) + Math.pow(height, 2);
        return Math.sqrt(distance);
    }

    public HttpEntity<String> sendDistanceMatrix(LatLong urgencePosition, Hospitals hopital){
        RestTemplate restTemplate = new RestTemplate();
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.TEXT_PLAIN);
        String url = "https://maps.googleapis.com/maps/api/distancematrix/json";
        UriComponentsBuilder builder = UriComponentsBuilder.fromHttpUrl(url)
                .queryParam("key", "AIzaSyD7_CK25gHelf-tuf251ytg3FdX3nu2SVg")
                .queryParam("origins", urgencePosition.toString())
                .queryParam("destinations", hopital.latLong());

        HttpEntity<?> entity = new HttpEntity<>(headers);
        HttpEntity<String> response = restTemplate.exchange(
                builder.toUriString(),
                HttpMethod.GET,
                entity,
                String.class);
        return response;
    }

    public int bedAvailable(int idHospital){
        RestTemplate restTemplate = new RestTemplate();
        HttpHeaders headers = new HttpHeaders();
        //headers.setContentType(MediaType.TEXT_PLAIN);
        //String url = "http://localhost:8082/Hospital/"+ idHospital;
        String url = "http://fake-api-hospital:8082/Hospital/"+ idHospital;
        UriComponentsBuilder builder = UriComponentsBuilder.fromHttpUrl(url);
//                .queryParam("key", "AIzaSyD7_CK25gHelf-tuf251ytg3FdX3nu2SVg")
//                .queryParam("origins", urgencePosition.toString())
//                .queryParam("destinations", hopital.latLong());

        HttpEntity<?> entity = new HttpEntity<>(headers);
        HttpEntity<String> response = restTemplate.exchange(
                builder.toUriString(),
                HttpMethod.GET,
                entity,
                String.class);

        JSONObject obj = new JSONObject(response.getBody());

        return obj.getInt("bed");
    }


    public void refreshHospital(){

    }

    public void refreshNHSSpec(){

    }

}
