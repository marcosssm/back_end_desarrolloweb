package com.desarrolloweb.back.controller;

import com.desarrolloweb.back.model.Movie;
import com.desarrolloweb.back.repository.MovieRepository;
import com.desarrolloweb.back.service.MovieService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
public class MovieController {

    @Autowired
    private MovieService movieService;

    @GetMapping("/getMovies")
    public Map<String, List<Movie>> getMovies() {
        List<Movie> movies = movieService.getMovies();
        Map<String, List<Movie>> response = new HashMap<>();
        response.put("peliculas", movies);
        return response;
    }

    @GetMapping("/search")
    public List<Movie> searchMovies(@RequestParam("query") String query) {
        return movieService.searchMovies(query);
    }


    @GetMapping("/suggestions")
    public List<String> suggestMovies(@RequestParam("query") String query) {
        return movieService.suggestMovieTitles(query);
    }

    @GetMapping("/facets")
    public Map<String, Long> facetByCategory(@RequestParam("query") String query) {
        return movieService.facetByCategory(query);
    }
}

