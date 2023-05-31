package com.lsh.guava.io;

import com.google.common.io.CharSource;

import org.junit.Test;

import java.io.IOException;
import java.util.List;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.IsEqual.equalTo;

/**
 * @Author lishaohui
 * @Date 2023/5/27 17:08
 */
public class CharSourceTest {

    @Test
    public void testCharSourceWrap() throws IOException {
        String wrapContent = "I am the char source";
        CharSource charSource = CharSource.wrap(wrapContent);
        String content = charSource.read();
        assertThat(content, equalTo(wrapContent));
        List<String> strings = charSource.readLines();
        assertThat(strings.size(), equalTo(1));
        assertThat(charSource.length(), equalTo(20L));
        assertThat(charSource.lengthIfKnown().get(), equalTo(20L));
        assertThat(charSource.isEmpty(), equalTo(false));
    }

    @Test
    public void testSourceConcat() throws IOException {
        CharSource charSource = CharSource.concat(
                CharSource.wrap("I am the char source1\n"),
                CharSource.wrap("I am the char source2")
        );
        System.out.println(charSource.readLines().size());
        charSource.lines().forEach(System.out::println);
    }


}
