package com.ste4o26.cookviser_rest_api.domain.response_models;

import lombok.*;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class CuisineResponseModel extends BaseResponseModel{
    private String name;
    private String imageThumbnailUrl;
}