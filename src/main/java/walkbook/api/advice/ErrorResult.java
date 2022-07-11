package walkbook.api.advice;

import lombok.Data;

import java.util.Map;

@Data
public class ErrorResult {

    private final int code;
    private final Map<String, String> errors;

}
