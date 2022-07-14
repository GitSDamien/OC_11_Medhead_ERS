package com.medhead.gateway.model;

import javax.persistence.*;

@Entity
@Table(name = "APIKEY")
public class ApiKey {
    @Id
//    @GeneratedValue
    private int id;

    private String token;

    private String route;

    public ApiKey() { }

    public ApiKey(int id, String token, String route) {
        this.id = id;
        this.token = token;
        this.route = route;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public String getRoute() {
        return route;
    }

    public void setRoute(String routeId) {
        this.route = routeId;
    }

    @Override
    public String toString() {
        return "ApiKey{" +
                "id=" + id +
                ", token='" + token + '\'' +
                ", route='" + route + '\'' +
                '}';
    }
}
