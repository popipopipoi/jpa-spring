package com.ohgiraffers.springdatajpa.menu.service;

import com.ohgiraffers.springdatajpa.menu.dto.CategoryDTO;
import com.ohgiraffers.springdatajpa.menu.dto.MenuDTO;
import com.ohgiraffers.springdatajpa.menu.entity.Category;
import com.ohgiraffers.springdatajpa.menu.entity.Menu;
import com.ohgiraffers.springdatajpa.menu.repository.CategoryRepository;
import com.ohgiraffers.springdatajpa.menu.repository.MenuRepository;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class MenuService {

    private final MenuRepository menuRepository;

    private final CategoryRepository categoryRepository;
    private final ModelMapper modelMapper;


    public MenuService(MenuRepository menuRepository, CategoryRepository categoryRepository, ModelMapper modelMapper) {
        this.menuRepository = menuRepository;
        this.categoryRepository = categoryRepository;
        this.modelMapper = modelMapper;
    }

    /* 1. id로 entity 조회 : findById */
    public MenuDTO findMenuByCode(int menuCode) {

        // Entity로 조회한 뒤 비영속 객체인 MenuDTO로 변환해서 반환한다.
        Menu menu = menuRepository.findById(menuCode).orElseThrow(IllegalAccessError::new);

//        MenuDTO menuDTO = new MenuDTO();
//        menuDTO.setMenuCode(menu.getMenuCode());
//        .. -> 하나하나 변환하기에는 너무 길고 비효율적. 그래서 한번에 변환해주는 라이브러리 사용 ->ModelMapper

        return modelMapper.map(menu, MenuDTO.class);
    }

    /* 2-1. 모든 entity 조회 : findAll(Sort) */
    public List<MenuDTO> findMenuList() {

        List<Menu> menuList = menuRepository.findAll(Sort.by("menuCode").descending());
        //menuCode 이 속성값을 기준으로 해서 Sort한다.

        return menuList.stream().map(menu -> modelMapper.map(menu, MenuDTO.class)).collect(Collectors.toList());
        // stream : 배열이든, 컬렉션이든 안에 있는 요소들을 가공하거나 순회하고 싶을 때 사용한다. 가공을 할려고 하는거지
        //         객체로서 사용하지는 않는다.-> List<Menu> -> Stream<Menu> 변환
        // map : 배열안에 있는 요소들을 가공하고 반환할때 사용, 람다식으로 작성한거를 가공하고 반환 ->
        //       <Stream Menu> -> Stream<MenuDTO> 변환
        //collect(Collectors.toList()) : 컬렉션 리스트로 반환해라 -> Stream<MenuDTO> -> List<MenuDTO>
    }

    /* 2-2. 페이징 된 entity 조회 : findAll(Pageable) */
    public Page<MenuDTO> findMenuList(Pageable pageable) {

        pageable = PageRequest.of(pageable.getPageNumber() <= 0 ? 0 : pageable.getPageNumber() - 1,
                pageable.getPageSize(),
                Sort.by("menuCode").descending());

        Page<Menu> menuList = menuRepository.findAll(pageable);

        return menuList.map(menu -> modelMapper.map(menu, MenuDTO.class));
        // Page 자체에 map으로 가공하는 기능이 정의되어 있다.
    }

    /* 3. Query Method Test */
    public List<MenuDTO> findByMenuPrice(Integer menuPrice) {

//        List<Menu> menuList = menuRepository.findByMenuPriceGreaterThan(menuPrice);

//        List<Menu> menuList = menuRepository.findByMenuPriceGreaterThanOrderByMenuPrice(menuPrice);

        List<Menu> menuList = menuRepository.findByMenuPriceGreaterThan(menuPrice, Sort.by("menuPrice").descending());


        return menuList.stream().map(menu -> modelMapper.map(menu, MenuDTO.class)).collect(Collectors.toList());
    }

    /* 4. JPQL or Native Query Test */
    public List<CategoryDTO> findAllCategory() {

        List<Category> categoryList = categoryRepository.findAllCategory();

        return categoryList.stream()
                .map(category -> modelMapper.map(category, CategoryDTO.class))
                .collect(Collectors.toList());
    }

    /* 5. Entity 저장 */
    @Transactional
    public void registNewMenu(MenuDTO menu) {

        menuRepository.save(modelMapper.map(menu, Menu.class));
    }


    /* 6. Entity 수정 */
    @Transactional
    public void modifyMenu(MenuDTO menu) {
        Menu foundMenu = menuRepository.findById(menu.getMenuCode()).orElseThrow(IllegalAccessError::new);
        foundMenu.setMenuName(menu.getMenuName());
    }

    /* 7. Entity 삭제 */
    @Transactional
    public void deleteMenu(Integer menuCode) {
        menuRepository.deleteById(menuCode);
    }


    /* 과제 1. 검색어가 들어간 메뉴 조회 */
    public List<MenuDTO> findByNameLike(String menuName) {

        List<Menu> menuList = menuRepository.findBymenuNameContaining(menuName);


        return menuList.stream().map(menu -> modelMapper.map(menu, MenuDTO.class)).collect(Collectors.toList());
    }

    /* 과제 2. 가격 사이 조회 */

    public List<MenuDTO> findByMenuPriceBetween(Integer menuPrice1, Integer menuPrice2) {

        List<Menu> menuList = menuRepository.findByMenuPriceBetween(menuPrice1, menuPrice2);

        return menuList.stream().map(menu -> modelMapper.map(menu, MenuDTO.class)).collect(Collectors.toList());
    }

    /* 과제 3. 카테고리 별 조회 */
    public List<MenuDTO> findByCategoryCode(Integer categoryCode) {

        List<Menu> menuList = menuRepository.findByCategoryCode(categoryCode);

        return menuList.stream().map(menu -> modelMapper.map(menu, MenuDTO.class)).collect(Collectors.toList());

    }

}
