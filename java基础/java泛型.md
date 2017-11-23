# java 泛型

## 1. 泛型的定义

类型形参化，不指定具体的类型，其只是作用于代码编译阶段，在编译之后的class文件不包含任何泛型信息，泛型信息是不会进入到运行时阶段。

## 2. 泛型的分类



## 2.1 泛型接口 

```
public interface Test<T> {
    public T next();
}
```

```java
// 未传入泛型实参
public class Next1<T> implements Test<T>{
    @Override
    public T next() {
        return null;
    }
}
```



```java
// 传入泛型实参
public class Next2 implements Test<String>{
    @Override
    public String next() {
        return null;
    }
}
```
# 2.2  泛型类



```java
public class Test<T> {
    private T key;
    // 实例化类的时候指明泛型的具体类型
    public Test(T key) {
        this.key = key;
    }

    public T get() {
        return key;
    }
}
```

## 2.3 泛型方法



**调用方法的时候指明泛型的具体类型**

```
public <T> T showKeyName(Generic<T> container){
        System.out.println("container key :" + container.getKey());
        T test = container.getKey();
        return test;
    }
```



## 3. 泛型通配符

泛型通配符用`?`表示，代表具体类型的实参(非形参)，
