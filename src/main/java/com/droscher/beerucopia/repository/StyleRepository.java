package com.droscher.beerucopia.repository;

import com.droscher.beerucopia.domain.Style;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

/**
 * Created by simon on 2014-08-17.
 */
@RepositoryRestResource(collectionResourceRel = "styles", path = "styles")
public interface StyleRepository extends MongoRepository<Style, String> {

    public Style findByName(@Param("name") String name);

}
