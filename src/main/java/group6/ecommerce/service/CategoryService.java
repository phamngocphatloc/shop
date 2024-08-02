package group6.ecommerce.service;

import group6.ecommerce.model.Category;
import group6.ecommerce.payload.response.CategoryResponse;

import java.util.List;

public interface CategoryService {
    Category findCategoryByName(String name);
    List<CategoryResponse> finaAllCategory ();
}

