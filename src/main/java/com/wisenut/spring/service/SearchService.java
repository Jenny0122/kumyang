package com.wisenut.spring.service;

import QueryAPI530.Search;
import com.wisenut.spring.dto.TotalSearchRequestDTO;
import com.wisenut.spring.dto.TotalSearchResponseDTO;
import com.wisenut.spring.vo.*;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

@Service
@Slf4j
@RequiredArgsConstructor
public class SearchService {

    final int QUERY_LOG = 1;

    final String ENCODE_VALUE = "UTF-8";

    final int SERVER_PORT = 7000;

    final int SERVER_TIMEOUT = 10 * 1000;

    final int EXTEND_OR = 0;

    @Value("${wise.sf1.serverIp}")
    String SERVER_IP;

    final DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("YYYY/MM/DD");

    public TotalSearchResponseDTO run(TotalSearchRequestDTO requestDTO) {

        TotalSearchResponseDTO dto = new TotalSearchResponseDTO();

        String query = requestDTO.getQuery();
        String COLLECTION = requestDTO.getCollection();
        String SORT_OPTION = requestDTO.getSortOption();

        // appr 권한 값(user의 deptId)
        String deptId = requestDTO.getDeptId();

        // 결과 내 재검색
        boolean requery = requestDTO.isRequery();
        String realquery = requestDTO.getRealquery();

        if (requery) query += " " + realquery;
        dto.setQuery(query);

        // 상세검색 : 작성자
        String userName = requestDTO.getUserName();

        // 상세검색 : 부서
        String department = requestDTO.getDepartment();

        // 상세검색 : 검색영억
        boolean title = requestDTO.isTitle(); //true
        boolean content = requestDTO.isContent(); //true
        boolean file = requestDTO.isFile(); //true

        // 상세검색 : 검색기간
        LocalDate modifyFrom = requestDTO.getModifyFrom();
        LocalDate modifyTo = requestDTO.getModifyTo();

        int RESULT_COUNT = requestDTO.getCount(); // 한번에 출력되는 검색 건수
        int PAGE_START = requestDTO.getPageStart(); // 검색결과 시작페이지

        Search search = new Search();

        int ret = 0;

        // common query 설정
        ret = search.w3SetCodePage(ENCODE_VALUE);
        ret = search.w3SetQueryLog(QUERY_LOG);
        ret = search.w3SetCommonQuery(query, EXTEND_OR);


        StringBuilder prefixQueryBuilder = new StringBuilder();
        StringBuilder apprCollectionQueryBuilder = new StringBuilder();

        // 전자결재 권한
        String apprPrefixQuery = deptId != null && !deptId.isEmpty() ? "<FLDROWNERID:contains:" + deptId + ">" : "";


        // 상세검색 : 전자결재(작성자 & 부서)
        if (userName != null && !userName.isEmpty()) {
            apprCollectionQueryBuilder.append("<USERNM:contains:")
                                      .append(userName)
                                      .append(">");

            // userName 추가 시 항상 공백 추가
            apprCollectionQueryBuilder.append(" ");
        }

        if (department != null && !department.isEmpty()) {
            apprCollectionQueryBuilder.append("<DEPT:contains:")
                                      .append(department)
                                      .append(">");
            // dept 추가 시 공백 추가
            apprCollectionQueryBuilder.append(" ");
        }

        // 상세검색 : 게시판(작성자)
        String boardCollectionQuery = userName != null && !userName.isEmpty() ? "<POSTER_NAME:contains:" + userName + ">" : "";

        // 검색 영역 : 제목
        if (title) {
            prefixQueryBuilder.append("<PREFIX_TITLE:contains:")
                              .append(query)
                              .append(">")
                              .append(" ");
        }

        // 검색 영역 : 내용
        if (content) {
            prefixQueryBuilder.append("<PREFIX_CONTENTS:contains:")
                              .append(query)
                              .append(">")
                              .append(" ");
        }

        // 검색 영역 : 첨부파일
        if (file) {
            prefixQueryBuilder.append("<PREFIX_FILE_NAME:contains:")
                              .append(query)
                              .append(">")
                              .append("|")
                              .append("<PREFIX_FILE_CONTENTS:contains:")
                              .append(query)
                              .append(">")
                              .append(" ");
        }

        String prefixQuery = prefixQueryBuilder.toString()
                                               .trim();

        String apprCollectionQuery = apprCollectionQueryBuilder.toString()
                                                               .trim();


        log.debug("prefix query: {}", prefixQuery);
        log.debug("appr Collection query: {}", apprCollectionQuery);
        log.debug("board Collection query: {}", boardCollectionQuery);

        SearchVo boardSearch = null;
        SearchVo apprSearch = null;

        String BOARD = "board"; String APPR = "appr";

        switch (COLLECTION) {
            case "ALL":
                boardSearch = Board.getSearchResult(this.searchBoard(search, BOARD, modifyFrom, modifyTo, prefixQuery, boardCollectionQuery, SORT_OPTION, RESULT_COUNT, PAGE_START), BOARD); dto.setBoard(boardSearch);

                apprSearch = Appr.getSearchResult(this.searchAppr(search, APPR, modifyFrom, modifyTo, prefixQuery, apprPrefixQuery, apprCollectionQuery, SORT_OPTION, RESULT_COUNT, PAGE_START), APPR); dto.setAppr(apprSearch);

                break;

            case "board":
                boardSearch = Board.getSearchResult(this.searchBoard(search, BOARD, modifyFrom, modifyTo, prefixQuery, boardCollectionQuery, SORT_OPTION, RESULT_COUNT, PAGE_START), BOARD); dto.setBoard(boardSearch); break;

            case "appr":
                apprSearch = Appr.getSearchResult(this.searchAppr(search, APPR, modifyFrom, modifyTo, prefixQuery, apprPrefixQuery, apprCollectionQuery, SORT_OPTION, RESULT_COUNT, PAGE_START), APPR); dto.setAppr(apprSearch); break;

            default:
                break;
        }

        return dto;
    }

