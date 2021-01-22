package softuni.supermarket.services;

import softuni.supermarket.models.entities.Product;

import java.util.Optional;

public interface ProductService {
    String addProduct(String[] productDetails);

    Product findProduct(String productName);


}
