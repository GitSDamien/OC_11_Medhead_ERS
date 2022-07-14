package com.medhead.gateway.model;

public class Routes {
    private String path;
    private String uri;
    private String key;

    public Routes() { }

    public Routes(String path, String uri, String key) {
        this.path = path;
        this.uri = uri;
        this.key = key;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public String getUri() {
        return uri;
    }

    public void setUri(String uri) {
        this.uri = uri;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    @Override
    public String toString() {
        return "Routes{" +
                "path='" + path + '\'' +
                ", uri='" + uri + '\'' +
                ", key='" + key + '\'' +
                '}';
    }
}
