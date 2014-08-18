package com.droscher.beerucopia.data.mongo;

import org.springframework.data.mongodb.repository.MongoRepository;

/**
 * Created by simon on 2014-08-17.
 */
public interface StyleRepository extends MongoRepository<Style, String> {

    public Style findByName(String name);

}
