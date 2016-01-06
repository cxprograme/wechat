package com.ujs.util;

import java.io.IOException;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;

import com.ujs.po.AccessToken;
import com.ujs.po.UserInfo;
import com.ujs.po.WeChatOauth2Token;

import net.sf.json.JSONObject;

@SuppressWarnings("deprecation")
public class WeChatUtil {
	private static final String APPID = "wxcd7b127906205dc7";
	private static final String APPSECRET = "d4624c36b6795d1d99dcf0547af5443d";

	// 获取access_tokend的url
	private static final String ACCESS_TOKEN_URL = "https://api.weixin.qq.com/cgi-bin/token?grant_type=client_credential&appid=APPID&secret=APPSECRET";

	// 获取用户信息的url
	private static final String USER_INFO_URL = "https://api.weixin.qq.com/cgi-bin/user/info?access_token=ACCESS_TOKEN&openid=OPENID&lang=zh_CN";

	// 获取网页授权access_token的url
	private static final String Oauth2_ACCESS_TOKEN_URL = "https://api.weixin.qq.com/sns/oauth2/access_token?appid=APPID&secret=SECRET&code=CODE&grant_type=authorization_code";

	// 通过网页授权获得用户信息的url
	private static final String Oauth2_USER_INFO_URL = "https://api.weixin.qq.com/sns/userinfo?access_token=ACCESS_TOKEN&openid=OPENID";

	private static final String CHECK_TOKEN_URL = "https://api.weixin.qq.com/sns/auth?access_token=ACCESS_TOKEN&openid=OPENID";

	public static JSONObject doGetStr(String url) {

		DefaultHttpClient httpClient = new DefaultHttpClient();
		HttpGet httpGet = new HttpGet(url);
		JSONObject jsonObject = null;

		try {
			HttpResponse response = httpClient.execute(httpGet);
			HttpEntity entity = response.getEntity();
			if (entity != null) {
				String result = EntityUtils.toString(entity, "UTF-8");
				jsonObject = JSONObject.fromObject(result);
			}
		} catch (ClientProtocolException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return jsonObject;

	}

	/**
	 * 获取普通的access_token
	 * 
	 * @return
	 */
	public static AccessToken getAccessToken() {
		AccessToken accessToken = new AccessToken();
		String url = ACCESS_TOKEN_URL.replace("APPID", APPID).replace("APPSECRET", APPSECRET);

		JSONObject jsonObject = doGetStr(url);
		System.out.println(jsonObject);
		if (jsonObject != null) {
			accessToken.setAccess_token(jsonObject.getString("access_token"));
			accessToken.setExpires_in(jsonObject.getString("expires_in"));
		}
		return accessToken;
	}

	public static JSONObject doPostStr(String url, String outStr) {
		DefaultHttpClient httpClient = new DefaultHttpClient();
		HttpPost httpPost = new HttpPost();
		JSONObject jsonObject = null;

		try {
			httpPost.setEntity(new StringEntity(outStr, "UTF-8"));
			HttpResponse response = httpClient.execute(httpPost);
			HttpEntity entity = response.getEntity();
			if (entity != null) {
				String result = EntityUtils.toString(entity, "UTF-8");
				jsonObject = JSONObject.fromObject(result);

			}
		} catch (ClientProtocolException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return jsonObject;
	}

	/**
	 * 通过第一种方式获得用户信息
	 * 
	 * @param accessToken
	 * @param openId
	 * @return
	 */
	public static String getUserInfo(String accessToken, String openId) {
		String result = null;
		String requestUrl = USER_INFO_URL;
		requestUrl = requestUrl.replace("ACCESS_TOKEN", accessToken).replace("OPENID", openId);
		JSONObject jsonObject = doGetStr(requestUrl);
		if (null != jsonObject) {
			System.out.println(jsonObject);

			result = jsonObject.get("data") + "";
		}
		return result;
	}

	/**
	 * 获得网页授权的access_token
	 * 
	 * @return
	 */
	public static WeChatOauth2Token getOauth2Token(String code) {
		WeChatOauth2Token wt = new WeChatOauth2Token();
		String url = Oauth2_ACCESS_TOKEN_URL.replace("APPID", APPID).replace("SECRET", APPSECRET).replace("CODE", code);
		JSONObject jsonObject = doGetStr(url);
		if (null != jsonObject) {
		
			wt.setAccessToken(jsonObject.getString("access_token"));
			wt.setExpiresIn(jsonObject.getInt("expires_in"));
			wt.setRefreshToken(jsonObject.getString("refresh_token"));
			wt.setOpenId(jsonObject.getString("openid"));
			wt.setScope(jsonObject.getString("scope"));
		}
		System.out.println(wt);
		return wt;
	}

	public static UserInfo getOauth2UserInfo(String accessToken, String openId) {

		UserInfo userInfo = new UserInfo();
		String url = Oauth2_USER_INFO_URL.replace("ACCESS_TOKEN", accessToken).replace("OPENID", openId);
		JSONObject json = doGetStr(url);
		if (null != json) {
			System.out.println("json  " + json);
			userInfo.setOpenid(json.getString("openid"));
			userInfo.setNickname(json.getString("nickname"));
			userInfo.setSex(json.getInt("sex"));
			userInfo.setProvince(json.getString("province"));
			userInfo.setCity(json.getString("city"));
			userInfo.setCountry(json.getString("country"));
			userInfo.setHeadimgurl(json.getString("headimgurl"));
			userInfo.setPrivilege(json.getString("privilege"));
			//userInfo.setUnionid(json.getString("unionid"));
		}

		System.out.println("user: " + userInfo);
		return userInfo;

		/*
		 * String result = null; String requestUrl = Oauth2_USER_INFO_URL;
		 * requestUrl = requestUrl.replace("ACCESS_TOKEN",
		 * accessToken).replace("OPENID", openId); JSONObject jsonObject =
		 * doGetStr(requestUrl); if (null != jsonObject) {
		 * System.out.println(jsonObject);
		 * 
		 * result = jsonObject.get("data") + ""; } return result;
		 */
	}

	public static JSONObject checkToken(String accessToken, String openId) {
		String url = CHECK_TOKEN_URL.replace("ACCESS_TOKEN", accessToken).replace("OPENID", openId);
		JSONObject jsonObject = doGetStr(url);
		System.out.println("token是否有效" + jsonObject);
		return jsonObject;
	}
}
