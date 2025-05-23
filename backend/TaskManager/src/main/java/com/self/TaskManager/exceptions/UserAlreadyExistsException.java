package com.self.TaskManager.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

// @ResponseStatus helps Spring automatically return the specified HTTP status
// if this exception is not caught and handled by a @ControllerAdvice or in the controller.
// HttpStatus.CONFLICT (409) is often a good choice for "resource already exists".
// HttpStatus.BAD_REQUEST (400) is also acceptable.
@ResponseStatus(HttpStatus.CONFLICT) // Or HttpStatus.BAD_REQUEST
public class UserAlreadyExistsException extends RuntimeException {

    public UserAlreadyExistsException(String message) {
        super(message);
    }

    public UserAlreadyExistsException(String message, Throwable cause) {
        super(message, cause);
    }
}