package com.lsh.guava.collections.multi_maps;


import com.google.common.collect.LinkedListMultimap;
import com.google.common.collect.Maps;
import com.google.common.collect.Multimaps;

import org.junit.Test;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.IsEqual.equalTo;

/**
 * @author lishaohui
 * @date 2023/7/26 9:15
 */
public class MultiMapsDemoTest {

    @Test
    public void testLinkedListMultimap() {
        var linkedMultiMap = LinkedListMultimap.create();
        var stringMap = Maps.newHashMap();
        stringMap.put("1", "1");
        stringMap.put("1", "2");
        assertThat(stringMap.size(), equalTo(1));

        linkedMultiMap.put("1","1");
        linkedMultiMap.put("1","2");
        assertThat(linkedMultiMap.size(), equalTo(2));
        System.out.println(linkedMultiMap.get("1"));
    }


}