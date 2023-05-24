package com.datahouse.compatibilitypredictorscratch.entity;

import lombok.*;

import java.util.ArrayList;
import java.util.List;

@Data
@NoArgsConstructor
public class Attributes {
    private float communication;
    private float flexibility;
    private float positivity;
    private float openMindedness;

    public float getSum() {
        return communication + flexibility + positivity + openMindedness;
    }

    public List<Float> getAttributeList() {
        List<Float> attList = new ArrayList<>();
        attList.add(communication);
        attList.add(flexibility);
        attList.add(positivity);
        attList.add(openMindedness);
        return attList;
    }

}
