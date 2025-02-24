package com.site.hammerdown.payload;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.site.hammerdown.model.APIResponseStatus;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;


@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@JsonInclude(JsonInclude.Include.NON_NULL)
public class APIResponse {
    public Object data;
    private String errorMessage ;
    private int statusCode;
    private APIResponseStatus status;

    public APIResponse(String message) {
        this.data = message;
    }

    public ResponseEntity<APIResponse> sendResponse(){
        return new ResponseEntity<>(this, HttpStatus.valueOf(this.statusCode));
    }

    public static APIResponse success(Object data) {
        return APIResponse.builder()
                .status(APIResponseStatus.SUCCESS)
                .statusCode(HttpStatus.OK.value())
                .data(data)
                .build();
    }
}
