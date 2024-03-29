package org.blogy.service.impl;

import java.util.*;

import org.blogy.service.ArticleService;
import org.blogy.service.CategoryService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import org.blogy.entity.Article;
import org.blogy.entity.Category;
import org.blogy.form.ArticleForm;
import org.blogy.util.SlugGenerator;
import org.blogy.repository.ArticleRepository;

@Service
@Transactional
public class ArticleServiceImpl implements ArticleService {
    private final CategoryService categoryService;

    private final ArticleRepository articleRepository;

    public ArticleServiceImpl(CategoryService categoryService, ArticleRepository articleRepository) {
        this.categoryService = categoryService;
        this.articleRepository = articleRepository;
    }

    @Transactional(readOnly = true)
    public long count() {
        return articleRepository.count();
    }

    @Transactional(readOnly = true)
    public List<Article> getArticles() {
        return articleRepository.findAllByOrderByCreatedDesc();
    }

    @Transactional(readOnly = true)
    public Page<Article> getArticles(Pageable pageable) {
        return articleRepository.findAllByOrderByCreatedDesc(pageable);
    }

    @Transactional(readOnly = true)
    public List<Article> getArticles(String categoryName) {
        return articleRepository.findAllByCategoryName(categoryName);
    }

    @Transactional(readOnly = true)
    public Article getArticle(long id) {
        return articleRepository.findById(id).orElseThrow();
    }

    @Transactional(readOnly = true)
    public Article getArticle(String slug) {
        return articleRepository.findBySlug(slug).orElseThrow();
    }

    public Article createArticle(ArticleForm form) {
        Category category = categoryService.getCategory(form.getCategoryId());

        String slug = SlugGenerator.toSlug(form.getTitle());

        if (articleRepository.existsBySlug(slug)) {
            slug = SlugGenerator.toSlug(form.getTitle(), true);
        }

        Article article = new Article();

        article.setSlug(slug);
        article.setCategory(category);
        article.setTitle(form.getTitle());
        article.setContent(form.getContent());

        return articleRepository.save(article);
    }

    public Article updateArticle(long id, ArticleForm form) {
        Article article = getArticle(id);
        Category category = categoryService.getCategory(form.getCategoryId());

        article.setCategory(category);
        article.setTitle(form.getTitle());
        article.setContent(form.getContent());

        return articleRepository.save(article);
    }

    public void delete(long id) {
        articleRepository.deleteById(id);
    }
}
