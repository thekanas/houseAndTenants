package by.stolybko.validation;

import by.stolybko.database.dto.PersonRequestDTO;
import by.stolybko.exeption.ValidationException;
import lombok.RequiredArgsConstructor;
import org.apache.commons.collections4.CollectionUtils;
import org.springframework.stereotype.Service;
import javax.validation.ConstraintViolation;

import javax.validation.Validator;
import java.util.Set;

@Service
@RequiredArgsConstructor
public class PersonValidator {


    private final Validator validator;

    public void validate(PersonRequestDTO personRequest) {
        Set<ConstraintViolation<PersonRequestDTO>> violations = validator.validate(personRequest);
        if (!violations.isEmpty()) {
            for (ConstraintViolation<PersonRequestDTO> violation : violations) {
                throw  ValidationException.of(PersonRequestDTO.class, violation.getMessage());
            }
        }

    }

}
