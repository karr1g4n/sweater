package com.example.sweater.exeption;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.NOT_FOUND)
public class PathNotFound extends RuntimeException {
    public PathNotFound() {
    }

}
