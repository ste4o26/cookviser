package com.ste4o26.cookviser_rest_api.domain.entities;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity(name = "steps")
@Table(name = "steps")
@NoArgsConstructor
@AllArgsConstructor
@Data
public class StepEntity extends BaseEntity {
    @Column(name = "number", nullable = false)
    private int number;

    @Column(name = "content", nullable = false, columnDefinition = "TEXT")
    private String content;
}
