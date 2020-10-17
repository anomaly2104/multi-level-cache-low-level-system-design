package com.uditagarwal.model;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.List;

@AllArgsConstructor
@Getter
public class StatsResponse {

    private final Double avgReadTime;
    private final Double avgWriteTime;
    private final List<Double> usages;
}
