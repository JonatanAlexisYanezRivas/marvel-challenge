package com.test.api.marvelchallenge.web.controller;

import com.test.api.marvelchallenge.dto.MyPageable;
import com.test.api.marvelchallenge.persistence.integration.marvel.dto.ComicDto;
import com.test.api.marvelchallenge.services.ComicService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/comics")
public class ComicController {

    @Autowired
    private ComicService comicService;

    @GetMapping
    public ResponseEntity<List<ComicDto>> findAll(
            @RequestParam(required = false) Long charactedId,
            @RequestParam(required = false) long offset,
            @RequestParam(required = false) long limit
    ){
        MyPageable pageable = new MyPageable(offset, limit);
        return ResponseEntity.ok(comicService.findAll(pageable, charactedId));
    }

    @GetMapping
    public ResponseEntity<ComicDto> findById(
            @PathVariable long comicId
    ){
        return ResponseEntity.ok(comicService.findById(comicId));
    }
}
