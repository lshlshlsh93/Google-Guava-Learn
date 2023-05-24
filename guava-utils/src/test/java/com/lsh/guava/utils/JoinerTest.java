package com.lsh.guava.utils;

import com.google.common.base.Joiner;
import com.google.common.io.Files;

import org.junit.Test;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

import static com.google.common.collect.ImmutableMap.of;
import static java.util.stream.Collectors.joining;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.IsEqual.equalTo;
import static org.hamcrest.core.IsSame.sameInstance;
import static org.junit.Assert.fail;

/**
 * @Author lishaohui
 * @Date 2023/5/21 22:50
 */
public class JoinerTest {

    private static final List<String> STRING_LIST = Arrays.asList(
            "Guava", "Scale", "Kafka", "Redis"
    );
    private static final List<String> STRING_LIST_WITH_NULL_VALUE = Arrays.asList(
            "Guava", "Scale", "Kafka", null
    );

    private final String targetFileName = "D:\\Java Module\\Google-Guava-Learn\\guava-utils\\src\\main\\resources\\guava-joiner.txt";

    private final String mapTargetFileName = "D:\\Java Module\\Google-Guava-Learn\\guava-utils\\src\\main\\resources\\guava-joiner-map.txt";

    private final Map<String, String> stringMap = of("Guava", "Scale", "Kafka", "Redis");

    @Test
    public void testJoinerOnJoin() {
        String result = Joiner.on("#").join(STRING_LIST);
        assertThat(result, equalTo("Guava#Scale#Kafka#Redis"));
    }

    @Test(expected = NullPointerException.class)
    public void testJoinerOnJoinWithNull() {
        String result = Joiner.on("#").join(STRING_LIST_WITH_NULL_VALUE);
        assertThat(result, equalTo("Guava#Scale#Kafka#Redis"));
    }

    @Test
    public void testJoinerOnJoin_WithNullValue_But_Skip() {
        String result = Joiner.on("#").skipNulls().join(STRING_LIST_WITH_NULL_VALUE);
        assertThat(result, equalTo("Guava#Scale#Kafka"));
    }


    @Test
    public void testJoinerOnJoin_WithNullValue_But_UseDefaultValue() {
        String result = Joiner.on("#").useForNull("DEFAULT").join(STRING_LIST_WITH_NULL_VALUE);
        assertThat(result, equalTo("Guava#Scale#Kafka#DEFAULT"));
    }

    @Test
    public void testJoiner_On_Append_To_StringBuilder() {
        final StringBuilder stringBuilder = new StringBuilder();
        StringBuilder resultBuilder = Joiner.on("#").useForNull("DEFAULT").appendTo(stringBuilder, STRING_LIST_WITH_NULL_VALUE);
        assertThat(resultBuilder, sameInstance(stringBuilder));
        assertThat(resultBuilder.toString(), equalTo("Guava#Scale#Kafka#DEFAULT"));
    }

    @Test
    public void testJoiner_On_Append_To_Writer() {
        try (FileWriter fileWriter = new FileWriter(new File(targetFileName))) {
            Joiner.on("#").useForNull("DEFAULT").appendTo(fileWriter, STRING_LIST_WITH_NULL_VALUE);
            assertThat(Files.isFile().test(new File(targetFileName)), equalTo(true));
        } catch (IOException e) {
            fail("append to the writer occur fetal error.");
        }
    }

    @Test
    public void testJoiningBySteam() {
        String result = STRING_LIST_WITH_NULL_VALUE.stream().filter(s -> s != null && !s.isEmpty()).collect(joining("#"));
        assertThat(result, equalTo("Guava#Scale#Kafka"));
    }

    @Test
    public void testJoiningBySteamWithDefaultValue() {
        String result = STRING_LIST_WITH_NULL_VALUE.stream().map(this::defaultValue).collect(joining("#"));
        assertThat(result, equalTo("Guava#Scale#Kafka#DEFAULT"));
    }

    private String defaultValue(final String str) {
        return str == null || str.isEmpty() ? "DEFAULT" : str;
    }


    @Test
    public void testJoiner_On_ImmutableMap_of() {
        String result = Joiner.on("#").withKeyValueSeparator("=").join(stringMap);
        assertThat(result,equalTo("Guava=Scale#Kafka=Redis"));
    }

    @Test
    public void testJoiner_On_ImmutableMap_of_Append_To_File() {
        try(FileWriter fileWriter = new FileWriter(new File(mapTargetFileName))){
           Joiner.on("#").withKeyValueSeparator("=").appendTo(fileWriter,stringMap);
            assertThat(Files.isFile().test(new File(mapTargetFileName)),equalTo(true));
        } catch (IOException e) {
            fail("append to the writer occur fetal error.");
        }
    }

}
