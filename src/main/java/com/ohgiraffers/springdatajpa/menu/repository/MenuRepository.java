package com.ohgiraffers.springdatajpa.menu.repository;

import com.ohgiraffers.springdatajpa.menu.entity.Menu;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface MenuRepository extends JpaRepository<Menu, Integer> {

    // 전달 받은 menuPrice보다 큰 menuPrice를 가진 메뉴 목록 조회
    List<Menu> findByMenuPriceGreaterThan(Integer menuPrice);

    // 전달 받은 menuPrice보다 큰 menuPrice를 가진 메뉴 목록을 메뉴 가격 오름차순으로 조회
    List<Menu> findByMenuPriceGreaterThanOrderByMenuPrice(Integer menuPrice);

    // 전달 받은 menuPrice보다 큰 menuPrice를 가진 메뉴 목록을 메뉴 가격 내림차순으로 조회
    List<Menu> findByMenuPriceGreaterThan(Integer menuPrice, Sort sort);

    // /* 과제 1. 검색어가 들어간 메뉴 조회 */
    List<Menu> findBymenuNameContaining(String menuName);

    /* 과제 2. 가격 사이 조회 */
    List<Menu> findByMenuPriceBetween(Integer menuPrice1, Integer menuPrice2);

//    List<Menu> findByCategoryCode(Integer categoryCode);

    /* 과제 3. 카테고리 별 조회 */
    @Query(value = "SELECT MENU_CODE, MENU_NAME, MENU_PRICE, CATEGORY_CODE, ORDERABLE_STATUS FROM TBL_MENU WHERE CATEGORY_CODE = ?1  ORDER BY MENU_CODE",
            nativeQuery = true)
    List<Menu> findByCategoryCode(Integer categoryCode);
}
