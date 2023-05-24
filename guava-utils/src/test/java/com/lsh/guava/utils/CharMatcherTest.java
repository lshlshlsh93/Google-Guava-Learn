package com.lsh.guava.utils;

import com.google.common.base.CharMatcher;

import org.junit.Test;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.IsEqual.equalTo;

/**
 * @Author lishaohui
 * @Date 2023/5/24 18:14
 */
public class CharMatcherTest {

    @Test
    public void testCharMatcher() {
        assertThat(CharMatcher.is('L').countIn("ALl sLlllll LLl l"), equalTo(4));
        assertThat(CharMatcher
                .breakingWhitespace()
                .collapseFrom("      Guava learn      ", '*'), equalTo("*Guava*learn*"));
    }

}
