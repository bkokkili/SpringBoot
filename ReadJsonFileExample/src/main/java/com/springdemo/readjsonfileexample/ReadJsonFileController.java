package com.springdemo.readjsonfileexample;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.springdemo.readjsonfileexample.model.Product;
import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.core.io.Resource;
import org.springframework.util.ResourceUtils;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.file.Files;
import java.util.List;

@RestController
public class ReadJsonFileController {

    Resource jsonFile;

    @RequestMapping("/loadProducts")
    public List<Product> loadProducts() {
        System.out.println("*********loadProducts*******");
        return getJson("classpath:products.json");
    }
    @CrossOrigin
    @RequestMapping("/loadProductsJson")
    public String loadProductsJson() {
        System.out.println("*********loadProducts*******");
        return getProductsJson("classpath:products.json");
    }

    public String getProductsJson(String path){
        try {
            File file = ResourceUtils.getFile(path);
            return new String(Files.readAllBytes(file.toPath()));
        }catch (IOException e){
            throw new RuntimeException(e);
        }
    }

    public List<Product> getJson(String path) {
        try {
            File file = ResourceUtils.getFile(path);
            String content = new String(Files.readAllBytes(file.toPath()));
            ObjectMapper mapper = new ObjectMapper();
            JSONObject jsonObject = new JSONObject(content);
            JSONArray jsonArray = jsonObject.getJSONArray("products");
            mapper.configure(DeserializationFeature.ACCEPT_SINGLE_VALUE_AS_ARRAY, true);
            return mapper.readValue(jsonArray.toString(), new TypeReference<List<Product>>() {});
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }


}
