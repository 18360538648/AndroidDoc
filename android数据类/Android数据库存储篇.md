Android数据库存储篇

## 以前学过android sqlite数据库存储，但由于项目中一直用sharedPreference和文件存储，就一直没有用sqlite数据库存储了。最近在做视频播放记录时，用到了sqlite数据库存储，在此就做笔记。


### 1. 创建数据库

```
public class AndroidSQLiteOpenHelper extends SQLiteOpenHelper {
    // 数据库名称
    public static final String DBNAME = "android.db";
    // 数据库版本
    public static final int VERSION = 2;
    // 建表
    private static final String CREATETABLE = "create table "
            + VideoijkBean.TABLENAME
            + "(url string,lastplaytime long)";

    public AndroidSQLiteOpenHelper(Context context) {
        super(context, DBNAME, null, VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(CREATETABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion{
        this.deleteDB(db);
        this.onCreate(db);
    }

    // 删除表
    private void deleteDB(SQLiteDatabase db) {
        db.execSQL("drop table if exists " + VideoijkBean.TABLENAME);
    }
}

```


### 2. 管理数据库(增删改查)


```
public class DatabaseManager {
    private AndroidSQLiteOpenHelper dbHelper;

    public DatabaseManager(Context context) {
        dbHelper = new AndroidSQLiteOpenHelper(context);
    }

    // 插入记录
    public int insert(VideoijkBean videoijkBean) {
        ArrayList<VideoijkBean> hasExist = query(videoijkBean.getUrl());
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        // 插入数据之前，判断是否存在这条数据
        if (hasExist.size() == 0) {
            db.beginTransaction();
            try {
                db.execSQL("insert into " + videoijkBean.TABLENAME
                        + " values(?,?)", new Object[]{videoijkBean.getUrl(), videoijkBean.getLastPlaytime()});
                db.setTransactionSuccessful();
            } catch (Exception e) {
                return 0;
            } finally {
                db.endTransaction();
            }
        } else {
            // 有这条数据需要最新播放时间
            update(videoijkBean);
        }
        db.close();
        return 1;
    }

    // 查询某一个记录
    public ArrayList<VideoijkBean> query(String url) {
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        Cursor cursor;
        VideoijkBean videoijkBean;
        ArrayList<VideoijkBean> list = new ArrayList<VideoijkBean>();
        cursor = db.rawQuery("select * from " + VideoijkBean.TABLENAME
                + " where url=?", new String[]{url});
        while (cursor.moveToNext()) {
            videoijkBean = new VideoijkBean();
            videoijkBean.url = cursor.getString(cursor.getColumnIndex("url"));
            Log.e("SQLite", videoijkBean.toString());
            list.add(videoijkBean);
        }
        cursor.close();
        db.close();
        if (list.size() == 0) {
            Log.e("SQLite", "****表中无数据****");
        }
        return list;
    }

    // 查询所有记录
    public ArrayList<VideoijkBean> queryAlldata() {
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        Cursor cursor;
        VideoijkBean videoijkBean;
        ArrayList<VideoijkBean> list = new ArrayList<VideoijkBean>();
        cursor = db.rawQuery("select * from " + VideoijkBean.TABLENAME + " ORDER BY lastplaytime DESC", null);
        while (cursor.moveToNext()) {
            videoijkBean = new VideoijkBean();
            videoijkBean.url = cursor.getString(cursor.getColumnIndex("url"));
            videoijkBean.lastPlaytime = cursor.getLong(cursor.getColumnIndex("lastplaytime"));
            list.add(videoijkBean);
        }
        cursor.close();
        db.close();
        if (list.size() == 0) {
            Log.e("SQLite", "****表中无数据****");
        }
        return list;
    }

    // 更新数据
    public int update(VideoijkBean videoijkBean) {
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        db.beginTransaction();
        try {
            db.execSQL("update " + VideoijkBean.TABLENAME
                    + " set lastplaytime=? where url=?", new Object[]{
                    videoijkBean.lastPlaytime, videoijkBean.url});
            db.setTransactionSuccessful();
        } catch (Exception e) {
            return 0;
        } finally {
            db.endTransaction();
        }
        db.close();
        return 1;
    }
    // 删除数据
    delete from person  where id=10
    
    
}
```