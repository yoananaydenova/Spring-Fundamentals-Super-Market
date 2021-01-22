package softuni.supermarket.services.impl;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import softuni.supermarket.models.dtos.CategoryDto;
import softuni.supermarket.models.dtos.ProductDto;
import softuni.supermarket.models.entities.Category;
import softuni.supermarket.models.entities.Product;
import softuni.supermarket.models.entities.Seller;
import softuni.supermarket.models.entities.Shop;
import softuni.supermarket.repositories.ProductRepository;
import softuni.supermarket.services.CategoryService;
import softuni.supermarket.services.ProductService;
import softuni.supermarket.services.ShopService;
import softuni.supermarket.utils.DateValidator;
import softuni.supermarket.utils.ValidatorUtil;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;

@Service
public class ProductServiceImpl implements ProductService {

    private final ProductRepository productRepository;
    private final ValidatorUtil validatorUtil;
    private final ModelMapper modelMapper;
    private final DateValidator dateValidator;
    private final CategoryService categoryService;
    private final ShopService shopService;

    @Autowired
    public ProductServiceImpl(ProductRepository productRepository, ValidatorUtil validatorUtil, ModelMapper modelMapper, DateValidator dateValidator, CategoryService categoryService, ShopService shopService) {
        this.productRepository = productRepository;
        this.validatorUtil = validatorUtil;
        this.modelMapper = modelMapper;
        this.dateValidator = dateValidator;
        this.categoryService = categoryService;
        this.shopService = shopService;
    }


    @Override
    public String addProduct(String[] productDetails) {
        // name price bestBefore(dd-MM-yyyy) category
        StringBuilder sb = new StringBuilder();

        if (productDetails.length != 4) {
            return "Wrong input! Please enter product details in format: name price bestBefore(dd-MM-yyyy) category";
        }
        if(!dateValidator.isValid(productDetails[2])){
            return "Bad data input! BestBefore must be in format: dd-MM-yyyy";
        }

        ProductDto productDto = new ProductDto(productDetails[0],
                new BigDecimal(productDetails[1]),
                LocalDate.parse(productDetails[2], DateTimeFormatter.ofPattern("dd-MM-yyyy")),
                new CategoryDto(productDetails[3]));

        if (this.validatorUtil.isValid(productDto)) {
            Category categoryByName = this.categoryService.findByName(productDto.getCategory().getName());
            if(categoryByName==null){
                String addedCategory = this.categoryService.addCategory(productDto.getCategory().getName());
                categoryByName = this.categoryService.findByName(productDto.getCategory().getName());
                sb.append(addedCategory).append(System.lineSeparator());
            }

            Product product = this.modelMapper.map(productDto, Product.class);
            product.setCategory(categoryByName);
            this.productRepository.saveAndFlush(product);

           sb.append(String.format("Successfully product %s with price %s!%n========================%n", product.getName(), product.getPrice()));
        } else {
            this.validatorUtil.violations(productDto)
                    .forEach(e -> sb.append(e.getMessage()).append(System.lineSeparator()));
        }
        return sb.toString();
    }

    @Override
    public Product findProduct(String productName) {
       return this.productRepository.findByName(productName).orElse(null);
   }


}
