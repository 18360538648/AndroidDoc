# Iterator(迭代器)

## 迭代器是一种设计模式，它是一个对象，它可以遍历并选择序列中的对象，而开发人员不需要了解该序列的底层结构。迭代器通常被称为“轻量级”对象，因为创建它的代价小。
## 使用方法

* 使用方法iterator()要求容器返回一个Iterator。第一次调用Iterator的next()方法时，它返回序列的第一个元素。注意：iterator()方法是java.lang.Iterable接口,被Collection继承。
* 使用next()获得序列中的下一个元素。
* 使用hasNext()检查序列中是否还有元素。
* 使用remove()将迭代器新返回的元素删除。

## 使用代码块


```
 list l = new ArrayList();
 l.add("aa");
 l.add("bb");
 l.add("cc");
 for (Iterator iter = l.iterator(); iter.hasNext();) {
  String str = (String)iter.next();
  System.out.println(str);
 }
```

## 使用Iterator遍历Map

```
 Iterator childIterator = childMap.entrySet().iterator();
  while (childIterator.hasNext()) {
        Map.Entry childEntry = (Map.Entry) childIterator.next();
        // 获取key值
         String childKey = (String) childEntry.getKey();
         // 获取value值
         childMap.get(childKey);                 
  }
```