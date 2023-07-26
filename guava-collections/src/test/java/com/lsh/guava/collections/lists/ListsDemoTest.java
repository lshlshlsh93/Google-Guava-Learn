package com.lsh.guava.collections.lists;


import com.google.common.base.Joiner;
import com.google.common.collect.Lists;

import org.junit.Test;

import java.util.Collections;
import java.util.Locale;
import java.util.concurrent.CopyOnWriteArrayList;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.IsEqual.equalTo;

/**
 * @author lishaohui
 * @Date 2023/7/24 10:22
 */
public class ListsDemoTest {

    @Test
    public void testCartesianProduct() {
        // 生成笛卡尔积
        var result = Lists.cartesianProduct(
                Lists.newArrayList("1", "2"),
                Lists.newArrayList("A", "B")
        );
        System.out.println(result);
    }

    @Test
    public void testTransform() {
        var source = Lists.newArrayList("aa", "vv");
        Lists.transform(source, e -> e.toUpperCase()).forEach(System.out::println);
    }

    @Test
    public void testNewArrayListWithCapacity() {
        var source = Lists.newArrayListWithCapacity(10);
        source.add("x");
        source.add("y");
        source.add("z");
        System.out.println(source);
    }

    @Test
    public void testNewArrayListWithExpectedSize() {
        var source = Lists.newArrayListWithExpectedSize(10);
        source.add("x");
        source.add("y");
        source.add("z");
        System.out.println(source);
    }

    @Test
    public void testReverse() {
        var list = Lists.newArrayList("1", "2", "3");
        assertThat(Joiner.on(",").join(list),equalTo("1,2,3"));
        var result  = Lists.reverse(list);
        assertThat(Joiner.on(",").join(result),equalTo("3,2,1"));
    }

    @Test
    public void testNewCopyOnWriteArrayList() {
        // CopyOnWriteArrayList 多线程下适合读多写少的场景
        var list = Lists.newCopyOnWriteArrayList(Lists.newArrayList("1", "2", "3"));
        assertThat(Joiner.on(",").join(list),equalTo("1,2,3"));
        var result  = Lists.reverse(list);
        assertThat(Joiner.on(",").join(result),equalTo("3,2,1"));
    }


    @Test
    public void testPartition() {
        // 分区
        var list = Lists.partition(Lists.newArrayList("1", "2", "3"),2);
        System.out.println(list);
        System.out.println(list.get(0));
        System.out.println(list.get(1));
    }

}