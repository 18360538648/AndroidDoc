# android 退出逻辑

## 1.弹退出框

```
@Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            //back key Constant Value: 4 (0x00000004)
            //创建退出对话框
            AlertDialog.Builder isExit = new AlertDialog.Builder(this);
            //设置对话框标题
            isExit.setTitle("退出提示");
            //设置对话框消息
            isExit.setMessage("确定退出应用吗");
            // 添加选择按钮并注册监听
            isExit.setPositiveButton("确定", diaListener);
            isExit.setNegativeButton("取消", diaListener);
            //对话框显示
            isExit.show();
        }
        return false;

    }

    DialogInterface.OnClickListener diaListener = new DialogInterface.OnClickListener() {

        @Override
        public void onClick(DialogInterface dialog, int buttonId) {
            // TODO Auto-generated method stub
            switch (buttonId) {
                case AlertDialog.BUTTON_POSITIVE:// "确认"按钮退出程序
                    finish();
                    break;
                case AlertDialog.BUTTON_NEGATIVE:// "确认"按钮退出程序
                    //什么都不做
                    break;
                default:
                    break;
            }
        }
    };
```


##  2. 短时间不退出

 
 ```
 
 private long lastKeyBackDown;
	
	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		if(keyCode == KeyEvent.KEYCODE_BACK) {
			long currentTime = System.currentTimeMillis();
			if(currentTime - lastKeyBackDown > 1500) {
				Toast.makeText(this, "再按一次退出", Toast.LENGTH_SHORT).show();
			} else {
				finish();
			}
			lastKeyBackDown = currentTime;
			return false;
		}
		return super.onKeyDown(keyCode, event);
	}
	
	
 ```
 