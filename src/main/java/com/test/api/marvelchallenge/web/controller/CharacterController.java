package com.test.api.marvelchallenge.web.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.test.api.marvelchallenge.dto.MyPageable;
import com.test.api.marvelchallenge.persistence.integration.marvel.dto.CharacterDto;
import com.test.api.marvelchallenge.services.CharacterService;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;


@RestController
@RequestMapping("/characters")
public class CharacterController {

    @Autowired
    private CharacterService characterService;
    @GetMapping
    public ResponseEntity<List<CharacterDto>> findAll(
        @RequestParam(required = false) String name,
        @RequestParam(required = false) int[] comics,
        @RequestParam(required = false) int[] series,
        @RequestParam(defaultValue = "0") long offset,
        @RequestParam(defaultValue = "10") long limit)
    {
        MyPageable pageable = new MyPageable(offset, limit);
        return ResponseEntity.ok(characterService.findAll(pageable, name, comics, series));
    }

    @GetMapping("/{characterId}")
    public ResponseEntity<CharacterDto.CharacterInfoDto> findInfoById(
        @PathVariable Long characterId
    )      
    {
        return  ResponseEntity.ok(characterService.findInfoById(characterId));
    }
}
