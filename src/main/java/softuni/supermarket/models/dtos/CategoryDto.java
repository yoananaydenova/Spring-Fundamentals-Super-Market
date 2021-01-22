package softuni.supermarket.models.dtos;

import softuni.supermarket.models.entities.BaseEntity;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;


public class CategoryDto extends BaseEntity {

    private String name;

    public CategoryDto() {
    }


    public CategoryDto(String name) {
        this.name = name;
    }

    @Size(min=2, message = "Category name must be at least 2 characters!")
    @NotNull(message = "Category name cannot be null")
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
