//package com.wisenut.spring.config;
//
//import QueryAPI530.Search;
//import lombok.extern.slf4j.Slf4j;
//import org.springframework.beans.factory.annotation.Value;
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//
//@Configuration
//@Slf4j
//public class SearchConfig {
//
//    final int QUERY_LOG = 1;
//
//    final String ENCODE_VALUE = "UTF-8";
//
//    final int SERVER_TIMEOUT = 10 * 1000;
//
//    @Value("${wise.sf1.serverIp}")
//    String SERVER_IP;
//
//    @Value("${wise.sf1.serverPort}")
//    int SERVER_PORT;
//
//    @Bean
//    public Search getSearch() {
//
//
//        Search search = new Search();
//        int ret = 0;
//
//        // common query 설정
//        ret = search.w3SetCodePage(ENCODE_VALUE);
//        ret = search.w3SetQueryLog(QUERY_LOG);
//        ret = search.w3ConnectServer(SERVER_IP, SERVER_PORT, SERVER_TIMEOUT);
//
//        return search;
//    }
//}
