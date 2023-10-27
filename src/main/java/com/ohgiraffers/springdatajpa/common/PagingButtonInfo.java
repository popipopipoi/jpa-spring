package com.ohgiraffers.springdatajpa.common;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
@AllArgsConstructor // 모든 매개변수를 전달받는 어노테이션
public class PagingButtonInfo {

    private int currentPage;
    private int startPage;
    private int endPage;
}
