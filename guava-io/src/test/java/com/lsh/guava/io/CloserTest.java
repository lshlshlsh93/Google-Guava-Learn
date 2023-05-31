package com.lsh.guava.io;

import com.google.common.io.ByteSource;
import com.google.common.io.Closer;
import com.google.common.io.Files;

import org.junit.Test;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;

/**
 * @Author lishaohui
 * @Date 2023/5/27 17:56
 */
public class CloserTest {

    private final String SOURCE_FILE =
            "D:\\Java Module\\Google-Guava-Learn\\guava-io\\src\\main\\resources\\io\\source.txt";

    @Test
    public void testCloser() throws IOException {
        ByteSource byteSource = Files.asByteSource(new File(SOURCE_FILE));
        // create a closer
        Closer closer = Closer.create();
        try {
            InputStream inputStream = closer.register(byteSource.openStream());
        } catch (IOException e) {
            e.printStackTrace();
            throw closer.rethrow(e);
        } finally {
            closer.close();
        }
    }

    @Test(expected = RuntimeException.class)
    public void testTryCatchFinally() {
        // 抛出异常之前finally语句块先执行一次
        try {
            System.out.println("work area.");
            throw new IllegalArgumentException("illegal arguments error.");
        } catch (Exception e) {
            System.out.println("exception area.");
            throw new RuntimeException("Occur error.");
        } finally {
            System.out.println("finally area.");
        }
    }

    @Test(expected = RuntimeException.class)
    public void testTryCatch() {
        try {
            System.out.println("work area.");
            throw new IllegalArgumentException("illegal arguments error.");
        } catch (Exception e) {
            System.out.println("exception area.");
            throw new RuntimeException("Occur error.");
        }
    }


}
