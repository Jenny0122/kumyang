package com.wisenut.spring.vo;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public enum Condition {
    AND("AND"), OR("OR");

    private final String value;
}
