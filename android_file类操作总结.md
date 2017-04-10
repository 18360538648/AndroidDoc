# Android File类和Stream类操作总结

## 1. File类

### 1.1 创建文件

```

 Environment.getExternalStorageDirectory().getPath();
        if ("mounted".equals(state)) {
            String path = (new StringBuilder(String.valueOf(
                    Environment.getExternalStorageDirectory().getPath())))
                    .append("/steam/cache").toString();
            Log.i(LOGTAG, "文件目录路径" + path);
            File fileDir = new File(path);
            //只有文件夹对象创建成功了以后，才可以创建文件对象，如果不按这个顺序，会导致创建文件对象时，找不到这个目录
            if (!fileDir.exists()) {
                Log.i(LOGTAG, "文件目录不存在");
                fileDir.mkdirs();
                Log.i(LOGTAG, "文件目录不存在以后:" + fileDir.exists());
            }
            file = new File(path + "/b.zip");
            if (!file.exists()) {
                Log.i(LOGTAG, "文件不存在");
                try {
                    file.createNewFile();
                    Log.i(LOGTAG, "文件存在以后:" + file.exists());
                } catch (IOException e) {
                    e.printStackTrace();
                }

            }
        }
        //  写只能针对特定文件对象写，不能对文件夹对象写
        FileOutputStream fileOutputStream = new FileOutputStream(file);
 
 // 获取assets目录下的所有文件及目录名
            String fileNames[] = context.getAssets().list(oldPath);
            if (fileNames.length > 0) {// 如果是目录
                File file = new File(newPath);
                file.mkdirs();// 如果文件夹不存在，则递归
                for (String fileName : fileNames) {
                    CopyAssets(context, oldPath + "/" + fileName, newPath + "/" + fileName);
                }
        
```

### 1.2 删除文件

```
// 递归删除文件夹,如果一个目录为非空目录，需要把其子文件都删除以后，才能删除这个文件➕
    public static void deleteFile(File file) {
        if (file.exists()) {//判断文件是否存在
            if (file.isFile()) {//判断是否是文件
                file.delete();//删除文件
            } else if (file.isDirectory()) {//否则如果它是一个目录
                File[] files = file.listFiles();//声明目录下所有的文件 files[];
                for (int i = 0; i < files.length; i++) {//遍历目录下所有的文件
                    deleteFile(files[i]);//把每个文件用这个方法进行迭代
                }
                file.delete();//删除文件夹
            }
        } else {
            System.out.println("所删除的文件不存在");
        }
    }
```

## 2. Stream类
### 2.1 InputStream
内部存储空间到内存中的流

内存中从流中读取数据的方法有如下几种：

 int read() 读取一个字节，返回值为所读得字节
 
 int read(byte b[]) 读取多个字节，放置到字节数组b中，通常读取的字节数量为b的长度，返回值为实际独取的字节的数量。
 
 int read(byte b[] ,int off,int len)读取len个字节，放置到以下标off开始字节数组b中，返回值为实际读取的字节的数量。（这个方法用的是最多的）
 流操作完毕后必须关闭：close()
 
 
### 2.2 OutputStream
内存中的流到内部存储空间

内存中从流中读取数据的方法有如下几种：

void write(int b)往流中写一个字节b
  
void write(byte b[])往流中写一个字节数组b

void write(byte b[],int off,int len)把字节数组b中从下标off开始，长度为len的字节写入流中

flush()刷空输出流，并输出所有被缓存的字节由于某些流支持缓存功能，该方法将把缓存中所有内容强制输出到流中。

close()流操作完毕后必须关闭

以下是一个常用的操作代码

```
InputStream is = context.getAssets().open(oldPath);
// 要针对某个特定的文件对象
FileOutputStream fos = new FileOutputStream(new File(newPath));
//定义一个供输入输出流存放字节的字节数组
byte[] buffer = new byte[1024];
//实际读取的字节的数量
int byteCount = 0;
//is.read(buffer)将读取的多个字节往字节数组中放
//fos.write(buffer, 0, byteCount)为将存放在字节数组中的字节读取出来输出
while ((byteCount = is.read(buffer)) != -1) {// 循环从输入流读取
// buffer字节
fos.write(buffer, 0, byteCount);// 将读取的输入流写入到输出流
         }
fos.flush();// 刷新缓冲区
is.close();
fos.close();
```
## 3. 不同内容存放的位置不同
data/data目录放的是安装的软件和各种数据文件，当应用卸载时，这个目录会被清理掉，通过getFilesDir()获取这个目录
/mnt/sdcard/Android/data/package，当应用卸载时，这个目录会被清理掉，通过getExternalFilesDir()
SDcard存放比较大的文件，当应用卸载时文件不会删除

