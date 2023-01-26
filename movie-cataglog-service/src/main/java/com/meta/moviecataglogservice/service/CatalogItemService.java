package com.meta.moviecataglogservice.service;

import com.meta.moviecataglogservice.model.CatalogItem;
import com.meta.moviecataglogservice.model.Movie;
import com.meta.moviecataglogservice.model.Rating;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixProperty;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.client.RestTemplate;

import java.util.Collections;
import java.util.List;

@Service
public class CatalogItemService {

    @Autowired
    RestTemplate restTemplate;
    @Value("${movie.movieInfo}")
    private String movieInfoLink;
    @HystrixCommand(
            fallbackMethod = "getFallbackCatalogItem",
            threadPoolKey = "movieInfoService",
            threadPoolProperties ={
                    @HystrixProperty(name = "coreSize", value = "20"),
                    @HystrixProperty(name = "maxQueueSize", value = "10")
            }
    )
    public CatalogItem getCatalogItem(Rating rating) {
        Movie movie = restTemplate.getForObject(movieInfoLink + rating.getMovieId(), Movie.class);
        return new CatalogItem(movie.getName(), movie.getDescription(), rating.getRating());
    }

    private CatalogItem getFallbackCatalogItem(Rating rating){
        return new CatalogItem("Not found","No movie found",rating.getRating());
    }
}
