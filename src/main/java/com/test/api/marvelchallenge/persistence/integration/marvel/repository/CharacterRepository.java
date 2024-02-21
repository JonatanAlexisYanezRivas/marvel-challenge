package com.test.api.marvelchallenge.persistence.integration.marvel.repository;

import com.fasterxml.jackson.databind.JsonNode;
import com.test.api.marvelchallenge.dto.MyPageable;
import com.test.api.marvelchallenge.persistence.integration.marvel.MarvelAPIConfig;
import com.test.api.marvelchallenge.persistence.integration.marvel.dto.CharacterDto;

import jakarta.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Repository;
import org.springframework.util.StringUtils;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

@Repository
public class CharacterRepository {

    @Autowired
    private MarvelAPIConfig marvelAPIConfig;

    @Value("${integration.marvel.base-path}")
    private String basePath; 
    private String characterPath;

    @PostConstruct void setPath(){
        characterPath = basePath.concat("/").concat("characters");
    }


    public List<CharacterDto> findAll(MyPageable pageable, String name, int[] comics, int[] series) {
        Map<String, String>  marvelQueryParams = getQueryParamsForFindAll(pageable, name, comics, series);
        JsonNode response = httpClientService.doGet(characterPath, marvelQueryParams, JsonNode.class);

        return CharacterMapper.toDoList(response);
    }

    private Map<String, String> getQueryParamsForFindAll(MyPageable pageable, String name, int[] comics, int[] series) {

        Map<String, String> marvelQueryParams = marvelAPIConfig.getAuthenticationQueryParams();

        marvelQueryParams.put("offset", Long.toString(pageable.offset()));
        marvelQueryParams.put("limit", Long.toString(pageable.limit()));

        if(StringUtils.hasText(name)){
            marvelQueryParams.put("name", name);
        }

        if(comics != null) {
            String comicsAsSting = this.joinIntArray(comics);
            marvelQueryParams.put("comics", comicsAsSting);
        }

        if(series != null) {
            String seriesAsString = this.joinIntArray(series);
            marvelQueryParams.put("comics", seriesAsString);
        }

        return marvelQueryParams;

    }

    private String joinIntArray(int[] comics) {
        List<String> stringArray = IntStream.of(comics).boxed()
                .map(each -> each.toString())
                .collect(Collectors.toList());
        return String.join(",", stringArray);
    }

    public CharacterDto.CharacterInfoDto findInfoById(Long characterId) {

        Map<String, String> marverlQueryParams =  marvelAPIConfig.getAuthenticationQueryParams();

        String finalUrl = characterPath.concat("/").concat(Long.toString(characterId));

        JsonNode response = httpClientService.doGet(finalUrl, marverlQueryParams, JsonNode.class);

        return CharacterMapper.toDoList(response).get(0);
    }
}
