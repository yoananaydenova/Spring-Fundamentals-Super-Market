package softuni.supermarket.services.impl;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import softuni.supermarket.models.dtos.TownDto;
import softuni.supermarket.models.entities.Category;
import softuni.supermarket.models.entities.Town;
import softuni.supermarket.repositories.TownRepository;
import softuni.supermarket.services.TownService;
import softuni.supermarket.utils.ValidatorUtil;

@Service
public class TownServiceImpl implements TownService {

    private final TownRepository townRepository;
    private final ValidatorUtil validatorUtil;
    private final ModelMapper modelMapper;

    @Autowired
    public TownServiceImpl(TownRepository townRepository, ValidatorUtil validatorUtil, ModelMapper modelMapper) {
        this.townRepository = townRepository;
        this.validatorUtil = validatorUtil;
        this.modelMapper = modelMapper;
    }

    @Override
    public String addTown(String name) {
        StringBuilder sb = new StringBuilder();

        TownDto townDto = new TownDto(name);

        if(this.validatorUtil.isValid(townDto)){
            Town town = this.modelMapper.map(townDto, Town.class);
            this.townRepository.saveAndFlush(town);
            sb.append(String.format("Successfully added town %s!%n========================%n", town.getName()));
        }else{
            this.validatorUtil.violations(townDto)
                    .forEach(e -> sb.append(e.getMessage()).append(System.lineSeparator()));

        }

        return sb.toString();
    }

    @Override
    public Town findTownByName(String name) {
        return this.townRepository.findByName(name).orElse(null);
    }
}
