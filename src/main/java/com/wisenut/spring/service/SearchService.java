package com.wisenut.spring.service;

import QueryAPI530.Search;
import com.wisenut.spring.dto.TotalSearchRequestDTO;
import com.wisenut.spring.dto.TotalSearchResponseDTO;
import com.wisenut.spring.vo.Board;
import com.wisenut.spring.vo.SearchVo;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;

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

    public TotalSearchResponseDTO run(TotalSearchRequestDTO requestDTO) {

        TotalSearchResponseDTO dto = new TotalSearchResponseDTO();

        String query = requestDTO.getQuery()
                                 .trim();
        String COLLECTION = requestDTO.getCollection()
                                      .trim();
        String SORT_FIELD = requestDTO.getSortField().trim() + "/" + requestDTO.getSortDirection().trim();

        int requery = requestDTO.getRequery();
        String realquery = requestDTO.getRealquery();

        // 결과 내 재검색
        if(requery == 1)
            query += " " + realquery;

        String title = requestDTO.getTitle();
        String content = requestDTO.getContent();
        String attached = requestDTO.getAttached();

        String modifyFrom = requestDTO.getModifyFrom();
        String modifyTo = requestDTO.getModifyTo();

        int RESULT_COUNT = requestDTO.getCount(); // 한번에 출력되는 검색 건수
        int PAGE_START = requestDTO.getPageStart(); // 검색결과 페이지

        dto.setQuery(query);

        Search search = new Search();
        int ret = 0;

        // common query 설정
        ret = search.w3SetCodePage(ENCODE_VALUE);
        ret = search.w3SetQueryLog(QUERY_LOG);

        ret = search.w3SetCommonQuery(query, EXTEND_OR);


        StringBuilder prefixQueryBuilder = new StringBuilder();

        // 검색 영역 : 제목
        if (title != null) {
            prefixQueryBuilder.append( "<:contains:" )
                              .append( title )
                              .append( ">")
                              .append( " ");
        }

        // 검색 영역 : 내용
        if (content != null) {
            prefixQueryBuilder.append( "<:contains:" )
                              .append( content )
                              .append( ">")
                              .append( " ");
        }

        // 검색 영역 : 첨부파일
        if (attached != null) {
            prefixQueryBuilder.append( "<:contains:" )
                              .append( attached )
                              .append( ">")
                              .append( " ");
        }


        String prefixQuery = prefixQueryBuilder.toString( )
                                               .trim( );

        ret = search.w3SetPrefixQuery( COLLECTION , prefixQuery, 1 );

        // 검색 기간 설정
        if (modifyTo != null & modifyFrom != null) {
            ret = search.w3SetDateRange(COLLECTION,
                    modifyFrom.substring(0, 4) + "/" + modifyFrom.substring(4, 6) + "/" + modifyFrom.substring(6, 8),
                    modifyTo.substring(0, 4) + "/" + modifyTo.substring(4, 6) + "/" + modifyTo.substring(6, 8));
        }

        SearchVo boardSearch = null;

        String BOARD = "board";

        switch (COLLECTION) {
            case "ALL":
                boardSearch = Board.getSearchResult(this.searchBoard(search, BOARD, SORT_FIELD, RESULT_COUNT, PAGE_START), BOARD);
                dto.setBoard(boardSearch);
        }

        return null;
    }

    private Search searchBoard(Search search, String COLLECTION, String SORT_FIELD, int RESULT_COUNT, int PAGE_START) {
        int ret = 0;
        List<String> SEARCH_FIELD_LIST = null;
        String SEARCH_FIELD = null;
        String DOCUMENT_FIELD = null;

        SEARCH_FIELD_LIST = getBoardSearchFieldList();
        SEARCH_FIELD = String.join(",", SEARCH_FIELD_LIST);
        DOCUMENT_FIELD = getBoardDocumentFieldList();

        // collection, 검색 필드, 출력 필드 설정
        ret = search.w3AddCollection(COLLECTION);
        ret = search.w3SetPageInfo(COLLECTION, PAGE_START, RESULT_COUNT);
        ret = search.w3SetSortField(COLLECTION, SORT_FIELD);
        ret = search.w3SetQueryAnalyzer(COLLECTION, 1, 1, 1, 0);

        ret = search.w3SetSearchField(COLLECTION, SEARCH_FIELD);
        ret = search.w3SetDocumentField(COLLECTION, DOCUMENT_FIELD);

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

    private String getBoardDocumentFieldList() {return null;}

    private List<String> getBoardSearchFieldList() {return null;}
}
