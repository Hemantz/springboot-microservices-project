package com.meta.movieinfoservice.resource;

import com.meta.movieinfoservice.model.Movie;
import com.meta.movieinfoservice.model.MovieSummary;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import java.util.Collections;
import java.util.List;

@RestController
@RequestMapping("/movie")
public class MovieInfoController {

    @Autowired
    RestTemplate restTemplate;

    @Value("${api.key}")
    String apiKey;
    @Value("${api.link}")
    String movieDbLink;
    @RequestMapping("{movieId}")
    public Movie getMovieInfo(@PathVariable("movieId") String movieId){
        MovieSummary movieSummary = restTemplate.getForObject(movieDbLink + movieId + "?api_key=" + apiKey, MovieSummary.class);
        return new Movie(movieSummary.getTitle(),movieSummary.getOverview());
    }
}
