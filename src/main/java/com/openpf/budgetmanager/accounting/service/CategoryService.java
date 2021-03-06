package com.openpf.budgetmanager.accounting.service;

import com.google.common.collect.Lists;
import com.openpf.budgetmanager.accounting.model.Category;
import com.openpf.budgetmanager.accounting.repository.CategoryRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.Set;

@Service
public class CategoryService {

    private final CategoryRepo repo;

    @Autowired
    public CategoryService(CategoryRepo repo) {
        this.repo = repo;
    }

    public Optional<Category> get(Long id) {
        return (id != null)
                ? repo.findById(id)
                : Optional.empty();
    }

    public Category create(String title) {
        if (title == null || title.isBlank()) {
            throw new IllegalArgumentException("Category title can't be empty or null.");
        }

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
        return repo.findAllByOrderByTitleAsc();
    }

    public Optional<Category> rename(Long id, String title) {
        var opt = get(id);
        opt.ifPresent(category -> {
            category.title = title;
            repo.save(category);
        });
        return opt;
    }

    public boolean exists(Long id) {
        return (id != null) && repo.existsById(id);
    }

    public List<Category> getMany(Set<Long> keys) {
        return Lists.newLinkedList(repo.findAllById(keys));
    }
}
