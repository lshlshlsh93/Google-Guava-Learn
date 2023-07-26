package com.lsh.guava.collections.fluent_iterable;


import com.google.common.base.Joiner;
import com.google.common.base.Optional;
import com.google.common.collect.FluentIterable;
import com.google.common.collect.Lists;

import org.hamcrest.core.Is;
import org.junit.Test;

import java.util.ArrayList;
import java.util.stream.Stream;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.Is.is;
import static org.hamcrest.core.IsEqual.equalTo;

/**
 * @Author lishaohui
 * @Date 2023/7/15 23:06
 */
public class FluentIterableDemoTest {

    private FluentIterable<String> build() {
        final var strings = Lists.newArrayList("Lsh", "XX", "MickA", "METHa");
        return FluentIterable.from(strings);
    }

    private Stream<String> buildToStream() {
        final var strings = Lists.newArrayList("Lsh", "XX", "MickA", "METHa");
        return strings.stream();
    }

    @Test
    public void testFilter() {
        FluentIterable<String> fit = build();
        assertThat(fit.size(), equalTo(4));
        FluentIterable<String> result = fit.filter(e -> e != null && e.length() > 4);
        assertThat(result.size(), equalTo(2));
    }

    @Test
    public void testAppend() {

        var fit = build();

        var append = Lists.newArrayList("APPEND");
        assertThat(fit.size(), equalTo(4));

        // append
        FluentIterable<String> afit = fit.append(append);
        assertThat(afit.size(), equalTo(5));
        assertThat(afit.contains("APPEND"), is(true));

    }

    @Test
    public void testMatch() {
        var fit = build();
        // allMatch 所有都满足才返回true
        var result = fit.allMatch(e -> e != null && e.length() >= 4);
        assertThat(result, is(false));
        // anyMatch 任意一个满足就返回true
        result = fit.anyMatch(e -> e != null && e.length() == 5);
        assertThat(result, is(true));

        // firstMatch 第一个满足条件的
        var optional = fit.firstMatch(e -> e != null && e.length() == 5);
        // 是存在的
        assertThat(optional.isPresent(), is(true));
        // 取出第一个满足字符串不为空且长度为5的元素
        assertThat(optional.get(), equalTo("MickA"));
    }

    @Test
    public void testJava_Stream_Match() {
        var fit = build();
        // allMatch 所有都满足才返回true
        var result = fit.stream().allMatch(e -> e != null && e.length() >= 4);
        assertThat(result, is(false));
        // anyMatch 任意一个满足就返回true
        result = fit.stream().anyMatch(e -> e != null && e.length() == 5);
        assertThat(result, is(true));

        // noneMatch 都不满足满足条件的返回true
        var res = fit.stream().noneMatch(e -> e != null && e.length() == 1);
        assertThat(res, is(true));
    }


    @Test
    public void testFirst_Last() {
        var fit = build();

        // last 取出第一个元素
        Optional<String> firstElem = fit.first();
        assertThat(firstElem.isPresent(), is(true));
        // 取出第一个满足字符串不为空且长度为5的元素
        assertThat(firstElem.get(), equalTo("Lsh"));

        // last 取出最后一个元素
        Optional<String> lastElem = fit.last();
        assertThat(lastElem.isPresent(), is(true));
        // 取出第一个满足字符串不为空且长度为5的元素
        assertThat(lastElem.get(), equalTo("METHa"));
    }

    @Test
    public void testLimit() {
        var fit = build();

        // limit 限制元素个数
        var limit = fit.limit(3);
        System.out.println(limit);

    }

    @Test
    public void testCopyInto() {
        var fit = build();
        var newList = Lists.newArrayList("java");
        var result = fit.copyInto(newList);

        assertThat(result.size(), equalTo(5));
        assertThat(result.contains("Lsh"), is(true));
    }

    @Test
    public void testCycle() {
        var fit = build();
        var result = fit.cycle().limit(20);
        result.forEach(System.out::println);
    }


    @Test
    public void testTransform() {
        var fit = build();
        fit.transform(e -> e.length()).forEach(System.out::println);
    }

    @Test
    public void testTransformAndConcat() {
        var fit = build();
        var list = Lists.newArrayList(1);
        var result = fit.transformAndConcat(e -> list);
        result.forEach(System.out::println);
    }

    @Test
    public void testJoin() {
        var fit = build();
        var result = fit.join(Joiner.on(","));
        assertThat(result,equalTo("Lsh,XX,MickA,METHa"));
    }

}