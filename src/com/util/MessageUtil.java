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
	 * xml转为map集合
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
	 * 将文本消息对象转换为xml
	 * @param textMessage
	 * @return
	 */
	public static String TextMessageToXml(TextMessage textMessage){
		XStream xstream=new XStream();
		xstream.alias("xml", textMessage.getClass()); //将根节点切换为xml
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
	 * 关注后的主菜单
	 * @return
	 */
	public static String menuText(){
		StringBuffer sb=new StringBuffer();
		sb.append("欢迎关注叶炀yeyang_blue，回复以下数字可获取：\n\n");
		sb.append("    1 . 账号简介\n");
		sb.append("    2 . 叶炀微博\n\n");
		sb.append("若需帮助请留言。");
		return sb.toString();
	}
	public static String firstMenu(){
		StringBuffer sb=new StringBuffer();
		sb.append("本账号不定时推送各类消息，署名叶炀为原创文章，其余署名为转载文章，感谢关注！");
		return sb.toString();
	}
	public static String secondMenu(){
		StringBuffer sb=new StringBuffer();
		sb.append("叶炀\n");
		sb.append("http://weibo.com/11031400/");
		return sb.toString();
	}
	/**
	 * 图文消息转为xml
	 * @param newsMessage
	 * @return
	 */
	public static String NewsMessageToXml(NewsMessage newsMessage){
		XStream xstream=new XStream();
		xstream.alias("xml", newsMessage.getClass()); //将根节点切换为xml
		xstream.alias("item",new News().getClass()); //将News的节点信息切换为item
		return xstream.toXML(newsMessage);
	}
	/**
	 * 图片消息转为xml
	 * @param newsMessage
	 * @return
	 */
	public static String ImageMessageToXml(ImageMessage imageMessage){
		XStream xstream=new XStream();
		xstream.alias("xml", imageMessage.getClass()); //将根节点切换为xml
		//xstream.alias("Image",new Image().getClass()); 
		return xstream.toXML(imageMessage);
	}
	/**
	 * 音乐消息转为xml
	 * @param newsMessage
	 * @return
	 */
	public static String MusicMessageToXml(MusicMessage musicMessage){
		XStream xstream=new XStream();
		xstream.alias("xml", musicMessage.getClass()); //将根节点切换为xml
		return xstream.toXML(musicMessage);
	}
	/**
	 * 图文消息组装
	 * @param toUserName
	 * @param fromUserName
	 * @return
	 */
	public static String initNewsMessage(String toUserName,String fromUserName){
		String message=null;
		List<News> newsList=new ArrayList<News>();
		NewsMessage newsMessage=new NewsMessage();
		News news=new News();
		news.setTitle("图文消息标题");
		news.setDescription("我是描述");
		news.setPicUrl("http://0af4068a.ngrok.io/weixin/image/1.jpg");
		news.setUrl("www.baidu.com");
		newsList.add(news);
		news=new News();
		/*多图文示例，多图文时其实并不显示描述
		news.setTitle("图文消息标题2");
		news.setDescription("我是描述2");
		news.setPicUrl("http://0af4068a.ngrok.io/weixin/image/2.jpg");
		news.setUrl("www.sina.com");
		newsList.add(news);
		news=new News();
		news.setTitle("图文消息标题3");
		news.setDescription("我是描述3");
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
	 * 组装图片消息，语音、视频消息，同图片
	 * @param toUserName
	 * @param fromUserName
	 * @return
	 */
	public static String initImageMessage(String toUserName,String fromUserName){
		String message=null;
		Image image=new Image();
		image.setMediaId("6XJTyN7o4VzfFm_0vzU0dJwc4qCIQlAvDZrxafC4ol3qIxKJZ9lEe21pc3RC5ljR"); 
		//注意，WeixinTest下的MediaId每运行一次，MediaId都会更新，此处也需同步更新，否则收不到图片
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
	 * 组装音乐消息，注意，音乐回复可能手机端提示播放失败，链接无效
	 * @param toUserName
	 * @param fromUserName
	 * @return
	 */
	public static String initMusicMessage(String toUserName,String fromUserName){
		String message=null;
		Music music=new Music();
		music.setThumbMediaId("U4D1yorSBVDbGmhnkvPLeh6p6Wyq5S0J8MP1vGpa8PtQAom4iK4RJAZUlUjUy105"); 
		//注意，WeixinTest下的MediaId每运行一次，MediaId都会更新，此处也需同步更新，否则收不到图片
		music.setTitle("我是音乐消息标题");
		music.setDescription("我是音乐消息的描述");
		music.setMusicUrl("http://127.0.0.1:8081/weixin/resource/大鱼-周深.mp3");
		music.setHQMusicUrl("http://127.0.0.1:8081/weixin/resource/大鱼-周深.mp3");
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
