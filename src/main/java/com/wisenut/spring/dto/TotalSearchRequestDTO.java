package com.wisenut.spring.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

import javax.validation.constraints.NotNull;

@Data
@Builder
@AllArgsConstructor
public class TotalSearchRequestDTO {

    // 검색어
    @NotNull(message = "검색어는 '필수'값입니다.")
    private String query;

    // 컬렉션
    @NotNull(message = "collection은 '필수'값 입니다.")
    private String collection;

    // 정렬 필드 : DATE or RANK
    private String sortField;

    // 정렬 방식 : ASC or DESC
    private String sortDirection;

    // 검색 결과 출력되는 위치
    private int pageStart;

    // 결과 내 재검색 여부 : 1이면 결과 내 재검색
    private int requery;

    // 결과 내 재검색하는 검색어
    private String realquery;

    // 검색 영역 : 제목
    private boolean title;

    // 검색 영역 : 내용
    private boolean content;

    // 검색 영역 : 첨부파일
    private boolean file;

    // 검색 기간(시작)
    private String modifyFrom;

    // 검색 기간(끝)
    private String modifyTo;

    // 한번에 출력되는 검색 건수
    private int count;

    public TotalSearchRequestDTO() {
        pageStart = 0;
        count = 10;
        sortField = "RANK";
        sortDirection = "DESC";
    }
}
