package pl.bmarkowski.auth_server.web;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

import java.util.List;
import java.util.stream.Collectors;

@ControllerAdvice
public class ErrorHandler {

    @ResponseBody
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(MethodArgumentNotValidException.class)
    protected List<ValidationError> handleValidation(MethodArgumentNotValidException ex){
        return ex.getBindingResult()
                .getFieldErrors().stream()
                .map(it -> new ValidationError(it.getField(), it.getDefaultMessage()))
                .collect(Collectors.toUnmodifiableList());
    }
}
