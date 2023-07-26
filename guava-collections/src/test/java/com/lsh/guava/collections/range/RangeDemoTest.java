package com.lsh.guava.collections.range;


import com.google.common.collect.BoundType;
import com.google.common.collect.Range;

import org.junit.Test;


import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.Is.is;
import static org.hamcrest.core.IsEqual.equalTo;

/**
 * @author lishaohui
 * @Date 2023/7/26 9:50
 */
public class RangeDemoTest {


    /**
     * 闭区间 { x | a <= x <= b } [a..b]
     */
    @Test
    public void testClosedRange() {
        var closedRange = Range.closed(0, 9);
        System.out.println(closedRange); // [0..9]
        assertThat(closedRange.contains(5), is(true));
        assertThat(closedRange.lowerEndpoint(), equalTo(0));
        assertThat(closedRange.upperEndpoint(), equalTo(9));
    }

    /**
     * 开区间 { x | a < x < b } (a..b)
     */
    @Test
    public void testOpenRange() {
        var openRange = Range.open(0, 9);
        System.out.println(openRange); // (0..9)
        assertThat(openRange.contains(0), is(false));
        assertThat(openRange.contains(5), is(true));
        assertThat(openRange.contains(9), is(false));
        assertThat(openRange.lowerEndpoint(), equalTo(0));
        assertThat(openRange.upperEndpoint(), equalTo(9));
    }

    /**
     * 前开后闭 { x | a < x <= b } (a..b]
     */
    @Test
    public void testOpenClosedRange() {
        var openClosedRange = Range.openClosed(0, 9);
        System.out.println(openClosedRange); // (0..9]
        assertThat(openClosedRange.contains(0), is(false));
        assertThat(openClosedRange.contains(5), is(true));
        assertThat(openClosedRange.contains(9), is(true));
        assertThat(openClosedRange.lowerEndpoint(), equalTo(0));
        assertThat(openClosedRange.upperEndpoint(), equalTo(9));
    }


    /**
     * 前开后闭 { x | a <= x < b } [a..b)
     */
    @Test
    public void testClosedOpenRange() {
        var closedOpenRange = Range.closedOpen(0, 9);
        System.out.println(closedOpenRange); // [0..9)

        assertThat(closedOpenRange.contains(0), is(true));
        assertThat(closedOpenRange.contains(5), is(true));
        assertThat(closedOpenRange.contains(9), is(false));

        assertThat(closedOpenRange.lowerEndpoint(), equalTo(0));
        assertThat(closedOpenRange.upperEndpoint(), equalTo(9));
    }


    /**
     * a到正无穷 { x | a < x < +∞ } 不包含左端点 (a..+∞)
     */
    @Test
    public void testGreaterThan() {
        var greaterThanRange = Range.greaterThan(10);
        System.out.println(greaterThanRange); // (10..+∞)
        assertThat(greaterThanRange.contains(10), equalTo(false));
        assertThat(greaterThanRange.contains(Integer.MAX_VALUE), equalTo(true));
    }

    /**
     * a到正无穷 { x | a <= x < +∞ } 包含左端点 [a..+∞)
     */
    @Test
    public void testAtLastRange() {
        var atLastRange = Range.atLeast(10);
        System.out.println(atLastRange); // [10..+∞)
        assertThat(atLastRange.contains(10), is(true));
        assertThat(atLastRange.contains(Integer.MAX_VALUE), is(true));
    }


    /**
     * 负无穷到a { x | -∞ < x < a } 包含右端点 (-∞..a)
     */
    @Test
    public void testLessThan() {
        var lessThanRange = Range.lessThan(10);
        System.out.println(lessThanRange); // (-∞..10)
        assertThat(lessThanRange.contains(10), equalTo(false));
        assertThat(lessThanRange.contains(Integer.MIN_VALUE), equalTo(true));
    }

    /**
     * 负无穷到a { x | -∞ < x <= a } 包含右端点 (-∞..a]
     */
    @Test
    public void testAtMostRange() {
        var atMostRange = Range.atMost(10);
        System.out.println(atMostRange); // (-∞..10]
        assertThat(atMostRange.contains(10), is(true));
        assertThat(atMostRange.contains(Integer.MIN_VALUE), is(true));
    }

    /**
     * 负无穷到a { x | -∞ < x < a } 包含右端点 (-∞..a)
     */
    @Test
    public void testUpToRangeOpen() {
        var upToOpenRange = Range.upTo(1, BoundType.OPEN);
        System.out.println(upToOpenRange); // (-∞..1)
    }

    /**
     * 负无穷到a { x | -∞ < x <= a } 包含右端点 (-∞..a]
     */
    @Test
    public void testUpToRangeClosed() {
        var upToClosedRange = Range.upTo(1, BoundType.CLOSED);
        System.out.println(upToClosedRange); // (-∞..1]
    }

    /**
     * 负无穷到正无穷 { x | -∞ < x < +∞ } (-∞..+∞)
     */
    @Test
    public void testAllRange() {
        var allRange = Range.all();
        System.out.println(allRange); // (-∞..+∞)
        assertThat(allRange.contains(10), equalTo(true));
        assertThat(allRange.contains(Integer.MIN_VALUE), equalTo(true));
        assertThat(allRange.contains(Integer.MAX_VALUE), equalTo(true));
    }


    /**
     * { x | a <= x <= a } [a..a]
     */
    @Test
    public void testSingletonRange() {
        var singletonRange = Range.singleton(10);
        System.out.println(singletonRange); // [10..10]
    }

    /**
     * 求两个区间的交集 如果两个区间没有交集，会抛出 {@link IllegalArgumentException}
     */
    @Test
    public void testIntersectionRange() {
        var closedOpenRange = Range.range(1, BoundType.CLOSED, 5, BoundType.OPEN);
        var openClosedRange = Range.range(3, BoundType.OPEN, 6, BoundType.CLOSED);
        System.out.println(closedOpenRange); // [1..5)
        System.out.println(openClosedRange); // (3..6]
        // [1..5) intersection (3..6] ---> (3..5)
        var intersectionRange = closedOpenRange.intersection(openClosedRange);
        System.out.println(intersectionRange); // (3..5)
    }

    /**
     * 返回”同时包括两个区间的最小区间”，如果两个区间相连，那就是它们的并集
     */
    @Test
    public void testSpanRange() {
        var closedOpenRange = Range.range(1, BoundType.CLOSED, 5, BoundType.OPEN);
        var openClosedRange = Range.range(3, BoundType.OPEN, 6, BoundType.CLOSED);
        System.out.println(closedOpenRange); // [1..5)
        System.out.println(openClosedRange); // (3..6]
        var spanRange = closedOpenRange.span(openClosedRange);
        System.out.println(spanRange); // [1..6]
    }

    /**
     * 返回原区间范围与目标区间之间的最大范围。这个是有条件的：原区间一定要在目标区间的右边，并且不能有交集。
     */
    @Test
    public void testGapRange() {
        var closedOpenRange = Range.range(1, BoundType.CLOSED, 3, BoundType.OPEN);
        var openClosedRange = Range.range(3, BoundType.CLOSED, 5, BoundType.CLOSED);
        System.out.println(closedOpenRange); // [1..3)
        System.out.println(openClosedRange); // [3..5]
        var gapRange = closedOpenRange.gap(openClosedRange);
        System.out.println(gapRange); // [3..3)
    }





}