package qna.exception;

import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@Slf4j
@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(value = CustomException.class)
    public ErrorResponse handleCustomException(CustomException e) {
        log.error(e.getMessage());
        return ErrorResponse.of(e.getErrorCode());
    }
}
