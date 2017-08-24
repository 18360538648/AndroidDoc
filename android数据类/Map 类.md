# Map 类

## 1. keyset和entryset

keyset是map的key值集合，entryset是<key vlaue集合>，从效率方面将entryset效率要比keyset快，这样因为keyset还需从map中取一遍

## 2.遍历map

```
// keySet方式
Iterator<String> classIterator = finalMap.keySet().iterator();
while (classIterator.hasNext()) {
  String key = classIterator.next();
}
```

```
// entrySet()方式，推荐
Iterator<Map.Entry<String, ArrayList<String>>> classIterator1 = finalMap.entrySet().iterator();
while (classIterator1.hasNext()) {
Map.Entry<String, ArrayList<String>> entry = classIterator1.next();
String key = entry.getKey();
}
```

## 3. map排序

```
// 需继承TreeMap
    private Map<String, ArrayList<String>> classMap = new TreeMap<>(new Comparator<String>() {
        @Override
        public int compare(String o1, String o2) {
        // 升序
            int index = 0;
            if (Integer.parseInt(o1) > Integer.parseInt(o2)) {
                index = 1;
            } else if (Integer.parseInt(o1) == Integer.parseInt(o2)) {
                index = 0;
            } else {
                index = -1;
            }
            return index;
        }
    });
```

## 4. 对map中的元素直接取出来操作，即可以改变map