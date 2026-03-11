package com.welfare.welfare_management.config;


import com.welfare.welfare_management.dto.HouseholdDTOs.HouseholdMemberDTO;
import com.welfare.welfare_management.model.HouseholdModels.HouseholdMember;
import org.modelmapper.ModelMapper;
import org.modelmapper.convention.MatchingStrategies;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ModelMapperConfig {

    @Bean
    public ModelMapper modelMapper() {
        ModelMapper modelMapper = new ModelMapper();

        // Prevents fuzzy matching errors across all entities
        modelMapper.getConfiguration()
                .setMatchingStrategy(MatchingStrategies.STRICT);

        // Skip householdId on member - we set it manually in the service
        modelMapper.typeMap(HouseholdMemberDTO.class, HouseholdMember.class)
                .addMappings(mapper -> mapper.skip(HouseholdMember::setHouseholdId));

        return modelMapper;
    }
}
