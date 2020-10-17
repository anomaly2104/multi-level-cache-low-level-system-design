package com.uditagarwal.service;

import com.uditagarwal.model.LevelCacheData;
import com.uditagarwal.model.ReadResponse;
import com.uditagarwal.model.WriteResponse;
import com.uditagarwal.policy.LRUEvictionPolicy;
import com.uditagarwal.provider.CacheProvider;
import com.uditagarwal.provider.DefaultLevelCache;
import com.uditagarwal.provider.NullEffectLevelCache;
import com.uditagarwal.storage.InMemoryStorage;
import org.junit.Test;
import org.junit.jupiter.api.Assertions;

public class CacheServiceTest {

    @Test
    public void testLevelCache() {
        CacheProvider<String, String> c1 = createCache(10);
        CacheProvider<String, String> c2 = createCache(20);

        LevelCacheData cl1 = new LevelCacheData(1, 3);
        LevelCacheData cl2 = new LevelCacheData(2, 4);

        DefaultLevelCache<String, String> l2Cache = new DefaultLevelCache<>(cl2, c2, new NullEffectLevelCache<>());
        DefaultLevelCache<String, String> l1Cache = new DefaultLevelCache<>(cl1, c1, l2Cache);

        CacheService<String, String> cacheService = new CacheService<>(l1Cache, 5);

        WriteResponse w1 = cacheService.set("k1", "v1");
        WriteResponse w2 = cacheService.set("k2", "v2");

        Assertions.assertEquals(10, w1.getTimeTaken());
        Assertions.assertEquals(10, w2.getTimeTaken());

        ReadResponse<String> r1 = cacheService.get("k1");
        Assertions.assertEquals("v1", r1.getValue());
        Assertions.assertEquals(1, r1.getTotalTime());

        ReadResponse<String> r2 = cacheService.get("k2");
        Assertions.assertEquals("v2", r2.getValue());
        Assertions.assertEquals(1, r2.getTotalTime());

        // Explicitly remove key from l1 for testing.
        c1.set("k1", null);

        ReadResponse<String> r1AfterRemovalFromL1 = cacheService.get("k1");
        Assertions.assertEquals("v1", r1AfterRemovalFromL1.getValue());
        Assertions.assertEquals(6, r1AfterRemovalFromL1.getTotalTime());

        ReadResponse<String> reRead = cacheService.get("k1");
        Assertions.assertEquals("v1", reRead.getValue());
        Assertions.assertEquals(1, reRead.getTotalTime());

        WriteResponse reWritingValue = cacheService.set("k1", "v1");
        Assertions.assertEquals(3, reWritingValue.getTimeTaken());
    }

    private CacheProvider<String, String> createCache(int capacity) {
        return new CacheProvider<>(
                new LRUEvictionPolicy<>(),
                new InMemoryStorage<>(capacity));
    }
}