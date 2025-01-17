package com.wisenut.spring.controller;

import com.wisenut.spring.dto.TotalSearchRequestDTO;
import com.wisenut.spring.dto.TotalSearchResponseDTO;
import com.wisenut.spring.service.SearchService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import javax.validation.Valid;
@CrossOrigin(origins = "*")
@RequiredArgsConstructor
@Slf4j
@RestController
public class SearchController {

    private final SearchService service;

    // 입력받은 검색어와 컬렉션 정보로 통합검색
    @CrossOrigin(origins = "http://localhost:5173")
    @PostMapping(path = {"/search"}, consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> retriveSearchList(@RequestBody @Valid TotalSearchRequestDTO requestDTO) {

        log.debug("/search");
        log.debug("request: {}", requestDTO);
        TotalSearchResponseDTO responseDTO = service.run(requestDTO);

        log.debug("response: {}", responseDTO);
        return ResponseEntity.ok(responseDTO);
    }

}