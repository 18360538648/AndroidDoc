Android SharedPreferences

## SharedPreferences

存储的数据存在K-V关系，数据量少，数据可能需要频繁的读写。多用于保存用户在使用软件时的个性化设置，本质是使用xml存储数据，文件将保存在 /data/data/应用程序包名/shared_prefs/文件夹下。

### 1. 存储数据

```
// 存储SharedPreferences
    public void setSharedPreference(String key, String value, Context context) {
        // 第一个参数为 文件名
        // 第二个参数为 SharedPreferences的四种模式
        Context.MODE_PRIVATE: 代表文件是私有文件，只能被应用本身访问
        Context.MODE_WORLD_READABLE :表示当前文件可以被其他应用读取
        Context.MODE_WORLD_WRITEABLE :表示当前文件可以被其他应用写入
        Context.MODE_APPEND :
        SharedPreferences sharedPreferences = context.getSharedPreferences("wealth", Context.MODE_PRIVATE);
        // 一定要记得commit(),否则无效
        sharedPreferences.edit().putString(key, value).commit();
    }
```

### 2. 取数据

```
// 获取SharedPreferences的值
    public String getSharedPreference(String key, Context context) {
        SharedPreferences sharedPreferences = context.getSharedPreferences("wealth", Context.MODE_PRIVATE);
        return sharedPreferences.getString(key, null);

    }
```


