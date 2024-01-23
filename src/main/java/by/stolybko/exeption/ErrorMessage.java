package by.stolybko.exeption;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class ErrorMessage {

    private String errorMessage;
    private String errorCode;

}
