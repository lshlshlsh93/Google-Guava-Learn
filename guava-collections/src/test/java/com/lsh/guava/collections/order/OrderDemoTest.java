package com.lsh.guava.collections.order;

import com.google.common.collect.Ordering;

import org.hamcrest.core.Is;
import org.junit.Test;

import java.util.Arrays;
import java.util.Collections;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.Is.is;


/**
 * @author lishaohui
 * @date 2023/7/26 11:24
 */
public class OrderDemoTest {


    @Test
    public void testJDKOrder() {
        var integerList = Arrays.asList(1, 5, 3, 6, 10);
        System.out.println(integerList);
        Collections.sort(integerList);
        System.out.println(integerList);
    }


    @Test(expected = NullPointerException.class)
    public void testJDKOrderIssue() {
        var integerList = Arrays.asList(1, 8, null, 6, 10);
        System.out.println(integerList);
        Collections.sort(integerList);
        System.out.println(integerList);
    }


    @Test
    public void testOrderingNaturalByNullFirst() {
        var integerList = Arrays.asList(1, 8, null, 6, 10);
        System.out.println(integerList); // [1, 8, null, 6, 10]
        // 将null值放在第一个
        integerList.sort(Ordering.natural().nullsFirst());
        System.out.println(integerList); // [null, 1, 6, 8, 10]
    }

    @Test
    public void testOrderingNaturalByNullLast() {
        var integerList = Arrays.asList(1, 8, null, 6, 10);
        System.out.println(integerList); // [1, 8, null, 6, 10]
        // 将null值放在最后一个
        integerList.sort(Ordering.natural().nullsLast());
        System.out.println(integerList); // [1, 6, 8, 10, null]
    }

    @Test
    public void testOrderingNatural() {
        var integerList = Arrays.asList(1, 8, 3, 6, 10);
        System.out.println(integerList); // [1, 8, 3, 6, 10]
        // 判断是否是以自然顺序排序 从小到大
        Collections.sort(integerList);
        assertThat(Ordering.natural().isOrdered(integerList), is(true));
        System.out.println(integerList); // [1, 3, 6, 8, 10]
    }

    @Test
    public void testOrderingNaturalReverse() {
        var integerList = Arrays.asList(1, 8, 3, 6, 10);
        System.out.println(integerList); // [1, 8, 3, 6, 10]
        // 判断是否是以自然顺序排序
        integerList.sort(Ordering.natural().reverse());
        assertThat(Ordering.natural().isOrdered(integerList), is(false));
        System.out.println(integerList); // [10, 8, 6, 3, 1]
    }

}