package com.ujs.test;

import java.util.Map;

import com.ujs.po.AccessToken;
import com.ujs.util.MessageUtil;
import com.ujs.util.WeChatUtil;

public class WechatTest {
	public static void main(String[] args) {
		System.out.println("测试");
		AccessToken token=WeChatUtil.getAccessToken();
		System.out.println("票据: "+token.getAccess_token());
		System.out.println("时间："+token.getExpires_in());
	
	}
	
	
	
}
