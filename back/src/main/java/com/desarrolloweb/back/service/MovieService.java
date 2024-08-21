package com.desarrolloweb.back.service;

import com.desarrolloweb.back.model.Movie;
import com.desarrolloweb.back.repository.MovieRepository;
import org.hibernate.search.engine.search.query.SearchResult;
import org.hibernate.search.mapper.orm.Search;
import org.hibernate.search.mapper.orm.session.SearchSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.persistence.EntityManager;
import java.util.List;

@Service
public class MovieService {

    @Autowired
    private MovieRepository movieRepository;

    @Autowired
    private EntityManager entityManager;

    public List<Movie> searchMovies(String searchText) {
        SearchSession searchSession = Search.session(entityManager);
        SearchResult<Movie> result = searchSession.search(Movie.class)
                .where(f -> f.wildcard()
                        .fields("nombre", "director")
                        .matching("*" + searchText.toLowerCase() + "*"))
                .fetch(20); // Devuelve las primeras 20 coincidencias
        return result.hits();
    }

    public List<Movie> getMovies() {
        return movieRepository.findAll();
    }
}
