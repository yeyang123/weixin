package com.servlet;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.Date;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.dom4j.DocumentException;

import com.po.TextMessage;
import com.util.CheckUtil;
import com.util.MessageUtil;

public class WeixinServlet extends HttpServlet {
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		String signature=req.getParameter("signature");
		String timestamp=req.getParameter("timestamp");
		String nonce=req.getParameter("nonce");
		String echostr=req.getParameter("echostr");
	req.setCharacterEncoding("UTF-8");
	resp.setCharacterEncoding("UTF-8");
	PrintWriter out=resp.getWriter();
	if(CheckUtil.checkSignature(signature, timestamp, nonce)){
		out.print(echostr);
	}
	}
	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		req.setCharacterEncoding("UTF-8");
		resp.setCharacterEncoding("UTF-8");
		PrintWriter out=resp.getWriter();
		try {
			Map<String,String> map=MessageUtil.xmlToMap(req);
			String toUserName=map.get("ToUserName");
			String fromUserName=map.get("FromUserName");
			String msgType=map.get("MsgType");
			String content=map.get("Content");
			String message=null;
			if(MessageUtil.MESSAGE_TEXT.equals(msgType)){
				if("1".equals(content)){
					message=MessageUtil.initText(toUserName, fromUserName, MessageUtil.firstMenu());
				}else if("2".equals(content)){
					message=MessageUtil.initText(toUserName, fromUserName, MessageUtil.secondMenu());
				}else if("图文测试".equals(content)){
					message=MessageUtil.initNewsMessage(toUserName, fromUserName);
				}else if("图片测试".equals(content)){
					message=MessageUtil.initImageMessage(toUserName, fromUserName);
				}else if("音乐测试".equals(content)){
					message=MessageUtil.initMusicMessage(toUserName, fromUserName);
				}else{
				/*直接的获取对方发送的消息*/
				TextMessage text=new TextMessage();
				text.setFromUserName(toUserName);
				text.setToUserName(fromUserName);
				text.setMsgType("text");
				text.setCreateTime(new Date().getTime());
				text.setContent("您发送的消息是："+content+"\n后台已接收");
				message=MessageUtil.TextMessageToXml(text);
				
				}
			}else if(MessageUtil.MESSAGE_EVENT.equals(msgType)){
				String eventType=map.get("Event");
				if(MessageUtil.MESSAGE_SUBSCRIBE.equals(eventType)){
					message=MessageUtil.initText(toUserName, fromUserName, MessageUtil.menuText());
				}else if(MessageUtil.MESSAGE_CLICK.equals(eventType)){
					message=MessageUtil.initText(toUserName, fromUserName, MessageUtil.menuText());
					System.out.println(message);
				}else if(MessageUtil.MESSAGE_VIEW.equals(eventType)){
					String url=map.get("Eventkey");
					message=MessageUtil.initText(toUserName, fromUserName, url);
				}else if(MessageUtil.MESSAGE_SCANCODE.equals(eventType)){
					String key=map.get("Eventkey");
					message=MessageUtil.initText(toUserName, fromUserName, key);
				}
			}else if(MessageUtil.MESSAGE_LOCATION.equals(msgType)){
				String label=map.get("Label");
				message=MessageUtil.initText(toUserName, fromUserName, label);
			}
			System.out.println(message);
			out.print(message);
		} catch (DocumentException e) {
			e.printStackTrace();
		}finally{
			out.close();
		}
	}
}
