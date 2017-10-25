# java的四种引用方式

## 1. StrongReference(强引用)

任何时候GC不会回收它，及时内存不足时，系统会抛出OOM，也不会回收

```
Person person  = new Person();
```
## 2. SoftReference(软引用)

内存不足时会回收

```
/**
 * 测试软类型引用
 */
private static void testSoftReference() {
    Person jack = new Person("Jack");
    SoftReference<Person> personSoftReference = new SoftReference<>(jack);
    System.out.println(personSoftReference.get());
    jack = null;
    System.gc();
    System.gc();
    System.out.println(personSoftReference.get());
}
```
```
Person{name='Jack'}
Person{name='Jack'}
```

## 3. WeakReference(弱引用)

GC一运行就会回收

## 4. PhantomReference(虚引用)

一创建久回收了
