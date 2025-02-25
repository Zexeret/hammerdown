package com.site.hammerdown.common.exceptions;

import lombok.Getter;
import lombok.NoArgsConstructor;

import java.io.Serial;

@Getter
@NoArgsConstructor
public class APIException extends RuntimeException {
    @Serial
    private static final long serialVersionUID = 1L;

    private Integer statusCode;

    public APIException(String message) {
        super(message);
    }
    public APIException( String message, Integer statusCode) {
        super(message);
        this.statusCode = statusCode;
    }

}
