package com.lsh.guava.concurrency.future;

import com.google.common.util.concurrent.FutureCallback;
import com.google.common.util.concurrent.Futures;
import com.google.common.util.concurrent.ListenableFuture;
import com.google.common.util.concurrent.ListeningExecutorService;
import com.google.common.util.concurrent.MoreExecutors;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

/**
 * @Author lishaohui
 * @Date 2023/5/26 16:20
 */
public class GuavaListenableFutureDemo {

    public static void main(String[] args) {
        ExecutorService executorService = Executors.newFixedThreadPool(2);
        ListeningExecutorService listeningExecutorService = MoreExecutors.listeningDecorator(executorService);
        ListenableFuture<Integer> listenableFuture = listeningExecutorService.submit(
                () -> {
                    try {
                        TimeUnit.SECONDS.sleep(5);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    return 100;
                });
        // 方式1
//        listenableFuture.addListener(() -> System.out.println("Finished task."), executorService);
        System.out.println("====================");
        // 方式2
        Futures.addCallback(listenableFuture, new CallBack(), executorService);
    }

    static class CallBack implements FutureCallback<Integer> {
        @Override
        public void onSuccess(Integer integer) {
            System.out.println("finished. result is : " + integer);
        }

        @Override
        public void onFailure(Throwable throwable) {
            throwable.printStackTrace();
        }
    }

}
