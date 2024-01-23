package by.stolybko.service.mapper;

import by.stolybko.database.dto.PersonRequestDTO;
import by.stolybko.database.dto.PersonResponseDTO;
import by.stolybko.database.entity.PersonEntity;
import org.mapstruct.BeanMapping;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;
import org.mapstruct.NullValueCheckStrategy;
import org.mapstruct.NullValuePropertyMappingStrategy;
import org.mapstruct.ReportingPolicy;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface PersonMapper {

    PersonEntity toPersonEntity(PersonRequestDTO personRequestDTO);

    PersonResponseDTO toPersonResponseDTO(PersonEntity personEntity);
    PersonEntity update(@MappingTarget PersonEntity personEntity, PersonRequestDTO personRequestDTO);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE,
            nullValueCheckStrategy = NullValueCheckStrategy.ALWAYS)
    PersonEntity merge(@MappingTarget PersonEntity personEntity, PersonRequestDTO personRequestDTO);
}
