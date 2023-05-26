package com.lsh.guava.concurrency.ratelimiter.token_bucket;

import java.util.stream.IntStream;

/**
 * @Author lishaohui
 * @Date 2023/5/26 16:00
 */
public class TokenBucketClient {
    public static void main(String[] args) {
        final TokenBucket tokenBucket = new TokenBucket();
        IntStream.rangeClosed(1, 200).forEach(i -> {
            new Thread(tokenBucket::purchase).start();
        });
    }

}
