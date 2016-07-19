package com.util;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.dom4j.DocumentException;
import org.dom4j.Element;
import org.dom4j.Document;
import org.dom4j.io.SAXReader;

import com.menu.Button;
import com.menu.ClickButton;
import com.menu.Menu;
import com.menu.ViewButton;
import com.po.Image;
import com.po.ImageMessage;
import com.po.Music;
import com.po.MusicMessage;
import com.po.News;
import com.po.NewsMessage;
import com.po.TextMessage;
import com.thoughtworks.xstream.XStream;


public class MessageUtil {
	public static final String MESSAGE_TEXT="text";
	public static final String MESSAGE_IMAGE="image";
	public static final String MESSAGE_VOICE="voice";
	public static final String MESSAGE_VIDEO="video";
	public static final String MESSAGE_SHORTVIDEO="shortvideo";
	public static final String MESSAGE_LOCATION="location";
	public static final String MESSAGE_LINK="link";
	public static final String MESSAGE_EVENT="event";
	public static final String MESSAGE_SUBSCRIBE="subscribe";
	public static final String MESSAGE_UNSUBSCRIBE="unsubscribe";
	public static final String MESSAGE_CLICK="CLICK";
	public static final String MESSAGE_VIEW="VIEW";
	public static final String MESSAGE_NEWS="news";
	public static final String MESSAGE_MUSIC="music";
	public static final String MESSAGE_SCANCODE="scancode_push";
	/**
	 * xmlתΪmap����
	 * @param request
	 * @return
	 * @throws IOException
	 * @throws DocumentException
	 */
	public static Map<String,String> xmlToMap(HttpServletRequest request)throws IOException,DocumentException{
		Map<String,String> map=new HashMap<String,String>();
		SAXReader reader=new SAXReader();
		
		InputStream ins=request.getInputStream();
		Document doc= reader.read(ins);
		
		Element root=doc.getRootElement();
		
		List<Element> list=root.elements();
		for(Element e:list){
			map.put(e.getName(), e.getText());
		}
		ins.close();
		return map;
	}
	/**
	 * ���ı���Ϣ����ת��Ϊxml
	 * @param textMessage
	 * @return
	 */
	public static String TextMessageToXml(TextMessage textMessage){
		XStream xstream=new XStream();
		xstream.alias("xml", textMessage.getClass()); //�����ڵ��л�Ϊxml
		return xstream.toXML(textMessage);
	}
	public static String initText(String toUserName,String fromUserName,String content){
		TextMessage text=new TextMessage();
		text.setFromUserName(toUserName);
		text.setToUserName(fromUserName);
		text.setMsgType(MESSAGE_TEXT);
		text.setCreateTime(new Date().getTime());
		text.setContent(content);
		return TextMessageToXml(text);
	}
	/**
	 * ��ע������˵�
	 * @return
	 */
	public static String menuText(){
		StringBuffer sb=new StringBuffer();
		sb.append("��ӭ��עҶ�yeyang_blue���ظ��������ֿɻ�ȡ��\n\n");
		sb.append("    1 . �˺ż��\n");
		sb.append("    2 . Ҷ�΢��\n\n");
		sb.append("������������ԡ�");
		return sb.toString();
	}
	public static String firstMenu(){
		StringBuffer sb=new StringBuffer();
		sb.append("���˺Ų���ʱ���͸�����Ϣ������Ҷ�Ϊԭ�����£���������Ϊת�����£���л��ע��");
		return sb.toString();
	}
	public static String secondMenu(){
		StringBuffer sb=new StringBuffer();
		sb.append("Ҷ�\n");
		sb.append("http://weibo.com/11031400/");
		return sb.toString();
	}
	/**
	 * ͼ����ϢתΪxml
	 * @param newsMessage
	 * @return
	 */
	public static String NewsMessageToXml(NewsMessage newsMessage){
		XStream xstream=new XStream();
		xstream.alias("xml", newsMessage.getClass()); //�����ڵ��л�Ϊxml
		xstream.alias("item",new News().getClass()); //��News�Ľڵ���Ϣ�л�Ϊitem
		return xstream.toXML(newsMessage);
	}
	/**
	 * ͼƬ��ϢתΪxml
	 * @param newsMessage
	 * @return
	 */
	public static String ImageMessageToXml(ImageMessage imageMessage){
		XStream xstream=new XStream();
		xstream.alias("xml", imageMessage.getClass()); //�����ڵ��л�Ϊxml
		//xstream.alias("Image",new Image().getClass()); 
		return xstream.toXML(imageMessage);
	}
	/**
	 * ������ϢתΪxml
	 * @param newsMessage
	 * @return
	 */
	public static String MusicMessageToXml(MusicMessage musicMessage){
		XStream xstream=new XStream();
		xstream.alias("xml", musicMessage.getClass()); //�����ڵ��л�Ϊxml
		return xstream.toXML(musicMessage);
	}
	/**
	 * ͼ����Ϣ��װ
	 * @param toUserName
	 * @param fromUserName
	 * @return
	 */
	public static String initNewsMessage(String toUserName,String fromUserName){
		String message=null;
		List<News> newsList=new ArrayList<News>();
		NewsMessage newsMessage=new NewsMessage();
		News news=new News();
		news.setTitle("ͼ����Ϣ����");
		news.setDescription("��������");
		news.setPicUrl("http://0af4068a.ngrok.io/weixin/image/1.jpg");
		news.setUrl("www.baidu.com");
		newsList.add(news);
		news=new News();
		/*��ͼ��ʾ������ͼ��ʱ��ʵ������ʾ����
		news.setTitle("ͼ����Ϣ����2");
		news.setDescription("��������2");
		news.setPicUrl("http://0af4068a.ngrok.io/weixin/image/2.jpg");
		news.setUrl("www.sina.com");
		newsList.add(news);
		news=new News();
		news.setTitle("ͼ����Ϣ����3");
		news.setDescription("��������3");
		news.setPicUrl("http://0af4068a.ngrok.io/weixin/image/3.jpg");
		news.setUrl("www.163.com");
		newsList.add(news);
		*/
		newsMessage.setFromUserName(toUserName);
		newsMessage.setToUserName(fromUserName);
		newsMessage.setCreateTime(new Date().getTime());
		newsMessage.setMsgType(MESSAGE_NEWS);
		newsMessage.setArticles(newsList);
		newsMessage.setArticleCount(newsList.size());
		message=NewsMessageToXml(newsMessage);
		return message;
	}
	/**
	 * ��װͼƬ��Ϣ����������Ƶ��Ϣ��ͬͼƬ
	 * @param toUserName
	 * @param fromUserName
	 * @return
	 */
	public static String initImageMessage(String toUserName,String fromUserName){
		String message=null;
		Image image=new Image();
		image.setMediaId("6XJTyN7o4VzfFm_0vzU0dJwc4qCIQlAvDZrxafC4ol3qIxKJZ9lEe21pc3RC5ljR"); 
		//ע�⣬WeixinTest�µ�MediaIdÿ����һ�Σ�MediaId������£��˴�Ҳ��ͬ�����£������ղ���ͼƬ
		ImageMessage imageMessage=new ImageMessage();
		imageMessage.setFromUserName(toUserName);
		imageMessage.setToUserName(fromUserName);
		imageMessage.setCreateTime(new Date().getTime());
		imageMessage.setMsgType(MESSAGE_IMAGE);
		imageMessage.setImage(image);
		message=ImageMessageToXml(imageMessage);
		return message;
	}
	/**
	 * ��װ������Ϣ��ע�⣬���ֻظ������ֻ�����ʾ����ʧ�ܣ�������Ч
	 * @param toUserName
	 * @param fromUserName
	 * @return
	 */
	public static String initMusicMessage(String toUserName,String fromUserName){
		String message=null;
		Music music=new Music();
		music.setThumbMediaId("U4D1yorSBVDbGmhnkvPLeh6p6Wyq5S0J8MP1vGpa8PtQAom4iK4RJAZUlUjUy105"); 
		//ע�⣬WeixinTest�µ�MediaIdÿ����һ�Σ�MediaId������£��˴�Ҳ��ͬ�����£������ղ���ͼƬ
		music.setTitle("����������Ϣ����");
		music.setDescription("����������Ϣ������");
		music.setMusicUrl("http://127.0.0.1:8081/weixin/resource/����-����.mp3");
		music.setHQMusicUrl("http://127.0.0.1:8081/weixin/resource/����-����.mp3");
		MusicMessage musicMessage=new MusicMessage();
		musicMessage.setFromUserName(toUserName);
		musicMessage.setToUserName(fromUserName);
		musicMessage.setCreateTime(new Date().getTime());
		musicMessage.setMsgType(MESSAGE_MUSIC);
		musicMessage.setMusic(music);
		message=MusicMessageToXml(musicMessage);
		return message;
	}
	
}