## 4. 解压zip包(带解压进度)

```
// 使用需要下载ant.jar(http://download.csdn.net/detail/shizhending/4139787)
// archive为需要解压的文件对象(传文件路径也可以)
// upZipDir为解压路径
public static void unZipFile(File archive, String upZipDir) throws IOException, FileNotFoundException, ZipException {
        // 已解压文件大小
                    long sumLength = 0;
                    BufferedInputStream bi;
                    ZipFile zf = null;
                    zf = new ZipFile(originFile, "GBK");
                    // 获取解压之后文件的大小,用来计算解压的进度
                    // getZipTrueSize()需在另外一个文件中写,因为ZipFile函数在ant.jar中是不存在的，需要使用原生的java.util.zip.ZipFile类
                    long ziplength = FileUtils.getZipTrueSize(originFile);
                    Log.i("lsw", "要解压的文件大小" + ziplength);
                    Enumeration e = zf.getEntries();
                    while (e.hasMoreElements()) {
                        ZipEntry ze2 = (ZipEntry) e.nextElement();
                        String entryName = ze2.getName();
                        String path = unZipFilePath + "/" + entryName;
                        if (ze2.isDirectory()) {
                            System.out.println("正在创建解压目录 - " + entryName);
                            File decompressDirFile = new File(path);
                            if (!decompressDirFile.exists()) {
                                decompressDirFile.mkdirs();
                            }
                        } else {
                            System.out.println("正在创建解压文件 - " + entryName);
                            String fileDir = path.substring(0, path.lastIndexOf("/"));
                            File fileDirFile = new File(fileDir);
                            if (!fileDirFile.exists()) {
                                fileDirFile.mkdirs();
                            }
                            BufferedOutputStream bos = new BufferedOutputStream(new FileOutputStream(unZipFilePath + "/" + entryName));
                            bi = new BufferedInputStream(zf.getInputStream(ze2));
                            byte[] readContent = new byte[1024];
                            int readCount = bi.read(readContent);
                            while (readCount != -1) {
                                if (contextUtils != null) {
                                    sumLength += readCount;
                                    int progress = (int) ((sumLength * 100) / ziplength);
                                    Log.i("lsw","进度"+ progress);
                                }
                                bos.write(readContent, 0, readCount);
                                readCount = bi.read(readContent);
                            }
                            bos.close();
                        }
                    }
                    zf.close();
                    Log.i("lsw", "解压成功以后，发送数据");
                    System.out.println("解压文件成功");

    }
```

```
/**
     * 获取压缩包解压后的内存大小
     *
     * @param filePath 文件路径
     * @return 返回内存long类型的值
     */
    public static long getZipTrueSize(String filePath) {
        long size = 0;
        ZipFile f;
        try {
            f = new ZipFile(filePath);
            Enumeration<? extends ZipEntry> en = f.entries();
            while (en.hasMoreElements()) {
                size += en.nextElement().getSize();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return size;
    }
```

## 4. 存储信息到文件中

```
// 将json数据写入文本中
    public static void dataToFile(String strogeFileName, String statData) {
        FileOutputStream fileoutputstream = null;
        if (TextUtils.isEmpty(statData)) {
            return;
        }
        File file = new File(strogeFileName);
        if (!(file.exists())) {
            try {
                file.createNewFile();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        try {
            fileoutputstream = new FileOutputStream(file);
            byte abyte0[] = statData.getBytes("UTF-8");
            fileoutputstream.write(abyte0, 0, abyte0.length);
            fileoutputstream.close();
            fileoutputstream = null;
        } catch (Exception exception) {
        }
        try {
            if (fileoutputstream != null) {
                fileoutputstream.close();
                fileoutputstream = null;
            }
        } catch (Exception exception2) {
        }
    }

```

## 5. 从文件中读取信息

```
// 将文件中的信息往内存中输送
    public static String dataToRAM(String s1) {
        String inf = "";
        FileInputStream fileinputstream = null;
        File file = new File(s1);
        try {
            // 判读文件存在时
            if (file.exists()) {
                fileinputstream = new FileInputStream(file);
                byte abyte0[] = new byte[(int) file.length()];
                fileinputstream.read(abyte0);
                inf = new String(abyte0, 0, abyte0.length, "UTF-8");
                fileinputstream.close();
                fileinputstream = null;
            }
        } catch (Exception exception) {
        }
        try {
            if (fileinputstream != null) {
                fileinputstream.close();
                fileinputstream = null;
            }
        } catch (Exception exception) {
        }
        // 将字符串类型转换为jsonArray类型

        return inf;
    }

```


 

