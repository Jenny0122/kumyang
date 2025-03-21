package com.wisenut.spring.vo;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
@AllArgsConstructor
public class SearchCondition {
    private Condition condition;
    private String keyword;

}
