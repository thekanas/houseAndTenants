package by.stolybko.service.mapper;

import by.stolybko.database.dto.HouseRequestDTO;
import by.stolybko.database.dto.HouseResponseDTO;
import by.stolybko.database.entity.HouseEntity;
import org.mapstruct.BeanMapping;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;
import org.mapstruct.NullValueCheckStrategy;
import org.mapstruct.NullValuePropertyMappingStrategy;
import org.mapstruct.ReportingPolicy;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface HouseMapper {

    HouseEntity toHouseEntity(HouseRequestDTO houseRequestDTO);

    HouseResponseDTO toHouseResponseDTO(HouseEntity houseEntity);

    HouseEntity update(@MappingTarget HouseEntity houseEntity, HouseRequestDTO houseRequestDTO);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE,
            nullValueCheckStrategy = NullValueCheckStrategy.ALWAYS)
    HouseEntity merge(@MappingTarget HouseEntity houseEntity, HouseRequestDTO houseRequestDTO);
}
