package org.blogy.controller.admin;

import org.springframework.ui.Model;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import org.blogy.service.ArticleService;
import org.blogy.service.CategoryService;

@Controller
@RequestMapping("/admin")
public class AdminController {
    private final ArticleService articleService;

    private final CategoryService categoryService;

    public AdminController(ArticleService articleService, CategoryService categoryService) {
        this.articleService = articleService;
        this.categoryService = categoryService;
    }

    @GetMapping
    public String index(Model model) {
        long articlesCount = articleService.count();
        long categoriesCount = categoryService.count();

        model.addAttribute("articlesCount", articlesCount);
        model.addAttribute("categoriesCount", categoriesCount);

        return "admin/index";
    }
}
