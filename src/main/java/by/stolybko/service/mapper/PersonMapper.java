package by.stolybko.service.mapper;

import by.stolybko.database.dto.PersonRequestDTO;
import by.stolybko.database.dto.PersonResponseDTO;
import by.stolybko.database.entity.PersonEntity;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface PersonMapper {

    PersonEntity toPersonEntity(PersonRequestDTO personRequestDTO);

    PersonResponseDTO toPersonResponseDTO(PersonEntity personEntity);
}
