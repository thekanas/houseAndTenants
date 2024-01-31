package by.stolybko.service.mapper;

import by.stolybko.database.dto.request.HouseRequestDTO;
import by.stolybko.database.dto.response.HouseHistoryResponseDTO;
import by.stolybko.database.dto.response.HouseResponseDTO;
import by.stolybko.database.entity.HouseEntity;
import by.stolybko.database.entity.HouseHistory;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.ReportingPolicy;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface HouseMapper {

    HouseEntity toHouseEntity(HouseRequestDTO houseRequestDTO);

    HouseResponseDTO toHouseResponseDTO(HouseEntity houseEntity);

    @Mapping(target = "uuid", source = "house.uuid")
    @Mapping(target = "area", source = "house.area")
    @Mapping(target = "country", source = "house.country")
    @Mapping(target = "city", source = "house.city")
    @Mapping(target = "street", source = "house.street")
    @Mapping(target = "number", source = "house.number")
    @Mapping(target = "createDate", source = "house.createDate")
    HouseHistoryResponseDTO toHouseHistoryResponseDTO(HouseHistory houseHistory);

    HouseEntity update(@MappingTarget HouseEntity houseEntity, HouseRequestDTO houseRequestDTO);

}
