Map与String类型互转

一. Map转换为String

```
Map.toString();
```

一. String(将上面的String)转换为Map

```
public static Map StringToMap(String mapText) {
        // 将map.toString后的串反转成map
        String str1 = mapText.replaceAll("\\{|\\}", "");// singInfo是一个map toString后的字符串。
        String str2 = str1.replaceAll(" ", "");
        String str3 = str2.replaceAll(",", "&");
        Map<String, String> map = null;
        if ((null != str3) && (!"".equals(str3.trim()))) {
            String[] resArray = str3.split("&");
            if (0 != resArray.length) {
                map = new HashMap(resArray.length);
                for (String arrayStr : resArray) {
                    if ((null != arrayStr) && (!"".equals(arrayStr.trim()))) {
                        int index = arrayStr.indexOf("=");
                        if (-1 != index) {
                            map.put(arrayStr.substring(0, index), arrayStr.substring(index + 1));
                        }
                    }
                }
            }
        }

        return map;
    }
```