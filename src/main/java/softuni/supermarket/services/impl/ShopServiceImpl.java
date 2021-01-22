package softuni.supermarket.services.impl;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;
import softuni.supermarket.models.dtos.ShopDto;
import softuni.supermarket.models.dtos.TownDto;
import softuni.supermarket.models.entities.Product;
import softuni.supermarket.models.entities.Shop;
import softuni.supermarket.models.entities.Town;
import softuni.supermarket.repositories.ShopRepository;
import softuni.supermarket.services.ProductService;
import softuni.supermarket.services.ShopService;
import softuni.supermarket.services.TownService;
import softuni.supermarket.utils.ValidatorUtil;

import javax.transaction.Transactional;
import java.beans.Transient;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

@Service
public class ShopServiceImpl implements ShopService {

    private final ShopRepository shopRepository;
    private final TownService townService;
    private final ProductService productService;
    private final ValidatorUtil validatorUtil;
    private final ModelMapper modelMapper;

    @Autowired
    public ShopServiceImpl( ShopRepository shopRepository, TownService townService,@Lazy ProductService productService, ValidatorUtil validatorUtil, ModelMapper modelMapper) {
        this.shopRepository = shopRepository;
        this.townService = townService;
        this.productService = productService;
        this.validatorUtil = validatorUtil;
        this.modelMapper = modelMapper;
    }

    @Override
    public String addShop(String[] shopDetails) {
        StringBuilder sb = new StringBuilder();

        if (shopDetails.length != 3) {
            return "Wrong input! Please enter shop details in format: name address town";
        }

        Optional<Shop> shopByName = this.shopRepository.findByAddress(shopDetails[1]);
        if (shopByName.isPresent()) {
            return "Please enter unique shop address!";
        }

        TownDto townDto = new TownDto(shopDetails[2]);
        ShopDto shopDto = new ShopDto(shopDetails[0], shopDetails[1], townDto);

        if (this.validatorUtil.isValid(shopDto)) {
            Town townByName = this.townService.findTownByName(shopDto.getTown().getName());
            if (townByName == null) {
                String addedTown = this.townService.addTown(shopDto.getTown().getName());
                townByName = this.townService.findTownByName(shopDto.getTown().getName());
                sb.append(addedTown).append(System.lineSeparator());
            }
            Shop shop = this.modelMapper.map(shopDto, Shop.class);
            shop.setTown(townByName);

            this.shopRepository.saveAndFlush(shop);
            sb.append(String.format("Successfully added shop %s!%n========================%n", shop.getName()));
        } else {
            this.validatorUtil.violations(shopDto)
                    .forEach(e -> sb.append(e.getMessage()).append(System.lineSeparator()));

        }
        return sb.toString();
    }

    @Override
    public Shop findByName(String shopName) {
        return this.shopRepository.findByName(shopName).orElse(null);
    }

    @Override
    @Transactional
    public String deliveryProduct(String productName, String[] shopsNames) {
        StringBuilder sb = new StringBuilder();

        Product product = this.productService.findProduct(productName);
        if (product == null) {
            return String.format("Not exist product with name %s%n", productName);
        }

        Arrays.stream(shopsNames)
                .forEach(shop -> {
                    Optional<Shop> savedShop = this.shopRepository.findByName(shop);
                    if (savedShop.isEmpty()) {
                        sb.append(String.format("Not exist shop with name %s%n", shop));
                    } else {
                        Set<Product> products = savedShop.get().getProducts();
                        if(products.isEmpty()){
                            System.out.println();
                            savedShop.get().getProducts().add(product);
                            Set<Shop> shops = product.getShops();
                            shops.add(savedShop.get());
                            System.out.println();
                        }
                        this.shopRepository.saveAndFlush(savedShop.get());
                        sb.append(String.format("Successfully added product %s into shop %s!%n========================%n", product.getName(), savedShop.get().getName()));
                    }

                });
        return sb.toString();
    }

    @Override
    public String getAllProductsInShop(String shopForProducts) {

        Shop shopByName = this.shopRepository.findByName(shopForProducts).orElse(null);
        if (shopByName == null) {
            return String.format("Shop with name %s dont exist!", shopForProducts);
        }


        if (shopByName.getProducts().isEmpty()) {
            return String.format("Shop with name %s have not products!", shopByName.getName());
        }
        StringBuilder sb = new StringBuilder();

        shopByName.getProducts().forEach(product -> {
            sb.append(String.format("%s - %s $%n", product.getName(), product.getPrice()));
        });

        return sb.toString();

    }
}
