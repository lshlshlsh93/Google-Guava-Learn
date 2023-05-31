# Guava

👾 [doc](https://guava.dev/)

## utils

### Joiner

👾 [doc](https://guava.dev/releases/snapshot-jre/api/docs/com/google/common/base/Joiner.html)

#### 1. `join` 拼接集合中的元素

#### 2. `skipNulls` 跳过null元素

#### 3. `useForNull` 替换null元素为给定的值

#### 4. `withKeyValueSeparator` 以给定的分隔符拼接map中的元素

#### 5. `appendTo` 追加内容到StringBuilder、File等

#### 6. `on` 以何种形式拼接

--- 

### Splitter

👾 [doc](https://guava.dev/releases/snapshot-jre/api/docs/com/google/common/base/Splitter.html)

#### 1. `split` 分割字符串，转换为集合

#### 2. `splitToList` 分割字符串转换为list

#### 3. `splitToStream` 分割字符串转换为流

#### 4. `omitEmptyStrings` 过滤空串

#### 5. `trimResults` 将指定CharMatcher转换为空串

#### 6. `limit` 将字符串分割为指定个数

#### 7. `withKeyValueSeparator` 转换为map 该方法标记为@beta不建议使用

#### 8. `fixedLength` 限定长度

#### 9. `on` 以何种形式分割: Pattern、Char、String

---

### Preconditions

👾 [doc](https://guava.dev/releases/snapshot-jre/api/docs/com/google/common/base/Preconditions.html)

#### 1. `checkArgument`  校验参数，参数不符合预期抛出--`IllegalArgumentException`非法参数异常

#### 2. `checkState` 校验状态，状态不符合预期抛出--`IllegalStateException`非法状态异常

#### 3. `checkElementIndex` 校验元素索引,失败时抛出--`IndexOutOfBoundsException`数组下标越界异常

#### 4. `checkNotNull` 校验不为空，否则抛出--`NullPointerException`空指针异常

--- 

### Objects

👾 [doc](https://guava.dev/releases/snapshot-jre/api/docs/com/google/common/base/Objects.html)

#### 1. `MoreObjects.toStringHelper` 帮助我们编写重写的toString方法

#### 2. `equal` 帮助我们编写重写的equals方法

#### 3. `hashCode` 帮助我们编写重写的hashcode方法

#### 4. `ComparisonChain.start() .compare().result()` 帮助我们编写比较大小时的compareTo方法

---

### Strings

👾 [doc](https://guava.dev/releases/snapshot-jre/api/docs/com/google/common/base/Strings.html)

#### 1. `emptyToNull` 将空字符串转换为null

#### 2. `nullToEmpty` 将null转换为空字符串,如果不为空返回原来的值

#### 3. `isNullOrEmpty` 判断字符串是否为空串或者null。为空或null返回true，否则返回false

#### 4. `commonPrefix` 判断两个字符是否有公共前缀。有则返回其最长公共前缀，否则返回空串

#### 5. `commonsuffix` 判断两个字符串是否有公共后缀。有则返回其最长公共后缀，否则返回空串

#### 6. `repeat` 将字符串复制给定的次数 -- **Java 11+ API only**

#### 7. `padStart` 判断字符串长度是否符合给定的最小长度，否则用给定的字符填充到最前面

#### 7. `padEnd` 判断字符串长度是否符合给定的最小长度，否则用给定的字符填充到最后面

#### 8. `String lenientFormat(String template,Object... args)` 利用args中的参数代替template中的`%s`

---

### CharMatcher

[doc](https://guava.dev/releases/snapshot-jre/api/docs/com/google/common/base/CharMatcher.html)


--- 

### Charsets

👾 [doc](https://guava.dev/releases/snapshot-jre/api/docs/com/google/common/base/Charsets.html)

- UTF_8

- ISO_8859_1

- UTF_16

- UTF_16BE

- UTF_16LE

- US_ASCII

--- 

### Monitor

👾 [doc](https://guava.dev/releases/snapshot-jre/api/docs/com/google/common/util/concurrent/Monitor.html)

- guava monitor guard

```java

public class GuavaMonitorDemo<V> {

    private final LinkedList<V> queue;

    private final int max;

    private final Monitor monitor = new Monitor();
    ;

    private final Monitor.Guard CAN_OFFER;

    private final Monitor.Guard CAN_TAKE;

    public GuavaMonitorDemo(int max) {
        this.queue = new LinkedList<>();
        this.max = max;
        CAN_OFFER = monitor.newGuard(() -> queue.size() < this.max);
        CAN_TAKE = monitor.newGuard(() -> !queue.isEmpty());
    }

    public void offer(V v) {
        try {
            // when can offer then enter
            monitor.enterWhen(CAN_OFFER);
            // add value
            queue.addLast(v);
        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {
            // leave
            monitor.leave();
        }
    }

    public V take() {
        V v;
        try {
            // when can take then enter
            monitor.enterWhen(CAN_TAKE);
            v = queue.removeFirst();
            return v;
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        } finally {
            // leave when do thing over
            monitor.leave();
        }
    }
}

```

- reentrantLock

```java

public class ReentrantLockDemo<V> {

    private final ReentrantLock LOCK = new ReentrantLock();

    // full condition
    private final Condition FULL_CONDITION = LOCK.newCondition();
    // empty condition
    private final Condition EMPTY_CONDITION = LOCK.newCondition();
    // queue
    private final LinkedList<V> queue;
    // max
    private final int max;

    public ReentrantLockDemo(int max) {
        this.queue = new LinkedList<>();
        this.max = max;
    }

    public void put(V v) {
        try {
            LOCK.lock();
            while (queue.size() >= max) {
                FULL_CONDITION.await();
            }
            queue.addLast(v);
            EMPTY_CONDITION.signalAll();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {
            LOCK.unlock();
        }
    }

    public V get() {
        V v;
        try {
            LOCK.lock();
            while (queue.isEmpty()) {
                EMPTY_CONDITION.await();
            }
            v = queue.removeFirst();
            FULL_CONDITION.signalAll();
            return v;
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        } finally {
            LOCK.unlock();
        }
    }
}

```

- synchronized

```java
public class SynchronizedDemo<V> {

    private final LinkedList<V> queue;

    private final int max;

    public SynchronizedDemo(int max) {
        this.max = max;
        this.queue = new LinkedList<>();
    }

    public void put(V v) {
        synchronized (queue) {
            while (queue.size() >= max) {
                try {
                    queue.wait();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
            queue.addLast(v);
            queue.notifyAll();
        }
    }

    public V get() {
        V v;
        synchronized (queue) {
            while (queue.isEmpty()) {
                try {
                    queue.wait();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
            v = queue.removeFirst();
            queue.notifyAll();
            return v;
        }
    }
}


```

--- 

### RateLimiter 限流

[👾doc](https://guava.dev/releases/snapshot-jre/api/docs/com/google/common/util/concurrent/TimeLimiter.html)

Google的Guava的限流策略是使用的令牌桶算法

### IO

[👾doc](https://guava.dev/releases/snapshot-jre/api/docs/com/google/common/io/package-summary.html)

#### Files

- `copy` 将source文件内容拷贝到target文件中

- `move` 将source文件内容移动到target文件中

- `readLines` 将source文件内容读取到List中

- `asCharSink(File file, Charset charset).write(String content)` 将content文件内容写入到文件中

- `asCharSink(File file, Charset charset, FileWriteMode... modes).write(String content)`
  将content文件内容append到文件中（非覆盖）

- `touch` touch方法类似于Linux的touch指令，创建一个空文件或者更新它的最近更新时间

- `fileTraverser` 文件递归遍历

#### CharSource

- `wrap` 根据给定的字符集构造一个CharSource

- `read` 以字符串形式读取内容

- `readFirstLine` 以字符串形式读取第一行内容

- `readLines` 以字符串形式读取所有行内容

- `readLines(LineProcessor<T> processor)` 以字符串形式读取所有行内容，可以在读取过程中进行处理操作

- `length`  获取长度

- `isEmpty` 判断是否为空,同静态方法`empty`

- `asByteSource` 将字符流转换为字节流

- `concat` 连接可变个CharSource

--- 

#### ByteSource

- `wrap` 根据给定的字节数组构造一个ByteSource

- `asCharSource` 将ByteSource转换为CharSource

- `read` 读取ByteSource作为一个字节数组

- `size`  获取大小

- `isEmpty` 判断是否为空,同静态方法`empty`

- `concat` 将多个ByteSource连接为一个ByteSource

---

#### Closer

- `create` 创建一个closer
- `rethrow` 存储抛出的java.lang.Throwable，然后重新抛出

---

### BaseEncoding
- `base64` Base 64 Encoding

custom Base64
```java
public final class Base64 {

    private static final String CODE_STRING = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz+/";

    private static final char[] CODE_DICT = CODE_STRING.toCharArray();

    private Base64() {
    }

    public static String encode(String plain) {
        Preconditions.checkNotNull(plain);
        StringBuilder result = new StringBuilder();
        String binaryStr = toBinary(plain);
        int delta = 6 - binaryStr.length() % 6;
        for (int i = 0; i < delta && delta != 6; i++) {
            binaryStr += "0";
        }
        for (int j = 0; j < binaryStr.length() / 6; j++) {
            int begin = j * 6;
            String encodeStr = binaryStr.substring(begin, begin + 6);
            char encodeChar = CODE_DICT[Integer.valueOf(encodeStr, 2)];
            result.append(encodeChar);
        }
        if (delta != 6) {
            for (int j = 0; j < delta / 2; j++) {
                result.append("=");
            }
        }
        return result.toString();
    }


    public static String decode(final String encrypt) {
        StringBuilder resultBuilder = new StringBuilder();
        String tmp = encrypt;
        int equalIndex = tmp.indexOf("=");
        if (equalIndex > 0) {
            tmp = tmp.substring(0, equalIndex);
        }
        String base64Binary = toBase64Binary(tmp);
        for (int j = 0; j < base64Binary.length() / 8; j++) {
            int begin = j * 8;
            String str = base64Binary.substring(begin, begin + 8);
            char c = Character.toChars(Integer.valueOf(str, 2))[0];
            resultBuilder.append(c);
        }
        return resultBuilder.toString();
    }


    private static String toBinary(final String source) {
        final StringBuilder binaryResult = new StringBuilder();
        for (int index = 0; index < source.length(); index++) {
            String charBin = Integer.toBinaryString(source.charAt(index));
            int delta = 8 - charBin.length();
            for (int d = 0; d < delta; d++)
                charBin = "0" + charBin;
            binaryResult.append(charBin);
        }
        return binaryResult.toString();
    }


    private static String toBase64Binary(final String source) {
        final StringBuilder binaryResult = new StringBuilder();
        for (int index = 0; index < source.length(); index++) {
            int i = CODE_STRING.indexOf(source.charAt(index));
            String charBinary = Integer.toBinaryString(i);
            int delta = 6 - charBinary.length();
            for (int d = 0; d < delta; d++) {
                charBinary = "0" + charBinary;
            }
            binaryResult.append(charBinary);
        }
        return binaryResult.toString();
    }
    
}

```
