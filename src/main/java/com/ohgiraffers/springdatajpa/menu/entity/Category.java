package com.ohgiraffers.springdatajpa.menu.entity;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.*;

@Entity
@Table(name = "tbl_category")
@Getter
@Setter
@ToString
public class Category {

    @Id
    @GeneratedValue(
            strategy = GenerationType.SEQUENCE,
            generator = "seq_menu_code_generator")
    private int categoryCode;
    private String categoryName;
    private Integer refCategoryCode;
}
