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

    private String title;
    private String posterId;
    private String posterName;
    private String contents;
    private String attCnt;
    private String fileName;
    private String attExts;
    private String attOrds;
    private String fileContents;
    private String brdId;
    private String modifyDate;
    private String commentNum;
    private String brdFullPath;
    private String brdType;
    private String readNotMember;

    static public SearchVo getSearchResult(Search search, String COLLECTION) {

        List<Board> list = new ArrayList<>();

        // 전체건수, 결과건수 출력
        int totalCount = search.w3GetResultTotalCount(COLLECTION);
        int resultCount = search.w3GetResultCount(COLLECTION);

        for (int i = 0; i < resultCount; i++) {

            // 기본 검색결과 객체 생성
            String title = search.w3GetField(COLLECTION, "TITLE", i)
                                  .replaceAll("<!HS>", "<b>")
                                  .replaceAll("<!HE>", "</b>");
            String posterId = search.w3GetField(COLLECTION, "POSTER_ID", i);
            String posterName = search.w3GetField(COLLECTION, "POSTER_NAME", i)
                                      .replaceAll("<!HS>", "<b>")
                                      .replaceAll("<!HE>", "</b>");
            String contents = search.w3GetField(COLLECTION, "CONTENTS", i)
                                    .replaceAll("<!HS>", "<b>")
                                    .replaceAll("<!HE>", "</b>");
            String attCnt = search.w3GetField(COLLECTION, "ATT_CNT", i);
            String fileName = search.w3GetField(COLLECTION, "FILE_NAME", i);
            String attExts = search.w3GetField(COLLECTION, "ATT_EXTS", i);
            String attOrds = search.w3GetField(COLLECTION, "ATT_ORDS", i);
            String fileContents = search.w3GetField(COLLECTION, "FILE_CONTENTS", i)
                                        .replaceAll("<!HS>", "<b>")
                                        .replaceAll("<!HE>", "</b>");
            String brdId = search.w3GetField(COLLECTION, "BRD_ID", i);
            String modifyDate = search.w3GetField(COLLECTION, "MODIFY_DATE", i);
            String commentNum = search.w3GetField(COLLECTION, "COMMENT_NUM", i);
            String brdFullPath = search.w3GetField(COLLECTION, "BRDFULLPATH", i);
            String brdType = search.w3GetField(COLLECTION, "BRD_TYPE", i);
            String readNotMember = search.w3GetField(COLLECTION, "READNOTMEMBER", i);

            Board vo = Board.builder()
                    .title(title)
                    .posterId(posterId)
                    .posterName(posterName)
                    .contents(contents)
                    .attCnt(attCnt)
                    .fileName(fileName)
                    .attExts(attExts)
                    .attOrds(attOrds)
                    .fileContents(fileContents)
                    .brdId(brdId)
                    .modifyDate(modifyDate)
                    .commentNum(commentNum)
                    .brdFullPath(brdFullPath)
                    .brdType(brdType)
                    .readNotMember(readNotMember)
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
