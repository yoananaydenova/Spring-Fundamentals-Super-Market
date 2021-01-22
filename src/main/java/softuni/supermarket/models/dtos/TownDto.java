package softuni.supermarket.models.dtos;

import softuni.supermarket.models.entities.BaseEntity;


public class TownDto extends BaseEntity {

    private String name;

    public TownDto() {
    }

    public TownDto(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
