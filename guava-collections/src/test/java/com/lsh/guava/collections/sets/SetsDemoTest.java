package com.lsh.guava.collections.sets;

import com.google.common.collect.Lists;
import com.google.common.collect.Sets;

import org.junit.Test;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.IsEqual.equalTo;

/**
 * @author lishaohui
 * @Date 2023/7/24 10:51
 */
public class SetsDemoTest {

    @Test
    public void testNewHashSet() {
        var set = Sets.newHashSet(1, 2, 3);
        assertThat(set.size(), equalTo(3));
        var list = Lists.newArrayList(1, 1, 2, 3);
        assertThat(list.size(), equalTo(4));
        var result = Sets.newHashSet(list);
        assertThat(result.size(), equalTo(3));
    }

    @Test
    public void testCartesianProduct() {
        // 笛卡尔积
        var result = Sets.cartesianProduct(
                Sets.newHashSet(1, 2),
                Sets.newHashSet(3, 4),
                Sets.newHashSet(5, 6)
        );
        System.out.println(result);
    }


    @Test
    public void testCombinations() {
        var set = Sets.newHashSet(1, 2, 3);
        // 子集
        var result = Sets.combinations(set, 2);
        result.forEach(System.out::println);
    }

    @Test
    public void testDifference() {
        // 差集
        var result = Sets.difference(
                Sets.newHashSet(1, 2, 3),
                Sets.newHashSet(1, 5, 7)
        );
        System.out.println(result);

        var result2 = Sets.difference(
                Sets.newHashSet(1, 5, 7),
                Sets.newHashSet(1, 2, 3)
        );
        System.out.println(result2);
    }

    @Test
    public void testIntersection() {
        // 交集
        var result = Sets.intersection(
                Sets.newHashSet(1, 2, 3),
                Sets.newHashSet(1, 5, 7)
        );
        System.out.println(result);
    }

    @Test
    public void testUnion() {
        // 并集
        var result = Sets.union(
                Sets.newHashSet(1, 2, 3),
                Sets.newHashSet(4, 5, 6)
        );
        System.out.println(result);
    }

}