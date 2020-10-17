package com.uditagarwal.policy;

public interface EvictionPolicy<Key> {
    void keyAccessed(Key key);
    Key evictKey();
}
