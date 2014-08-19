package com.droscher.beerucopia.repository;

import java.util.List;

import com.droscher.beerucopia.domain.Brewery;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

/**
 * BreweryRepository.
 */
@RepositoryRestResource(collectionResourceRel = "breweries", path = "breweries")
public interface BreweryRepository extends MongoRepository<Brewery, String> {

	public Brewery findByName(@Param("name") String name);

	public List<Brewery> findByCountry(@Param("country") String country);

	public List<Brewery> findByCountryAndRegion(@Param("country") String country, @Param("region") String region);

}
