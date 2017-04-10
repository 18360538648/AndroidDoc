# Android Dialog

# 1. 提示是否退出对话框

```
protected void dialog() {
　　  AlertDialog.Builder builder = new Builder(Main.this);
　　  builder.setMessage("确认退出吗？");
　　  builder.setTitle("提示");
　　  builder.setPositiveButton("确认", new OnClickListener() {
　　   @Override
　　   public void onClick(DialogInterface dialog, int which) {
　　    dialog.dismiss();
　　    Main.this.finish();
　　   }
　　  });
　　  builder.setNegativeButton("取消", new OnClickListener() {
　　   @Override
　　   public void onClick(DialogInterface dialog, int which) {
　　    dialog.dismiss();
　　   }
　　  });
　　  builder.create().show();
　　 }
```

```
public boolean onKeyDown(int keyCode, KeyEvent event) {
　　  if (keyCode == KeyEvent.KEYCODE_BACK && event.getRepeatCount() == 0) {
　　   dialog();
　　  }
　　  return false;
　　 }
```

# 2. 提示三个按钮

```
Dialog dialog = new AlertDialog.Builder(this).setIcon(
　　     android.R.drawable.btn_star).setTitle("喜好调查").setMessage(
　　     "你喜欢李连杰的电影吗？").setPositiveButton("很喜欢",
　　     new OnClickListener() {
　　      @Override
　　      public void onClick(DialogInterface dialog, int which) {
　　       // TODO Auto-generated method stub
　　       Toast.makeText(Main.this, "我很喜欢他的电影。",
　　         Toast.LENGTH_LONG).show();
　　      }
　　     }).setNegativeButton("不喜欢", new OnClickListener() {
　　    @Override
　　    public void onClick(DialogInterface dialog, int which) {
　　     // TODO Auto-generated method stub
　　     Toast.makeText(Main.this, "我不喜欢他的电影。", Toast.LENGTH_LONG)
　　       .show();
　　    }
　　   }).setNeutralButton("一般", new OnClickListener() {
　　    @Override
　　    public void onClick(DialogInterface dialog, int which) {
　　     // TODO Auto-generated method stub
　　     Toast.makeText(Main.this, "谈不上喜欢不喜欢。", Toast.LENGTH_LONG)
　　       .show();
　　    }
　　   }).create();
　　   dialog.show();
```

# 3. 在dialog中添加组件如Edittext

```
new AlertDialog.Builder(this).setTitle("请输入").setIcon(
　　     android.R.drawable.ic_dialog_info).setView(
　　     new EditText(this)).setPositiveButton("确定", null)
　　     .setNegativeButton("取消", null).show();
```

# 4. 信息内容是一组单选框
```
new AlertDialog.Builder(this).setTitle("复选框").setMultiChoiceItems(
　　     new String[] { "Item1", "Item2" }, null, null)
　　     .setPositiveButton("确定", null)
　　     .setNegativeButton("取消", null).show();
```

# 5 信息内容是一组多选框

```
new AlertDialog.Builder(this).setTitle("单选框").setIcon(
　　     android.R.drawable.ic_dialog_info).setSingleChoiceItems(
　　     new String[] { "Item1", "Item2" }, 0,
　　     new DialogInterface.OnClickListener() {
　　      public void onClick(DialogInterface dialog, int which) {
　　       dialog.dismiss();
　　      }
　　     }).setNegativeButton("取消", null).show();
```

# 6 信息内容是一组简单列表项

```
new AlertDialog.Builder(this).setTitle("列表框").setItems(
　　     new String[] { "Item1", "Item2" }, null).setNegativeButton(
　　     "确定", null).show();
```

# 7 进度条
// 通过ProgressDialogUtil.show()展示进度条
// 通过ProgressDialogUtil.progressDialog.dismiss()消失进度条[此时已销毁，hide()方法为销毁]
// ProgressDialogUtil.progressDialog.setProcess(int value) 设置进度

```
public class ProgressDialogUtil {
    public static ProgressDialog progressDialog;

    public static void show(Context context, String msg) {
        progressDialog = new ProgressDialog(context);
        progressDialog.setMessage(msg);
        // 设置进度条是否不明确，false为明确
        progressDialog.setIndeterminate(false);
        // 可以改变样式
        progressDialog.setProgressStyle(ProgressDialog.STYLE_HORIZONTAL);
        progressDialog.setMax(100);
        progressDialog.setCanceledOnTouchOutside(false);
        progressDialog.show();
    }
}

```
