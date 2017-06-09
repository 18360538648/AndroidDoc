# android队列

在android中有时要按照顺序执行一系列操作，这时就要用到队列了

## 1. 申明一个队列

```
LinkedList deleteList = new LinkedList();
```

## 2. 往队列加入一个元素

```
deleteList.addLast(jsonObject);
```

## 3. 取出队列中的元素

```
public static JSONObject deQueue(LinkedList list)//出队
    {
        JSONObject jsonObject = null;
        if (!list.isEmpty()) {
            jsonObject = (JSONObject) list.removeFirst();
            return jsonObject;
        }
        return jsonObject;
    }
```

