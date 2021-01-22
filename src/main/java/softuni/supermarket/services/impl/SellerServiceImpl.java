package softuni.supermarket.services.impl;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import softuni.supermarket.models.dtos.SellerDto;
import softuni.supermarket.models.entities.Seller;
import softuni.supermarket.models.entities.Shop;
import softuni.supermarket.repositories.SellerRepository;
import softuni.supermarket.services.SellerService;
import softuni.supermarket.services.ShopService;
import softuni.supermarket.utils.ValidatorUtil;

import java.math.BigDecimal;
import java.util.List;

@Service
public class SellerServiceImpl implements SellerService {

    private final SellerRepository sellerRepository;
    private final ShopService shopService;
    private final ValidatorUtil validatorUtil;
    private final ModelMapper modelMapper;

    @Autowired
    public SellerServiceImpl(SellerRepository sellerRepository, ShopService shopService, ValidatorUtil validatorUtil, ModelMapper modelMapper) {
        this.sellerRepository = sellerRepository;
        this.shopService = shopService;
        this.validatorUtil = validatorUtil;
        this.modelMapper = modelMapper;
    }

    @Override
    public String addSeller(String[] sellerDetails) {

        StringBuilder sb = new StringBuilder();

        if (sellerDetails.length != 5) {
            return "Wrong input! Please enter seller details in format: firstName lastName age salary shopName";
        }

        SellerDto sellerDto = new SellerDto(sellerDetails[0], sellerDetails[1], Integer.parseInt(sellerDetails[2]), new BigDecimal(sellerDetails[3]), sellerDetails[4]);


        if (this.validatorUtil.isValid(sellerDto)) {

            Shop shopByName = this.shopService.findByName(sellerDto.getShop());
            if (shopByName == null) {
                return String.format("Shop with name %s dont exist!", sellerDetails[4]);
            }

            Seller seller = this.modelMapper.map(sellerDto, Seller.class);
            seller.setShop(shopByName);

            this.sellerRepository.saveAndFlush(seller);

            sb.append(String.format("Successfully added seller %s %s!%n========================%n", seller.getFirstName(), seller.getLastName()));
        } else {
            this.validatorUtil.violations(sellerDto)
                    .forEach(e -> sb.append(e.getMessage()).append(System.lineSeparator()));

        }
        return sb.toString();

    }

    @Override
    public String addManager(String[] sellerData, String[] managerData) {
        if (sellerData.length != 2) {
            return "Wrong input! Please enter seller details in format: firstName lastName";
        }

        if (managerData.length != 2) {
            return "Wrong input! Please enter manager details in format: firstName lastName";
        }

        Seller seller = this.sellerRepository.findByFirstNameAndLastName(sellerData[0], sellerData[1]).orElse(null);
        Seller manager = this.sellerRepository.findByFirstNameAndLastName(managerData[0], managerData[1]).orElse(null);
        if (seller == null) {
            return "Not exist seller with these names!";
        }
        if (manager == null) {
            return "Not exist manager with these names!";
        }
        if (seller.getId().equals(manager.getId())) {
            return "Seller cannot be manager of yourself!";
        }

        seller.setManager(manager);
        this.sellerRepository.saveAndFlush(seller);

        return String.format("Successfully added manager %s %s of seller %s %s!%n========================%n",
                manager.getFirstName(), manager.getLastName(), seller.getFirstName(), seller.getLastName());
    }

    @Override
    public String getAllSellersInShop(String shopName) {

        Shop shopByName = this.shopService.findByName(shopName);
        if (shopByName == null) {
            return String.format("Shop with name %s dont exist!", shopName);
        }

        List<Seller> sellers = this.sellerRepository.findAllByShopName(shopName);

        if (sellers.isEmpty()) {
            return String.format("Shop with name %s have not seller!", shopByName.getName());
        }

        StringBuilder sb = new StringBuilder();

        sellers.forEach(seller -> {
            sb.append(String.format("%s %s%n", seller.getFirstName(), seller.getLastName()));
        });

        return sb.toString();
    }
}
