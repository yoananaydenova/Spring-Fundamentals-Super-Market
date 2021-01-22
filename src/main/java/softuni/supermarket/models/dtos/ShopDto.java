package softuni.supermarket.models.dtos;

import softuni.supermarket.models.entities.BaseEntity;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.HashSet;
import java.util.Set;


public class ShopDto extends BaseEntity {


    private String name;
    private String address;
    private TownDto town;
    private Set<ProductDto> products;

    public ShopDto() {
    }

    public ShopDto(String name, String address, TownDto town) {
        this.address = address;
        this.name = name;
        this.town = town;
        this.products = new HashSet<>();
    }

    @NotNull(message = "Address cannot be null!")
    @Size(min = 2)
    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    @NotNull(message = "Shop name cannot be null!")
    @Size(min = 2)
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @NotNull(message = "Town cannot be null!")
    public TownDto getTown() {
        return town;
    }

    public void setTown(TownDto town) {
        this.town = town;
    }


    public Set<ProductDto> getProducts() {
        return products;
    }

    public void setProducts(Set<ProductDto> products) {
        this.products = products;
    }
}
