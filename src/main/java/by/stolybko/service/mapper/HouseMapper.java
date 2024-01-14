package by.stolybko.service.mapper;

import by.stolybko.database.dto.HouseRequestDTO;
import by.stolybko.database.dto.HouseResponseDTO;
import by.stolybko.database.entity.HouseEntity;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface HouseMapper {

    HouseEntity toHouseEntity(HouseRequestDTO houseRequestDTO);

    HouseResponseDTO toHouseResponseDTO(HouseEntity houseEntity);
}
