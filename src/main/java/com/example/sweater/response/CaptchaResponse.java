package com.example.sweater.response;

import com.fasterxml.jackson.annotation.JsonAlias;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;

import java.util.Set;

@JsonIgnoreProperties(ignoreUnknown = true)
@Data
public class CaptchaResponse {

    private boolean success;

    @JsonAlias("error-codes")
    private Set<String> errorCodes;
}
