package com.wisenut.spring.vo;

import lombok.*;

import java.util.List;

@Getter
@Setter
@EqualsAndHashCode
@NoArgsConstructor
@Builder
@AllArgsConstructor
public class SearchVo<T> { // 통합 검색 필드 정보

    private String collection;

    @Builder.Default
    private int totalCount = 0;

    @Builder.Default
    private int count = 0;

    @Builder.Default
    private int pageStart = 0;

    private List<T> result;
    @Override
    public String toString() {
        return "SearchVo{" + "collection='" + collection + '\'' + ", totalCount=" + totalCount + ", pageStart=" + pageStart + ", count=" + count + ", result=[.....]}";
    }
}

