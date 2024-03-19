package com.springdemo.moviessearchwithreact.service;


import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.springdemo.moviessearchwithreact.model.Movie;
import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.List;
import java.util.Objects;


@Service
public class MovieSearchService {
    public  String getClient(String searchTerm, String apiKey) throws JsonProcessingException {
           StringBuilder mdb_url = new StringBuilder();
           mdb_url.append("https://www.omdbapi.com/?s=");
           mdb_url.append(searchTerm);
           mdb_url.append("&apikey=");
           mdb_url.append(apiKey);

           JSONArray jsonArray1 = new JSONArray();
           if (searchTerm.isEmpty()){
               return jsonArray1.toString();
           }
           if (searchTerm.length() > 2){
               RestTemplate restTemplate = new RestTemplate();

               ResponseEntity<String> responseEntity = restTemplate.getForEntity(mdb_url.toString(), String.class);
               ObjectMapper objectMapper = new ObjectMapper();

               JSONObject jsonObject = new JSONObject(Objects.requireNonNull(responseEntity.getBody()));
               JSONArray jsonArray = jsonObject.getJSONArray("Search");
               objectMapper.configure(DeserializationFeature.ACCEPT_SINGLE_VALUE_AS_ARRAY, true);
               return jsonArray.toString();
           }

       return jsonArray1.toString();


    }
}
