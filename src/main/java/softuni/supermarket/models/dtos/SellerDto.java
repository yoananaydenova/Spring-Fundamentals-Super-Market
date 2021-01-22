package softuni.supermarket.models.dtos;

import softuni.supermarket.models.entities.BaseEntity;
import softuni.supermarket.models.entities.Shop;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.validation.constraints.DecimalMin;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.math.BigDecimal;


public class SellerDto extends BaseEntity {

    private String firstName;
    private String lastName;
    private int age;
    private BigDecimal salary;
    private String shop;

    public SellerDto() {
    }

    public SellerDto(String firstName, String lastName, int age, BigDecimal salary, String shop) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.age = age;
        this.salary = salary;
        this.shop = shop;
    }

    @NotNull(message = "First name cannot be null!")
    @Size(min=2)
    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    @NotNull(message = "Last name cannot be null!")
    @Size(min=2)
    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    @NotNull(message = "Age cannot be null!")
    @Min(value=18)
    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    @NotNull(message = "Salary cannot be null!")
    @DecimalMin(value = "0", message = "Salary must be positive number!")
    public BigDecimal getSalary() {
        return salary;
    }

    public void setSalary(BigDecimal salary) {
        this.salary = salary;
    }

    @NotNull(message = "Shop name cannot be null!")
    @Size(min = 2, message = "Shop name must be at least 2 characters")
    public String getShop() {
        return shop;
    }

    public void setShop(String shop) {
        this.shop = shop;
    }
}
