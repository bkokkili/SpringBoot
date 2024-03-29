package com.springdemo.moviessearchwithreact.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.springdemo.moviessearchwithreact.service.MovieSearchService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class MovieSearchController {
    @Autowired
    MovieSearchService movieSerachService;

    @CrossOrigin
    @RequestMapping("/loadMoviesBySearch")
    public String fetchMoviesBySearch(@RequestParam String searchTerm, @RequestParam String apiKey) throws JsonProcessingException {
        return movieSerachService.getClient(searchTerm.trim(), apiKey);
    }
}
