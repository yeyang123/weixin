package com.test;

import net.sf.json.JSONObject;

import org.apache.http.ParseException;

import com.po.AccessToken;
import com.util.WeixinUtil;

public class WeixinTest {
	public static void main(String[] args){
		try {
			AccessToken token=WeixinUtil.getAccessToken();
			System.out.println("票据："+token.getAccess_token());
			System.out.println("有效时间："+token.getExpires_in());
			String path="D:/3.jpg";
			String mediaId=WeixinUtil.upload(path, token.getAccess_token(), "image"); //获取图片ID
			System.out.println(mediaId);
			String thumbMediaId=WeixinUtil.upload(path, token.getAccess_token(), "thumb");   //获取缩略图ID
			System.out.println(thumbMediaId);
			String menu=JSONObject.fromObject(WeixinUtil.initMenu()).toString(); //创建菜单menu
			int result=WeixinUtil.createMenu(token.getAccess_token(), menu);
			if(result==0){
				System.out.println("创建菜单成功");
			}else{
				System.out.println("错误码："+result);
			}
			JSONObject jsonObject=WeixinUtil.queryMenu(token.getAccess_token()); //查询菜单menu
			System.out.println(jsonObject);
			/*
			int result1=WeixinUtil.deleteMenu(token.getAccess_token());  //删除菜单menu
			if(result1==0){
				System.out.println("删除菜单成功");
			}else{
				System.out.println("错误码："+result1);
			}
			*/
		} catch (Exception e) {
			e.printStackTrace();
		}
		
	}
}
