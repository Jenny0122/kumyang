package com.wisenut.spring.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.Getter;

@Data
@Builder
@AllArgsConstructor
public class SearchCondition {
    private Condition condition;
    private String keyword;

}
