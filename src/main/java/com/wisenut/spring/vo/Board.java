package com.wisenut.spring.vo;

import QueryAPI530.Search;
import lombok.*;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@ToString
@EqualsAndHashCode
@NoArgsConstructor
@Builder
@AllArgsConstructor
public class Board {

    // 중분류명
    private String mCatNm;
    static public SearchVo getSearchResult(Search search, String COLLECTION) {

        List<Board> list = new ArrayList<>();

        // 전체건수, 결과건수 출력
        int totalCount = search.w3GetResultTotalCount(COLLECTION);
        int resultCount = search.w3GetResultCount(COLLECTION);

        for (int i = 0; i < resultCount; i++) {

            // 기본 검색결과 객체 생성
            String mCatNm = search.w3GetField(COLLECTION, "M_CAT_NM", i)
                                  .replaceAll("<!HS>", "<em class=\"keyword\">")
                                  .replaceAll("<!HE>", "</em>");


            Board vo = Board.builder()
                                  .build();

            list.add(vo);

        }

        return SearchVo.<Board>builder()
                       .collection(COLLECTION)
                       .totalCount(totalCount)
                       .count(resultCount)
                       .result(list)
                       .build();
    }
}
