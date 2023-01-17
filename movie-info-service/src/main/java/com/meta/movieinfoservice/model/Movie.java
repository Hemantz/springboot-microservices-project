package com.meta.movieinfoservice.model;

public class Movie {
    private String name;
    private String description;

    public Movie(String name, String description) {
        this.name = name;
        this.description = description;
    }

    public Movie() {
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
