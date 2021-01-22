package softuni.supermarket.services;

public interface SellerService {
    String addSeller(String[] sellerDetails);

    String addManager(String[] sellerData, String[] managerData);

    String getAllSellersInShop(String shopName);
}
