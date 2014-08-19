package com.droscher.beerucopia.domain;

import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.Id;

import java.math.BigDecimal;
import java.util.*;

/**
 * Created by simon on 2014-08-16.
 */
public class Beer {

    @Id
    private String id;

    private Brewery brewery;
    private String name;
    private Style style;
    private Date dateBottled;
    private BigDecimal abv;
    private Integer size;
    private int quantity;
    private Map<RatingType, BigDecimal> ratings = new HashMap<RatingType, BigDecimal>();
    private String location;
    private boolean tried;
    private Set<String> tags = new HashSet<String>();
    private Availability availability;

    @CreatedDate
    private Date dateAdded;

}
