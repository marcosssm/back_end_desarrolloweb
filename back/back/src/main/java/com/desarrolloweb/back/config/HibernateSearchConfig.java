package com.desarrolloweb.back.config;

/*
import org.hibernate.search.backend.lucene.analysis.LuceneAnalysisConfigurer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.annotation.PostConstruct;

@Configuration
public class HibernateSearchConfig {

    @PostConstruct
    public void init() {
        System.out.println("HibernateSearchConfig initialized.");
    }

    @Bean
    public LuceneAnalysisConfigurer luceneAnalysisConfigurer() {
        return context -> {
            System.out.println("Setting up custom analyzer 'suggest'.");
            context.analyzer("suggest")
                    .custom()
                    .tokenizer("standard")
                    .tokenFilter("lowercase")
                    .tokenFilter("edge_ngram")
                    .param("minGramSize", "1")
                    .param("maxGramSize", "20");
            System.out.println("Analyzer 'suggest' has been registered.");
        };
    }
}
 */



