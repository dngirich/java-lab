package com.example;

import com.example.service.EntityNotFoundException;
import java.io.IOException;
import javax.servlet.http.HttpServletResponse;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler({EntityNotFoundException.class})
    public void handleException(HttpServletResponse response, Exception e) throws IOException {
        if (e instanceof EntityNotFoundException) {
            response.sendError(HttpStatus.NOT_FOUND.value(), e.getMessage());
        }
    }
}

class ErrorResponse {

    private final String message;

    public ErrorResponse(String message) {
        this.message = message;
    }
}
