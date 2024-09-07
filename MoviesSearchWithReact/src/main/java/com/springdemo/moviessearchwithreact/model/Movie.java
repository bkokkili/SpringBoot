package com.springdemo.moviessearchwithreact.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
@JsonIgnoreProperties(ignoreUnknown = true)
public class Movie {

    private String Title;
    private String Year;
    private String imdbID;
    private String Type;
    private String Poster;
}
