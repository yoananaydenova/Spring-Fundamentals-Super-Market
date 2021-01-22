package softuni.supermarket.config;

import org.modelmapper.ModelMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import softuni.supermarket.utils.DateValidator;
import softuni.supermarket.utils.DateValidatorUsingDateFormat;
import softuni.supermarket.utils.ValidatorUtil;
import softuni.supermarket.utils.ValidatorUtilImpl;

import javax.validation.Validation;
import javax.validation.Validator;

@Configuration
public class ApplicationBeanConfiguration {
    @Bean
    public ModelMapper modelMapper() {
        return new ModelMapper();
    }

    @Bean
    public Validator validator() {
        return Validation.buildDefaultValidatorFactory().getValidator();
    }

    @Bean
    public ValidatorUtil validatorUtil() {
        return new ValidatorUtilImpl(validator());
    }

    @Bean
    public DateValidator dateValidator(){
        return new DateValidatorUsingDateFormat();
    }
}
