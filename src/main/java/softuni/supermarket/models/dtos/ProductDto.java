package softuni.supermarket.models.dtos;

import org.springframework.format.annotation.DateTimeFormat;
import softuni.supermarket.models.entities.BaseEntity;

import javax.validation.constraints.DecimalMin;
import javax.validation.constraints.Future;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.math.BigDecimal;
import java.time.LocalDate;


public class ProductDto extends BaseEntity {

    private LocalDate bestBefore;
    private String description;
    private String name;
    private BigDecimal price;
    private CategoryDto category;
    // name price bestBefore(dd-MM-yyyy) category

    public ProductDto() {
    }

    public ProductDto(String name, BigDecimal price, LocalDate bestBefore, CategoryDto category) {
        this.bestBefore = bestBefore;
        this.name = name;
        this.price = price;
        this.category = category;
    }

    @DateTimeFormat(pattern = "dd-MM-yyyy")
    @Future(message = "Date of product must be in the future!")
    public LocalDate getBestBefore() {
        return bestBefore;
    }

    public void setBestBefore(LocalDate bestBefore) {
        this.bestBefore = bestBefore;
    }


    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    @NotNull(message = "Name of the product cannot be null!")
    @Size(min = 2, message = "Name of the product cannot be less then 2 characters")
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @NotNull(message = "Price of the product cannot be null!")
    @DecimalMin(value = "0", message = "Price of the product must be positive number!")
    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    @NotNull(message = "Category of the product cannot be null!")
    public CategoryDto getCategory() {
        return category;
    }

    public void setCategory(CategoryDto category) {
        this.category = category;
    }


}
