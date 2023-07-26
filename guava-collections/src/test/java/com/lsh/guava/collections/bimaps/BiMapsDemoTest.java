package com.lsh.guava.collections.bimaps;


import com.google.common.collect.HashBiMap;

import org.junit.Test;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.Is.is;
import static org.junit.Assert.fail;

/**
 * @author lishaohui
 * @date 2023/7/26 9:23
 */
public class BiMapsDemoTest {


    @Test
    public void testHashBiMapCreate(){
       var biMap =  HashBiMap.create();
       biMap.put("1","2");
       biMap.put("1","3");
       assertThat(biMap.containsKey("1"), is(true));
       assertThat(biMap.size(), is(1));
       try {
           biMap.put("2","3");
           fail();
       }catch (Exception e){
        e.printStackTrace(); // value already present: 2
       }
    }

    @Test
    public void testHashBiMapCreateFocusPut(){
        var biMap =  HashBiMap.create();
        biMap.put("1","2");
        assertThat(biMap.containsKey("1"), is(true));
        biMap.forcePut("2","2");
        assertThat(biMap.containsKey("1"), is(false));
        assertThat(biMap.containsKey("2"), is(true));
    }

    @Test
    public void testBiMapIeverse(){
        var biMap =  HashBiMap.create();
        biMap.put("1","2");
        biMap.put("2","3");
        biMap.put("3","4");
        assertThat(biMap.containsKey("1"), is(true));
        assertThat(biMap.containsKey("2"), is(true));
        assertThat(biMap.containsKey("3"), is(true));
        assertThat(biMap.size(), is(3));

        // key -> value
        // value -> key
        var inverseMap =  biMap.inverse();
        assertThat(inverseMap.containsKey("2"), is(true));
        assertThat(inverseMap.containsKey("3"), is(true));
        assertThat(inverseMap.containsKey("4"), is(true));
        assertThat(inverseMap.size(), is(3));
    }


}