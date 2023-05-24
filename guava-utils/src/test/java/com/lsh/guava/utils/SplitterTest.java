package com.lsh.guava.utils;

import com.google.common.base.Splitter;

import org.junit.Test;

import java.util.List;
import java.util.Map;
import java.util.regex.Pattern;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.IsEqual.equalTo;
import static org.hamcrest.core.IsNull.notNullValue;

/**
 * @Author lishaohui
 * @Date 2023/5/24 14:52
 */
public class SplitterTest {


    @Test
    public void testSplitterOnSplit() {
        List<String> resultList = Splitter.on("|").splitToList("learn|guava");
        assertThat(resultList, notNullValue()); // not null
        assertThat(resultList.size(), equalTo(2));
        assertThat(resultList.get(0), equalTo("learn"));
        assertThat(resultList.get(1), equalTo("guava"));
    }


    @Test
    public void testSplitterOnSpilt_OmitEmpty() {
        List<String> resultList = Splitter.on("|").splitToList("learn|guava|||");
        assertThat(resultList, notNullValue());
        assertThat(resultList.size(), equalTo(5));
        // ignore the empty string --> ""
        resultList = Splitter.on("|").omitEmptyStrings().splitToList("learn|guava|||");
        assertThat(resultList, notNullValue());
        assertThat(resultList.size(), equalTo(2));
    }


    @Test
    public void testSplitterOnSplit_OmitEmpty_Trim() {
        List<String> resultList = Splitter.on("|").omitEmptyStrings().splitToList("learn | guava|||");
        assertThat(resultList, notNullValue());
        assertThat(resultList.size(), equalTo(2));
        assertThat(resultList.get(0), equalTo("learn "));
        assertThat(resultList.get(1), equalTo(" guava"));

        // trim the blank
        resultList = Splitter.on("|").omitEmptyStrings().trimResults().splitToList("learn | guava|||");
        assertThat(resultList.get(0), equalTo("learn"));
        assertThat(resultList.get(1), equalTo("guava"));
    }


    @Test
    public void testSplitterOnSplit_FixLength() {
        // fix the length
        List<String> resultList = Splitter.fixedLength(4).splitToList("aaaabbbbccccdddd");
        // aaaa bbbb cccc dddd
        assertThat(resultList, notNullValue());
        assertThat(resultList.size(), equalTo(4));
        assertThat(resultList.get(0), equalTo("aaaa"));
        assertThat(resultList.get(3), equalTo("dddd"));
    }


    @Test
    public void testSplitterOnSplit_Limit() {
        // limit the length to be 3
        List<String> resultList = Splitter.on("*").limit(3).splitToList("this*is*the*guava");
        assertThat(resultList, notNullValue());
        assertThat(resultList.size(), equalTo(3));
        assertThat(resultList.get(0), equalTo("this"));
        assertThat(resultList.get(1), equalTo("is"));
        assertThat(resultList.get(2), equalTo("the*guava"));
    }


    @Test
    public void testSplitterOnSplit_PatternString() {
        List<String> resultList =
                Splitter
                        .onPattern("\\|")
                        .omitEmptyStrings()
                        .trimResults()
                        .splitToList("learn | guava|||");
        assertThat(resultList, notNullValue());
        assertThat(resultList.size(), equalTo(2));
    }

    @Test
    public void testSplitterOnSplit_Pattern() {
        List<String> resultList =
                Splitter
                        .on(Pattern.compile("\\|"))
                        .omitEmptyStrings()
                        .trimResults()
                        .splitToList("learn | guava|||");
        assertThat(resultList, notNullValue());
        assertThat(resultList.size(), equalTo(2));
    }


    @Test
    public void testSplitterOnSplit_ToMap() {
        Map<String, String> resultMap = Splitter
                .on(Pattern.compile("\\|"))
                .omitEmptyStrings()
                .trimResults()
                .withKeyValueSeparator("=")
                .split("username=lucy| gender=male|||");
        System.out.println(resultMap); // {username=lucy, gender=male}
        assertThat(resultMap, notNullValue());
        assertThat(resultMap.size(), equalTo(2));
        assertThat(resultMap.containsKey("username"), equalTo(true));
        assertThat(resultMap.get("gender"), equalTo("male"));
    }


}
