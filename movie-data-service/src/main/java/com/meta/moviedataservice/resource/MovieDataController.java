package com.meta.moviedataservice.resource;

import com.meta.moviedataservice.model.Rating;
import com.meta.moviedataservice.model.UserRating;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@RestController
@RequestMapping("/ratings-data")
public class MovieDataController {
    @RequestMapping("/{movieId}")
    public Rating getRating(@PathVariable("movieId") String movieId){
        return new Rating("1234",4);
    }

    @RequestMapping("/user/{userId}")
    public UserRating getUserRating(@PathVariable("userId") String userId){
        List<Rating> ratings = new ArrayList<>();
        ratings.add(new Rating("1234",4));
        ratings.add(new Rating("3456",3));
        UserRating userRating = new UserRating(ratings,userId);
        return userRating;
    }
}
