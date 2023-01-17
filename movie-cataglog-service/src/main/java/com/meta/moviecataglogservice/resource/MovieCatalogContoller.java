package com.meta.moviecataglogservice.resource;

import com.meta.moviecataglogservice.model.CatalogItem;
import com.meta.moviecataglogservice.model.Movie;
import com.meta.moviecataglogservice.model.Rating;
import com.meta.moviecataglogservice.model.UserRating;
import org.springframework.beans.factory.annotation.Autowired;
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
public class MovieCatalogContoller {
    @Autowired
    RestTemplate restTemplate;
    @Autowired
    WebClient.Builder webClient;
    @RequestMapping("/{userId}")
    public List<CatalogItem>  getCatalog(@PathVariable("userId") String userId){

        List<CatalogItem> catalogItems = new ArrayList<>();
        //get all related movie IDs
        UserRating userRating = restTemplate.getForObject("http://localhost:8082/ratings-data/user/"+userId, UserRating.class);
        userRating.getUserRating().stream().map(rating ->{
           Movie movie = restTemplate.getForObject("http://localhost:8083/movie/"+rating.getMovieId(), Movie.class);
            catalogItems.add(new CatalogItem(movie.getName(),movie.getDescription(), rating.getRating()));
            return catalogItems;
        }).collect(Collectors.toList());

        // For each Movies Ids , call the movie info service and movie data service

        //put them all together
        return catalogItems;
    }
    //Webclient builder which is async
          /*  webClient.build()
                    .get()
                    .uri("http://localhost:8082/ratings-data/\"+userId")
                    .retrieve()
                    .bodyToMono(Rating.class)
                    .block();*/
}
