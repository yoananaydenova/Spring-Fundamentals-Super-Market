package softuni.supermarket.models.entities;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Entity
@Table(name="categories")
public class Category extends BaseEntity{

    private String name;

    public Category() {
    }


    @Column(nullable = false, unique = true)
    @Size(min=2, message = "Category name must be at least 2 characters")
    @NotNull(message = "Category name cannot be null")
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
