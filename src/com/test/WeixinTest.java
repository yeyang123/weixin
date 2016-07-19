package com.test;

import net.sf.json.JSONObject;

import org.apache.http.ParseException;

import com.po.AccessToken;
import com.util.WeixinUtil;

public class WeixinTest {
	public static void main(String[] args){
		try {
			AccessToken token=WeixinUtil.getAccessToken();
			System.out.println("Ʊ�ݣ�"+token.getAccess_token());
			System.out.println("��Чʱ�䣺"+token.getExpires_in());
			String path="D:/3.jpg";
			String mediaId=WeixinUtil.upload(path, token.getAccess_token(), "image"); //��ȡͼƬID
			System.out.println(mediaId);
			String thumbMediaId=WeixinUtil.upload(path, token.getAccess_token(), "thumb");   //��ȡ����ͼID
			System.out.println(thumbMediaId);
			String menu=JSONObject.fromObject(WeixinUtil.initMenu()).toString(); //�����˵�menu
			int result=WeixinUtil.createMenu(token.getAccess_token(), menu);
			if(result==0){
				System.out.println("�����˵��ɹ�");
			}else{
				System.out.println("�����룺"+result);
			}
			JSONObject jsonObject=WeixinUtil.queryMenu(token.getAccess_token()); //��ѯ�˵�menu
			System.out.println(jsonObject);
			/*
			int result1=WeixinUtil.deleteMenu(token.getAccess_token());  //ɾ���˵�menu
			if(result1==0){
				System.out.println("ɾ���˵��ɹ�");
			}else{
				System.out.println("�����룺"+result1);
			}
			*/
		} catch (Exception e) {
			e.printStackTrace();
		}
		
	}
}
