package com.ohgiraffers.springdatajpa.menu.controller;

import com.ohgiraffers.springdatajpa.menu.dto.MenuDTO;
import com.ohgiraffers.springdatajpa.menu.service.MenuService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@Controller
@RequestMapping("/menu/homework")
public class HomeworkController {

    private final MenuService menuService;

    public HomeworkController(MenuService menuService) {
        this.menuService = menuService;
    }

    @GetMapping("/like")
    public void likequeryMethodPage() {}

    @GetMapping("/likeSearch")
    public String findByMenuName(@RequestParam String menuName, Model model) {

        List<MenuDTO> menuList = menuService.findByNameLike(menuName);

        model.addAttribute("menuList", menuList);

        return "menu/homework/likeSearchResult";
    }

    @GetMapping("/between")
    public void betweenqueryMethodPage() {}

    @GetMapping("/betweenSearch")
    public String findByMenuPriceBetween(@RequestParam("menuPrice1") Integer menuPrice1,
                                @RequestParam("menuPrice2") Integer menuPrice2, Model model) {

        List<MenuDTO> menuList = menuService.findByMenuPriceBetween(menuPrice1, menuPrice2);
        model.addAttribute("menuList", menuList);

        return "menu/homework/betweenSearchResult";
    }

    @GetMapping("/category")
    public void categoryQueryPage() {}

    @GetMapping("/categorySearch")
    public String findByCategoryCode(@RequestParam Integer categoryCode, Model model) {

        List<MenuDTO> menuList = menuService.findByCategoryCode(categoryCode);

        model.addAttribute("menuList", menuList);

        return "menu/homework/categorySearchResult";
    }

}