    private Search searchBoard(Search search, String COLLECTION, LocalDate modifyFrom, LocalDate modifyTo, String prefixQuery, String boardCollectionQuery, String SORT_FIELD, int RESULT_COUNT, int PAGE_START) {

        int ret = 0; String SEARCH_FIELD = null;
        String DOCUMENT_FIELD = null;

        SEARCH_FIELD = getBoardSearchFieldList();
        DOCUMENT_FIELD = getBoardDocumentFieldList();

        // collection, 검색 필드, 출력 필드 설정
        ret = search.w3AddCollection(COLLECTION);
        ret = search.w3SetPageInfo(COLLECTION, PAGE_START, RESULT_COUNT);
        ret = search.w3SetSortField(COLLECTION, SORT_FIELD);
        ret = search.w3SetSearchField(COLLECTION, SEARCH_FIELD);
        ret = search.w3SetDocumentField(COLLECTION, DOCUMENT_FIELD);
        ret = search.w3SetHighlight(COLLECTION, 1, 1);
        ret = search.w3SetRanking(COLLECTION, "basic", "prkmfo", 1000);
        ret = search.w3SetQueryAnalyzer(COLLECTION, 1, 1, 1, 0);

        if (!prefixQuery.isEmpty())
            ret = search.w3SetPrefixQuery(COLLECTION, prefixQuery, 1);

        if (!boardCollectionQuery.isEmpty())
            ret = search.w3SetCollectionQuery(COLLECTION, boardCollectionQuery);

        if(modifyFrom != null && modifyTo != null)
            ret = search.w3SetDateRange(COLLECTION, modifyFrom.format(dateFormatter), modifyTo.format(dateFormatter));

        // request
        ret = search.w3ConnectServer(SERVER_IP, SERVER_PORT, SERVER_TIMEOUT);
        ret = search.w3ReceiveSearchQueryResult(3);

        // check error
        if (search.w3GetError() != 0) {
            log.debug("board 검색 오류 로그 : {}", search.w3GetErrorInfo());
            return null;
        }

        return search;

    }

