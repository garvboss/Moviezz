package com.garvb.rating_data_service.resources;

import com.garvb.rating_data_service.models.Rating;
import com.garvb.rating_data_service.models.UserRating;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Arrays;
import java.util.List;

@RestController
@RequestMapping("/ratingdata")
public class RatingResource {

    @RequestMapping("/{movieId}")
    public Rating getRating(@PathVariable("movieId") String movieId) {
        return new Rating(movieId, 9);
    }

    @RequestMapping("/users/{userId}")
    public UserRating getUserRatings(@PathVariable("userId") String userId) {
        // Hard Coded these 2 movieId with rating
        // So for every user we get these two movies only rated by user
        List<Rating> ratings = Arrays.asList(
                new Rating("1234", 8),
                new Rating("5678", 9)
        );

        UserRating userRating = new UserRating();
        userRating.setUserRating(ratings);
        return userRating;
    }
}
