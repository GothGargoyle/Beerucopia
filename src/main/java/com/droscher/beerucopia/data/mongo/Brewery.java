package com.droscher.beerucopia.data.mongo;

import org.springframework.data.annotation.Id;

/**
 * Created by simon on 2014-08-16.
 */
public class Brewery {

    @Id
    private String id;

    private String name;
    private String country;
    private String region;

    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public void setName(final String name) {
        this.name = name;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(final String country) {
        this.country = country;
    }

    public String getRegion() {
        return region;
    }

    public void setRegion(final String region) {
        this.region = region;
    }

}
