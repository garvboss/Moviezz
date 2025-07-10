package com.garvb.movie_catalog_service.resources;

import com.garvb.movie_catalog_service.models.CatalogItem;
import com.garvb.movie_catalog_service.models.Movie;
import com.garvb.movie_catalog_service.models.Rating;
import com.garvb.movie_catalog_service.models.UserRating;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;


import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/catalog")
public class CatalogResource {

    @Autowired
    private RestTemplate restTemplate;

    @RequestMapping("/{userId}")
    public List<CatalogItem> getCatalog(@PathVariable("userId") String userId) {

        // 1. Get movieId with rating from rating-data-service
        // HardCoding for now
//        List<Rating> ratings = Arrays.asList(
//                new Rating("1234", 8),
//                new Rating("5678", 9)
//        );
        UserRating ratings = restTemplate.getForObject("http://Rating-Data-Service/ratingdata/users/" + userId, UserRating.class);



        // 2. Get movies details of all movie ids we get from rating-data-service
        // HardCoding for now
        return ratings.getUserRating()
                .stream()
                .map(rating -> {
                    Movie movie = restTemplate.getForObject("http://Movie-Info-Service/movies/" + rating.getMovieId(), Movie.class);
                    return new CatalogItem(movie.getName(), movie.getDescription(), rating.getRating());
                })
                .collect(Collectors.toList());


        // 3. Put them all together and show here

    }
}
