package com.meta.moviecataglogservice.resource;

import com.meta.moviecataglogservice.model.CatalogItem;
import com.meta.moviecataglogservice.model.Movie;
import com.meta.moviecataglogservice.model.Rating;
import com.meta.moviecataglogservice.model.UserRating;
import com.meta.moviecataglogservice.service.CatalogItemService;
import com.meta.moviecataglogservice.service.UserRatingService;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.reactive.function.client.WebClient;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/catalog")
@EnableEurekaClient
public class MovieCatalogContoller {
    @Autowired
    RestTemplate restTemplate;
    @Autowired
    WebClient.Builder webClient;
    @Autowired
    CatalogItemService catalogItemService;
    @Autowired
    UserRatingService userRatingService;


    @RequestMapping("/{userId}")
    public List<CatalogItem>  getCatalog(@PathVariable("userId") String userId){

        List<CatalogItem> catalogItems;
        //get all related movie IDs
        UserRating userRating = userRatingService.getUserRating(userId);
        // For each Movies Ids , call the movie info service and movie data service
        catalogItems = userRating.getUserRating().stream().map(
                rating -> catalogItemService.getCatalogItem(rating)
                ).collect(Collectors.toList());

        //put them all together
        return catalogItems;
    }




    //Webclient builder which is async
          /*  webClient.build()
                    .get()
                    .uri("http://localhost:8082/ratings-data/"+userId")
                    .retrieve()
                    .bodyToMono(Rating.class)
                    .block();*/
}
