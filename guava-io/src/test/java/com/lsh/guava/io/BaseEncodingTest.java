package com.lsh.guava.io;

import com.google.common.io.BaseEncoding;

import com.lsh.guava.io.base64.Base64;

import org.junit.Test;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.IsEqual.equalTo;

/**
 * @Author lishaohui
 * @Date 2023/5/27 18:28
 */
public class BaseEncodingTest {


    /*

       char 'a'--1byte--8bit-- 01100001
                                  |
                              011000 010000
                                  | 24,16
                              查base64字符表得
                              24->Y ; 16->Q
                                  |
        res:                    YQ==
     */


    @Test
    public void testBase64Encoding() {
        BaseEncoding base64 = BaseEncoding.base64();
        System.out.println(base64.encode("666".getBytes()));
        System.out.println(base64.encode("a".getBytes()));
    }


    @Test
    public void testSelfBase64Encoding(){
        String res = Base64.encode("jjjjjjjjjjjjjjjj");
        System.out.println(res);
        assertThat(res,equalTo(BaseEncoding.base64().encode("jjjjjjjjjjjjjjjj".getBytes())));
    }

    @Test
    public void testSelfBase64Decoding(){
        String res = Base64.decode("ampqampqampqampqampqag==");
        System.out.println(res);
    }


    @Test
    public void testBase64Decoding() {
        BaseEncoding base64 = BaseEncoding.base64();
        String result = new String(base64.decode("NjY2"));
        assertThat(result, equalTo("666"));
    }

}
