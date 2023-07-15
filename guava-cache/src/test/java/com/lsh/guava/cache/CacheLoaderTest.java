package com.lsh.guava.cache;

import com.google.common.base.Optional;
import com.google.common.cache.CacheBuilder;
import com.google.common.cache.CacheBuilderSpec;
import com.google.common.cache.CacheLoader;
import com.google.common.cache.CacheStats;
import com.google.common.cache.LoadingCache;
import com.google.common.cache.RemovalCause;
import com.google.common.cache.RemovalListener;
import com.google.common.cache.Weigher;

import com.lsh.guava.entity.Employee;

import org.junit.Test;

import java.util.Map;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.Is.is;
import static org.hamcrest.core.IsEqual.equalTo;
import static org.hamcrest.core.IsNull.notNullValue;
import static org.hamcrest.core.IsNull.nullValue;
import static org.junit.Assert.fail;

/**
 * @Author lishaohui
 * @Date 2023/6/10 11:22
 */
public class CacheLoaderTest {

    private boolean flag = false;

    @Test
    public void testLoadingCache() throws ExecutionException, InterruptedException {
        LoadingCache<String, Employee> employeeLoadingCache =
                CacheBuilder.newBuilder().maximumSize(10).expireAfterAccess(30, TimeUnit.MILLISECONDS)
                        .build(getEmployeeCacheLoader());
        Employee employee = employeeLoadingCache.get("Lsh");
        assertThat(employee, notNullValue());
        assertLoadFromDBThenReset();

        employee = employeeLoadingCache.get("Lsh");
        assertThat(employee, notNullValue());
        assertLoadFromCacheThenReset();

        TimeUnit.MILLISECONDS.sleep(31);

        employee = employeeLoadingCache.get("Lsh");
        assertThat(employee, notNullValue());
        assertLoadFromDBThenReset();
    }

    private void assertLoadFromDBThenReset() {
        assertThat(true, equalTo(flag));
        this.flag = false;
    }

    private void assertLoadFromCacheThenReset() {
        assertThat(false, equalTo(flag));
    }

    private Employee findEmployeeByName(final String name) {
        flag = true;
        return new Employee(name, name, name);
    }


    @Test
    public void testEvictionByWeight() {
        Weigher<String, Employee> weigher = (key, value) -> value.getName().length() + value.getDept().length() + value.getEmpId().length();
        LoadingCache<String, Employee> cache = CacheBuilder.newBuilder()
                .maximumWeight(45)
                .concurrencyLevel(1)
                .weigher(weigher)
                .build(getEmployeeCacheLoader());
        cache.getUnchecked("Lsh");
        assertLoadFromDBThenReset();
        cache.getUnchecked("Jac");
        assertLoadFromDBThenReset();
        cache.getUnchecked("Lee");
        assertLoadFromDBThenReset();

        assertThat(cache.size(), equalTo(3L));
        assertThat(cache.getIfPresent("Lsh"), notNullValue());

        cache.getUnchecked("HHHH");
        assertThat(cache.getIfPresent("Jac"), notNullValue());
        assertThat(cache.size(), equalTo(4L));
    }

    @Test
    public void testEvictionBySize() {

        // 根据缓存大小执行逐出策略
        LoadingCache<String, Employee> cache = CacheBuilder.newBuilder().maximumSize(3).build(getEmployeeCacheLoader());
        cache.getUnchecked("Lsh");
        assertLoadFromDBThenReset();
        cache.getUnchecked("Jack");
        assertLoadFromDBThenReset();
        cache.getUnchecked("Lee");
        assertLoadFromDBThenReset();

        assertThat(cache.size(), equalTo(3L));
        cache.getUnchecked("HHH");

        assertThat(cache.getIfPresent("Lsh"), nullValue());

        assertThat(cache.getIfPresent("HHH"), notNullValue());

    }

    private CacheLoader<String, Employee> getEmployeeCacheLoader() {
        return new CacheLoader<>() {
            @Override
            public Employee load(String key) throws Exception {
                return findEmployeeByName(key);
            }
        };
    }

