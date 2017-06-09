# EditText

## 1. 设置editext光标颜色
```
// 此为灰色
android:textCursorDrawable="@null"
```

## 2. 输入框的下划线问题

人眼看到的输入框下划线不是整个EditText的边框，默认的EditText是没有边框的，如果仅仅想设置EditText的下边框，可以在EditText的布局下面加一条横线

## 3. 设置edittext不可编辑

```
setLongClickable(false)
etCustomSelectionActionModeCallback(new ActionMode.Callback() {
            @Override
            public boolean onCreateActionMode(ActionMode mode, Menu menu) {
                return false;
            }

            @Override
            public boolean onPrepareActionMode(ActionMode mode, Menu menu) {
                return false;
            }

            @Override
            public boolean onActionItemClicked(ActionMode mode, MenuItem item) {
                return false;
            }

            @Override
            public void onDestroyActionMode(ActionMode mode) {

            }
        });
```
