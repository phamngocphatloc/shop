package group6.ecommerce.payload.response;

import group6.ecommerce.model.Category;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CategoryResponse {
    private int id;
    private String categoryName;

    public CategoryResponse (Category category){
        this.id = category.getId();
        this.categoryName = category.getCategoryName();
    }
}
