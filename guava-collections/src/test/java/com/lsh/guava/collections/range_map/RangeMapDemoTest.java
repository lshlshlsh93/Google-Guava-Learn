package com.lsh.guava.collections.range_map;


import com.google.common.collect.BoundType;
import com.google.common.collect.Range;
import com.google.common.collect.TreeRangeMap;

import org.junit.Test;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.IsEqual.equalTo;

/**
 * @author lishaohui
 * @date 2023/7/26 10:58
 */
public class RangeMapDemoTest {

    @Test
    public void testTreeRangeMap() {
        var grageScale = TreeRangeMap.create();
        grageScale.put(Range.range(0, BoundType.CLOSED, 60, BoundType.OPEN), "E");
        grageScale.put(Range.range(60, BoundType.CLOSED, 70, BoundType.OPEN), "D");
        grageScale.put(Range.range(70, BoundType.CLOSED, 80, BoundType.OPEN), "C");
        grageScale.put(Range.range(80, BoundType.CLOSED, 90, BoundType.OPEN), "B");
        grageScale.put(Range.range(90, BoundType.CLOSED, 100, BoundType.CLOSED), "A");
        assertThat(grageScale.get(77), equalTo("C"));

    }

}