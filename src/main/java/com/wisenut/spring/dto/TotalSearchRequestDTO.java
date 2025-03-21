package com.wisenut.spring.dto;

import com.wisenut.spring.vo.SearchCondition;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import java.time.LocalDate;
import java.util.List;

@Data
@Builder
@AllArgsConstructor
public class TotalSearchRequestDTO {

    // 검색어
    @NotBlank(message = "검색어는 '필수'값입니다.")
    private String query;

    // 컬렉션
    @NotBlank(message = "collection은 '필수'값 입니다.")
    private String collection;

    // 정렬 필드 : DATE or RANK
    @NotBlank(message = "정렬은 '필수'값 입니다.")
    private String sortOption;

    // 검색 결과 출력되는 위치
    private int pageStart;

    // 권한 : 부서ID
    private String deptId;

    // 결과 내 재검색 여부
    private boolean requery;

    // 결과 내 재검색하는 검색어
    private List<String> realquery;

    // 상세 검색 : 작성자
    private String userName;

    // 상세 검색 : 부서
    private String department;

    // 검색 영역 : 제목
    private boolean title;

    // 검색 영역 : 내용
    private boolean content;

    // 검색 영역 : 첨부파일
    private boolean file;

    // 검색 기간(시작)
    private LocalDate modifyFrom;

    // 검색 기간(끝)
    private LocalDate modifyTo;

    // 한번에 출력되는 검색 건수
    private int count;

    // AND/OR 검색
    private List<SearchCondition> searchConditions;

    public TotalSearchRequestDTO() {
        pageStart = 0;
        count = 10;
    }
}
