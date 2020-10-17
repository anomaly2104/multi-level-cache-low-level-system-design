package com.uditagarwal.provider;

import com.uditagarwal.model.ReadResponse;
import com.uditagarwal.model.WriteResponse;
import lombok.NonNull;

import java.util.List;

public interface ILevelCache<Key, Value> {

    @NonNull
    WriteResponse set(Key key, Value value);

    @NonNull
    ReadResponse<Value> get(Key key);

    @NonNull
    List<Double> getUsages();
}
