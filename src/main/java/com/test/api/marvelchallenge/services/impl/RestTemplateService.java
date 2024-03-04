package com.test.api.marvelchallenge.services.impl;

import com.test.api.marvelchallenge.exception.ApiErrorException;
import com.test.api.marvelchallenge.services.HttpClientService;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;

import java.util.Map;

public class RestTemplateService implements HttpClientService {


    @Autowired
    RestTemplate restTemplate;

    @Override
    public <T> T doGet(String endpoint, Map<String, String> queryParams, Class<T> responseType) {

        String finalUrl = buildFinalUrl(endpoint, queryParams);

        HttpEntity httpEntity = new HttpEntity(getHeaders());
        ResponseEntity<T> response =  restTemplate.exchange(finalUrl, HttpMethod.GET, null, responseType);

        if(response.getStatusCode().value() != HttpStatus.OK.value()){
            String message = String.format("Error consuminedo endpoint [ {} , {}], codigo de respuesta: {}", HttpMethod.GET, endpoint, response.getStatusCode());

            throw new ApiErrorException(message);
        }

        return response.getBody();
    }

    private static String buildFinalUrl(String endpoint, Map<String, String> queryParams) {
        UriComponentsBuilder builder = UriComponentsBuilder.fromHttpUrl(endpoint);

        if(queryParams != null){
            for(Map.Entry<String, String> entry : queryParams.entrySet()){
                builder.queryParam(entry.getKey(), entry.getValue());
            }
        }

        String finalUrl = builder.build().toUriString();
        return finalUrl;
    }

    private HttpHeaders getHeaders(){
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        return headers;
    }

    @Override
    public <T, R> T doPost(String endpoint, Map<String, String> queryParams, Class<T> responseType, R bodyRequest) {
        String finalUrl = buildFinalUrl(endpoint, queryParams);

        HttpEntity httpEntity = new HttpEntity(bodyRequest, getHeaders());
        ResponseEntity<T> response =  restTemplate.exchange(finalUrl, HttpMethod.POST, null, responseType);

        if(response.getStatusCode().value() != HttpStatus.OK.value() || response.getStatusCode().value() != HttpStatus.CREATED.value()){
            String message = String.format("Error consuminedo endpoint [ {} , {}], codigo de respuesta: {}", HttpMethod.POST, endpoint, response.getStatusCode());

            throw new ApiErrorException(message);
        }

        return response.getBody();
    }

    @Override
    public <T, R> T doPut(String endpoint, Map<String, String> queryParams, Class<T> responseType, R bodyRequest) {
        String finalUrl = buildFinalUrl(endpoint, queryParams);

        HttpEntity httpEntity = new HttpEntity(bodyRequest, getHeaders());
        ResponseEntity<T> response =  restTemplate.exchange(finalUrl, HttpMethod.PUT, null, responseType);

        if(response.getStatusCode().value() != HttpStatus.OK.value()){
            String message = String.format("Error consuminedo endpoint [ {} , {}], codigo de respuesta: {}", HttpMethod.PUT, endpoint, response.getStatusCode());

            throw new ApiErrorException(message);
        }

        return response.getBody();
    }

    @Override
    public <T> T doDelete(String endpoint, Map<String, String> queryParams, Class<T> responseType) {
        String finalUrl = buildFinalUrl(endpoint, queryParams);

        HttpEntity httpEntity = new HttpEntity(getHeaders());
        ResponseEntity<T> response =  restTemplate.exchange(finalUrl, HttpMethod.DELETE, null, responseType);

        if(response.getStatusCode().value() != HttpStatus.OK.value()){
            String message = String.format("Error consuminedo endpoint [ {} , {}], codigo de respuesta: {}", HttpMethod.DELETE, endpoint, response.getStatusCode());

            throw new ApiErrorException(message);
        }

        return response.getBody();
    }
}
