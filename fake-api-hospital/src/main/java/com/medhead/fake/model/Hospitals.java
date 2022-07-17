package com.medhead.fake.model;

import javax.persistence.Entity;
import javax.persistence.Id;

@Entity
public class Hospitals {

    @Id
    //@GeneratedValue
    private int id;

    private String name;

    private int bed;


    private double latitude;

    private double longitude;
    private double distance;
    private String distancestr;
    private String duration;

    public Hospitals() { }

    public Hospitals(int id, String name, int bed, double latitude, double longitude) {
        this.id = id;
        this.name = name;
        this.bed = bed;
        this.latitude = latitude;
        this.longitude = longitude;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getBed() {
        return bed;
    }

    public void setBed(int bed) {
        this.bed = bed;
    }

    public double getLatitude() {
        return latitude;
    }

    public void setLatitude(double latitude) {
        this.latitude = latitude;
    }

    public double getLongitude() {
        return longitude;
    }

    public void setLongitude(double longitude) {
        this.longitude = longitude;
    }

    public double getDistance() {
        return distance;
    }

    public void setDistance(double distance) {
        this.distance = distance;
    }


    public String getDistancestr() {
        return distancestr;
    }

    public void setDistancestr(String distanceStr) {
        this.distancestr = distanceStr;
    }

    public String getDuration() {
        return duration;
    }

    public void setDuration(String duration) {
        this.duration = duration;
    }

    public String latLong(){
        return this.latitude +","+ this.longitude;
    }

    @Override
    public String toString() {
        return "Hospitals{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", bed=" + bed +
                ", latitude=" + latitude +
                ", longitude=" + longitude +
                '}';
    }
}