    private CacheLoader<String, Employee> createEmployeeCacheLoader() {
        return CacheLoader.from(key -> new Employee(key, key, key));
    }

    @Test
    public void testEvictionByAccessTime() throws InterruptedException {
        LoadingCache<String, Employee> cache = CacheBuilder.newBuilder()
                // access time (read/write/update)
                .expireAfterAccess(2, TimeUnit.SECONDS)
                .build(this.createEmployeeCacheLoader());
        assertThat(cache.getUnchecked("Lsh"), notNullValue());
        assertThat(cache.size(), equalTo(1L));
        TimeUnit.SECONDS.sleep(3);
        assertThat(cache.getIfPresent("Lsh"), nullValue());

        assertThat(cache.getUnchecked("Guava"), notNullValue());

        TimeUnit.SECONDS.sleep(1);
        assertThat(cache.getIfPresent("Guava"), notNullValue());

        TimeUnit.SECONDS.sleep(1);
        assertThat(cache.getIfPresent("Guava"), notNullValue());

        TimeUnit.SECONDS.sleep(1);
        assertThat(cache.getIfPresent("Guava"), notNullValue());
    }

    @Test
    public void testEvictionByWriteTime() throws InterruptedException {
        LoadingCache<String, Employee> cache = CacheBuilder.newBuilder()
                // write time (write/update)
                .expireAfterWrite(2, TimeUnit.SECONDS)
                .build(this.getEmployeeCacheLoader());
        assertThat(cache.getUnchecked("Lsh"), notNullValue());
        assertThat(cache.size(), equalTo(1L));
        assertThat(cache.getUnchecked("HHH"), notNullValue());
        TimeUnit.SECONDS.sleep(1);
        assertThat(cache.getIfPresent("HHH"), notNullValue());
        TimeUnit.MILLISECONDS.sleep(800);
        assertThat(cache.getIfPresent("HHH"), notNullValue());
    }


    @Test
    public void testWeakKey() throws InterruptedException {
        LoadingCache<String, Employee> cache = CacheBuilder.newBuilder()
                .expireAfterWrite(2, TimeUnit.SECONDS)
                .weakKeys()
                .weakValues()
                .build(this.getEmployeeCacheLoader());
        assertThat(cache.getUnchecked("Lsh"), notNullValue());
        assertThat(cache.getUnchecked("HHH"), notNullValue());

        // active method
        // thread active design pattern
        System.gc();
        TimeUnit.MILLISECONDS.sleep(100);

        assertThat(cache.getIfPresent("Lsh"), nullValue());
    }


    @Test
    public void testSoftKey() throws InterruptedException {
        LoadingCache<String, Employee> cache = CacheBuilder.newBuilder()
                .expireAfterWrite(2, TimeUnit.SECONDS)
                .softValues()
                .build(this.getEmployeeCacheLoader());
        int i = 0;
        for (; ; ) {
            cache.put("Lsh" + i, new Employee("Lsh" + 1, "Lsh" + 1, "Lsh" + 1));
            System.out.println("The employee [" + (i++) + "] is store into the cache.");
            TimeUnit.MILLISECONDS.sleep(600);
        }
    }


    @Test
    public void testLoadNullValue() {
        CacheLoader<String, Employee> cacheLoader = CacheLoader
                .from(k -> k.equals("null") ? null : new Employee(k, k, k));
        LoadingCache<String, Employee> loadingCache = CacheBuilder.newBuilder().build(cacheLoader);
        Employee lsh = loadingCache.getUnchecked("Lsh");
        assertThat(lsh, notNullValue());
        assertThat(lsh.getName(), equalTo("Lsh"));
        try {
            assertThat(loadingCache.getUnchecked("null"), nullValue());
            fail("should not be process here.");
        } catch (Exception e) {
            assertThat(e instanceof CacheLoader.InvalidCacheLoadException, equalTo(true));
        }
    }

