package com.ste4o26.cookviser_rest_api.domain.response_models;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
public abstract class BaseResponseModel {
    private String id;
}
