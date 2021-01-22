package softuni.supermarket.services;

import softuni.supermarket.models.entities.Town;

import java.util.Optional;

public interface TownService {
    String addTown(String name);
    Town findTownByName(String name);
}
