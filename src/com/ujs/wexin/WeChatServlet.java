package com.ujs.wexin;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.Date;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.dom4j.DocumentException;

import com.ujs.po.AccessToken;
import com.ujs.po.TextMessage;
import com.ujs.util.CheckUtil;
import com.ujs.util.MessageUtil;
import com.ujs.util.WeChatUtil;

/**
 * Servlet implementation class WeChatServlet
 */

/**
 * 微信三方接口
 * @author ChenXu
 *
 */
public class WeChatServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public WeChatServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		System.out.println("进入三方后台");
		String signature=request.getParameter("signature");
		String timestamp=request.getParameter("timestamp");
		String nonce=request.getParameter("nonce");
		String echostr=request.getParameter("echostr");
		
		PrintWriter out=response.getWriter();
		if(CheckUtil.check(signature, timestamp, nonce)){
			out.print(echostr);
		}
	
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		System.out.println("进入消息传递");
		request.setCharacterEncoding("utf-8");
		response.setCharacterEncoding("utf-8");
		String url="https://open.weixin.qq.com/connect/oauth2/authorize?appid=wxcd7b127906205dc7&redirect_uri=http%3a%2f%2f120.27.106.117%2fwechat%2fot.do&response_type=code&scope=snsapi_userinfo&state=1#wechat_redirect";
		
		PrintWriter out=response.getWriter();
		try {
			Map<String, String>  map=MessageUtil.xmlToMap(request);
			String FromUserName=map.get("FromUserName");
			String ToUserName=map.get("ToUserName");
			String Content=map.get("Content");
			String MsgType=map.get("MsgType");
			
			String message=null;
			if("1".equals(Content)){
				message=MessageUtil.initText(FromUserName, ToUserName, MessageUtil.firstMenu());
			}else if("2".equals(Content)){
				message=MessageUtil.initNewsImage(FromUserName, ToUserName);
				//message=MessageUtil.initText(FromUserName, ToUserName, MessageUtil.secondMenu());
			}else if("?".equals(Content)||"？".equals(Content)){
				message=MessageUtil.initText(FromUserName, ToUserName, MessageUtil.menuText());
			}else if(MessageUtil.MESSAGE_EVENT.equals(MsgType)){
				String type=map.get("Event");
				if(MessageUtil.MESSAGE_SUBSCRIBE.equals(type)){
					message=MessageUtil.initText(FromUserName, ToUserName, MessageUtil.menuText());
				}
			}
			else if("3".equals(Content)){
				System.out.println("进入");
				//message=MessageUtil.initLinks(FromUserName, ToUserName, url);
				message=MessageUtil.initText(FromUserName, ToUserName, url);
			}
/*			if(MessageUtil.MESSAGE_TEXT.equals(MsgType)){
				TextMessage textMessage=new TextMessage();
				textMessage.setToUserName(FromUserName);
				textMessage.setFromUserName(ToUserName);
				textMessage.setCreateTime(new Date().getTime());
				textMessage.setMsgType("text");
				textMessage.setContent("你是："+Content);
				message=MessageUtil.textMessageToXml(textMessage);
			}*/
			
		
			System.out.println("From: "+FromUserName);
			System.out.println(message);
			out.print(message);
			//System.out.println(ToUserName);
			AccessToken token=WeChatUtil.getAccessToken();
			WeChatUtil.getUserInfo(token.getAccess_token(),FromUserName);
		} catch (DocumentException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}finally {
			out.close();
		}
		
		doGet(request, response);
	}

}