    private Search searchAppr(Search search, String COLLECTION, LocalDate modifyFrom, LocalDate modifyTo, String prefixQuery, String apprPrefixQuery, String apprCollectionQuery, String SORT_FIELD, int RESULT_COUNT, int PAGE_START) {


        int ret = 0; String SEARCH_FIELD = null;
        String DOCUMENT_FIELD = null;

        SEARCH_FIELD = getApprSearchFieldList();
        DOCUMENT_FIELD = getApprDocumentFieldList();

        // collection, 검색 필드, 출력 필드 설정
        ret = search.w3AddCollection(COLLECTION);
        ret = search.w3SetPageInfo(COLLECTION, PAGE_START, RESULT_COUNT);
        ret = search.w3SetSortField(COLLECTION, SORT_FIELD);
        ret = search.w3SetSearchField(COLLECTION, SEARCH_FIELD);
        ret = search.w3SetDocumentField(COLLECTION, DOCUMENT_FIELD);
        ret = search.w3SetHighlight(COLLECTION, 1, 1);
        ret = search.w3SetRanking(COLLECTION, "basic", "prkmfo", 1000);
        ret = search.w3SetQueryAnalyzer(COLLECTION, 1, 1, 1, 0);

        if (!prefixQuery.isEmpty() || !apprPrefixQuery.isEmpty())
            ret = search.w3SetPrefixQuery(COLLECTION, prefixQuery+ " " + apprPrefixQuery, 1);

        if (!apprCollectionQuery.isEmpty())
            ret = search.w3SetCollectionQuery(COLLECTION, apprCollectionQuery);

        if(modifyFrom != null && modifyTo != null)
            ret = search.w3SetDateRange(COLLECTION, modifyFrom.format(dateFormatter), modifyTo.format(dateFormatter));

        // request
        ret = search.w3ConnectServer(SERVER_IP, SERVER_PORT, SERVER_TIMEOUT);
        ret = search.w3ReceiveSearchQueryResult(3);

        // check error
        if (search.w3GetError() != 0) {
            log.debug("appr 검색 오류 로그 : {}", search.w3GetErrorInfo());
            return null;
        } return search;
    }

    private String getBoardDocumentFieldList() {
        List<String> list = new ArrayList<>();

        list.add("DOCID"); list.add("DATE");
        list.add("TITLE"); list.add("POSTER_ID");
        list.add("POSTER_NAME"); list.add("CONTENTS");
        list.add("ATT_CNT"); list.add("FILE_NAME");
        list.add("ATT_EXTS"); list.add("ATT_ORDS");
        list.add("FILE_CONTENTS"); list.add("BRD_ID");
        list.add("MODIFY_DATE"); list.add("COMMENT_NUM");
        list.add("BRDFULLPATH"); list.add("BRD_TYPE");
        list.add("READNOTMEMBER");

        return String.join(",", list);
    }

    private String getBoardSearchFieldList() {
        List<String> list = new ArrayList<>();

        list.add("TITLE"); list.add("CONTENTS");
        list.add("FILE_NAME"); list.add("FILE_CONTENTS");

        return String.join(",", list);
    }

    private String getApprDocumentFieldList() {
        List<String> list = new ArrayList<>();

        list.add("DOCID"); list.add("DATE");
        list.add("APPRID"); list.add("TITLE");
        list.add("USERNM"); list.add("CONTENTS");
        list.add("DEPT"); list.add("DOCREGNO");
        list.add("APPROVALDATE"); list.add("APPROVALTYPE");
        list.add("APPROVALSTATUS"); list.add("WORDTYPE");
        list.add("EXTERNALDOC");
        list.add("EXTERNALDOCTYPE");
        list.add("ATTACHCOUNT"); list.add("ISSECURITY");
        list.add("SUMMARYDOC"); list.add("HASOPINION");
        list.add("DOCTYPE"); list.add("FILE_NAME");
        list.add("FILE_EXTS"); list.add("FILE_CONTENTS");
        list.add("FILE_ID"); list.add("UPDATEDATE");
        list.add("OWNERIDS"); list.add("FLDROWNERID");
        list.add("FLDROWNERNAME");

        return String.join(",", list);
    }

    private String getApprSearchFieldList() {

        List<String> list = new ArrayList<>();
        list.add("TITLE"); list.add("USERNM");
        list.add("CONTENTS"); list.add("DEPT");
        list.add("DOCREGNO"); list.add("FILE_NAME");
        list.add("FILE_CONTENTS");

        return String.join(",", list);
    }
}
