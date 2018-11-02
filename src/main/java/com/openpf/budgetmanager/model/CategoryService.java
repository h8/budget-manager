package com.openpf.budgetmanager.model;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class CategoryService {

    private final CategoryRepo repo;

    @Autowired
    public CategoryService(CategoryRepo repo) {
        this.repo = repo;
    }

    public Optional<Category> get(Long id) {
        return repo.findById(id);
    }

    public Category create(String title) {
        Category category = new Category();
        category.title = title;

        return repo.save(category);
    }

    public Optional<Category> delete(Long id) {
        var opt = get(id);
        opt.ifPresent(repo::delete);
        return opt;
    }

    public List<Category> all() {
        return repo.findAll(Sort.by("title"));
    }

    public Optional<Category> rename(Long id, String title) {
        var opt = get(id);
        opt.ifPresent(category -> {
            category.title = title;
            repo.save(category);
        });
        return opt;
    }
}
