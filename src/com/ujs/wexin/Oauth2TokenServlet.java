package com.ujs.wexin;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.ujs.dao.DbUtil;
import com.ujs.po.UserInfo;
import com.ujs.po.WeChatOauth2Token;
import com.ujs.util.WeChatUtil;


/**
 * Servlet implementation class Oauth2TokenServlet
 */

public class Oauth2TokenServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public Oauth2TokenServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		
		String code=request.getParameter("code");
		
		WeChatOauth2Token wt=WeChatUtil.getOauth2Token(code);
		
		//验证token是否过期
		WeChatUtil.checkToken(wt.getAccessToken(), wt.getOpenId());
		
		//获取用户信息
		UserInfo snsUserInfo=WeChatUtil.getOauth2UserInfo(wt.getAccessToken(), wt.getOpenId());
		
		//将用户openId放入session中
		request.getSession().setAttribute("openId", snsUserInfo.getOpenid());
		
		// 设置要传递的参数
		request.setAttribute("snsUserInfo", snsUserInfo);
		//将用户信息存入数据库
		DbUtil.insertUserInfo(snsUserInfo);
		request.getRequestDispatcher("/shake.html").forward(request, response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}
