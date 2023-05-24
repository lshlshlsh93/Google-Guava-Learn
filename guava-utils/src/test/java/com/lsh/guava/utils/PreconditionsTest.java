package com.lsh.guava.utils;

import com.google.common.base.Preconditions;
import com.google.common.collect.ImmutableList;

import org.junit.Test;

import java.util.List;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.Is.is;
import static org.hamcrest.core.Is.isA;
import static org.hamcrest.core.IsEqual.equalTo;

/**
 * @Author lishaohui
 * @Date 2023/5/24 16:18
 */
public class PreconditionsTest {

    private void checkNotNull(final List<String> stringList) {
        Preconditions.checkNotNull(stringList);
    }

    private void checkNotNullWithMessage(final List<String> stringList, final String message) {
        Preconditions.checkNotNull(stringList, message);
    }

    private void checkNotNullWithFormatMessage(final List<String> stringList, final String message) {
        Preconditions.checkNotNull(stringList, message, 2);
    }


    @Test(expected = NullPointerException.class)
    public void testCheckNotNull() {
        checkNotNull(null);
    }

    @Test
    public void testCheckNotNullWithFormatMessage() {
        try {
            checkNotNullWithFormatMessage(null, "the list should not be null and the size must be %s");
        } catch (Exception e) {
            assertThat(e.getMessage(), equalTo("the list should not be null and the size must be 2"));
        }
    }

    @Test
    public void testCheckNotNullWithMessage() {
        try {
            checkNotNullWithMessage(null, "the list should not be null");
        } catch (Exception e) {
            assertThat(e.getMessage(), equalTo("the list should not be null"));
        }
    }

    @Test(expected = IllegalArgumentException.class) // 非法参数异常
    public void testCheckArguments() {
        final String type = "A";
        Preconditions.checkArgument(type.equals("B"));
    }

    @Test(expected = IllegalStateException.class) // 非法状态异常
    public void testCheckState() {
        final String state = "STOP";
        Preconditions.checkState(state.equals("RUNNING"), "The current [%s] state is illegal", state);
    }

    @Test(expected = IndexOutOfBoundsException.class) // 数组下标越界异常
    public void testCheckIndex() {
        List<String> stringList = ImmutableList.of();
        Preconditions.checkElementIndex(10, stringList.size());
    }

}
