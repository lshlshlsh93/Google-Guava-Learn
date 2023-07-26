package com.lsh.guava.collections.maps;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.google.common.collect.Sets;

import org.junit.Test;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.Is.is;


/**
 * @author lishaohui
 * @Date 2023/7/24 11:35
 */
public class MapsDemoTest {


    @Test
    public void testCreateMap() {
        var result = Maps.uniqueIndex(Lists.newArrayList("1", "2", "3"), k -> "key_" + k);
        System.out.println(result);

        var map = Maps.asMap(Sets.newHashSet("1", "2", "3"), k -> k + "_value");
        System.out.println(map);

    }


    @Test
    public void testTransformValues() {
        var source = Maps.asMap(Sets.newHashSet("1", "2", "3"), v -> v + "_value");
        // transform value
        var target = Maps.transformValues(source, v -> v + "_transform");
        assertThat(target.containsValue("1_value_transform"), is(true));
    }


    @Test
    public void testFilter() {
        var sourceMap = Maps.asMap(Sets.newHashSet("1", "2", "3"), v -> v + "_value");
        System.out.println(sourceMap);
        var filterMap = Maps.filterKeys(sourceMap, k -> Lists.newArrayList("1", "2").contains(k));
        System.out.println(filterMap);
        assertThat(filterMap.containsKey("3"), is(false));
    }

}