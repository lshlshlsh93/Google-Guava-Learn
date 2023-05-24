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