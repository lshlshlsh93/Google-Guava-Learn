package com.lsh.guava.io;

import com.google.common.io.ByteSource;
import com.google.common.io.Files;

import org.hamcrest.core.Is;
import org.junit.Test;

import java.io.File;
import java.io.IOException;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.Is.is;

/**
 * @Author lishaohui
 * @Date 2023/5/27 17:39
 */
public class ByteSourceTest {

    private final String SOURCE_FILE = "D:\\Java Module\\Google-Guava-Learn\\guava-io\\src\\main\\resources\\io\\source.txt";

    @Test
    public void testAsByteSource() throws IOException {
        File file = new File(SOURCE_FILE);
        ByteSource byteSource = Files.asByteSource(file);
        byte[] readBytes = byteSource.read();
        assertThat(readBytes, is(Files.toByteArray(file)));
    }

}
