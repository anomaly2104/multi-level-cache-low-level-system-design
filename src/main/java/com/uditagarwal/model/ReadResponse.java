package com.uditagarwal.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.ToString;

@ToString
@AllArgsConstructor
@Getter
public class ReadResponse<Value> {
    Value value;
    Double totalTime;
}
