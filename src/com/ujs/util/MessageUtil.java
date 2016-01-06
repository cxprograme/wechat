package com.ujs.util;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;

import com.thoughtworks.xstream.XStream;
import com.ujs.po.Links;
import com.ujs.po.News;
import com.ujs.po.NewsMessage;
import com.ujs.po.TextMessage;



/**
 * 数据格式转换接口类
 * @author ChenXu
 *
 */
public class MessageUtil {
	
	public static final String MESSAGE_TEXT="text";
	public static final String MESSAGE_NEWS="news";
	public static final String MESSAGE_IMAGE="image";
	public static final String MESSAGE_VOICE="voice";
	public static final String MESSAGE_VIDEO="video";
	public static final String MESSAGE_SHORTVIDEO="shortvideo";
	public static final String MESSAGE_LOCATION="location";
	public static final String MESSAGE_LINK="link";
	public static final String MESSAGE_EVENT="event";
	public static final String MESSAGE_SUBSCRIBE="subscribe";
	public static final String MESSAGE_SCAN="SCAN";
	public static final String MESSAGE_CLICK="CLICK";
	public static final String MESSAGE_VIEW="VIEW";
	
	/**
	 * 将xml转换为集合数据类型
	 * @param request
	 * @return
	 * @throws IOException
	 * @throws DocumentException
	 */
	public static Map<String, String>  xmlToMap(HttpServletRequest request) throws IOException, DocumentException{
		Map<String, String> map=new HashMap<String, String>();
		SAXReader reader=new SAXReader();
		
		InputStream ins=request.getInputStream();
		Document doc=reader.read(ins);
		
		Element root=doc.getRootElement();
		
		List<Element> element=root.elements();
		
		for(Element e:element){
			map.put(e.getName(), e.getText());
		}
		
		return map;
	}
	
	
	/**
	 * 将文本消息转换为xml
	 * @param newsMessage
	 * @return
	 */
	public static String textMessageToXml(TextMessage textMessage){
		XStream xStream=new XStream();
		xStream.alias("xml", textMessage.getClass());
		return xStream.toXML(textMessage);
	}
	
	public static String firstMenu(){
		StringBuffer str=new StringBuffer();
		str.append("杰哥就是个笑话");

		return str.toString();
	}
	
	
	public static String secondMenu(){
		StringBuffer str=new StringBuffer();
		str.append("笑话是杰哥");

		return str.toString();
	}
	/**
	 * 将文本消息转换为xml格式
	 * @param fromUserName
	 * @param toUserName
	 * @param content
	 * @return
	 */
	public static String initText(String fromUserName,String toUserName,String content){
		TextMessage textMessage=new TextMessage();
		textMessage.setToUserName(fromUserName);
		textMessage.setFromUserName(toUserName);
		textMessage.setCreateTime(new Date().getTime());
		textMessage.setMsgType(MESSAGE_TEXT);
		textMessage.setContent(content);
		return textMessageToXml(textMessage);
	}
	
	public static String  getOpenID(String toUserName){
		String openId=toUserName;
		
		System.out.println("openId: "+openId);
		return openId;
	}
	
	/**
	 * 关注回复菜单
	 * @return
	 */
	public static String menuText(){
		StringBuffer str=new StringBuffer();
		str.append("欢迎进入码农小二,请根据提示菜单进行操作:\n\n");
		str.append("1.笑话 \n");
		str.append("2.杰哥 \n\n");
		str.append("回复?,调出此菜单");
		return str.toString();
		
	}
	
	/**
	 * 将图文消息转化为xml
	 * @param newsMessage
	 * @return
	 */
	public static String newsMessageToXml(NewsMessage newsMessage){
		XStream xStream=new XStream();
		xStream.alias("xml", new NewsMessage().getClass());
		xStream.alias("item", new News().getClass());
		return xStream.toXML(newsMessage);
	}
	
	
	public static String initNewsImage(String fromUserName,String toUserName){
		
		List<News> nlists=new ArrayList<News>();
		News news=new News();
		news.setTitle("张艺兴");
		news.setDescription("张艺兴，艺名LAY，1991年10月7日出生于中国湖南省长沙市，歌手，演员，男子演唱组合EXO/EXO-M中国籍成员。2005年张艺兴参加湖南经视《明星学院》比赛获得总决赛季军。");
		news.setPicUrl("http://cxprogram1206.imwork.net/wechat/images/zyx.jpg");
		news.setUrl("http://baike.baidu.com/link?url=PfkMPD4PN2U96MoppelWEMmH8a6PAsh2md1-d50IhCmVN1ZSpuQPvUSOzh74NcJycAi6mkdQlexE4RquXCM_5K");
		nlists.add(news);
		
		NewsMessage newsMessage=new NewsMessage();
		
		newsMessage.setToUserName(fromUserName);
		newsMessage.setFromUserName(toUserName);
		newsMessage.setCreateTime(new Date().getTime());
		newsMessage.setMsgType(MESSAGE_NEWS);
		newsMessage.setArticleCount(nlists.size());
		newsMessage.setArticle(nlists);
		return newsMessageToXml(newsMessage);
	}
	
	
	/**
	 * 链接消息转换为xml
	 * @param links
	 * @return
	 */
	public static String linkToXml(Links links){
		XStream xStream=new XStream();
		xStream.alias("xml",new Links().getClass());
		return xStream.toXML(links);
	}
	
	/**
	 * 设置链接消息内容
	 * @param fromUserName
	 * @param toUserName
	 * @param url
	 * @return
	 */
	public static String initLinks(String fromUserName,String toUserName,String url){
		Links links=new Links();
		links.setToUserName(fromUserName);
		links.setFromUserName(toUserName);
		links.setCreateTime(new Date().getTime());
		links.setMsgType(MESSAGE_LINK);
		links.setTitle("授权");
		links.setDescription("摇一摇授权页面");
		links.setUrl(url);
		return linkToXml(links);
	}
}
