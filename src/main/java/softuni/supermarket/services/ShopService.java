package softuni.supermarket.services;

import softuni.supermarket.models.entities.Shop;

public interface ShopService {
    String addShop(String[] shopDetails);

    Shop findByName(String sellerDetail);

    String deliveryProduct(String product, String[] shopsNames);

    String getAllProductsInShop(String shop);
}
