package com.wisenut.spring.vo;

import QueryAPI530.Search;
import lombok.*;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@ToString
@EqualsAndHashCode
@NoArgsConstructor
@Builder
@AllArgsConstructor
public class Appr {

    private String apprId;
    private String title;
    private LocalDate draftDate;
    private LocalDateTime draftDateTime;
    private String userNm;
    private String contents;
    private String dept;
    private String docRegNo;
    private String approvalDate;
    private String approvalType;
    private String approvalStatus;
    private String wordType;
    private String externalDoc;
    private String externalDoctype;
    private String attachCount;
    private String isSecurity;
    private String summaryDoc;
    private String hasOpinion;
    private String doctype;
    private String fileName;
    private String fileExts;
    private String fileContents;
    private String fileId;
    private String updateDate;
    private String ownerIds;
    private String fldrOwnerId;
    private String fldrOwnerName;


    static public SearchVo getSearchResult(Search search, String COLLECTION) {

        List<Appr> list = new ArrayList<>();

        // 전체건수, 결과건수 출력
        int totalCount = search.w3GetResultTotalCount(COLLECTION);
        int resultCount = search.w3GetResultCount(COLLECTION);
        DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("yyyyMMddHHmmss");
        DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("yyyyMMddHHmmss");

        for (int i = 0; i < resultCount; i++) {

            // 기본 검색결과 객체 생성
            String apprId = search.w3GetField(COLLECTION, "APPRID", i);
            LocalDate draftDate = LocalDate.parse(search.w3GetField(COLLECTION, "DATE", i), dateFormatter);
            LocalDateTime draftDateTime = LocalDateTime.parse(search.w3GetField(COLLECTION, "DATE", i), dateTimeFormatter);
            String title = search.w3GetField(COLLECTION, "TITLE", i)
                                 .replaceAll("<!HS>", "<font color=red>")
                                 .replaceAll("<!HE>", "</font>");
            String userNm = search.w3GetField(COLLECTION, "USERNM", i);
            String contents = search.w3GetField(COLLECTION, "CONTENTS", i)
                                    .replaceAll("<!HS>", "<font color=red>")
                                    .replaceAll("<!HE>", "</font>");
            String dept = search.w3GetField(COLLECTION, "DEPT", i)
                                .replaceAll("<!HS>", "<font color=red>")
                                .replaceAll("<!HE>", "</font>");;
            String docRegNo = search.w3GetField(COLLECTION, "DOCREGNO", i);
            String approvalDate = search.w3GetField(COLLECTION, "APPROVALDATE", i);
            String approvalType = search.w3GetField(COLLECTION, "APPROVALTYPE", i);
            String approvalStatus = search.w3GetField(COLLECTION, "APPROVALSTATUS", i);
            String wordType = search.w3GetField(COLLECTION, "WORDTYPE", i);
            String externalDoc = search.w3GetField(COLLECTION, "EXTERNALDOC", i);
            String externalDoctype = search.w3GetField(COLLECTION, "EXTERNALDOCTYPE", i);
            String attachCount = search.w3GetField(COLLECTION, "ATTACHCOUNT", i);
            String isSecurity = search.w3GetField(COLLECTION, "ISSECURITY", i);
            String summaryDoc = search.w3GetField(COLLECTION, "SUMMARYDOC", i);
            String hasOpinion = search.w3GetField(COLLECTION, "HASOPINION", i);
            String doctype = search.w3GetField(COLLECTION, "DOCTYPE", i);
            String fileName = search.w3GetField(COLLECTION, "FILE_NAME", i)
                                    .replaceAll("<!HS>", "<font color=red>")
                                    .replaceAll("<!HE>", "</font>");
            String fileExts = search.w3GetField(COLLECTION, "FILE_EXTS", i);
            String fileContents = search.w3GetField(COLLECTION, "FILE_CONTENTS", i);
            String fileId = search.w3GetField(COLLECTION, "FILE_ID", i);
            String updateDate = search.w3GetField(COLLECTION, "UPDATEDATE", i);
            String ownerIds = search.w3GetField(COLLECTION, "OWNERIDS", i);
            String fldrOwnerId = search.w3GetField(COLLECTION, "FLDROWNERID", i);
            String fldrOwnerName = search.w3GetField(COLLECTION, "FLDROWNERNAME", i);

            Appr vo = Appr.builder()
                          .apprId(apprId)
                          .draftDate(draftDate)
                          .draftDateTime(draftDateTime)
                          .title(title)
                          .userNm(userNm)
                          .contents(contents)
                          .dept(dept)
                          .docRegNo(docRegNo)
                          .approvalDate(approvalDate)
                          .approvalType(approvalType)
                          .approvalStatus(approvalStatus)
                          .wordType(wordType)
                          .externalDoc(externalDoc)
                          .externalDoctype(externalDoctype)
                          .attachCount(attachCount)
                          .isSecurity(isSecurity)
                          .summaryDoc(summaryDoc)
                          .hasOpinion(hasOpinion)
                          .doctype(doctype)
                          .fileName(fileName)
                          .fileExts(fileExts)
                          .fileContents(fileContents)
                          .fileId(fileId)
                          .updateDate(updateDate)
                          .ownerIds(ownerIds)
                          .fldrOwnerId(fldrOwnerId)
                          .fldrOwnerName(fldrOwnerName)
                          .build();

            list.add(vo);

        }

        return SearchVo.<Appr>builder()
                       .collection(COLLECTION)
                       .totalCount(totalCount)
                       .count(resultCount)
                       .result(list)
                       .build();
    }
}
