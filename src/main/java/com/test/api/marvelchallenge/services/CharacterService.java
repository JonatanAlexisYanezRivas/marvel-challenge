package com.test.api.marvelchallenge.services;

import java.util.List;

import com.test.api.marvelchallenge.dto.MyPageable;
import com.test.api.marvelchallenge.persistence.integration.marvel.dto.CharacterDto;

public interface CharacterService {

    List<CharacterDto> findAll(MyPageable pageable, String name, int[] comics, int[] series);

	CharacterDto.CharacterInfoDto findInfoById(Long characterId);
    
    
    
}

