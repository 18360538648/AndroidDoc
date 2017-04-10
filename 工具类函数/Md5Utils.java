package com.itheima62.mobileguard.utils;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class Md5Utils {
	public static String md5(String str){
		StringBuilder mess = new StringBuilder();
		try {
			//获取MD5加密器
			MessageDigest md = MessageDigest.getInstance("MD5");
			byte[] bytes = str.getBytes();
			byte[] digest = md.digest(bytes);
			
			for (byte b : digest){
				//把每个字节转成16进制数  
				int d = b & 0xff;// 0x000000ff
				String hexString = Integer.toHexString(d);
				if (hexString.length() == 1) {//字节的高4位为0
					hexString = "0" + hexString;
				}
				mess.append(hexString);//把每个字节对应的2位十六进制数当成字符串拼接一起
				
			}
		} catch (NoSuchAlgorithmException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return mess + "";
	}
}
