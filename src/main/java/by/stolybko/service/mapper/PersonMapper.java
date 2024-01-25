package by.stolybko.service.mapper;

import by.stolybko.database.dto.response.PersonHistoryResponseDTO;
import by.stolybko.database.dto.request.PersonRequestDTO;
import by.stolybko.database.dto.response.PersonResponseDTO;
import by.stolybko.database.entity.HouseHistory;
import by.stolybko.database.entity.PersonEntity;
import org.mapstruct.BeanMapping;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.NullValueCheckStrategy;
import org.mapstruct.NullValuePropertyMappingStrategy;
import org.mapstruct.ReportingPolicy;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface PersonMapper {

    PersonEntity toPersonEntity(PersonRequestDTO personRequestDTO);

    PersonResponseDTO toPersonResponseDTO(PersonEntity personEntity);

    @Mapping(target = "uuid", source = "person.uuid")
    @Mapping(target = "name", source = "person.name")
    @Mapping(target = "surname", source = "person.surname")
    @Mapping(target = "sex", source = "person.sex")
    @Mapping(target = "passport", source = "person.passport")
    @Mapping(target = "createDate", source = "person.createDate")
    @Mapping(target = "updateDate", source = "person.updateDate")
    PersonHistoryResponseDTO toPersonHistoryResponseDTO(HouseHistory houseHistory);

    PersonEntity update(@MappingTarget PersonEntity personEntity, PersonRequestDTO personRequestDTO);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE,
            nullValueCheckStrategy = NullValueCheckStrategy.ALWAYS)
    PersonEntity merge(@MappingTarget PersonEntity personEntity, PersonRequestDTO personRequestDTO);
}
