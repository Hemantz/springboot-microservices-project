package com.meta.moviecataglogservice.service;

import com.meta.moviecataglogservice.model.Rating;
import com.meta.moviecataglogservice.model.UserRating;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixProperty;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.client.RestTemplate;

import java.lang.reflect.Array;
import java.util.Arrays;

@Service
public class UserRatingService {

    @Autowired
    RestTemplate restTemplate;
    @Value("${movie.userRatingLink}")
    String userRatingLink;
    @HystrixCommand(
            fallbackMethod = "getFallbackUserRating",
            threadPoolKey = "movieDataService",
            threadPoolProperties ={
                    @HystrixProperty(name = "coreSize", value = "20"),
                    @HystrixProperty(name = "maxQueueSize", value = "10")
            }
    )
    public UserRating getUserRating(String userId) {
        return restTemplate.getForObject(userRatingLink + userId, UserRating.class);
    }

    public UserRating getFallbackUserRating(String userId) {
        return new UserRating(Arrays.asList(new Rating("No Movie found",1)),userId);
    }

}
