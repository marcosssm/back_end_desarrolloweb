package com.desarrolloweb.back.service;

import com.desarrolloweb.back.model.Movie;
import com.desarrolloweb.back.repository.MovieRepository;
import org.hibernate.search.engine.search.aggregation.AggregationKey;
import org.hibernate.search.engine.search.query.SearchResult;
import org.hibernate.search.mapper.orm.Search;
import org.hibernate.search.mapper.orm.session.SearchSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.persistence.EntityManager;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class MovieService {

    @Autowired
    private MovieRepository movieRepository;

    @Autowired
    private EntityManager entityManager;

    public List<Movie> getMovies() {
        return movieRepository.findAll();
    }

    public List<Movie> searchMovies(String searchText) {
        SearchSession searchSession = Search.session(entityManager);
        List<Movie> results = searchSession.search(Movie.class)
                .where(f -> f.match()
                        .fields("nombre", "director") // Busca en los campos 'nombre' y 'director'
                        .matching(searchText) // El texto de búsqueda
                        .fuzzy(2) // Permite errores tipográficos (fuzzy)
                )
                .fetchHits(20); // Devuelve hasta 20 resultados
        return results;
    }

    public List<String> suggestMovieTitles(String searchText) {
        SearchSession searchSession = Search.session(entityManager);
        List<String> suggestions = searchSession.search(Movie.class)
                .where(f -> f.match()
                        .field("nombre")
                        .matching(searchText)
                        .analyzer("suggest") // Usa un analizador específico para sugerencias, si está configurado
                )
                .fetchHits(20) // Limita el número de sugerencias
                .stream()
                .map(Movie::getNombre) // Devuelve solo los nombres de las películas
                .collect(Collectors.toList());
        return suggestions;
    }

    public List<Movie> searchMoviesWithCorrections(String searchText) {
        SearchSession searchSession = Search.session(entityManager);
        List<Movie> results = searchSession.search(Movie.class)
                .where(f -> f.match()
                        .fields("nombre", "director")
                        .matching(searchText)
                        .fuzzy(2) // Corrección automática de errores tipográficos
                )
                .fetchHits(20);

        if (results.isEmpty()) {
            // Podrías agregar lógica adicional aquí para proporcionar sugerencias o correcciones.
        }

        return results;
    }

    public Map<String, Long> facetByCategory(String searchText) {
        SearchSession searchSession = Search.session(entityManager);

        // Crear una clave de agregación
        AggregationKey<Map<String, Long>> categoryFacetKey = AggregationKey.of("categoryFacets");

        SearchResult<Movie> result = searchSession.search(Movie.class)
                .where(f -> f.match()
                        .fields("nombre", "director")
                        .matching(searchText))
                .aggregation(categoryFacetKey, f -> f.terms()
                        .field("categoria", String.class))
                .fetch(20);

        // Obtener los resultados de la faceta
        Map<String, Long> categoryFacets = result.aggregation(categoryFacetKey);

        return categoryFacets;
    }

}
