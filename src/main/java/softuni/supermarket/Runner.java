package softuni.supermarket;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Component;
import softuni.supermarket.services.*;

import java.io.BufferedReader;
import java.io.InputStreamReader;

@Component
public class Runner implements CommandLineRunner {

    private final CategoryService categoryService;
    private final TownService townSevice;
    private final ShopService shopService;
    private final SellerService sellerService;
    private final ProductService productService;

    @Autowired
    public Runner(CategoryService categoryService, TownService townSevice, ShopService shopService, SellerService sellerService, ProductService productService) {
        this.categoryService = categoryService;
        this.townSevice = townSevice;
        this.shopService = shopService;
        this.sellerService = sellerService;
        this.productService = productService;
    }

    @Override
    public void run(String... args) throws Exception {

        BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));

        System.out.println("Hi");

        while (true){
            System.out.printf("Choose option from:%n" +
                    "1 - for Add Category%n" +
                    "2 - for Add Town%n" +
                    "3 - for Add Shop%n" +
                    "4 - for Add Seller%n" +
                    "5 - for Add Product%n" +
                    "6 - for Set seller new manager%n" +
                    "7 - for Distributing product in shops%n" +
                    "8 - for Showing all sellers in Shop%n" +
                    "9 - for Showing All Products in Shop%n" +
                    "0 - for EXIT%n");

            String command = reader.readLine();
            switch (command){
                case "0":
                    System.out.println("Bye! Have a nice day!");
                    return;
                case "1":
                    System.out.println("Enter category name:");
                    String categoryName = reader.readLine().trim();
                    System.out.println(this.categoryService.addCategory(categoryName));
                    break;
                case "2":
                    System.out.println("Enter town name:");
                    String townName = reader.readLine().trim();
                    System.out.println(this.townSevice.addTown(townName));
                    break;
                case "3":
                    System.out.println("Enter shop details in format: name address town");
                    String [] shopDetails = reader.readLine().trim().split("\\s+");
                  System.out.println(this.shopService.addShop(shopDetails));
                    break;
                case "4":
                    System.out.println("Enter seller details in format: firstName lastName age salary shopName");
                    String[] sellerDetails = reader.readLine().trim().split("\\s+");
                    System.out.println(this.sellerService.addSeller(sellerDetails));
                    break;
                case "5":
                    System.out.println("Enter product details in format: name price bestBefore(dd-MM-yyyy) category");
                    String[] productDetails = reader.readLine().trim().split("\\s+");
                    System.out.println(this.productService.addProduct(productDetails));
                    break;
                case "6":
                    System.out.println("Enter seller first and last names:");
                    String[] sellerData = reader.readLine().trim().split("\\s+");
                    System.out.println("Enter manager first and last names:");
                    String[] managerData = reader.readLine().trim().split("\\s+");
                    System.out.println(this.sellerService.addManager(sellerData, managerData));
                    break;
                case "7":
                    System.out.println("Enter product name:");
                    String productName = reader.readLine().trim();
                    System.out.println("Enter product distribution in Shops names in format [shopName1 shopName2 ...]:");
                    String[] shopsNames = reader.readLine().trim().split("\\s+");
                    System.out.println(this.shopService.deliveryProduct(productName, shopsNames));
                    break;
                case "8":
                    System.out.println("Enter shop name:");
                    String shopName = reader.readLine().trim();
                   System.out.println(this.sellerService.getAllSellersInShop(shopName));
                    break;
                case "9":
                    System.out.println("Enter shop name:");
                    String shopForProducts = reader.readLine().trim();
                    System.out.println(this.shopService.getAllProductsInShop(shopForProducts));
                    break;
                default:
                    System.out.println("Try again!");
            }
        }
    }
}
