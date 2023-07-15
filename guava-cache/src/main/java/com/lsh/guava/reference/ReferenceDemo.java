package com.lsh.guava.reference;

import com.google.common.cache.Cache;

import java.lang.ref.PhantomReference;
import java.lang.ref.ReferenceQueue;
import java.lang.ref.SoftReference;
import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

/**
 * @Author lishaohui
 * @Date 2023/6/9 23:59
 */
public class ReferenceDemo {

    /*
        new class belong to strong reference.


     */


    public static void main(String[] args) throws InterruptedException {

        // strong reference
//        int counter = 1;
//        List<Ref> container = new ArrayList<>();
//        for (; ; ) {
//            int current = counter++;
//            container.add(new Ref(current));
//            System.out.println("the " + current + "Ref will be insert into container.");
//            TimeUnit.MILLISECONDS.sleep(500L);
//        }


        // soft reference
//        int counter = 1;
//        List<SoftReference<Ref>> container = new ArrayList<>();

//        //  SoftReference detected the JVM process memory have little space try to GC soft reference.
//        //  always use SoftReference for Cache
//        SoftReference<Ref> softReference = new SoftReference<>(new Ref(0));
//        for (; ; ) {
//            int current = counter++;
//            container.add(new SoftReference<>(new Ref(current)));
//            System.out.println("the " + current + "Ref will be insert into container.");
//            TimeUnit.MILLISECONDS.sleep(500L);
//        }

        // Weak Reference
//        int counter = 1;
//        List<WeakReference<Ref>> container = new ArrayList<>();
//        // the reference will be collected when GC.
//        for (; ; ) {
//            int current = counter++;
//            container.add(new WeakReference<>(new Ref(current)));
//            System.out.println("the " + current + "Ref will be insert into container.");
//            TimeUnit.MILLISECONDS.sleep(200L);
//        }


        Ref ref = new Ref(10);
        ReferenceQueue<Ref> referenceQueue = new ReferenceQueue<>();
        PhantomReference<Ref> reference = new PhantomReference<>(ref, referenceQueue);
        ref = null;
        System.out.println(reference.get());
//        System.gc();
//        TimeUnit.SECONDS.sleep(1);
//        System.out.println(ref);
//        System.out.println(reference.get().index);

    }

    private static class Ref {
        private byte[] data = new byte[1024 * 1024];

        private final int index;

        private Ref(int index) {
            this.index = index;
        }

        @Override
        protected void finalize() throws Throwable {
            System.out.println("The index [" + index + "] will be GC.");
        }
    }

}
