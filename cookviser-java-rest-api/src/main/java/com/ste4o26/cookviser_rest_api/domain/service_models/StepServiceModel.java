package com.ste4o26.cookviser_rest_api.domain.service_models;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class StepServiceModel extends BaseServiceModel {
    private int number;
    private String content;
}
