package com.wisenut.spring.controller;

import com.wisenut.spring.dto.TotalSearchRequestDTO;
import com.wisenut.spring.dto.TotalSearchResponseDTO;
import com.wisenut.spring.service.SearchService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.Map;

@CrossOrigin(origins = "*")
@RequiredArgsConstructor
@Slf4j
@RequestMapping("/api")
@RestController
public class SearchController {

    private final SearchService service;

//    @Value("${spring.profiles.active:default}")
//    private String activeProfile;
//
//    @Value("${server.url}")
//    String serverUrl;

    // 입력받은 검색어와 컬렉션 정보로 통합검색
    @CrossOrigin(origins = "http://localhost:5173")
    @PostMapping(path = {"/search"}, consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> retriveSearchList(@RequestBody @Valid TotalSearchRequestDTO requestDTO) {

        log.debug("/search");
        log.debug("request: {}", requestDTO);
//        log.debug("referer: {}", referer);
//
//        // profile이 dev OR prod일때, true
//        boolean profileCheck = activeProfile.contentEquals("prod") || activeProfile.contentEquals("dev");
//        // referer이 null이면 true
//        // referer이 serverUrl을 포함하면
//        boolean urlCheck = referer != null || referer.contains(serverUrl);
//
//
//
//        if (profileCheck) {
//            if (!urlCheck) {
//
//                final String errorMessage = "검색엔진은 그룹웨어에서의 접근만 가능합니다.";
//
//                log.error("{} referer: {}", errorMessage, referer);
//
//                return ResponseEntity.badRequest()
//                                     .body(errorMessage);
//            }
//        }

        TotalSearchResponseDTO responseDTO = service.run(requestDTO);

        log.debug("response: {}", responseDTO);
        return ResponseEntity.ok(responseDTO);
    }


}