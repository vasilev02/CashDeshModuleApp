package com.example.demo.exception;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Getter;
import org.springframework.http.HttpStatus;

import java.time.LocalDateTime;

/**
 * ApiException class is used to create Exception with date, status and message to display in our API when something do not
 * fulfill our requirements.
 */
@Getter
public class ApiException {

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd-MM-yyyy hh:mm:ss")
    private LocalDateTime timestamp;
    private HttpStatus status;
    private String message;

    public ApiException(HttpStatus status, String message) {
        timestamp = LocalDateTime.now();
        this.status = status;
        this.message = message;
    }
}