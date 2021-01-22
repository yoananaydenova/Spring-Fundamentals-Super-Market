package softuni.supermarket.services;

import softuni.supermarket.models.entities.Category;

public interface CategoryService {
    String addCategory(String name);

    Category findByName(String name);
}