    @Test
    public void testLoadNullValueUseOptional() {
        CacheLoader<String, Optional<Employee>> loader = new CacheLoader<>() {
            @Override
            public Optional<Employee> load(String key) throws Exception {
                if (key.equals("null"))
                    return Optional.fromNullable(null);
                else
                    return Optional.fromNullable(new Employee(key, key, key));
            }
        };
        LoadingCache<String, Optional<Employee>> loadingCache = CacheBuilder.newBuilder().build(loader);
        assertThat(loadingCache.getUnchecked("Lsh").get(), notNullValue());
        assertThat(loadingCache.getUnchecked("null").orNull(), nullValue());
        Employee def = loadingCache.getUnchecked("null").or(new Employee("default", "default", "default"));
        assertThat(def.getName().length(), equalTo(7));

    }


    @Test
    public void testCacheRefresh() throws InterruptedException {
        AtomicInteger counter = new AtomicInteger(0);
        CacheLoader<Object, Long> cacheLoader = CacheLoader.from(k -> {
            counter.incrementAndGet();
            return System.currentTimeMillis();
        });
        LoadingCache<Object, Long> cache =
                CacheBuilder.newBuilder().refreshAfterWrite(2, TimeUnit.SECONDS).build(cacheLoader);
        Long res1 = cache.getUnchecked("Lsh");
        TimeUnit.SECONDS.sleep(3);
        Long res2 = cache.getUnchecked("Lsh");
        assertThat(counter.get(), equalTo(2));
        assertThat(res1.longValue() != res2.longValue(), equalTo(true));
    }


    @Test
    public void testCachePreLoad() {
        CacheLoader<String, String> loader = CacheLoader.from(String::toUpperCase);
        LoadingCache<String, String> cache = CacheBuilder.newBuilder().build(loader);
        Map<String, String> preData = Map.of("lsh", "LSH", "hello", "hello");
        cache.putAll(preData);
        assertThat(cache.size(), equalTo(2L));
    }

    @Test
    public void testCacheRemoveNotification() {
        CacheLoader<String, String> loader = CacheLoader.from(String::toUpperCase);
        RemovalListener<String, String> listener = notification -> {
            if (notification.wasEvicted()) {
                RemovalCause cause = notification.getCause();
                assertThat(cause, is(RemovalCause.SIZE));
                assertThat(notification.getKey(), equalTo("Lsh"));
            }
        };
        LoadingCache<String, String> cache = CacheBuilder.newBuilder()
                .maximumSize(3)
                .removalListener(listener)
                .build(loader);
        cache.getUnchecked("Lsh");
        cache.getUnchecked("HHH");
        cache.getUnchecked("Jack");
        cache.getUnchecked("Lucy");
    }


    @Test
    public void testCacheStat() {
        CacheLoader<String, String> loader = CacheLoader.from(String::toUpperCase);
        LoadingCache<String, String> cache = CacheBuilder.newBuilder().recordStats().build(loader);
        assertCache(cache);
    }


    @Test
    public void testCacheSpec(){
        String spec = "maximumSize=5,recordStats";
        CacheBuilderSpec build = CacheBuilderSpec.parse(spec);
        CacheLoader<String, String> loader = CacheLoader.from(String::toUpperCase);
        LoadingCache<String, String> loadingCache = CacheBuilder.from(build).build(loader);
        assertCache(loadingCache);
    }

    private void assertCache(LoadingCache<String, String> loadingCache) {
        assertThat(loadingCache.getUnchecked("lsh"), equalTo("LSH"));
        CacheStats stats = loadingCache.stats();
        System.out.println(stats.hashCode());
        assertThat(stats.hitCount(), equalTo(0L));
        assertThat(stats.missCount(), equalTo(1L));

        assertThat(loadingCache.getUnchecked("lsh"), equalTo("LSH"));
        stats = loadingCache.stats();
        System.out.println(loadingCache.hashCode());
        assertThat(stats.hitCount(), equalTo(1L));
        assertThat(stats.missCount(), equalTo(1L));


        System.out.println(stats.missRate());
        System.out.println(stats.hitRate());
    }

}
