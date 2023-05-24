# Guava

## utils

### Joiner

#### 1. `join` 拼接集合中的元素

#### 2. `skipNulls` 跳过null元素

#### 3. `useForNull` 替换null元素为给定的值

#### 4. `withKeyValueSeparator` 以给定的分隔符拼接map中的元素

#### 5. `appendTo` 追加内容到StringBuilder、File等

#### 6. `on` 以何种形式拼接

--- 

### Splitter

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

#### 1. `checkArgument`  校验参数，参数不符合预期抛出--`IllegalArgumentException`非法参数异常

#### 2. `checkState` 校验状态，状态不符合预期抛出--`IllegalStateException`非法状态异常

#### 3. `checkElementIndex` 校验元素索引,失败时抛出--`IndexOutOfBoundsException`数组下标越界异常

#### 4. `checkNotNull` 校验不为空，否则抛出--`NullPointerException`空指针异常

--- 

### Objects

#### 1. `MoreObjects.toStringHelper` 帮助我们编写重写的toString方法

#### 2. `equal` 帮助我们编写重写的equals方法

#### 3. `hashCode` 帮助我们编写重写的hashcode方法

#### 4. `ComparisonChain.start() .compare().result()` 帮助我们编写比较大小时的compareTo方法

---
