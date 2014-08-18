package com.droscher.beerucopia.data.mongo;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;

/**
 * Created by simon on 2014-08-17.
 */
public class Style {

    @Id
    private String id;

    @Indexed(unique = true)
    private String name;

    private String key;

    private String ratebeerId;


    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public String getRatebeerId() {
        return ratebeerId;
    }

    public void setRatebeerId(String ratebeerId) {
        this.ratebeerId = ratebeerId;
    }

}
