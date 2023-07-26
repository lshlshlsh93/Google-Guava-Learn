package com.lsh.guava.collections.immutable;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.Lists;

import org.junit.Test;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.IsNull.notNullValue;
import static org.junit.Assert.fail;


/**
 * @author lishaohui
 * @date 2023/7/26 11:11
 */
public class ImmutableCollectionDemoTest {


    @Test(expected = UnsupportedOperationException.class)
    public void testOf() {
        var immutableList = ImmutableList.of(1, 2, 3);
        assertThat(immutableList, notNullValue());
        immutableList.add(4);
        fail();
    }


    @Test
    public void testCopyOf() {
        //   List -- ImmutableList
        var copyOfList = ImmutableList.copyOf(new Integer[]{1, 2, 3, 4, 5});
        System.out.println(copyOfList);
    }


    @Test
    public void testBuilder() {
        var buildImmutableList = ImmutableList.builder()
                .add(1)
                .add(2, 3, 4)
                .addAll(Lists.newArrayList(5, 6))
                .build();
        System.out.println(buildImmutableList);
    }


}