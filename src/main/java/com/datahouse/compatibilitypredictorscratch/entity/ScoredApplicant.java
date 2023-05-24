package com.datahouse.compatibilitypredictorscratch.entity;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class ScoredApplicant {
    private String name;
    private double score;
}
