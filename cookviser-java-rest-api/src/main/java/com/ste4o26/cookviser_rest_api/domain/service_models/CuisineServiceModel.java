package com.ste4o26.cookviser_rest_api.domain.service_models;

import lombok.*;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class CuisineServiceModel extends BaseServiceModel {
    private String name;
    private String imageThumbnailUrl;
}
