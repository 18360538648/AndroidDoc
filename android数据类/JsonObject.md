# JsonObject

# 1. 遍历JsonObject

```
JSONObject classInfJo = new JSONObject(classInf);
                if (null != classInfJo) {
                    Iterator iterator = classInfJo.keys();
                    while (iterator.hasNext()) {
                        String key = iterator.next().toString();
                    }
                }

```

# 2. 对JsonObject按key值排序

将JsonObject的key值和value值放入map中，利用map按key值排序来实现，具体可参考map排序