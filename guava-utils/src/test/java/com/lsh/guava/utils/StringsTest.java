package com.lsh.guava.utils;

import com.google.common.base.Strings;

import org.junit.Test;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.IsEqual.equalTo;
import static org.hamcrest.core.IsNull.nullValue;

/**
 * @Author lishaohui
 * @Date 2023/5/24 17:33
 */
public class StringsTest {

    @Test
    public void testStrings_EmptyToNull() {
        assertThat(Strings.emptyToNull(""), nullValue());
    }

    @Test
    public void testStrings_NullToEmpty() {
        assertThat(Strings.nullToEmpty(null), equalTo(""));
        assertThat(Strings.nullToEmpty("guava"), equalTo("guava"));
    }

    @Test
    public void testStrings_IsNullOrEmpty() {
        assertThat(Strings.isNullOrEmpty(null), equalTo(true));
        assertThat(Strings.isNullOrEmpty("guava"), equalTo(false));
    }

    @Test
    public void testStrings_CommonPrefix() {
        assertThat(Strings.commonPrefix("Guava","Google"), equalTo("G"));
        assertThat(Strings.commonPrefix("Java","Script"), equalTo(""));
    }

    @Test
    public void testStrings_CommonSuffix() {
        assertThat(Strings.commonSuffix("Guava","Java"), equalTo("ava"));
        assertThat(Strings.commonSuffix("Java","Script"), equalTo(""));
    }

    @Test
    public void testStrings_Repeat() {
        assertThat(Strings.repeat("Guava",3), equalTo("GuavaGuavaGuava"));
    }

    @Test
    public void testStrings_PadStart() {
        assertThat(Strings.padStart("Guava",3,'T'), equalTo("Guava"));
        assertThat(Strings.padStart("Ho",3,'T'), equalTo("THo"));
        assertThat(Strings.padStart("aa",5,'T'), equalTo("TTTaa"));
    }

    @Test
    public void testStrings_PadEnd() {
        assertThat(Strings.padEnd("Guava",3,'T'), equalTo("Guava"));
        assertThat(Strings.padEnd("Ho",3,'T'), equalTo("HoT"));
        assertThat(Strings.padEnd("aa",5,'T'), equalTo("aaTTT"));
    }

}
