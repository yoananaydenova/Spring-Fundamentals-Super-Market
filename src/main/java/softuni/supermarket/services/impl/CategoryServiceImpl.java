package softuni.supermarket.services.impl;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import softuni.supermarket.models.dtos.CategoryDto;
import softuni.supermarket.models.entities.Category;
import softuni.supermarket.repositories.CategoryRepository;
import softuni.supermarket.services.CategoryService;
import softuni.supermarket.utils.ValidatorUtil;

import java.util.Optional;

@Service
public class CategoryServiceImpl implements CategoryService {

    private final CategoryRepository categoryRepository;
    private final ValidatorUtil validatorUtil;
    private final ModelMapper modelMapper;

    @Autowired
    public CategoryServiceImpl(CategoryRepository categoryRepository, ValidatorUtil validatorUtil, ModelMapper modelMapper) {
        this.categoryRepository = categoryRepository;
        this.validatorUtil = validatorUtil;
        this.modelMapper = modelMapper;
    }


    @Override
    public String addCategory(String name) {
        StringBuilder sb = new StringBuilder();

        CategoryDto categoryDto = new CategoryDto(name);
        Optional<Category> categoryByName = this.categoryRepository.findByName(name);
        if(categoryByName.isPresent()){
            return "Please enter unique category name!";
        }
        if(this.validatorUtil.isValid(categoryDto) ){
            Category category = this.modelMapper.map(categoryDto, Category.class);
            this.categoryRepository.saveAndFlush(category);
            sb.append(String.format("Successfully added category %s!%n========================%n", category.getName()));
        }else{
            this.validatorUtil.violations(categoryDto)
                    .forEach(e -> sb.append(e.getMessage()).append(System.lineSeparator()));

        }
        return sb.toString();
    }

    @Override
    public Category findByName(String name) {
        return this.categoryRepository.findByName(name).orElse(null);
    }
}
