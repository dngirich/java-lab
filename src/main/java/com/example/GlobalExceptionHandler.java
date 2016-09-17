package com.example;

import com.example.service.EntityNotFoundException;
import javax.servlet.http.HttpServletRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

@ControllerAdvice  
@RestController
public class GlobalExceptionHandler {

  @ResponseBody
  @ExceptionHandler(Throwable.class)
  public ResponseEntity<Object> handleControllerException(
          HttpServletRequest req, Exception exception) {
      
      if (exception instanceof EntityNotFoundException) {
          return new ResponseEntity<>(
                  new ErrorResponse(exception.getMessage()), HttpStatus.NOT_FOUND);
      } else {
          return new ResponseEntity<>(
                  new ErrorResponse("Internal server error."), HttpStatus.INTERNAL_SERVER_ERROR);
      }
  }

    private static class ErrorResponse {
        
        private final String message;

        public ErrorResponse(String message) {
            this.message = message;
        }
    }
}
