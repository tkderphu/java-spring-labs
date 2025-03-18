package viosmash.cache;


import com.google.common.cache.CacheBuilder;
import com.google.common.cache.CacheLoader;
import com.google.common.cache.LoadingCache;
import org.junit.jupiter.api.Test;

import java.time.Duration;
import java.time.temporal.ChronoUnit;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

public class Cache {

    @Test
    public void whenCacheMiss_thenValueIsComputed() throws ExecutionException {
        //this is used to compute the value stored in cache
        CacheLoader<String, String> loader = new CacheLoader<String, String>() {
            @Override
            public String load(String key) {
                return key.toUpperCase();
            }
        };

        LoadingCache<String, String> cache;

        cache = CacheBuilder.newBuilder().build(loader);

        cache.put("HIHI", "kakaka");

//        assertEquals(0, cache.size());
//        assertEquals("HELLO", cache.getUnchecked("hello"));
//        assertEquals(1, cache.size());

        System.out.println("key: " +cache.get("HIHI"));
        System.out.println(cache.asMap());
    }

    @Test
    public void whenCacheReachLimit_thenEviction() {
        CacheLoader<String, String> cacheLoader = new CacheLoader<String, String>() {
            @Override
            public String load(String key) throws Exception {
                return key.toUpperCase();
            }
        };
        LoadingCache<String, String> loadingCache = CacheBuilder.newBuilder()
                .maximumSize(3)
                .build(cacheLoader);

        loadingCache.getUnchecked("first");//put
        loadingCache.getUnchecked("second");//put
        loadingCache.getUnchecked("third");//put
        loadingCache.getUnchecked("four");//put and (key: first will be removed)

        assertEquals(3, loadingCache.size());
        assertNull(loadingCache.getIfPresent("first"));
        assertEquals("FOUR", loadingCache.getIfPresent("four"));

        loadingCache.refresh("second");
        System.out.println(loadingCache.asMap());
    }

    //remove cache after period
    @Test
    void whenEntryIdle_thenEviction() {
        CacheLoader<String, String> cacheLoader = new CacheLoader<String, String>() {
            @Override
            public String load(String key) throws Exception {
                return key.toUpperCase();
            }
        };
        LoadingCache<String, String> loadingCache = CacheBuilder.newBuilder()
                .expireAfterWrite(Duration.of(10, ChronoUnit.SECONDS))
                .build(cacheLoader);

    }



    @Test
    void systemForExchangeRateCurrency() throws InterruptedException {
        class RandomRate {
            static Double randomRate(String currency) {
//                System.out.println("Fetch exchange rate for " + currency);
                return Math.random() * 100_000;
            }
        }
        CacheLoader<String, Double> cacheLoader = new CacheLoader<String, Double>() {
            @Override
            public Double load(String key) throws Exception {
                return RandomRate.randomRate(key);
            }
        };
        LoadingCache<String, Double> loadingCache = CacheBuilder.newBuilder()
                .refreshAfterWrite(2, TimeUnit.SECONDS)
                .build(cacheLoader);

        loadingCache.getUnchecked("USD");
        loadingCache.getUnchecked("VND");

        for(int i = 0; i < 20; i++) {
            System.out.println("Step: " + (i + 1));
            System.out.println("Exchange USD: " + loadingCache.getUnchecked("USD"));
            System.out.println("Exchange VND: " + loadingCache.getUnchecked("VND"));
            Thread.sleep(1000);
        }

    }

}
