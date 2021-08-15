package com.ste4o26.cookviser_rest_api.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class HttpResponseModel {
    private int statusCode;
    private String status;
    private String reason;
    private String message;
}
