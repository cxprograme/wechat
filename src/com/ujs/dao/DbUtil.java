package com.ujs.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.Date;

import com.ujs.po.PrizeInfo;
import com.ujs.po.UserInfo;

/**
 * 数据操作（增删改查）
 * 
 * @author ChenXu
 *
 */
public class DbUtil {

	private static boolean fasle;

	/**
	 * 插入用户信息
	 * 
	 * @param user
	 * @return
	 */
	public static int insertUserInfo(UserInfo user) {
		System.out.println("进入插入操作");
		//获取用户的openid
		String openId=user.getOpenid();
		//与数据库中的进行比对
		int i = 0;
		Boolean isHave=getUserInfoById(openId);
		if(!isHave){
			
			// 获取连接
			Connection conn = DBConnection.getConnection();
			
			String sql = "insert into user_info (openid,nickname,sex,province,city,country,headimgurl,privilege) "
					+ "values(?,?,?,?,?,?,?,?)";
			System.out.println("sql:" + sql);
			PreparedStatement pstmt = null;
			
			try {
				pstmt = conn.prepareStatement(sql);
				pstmt.setString(1, user.getOpenid());
				pstmt.setString(2, user.getNickname());
				pstmt.setInt(3, user.getSex());
				pstmt.setString(4, user.getProvince());
				pstmt.setString(5, user.getCity());
				pstmt.setString(6, user.getCountry());
				pstmt.setString(7, user.getHeadimgurl());
				pstmt.setString(8, user.getPrivilege());
				i = pstmt.executeUpdate();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} finally {
				try {
					pstmt.close();
					conn.close();
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				
			}
			
		}
		System.out.println("插入记录：" + i);
		return i;

	}

	/**
	 * 插入用户得奖信息
	 * 
	 * @param prizeInfo
	 * @return
	 */
	public static int insertPrizeInfo(PrizeInfo prizeInfo) {
		Date time = new Date(System.currentTimeMillis());
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		String current = sdf.format(time);

		Connection conn = DBConnection.getConnection();
		String sql = "insert into prize_info (openid,isget,time) values(?,?,?)";
		int i = 0;
		PreparedStatement pstmt = null;

		try {
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, prizeInfo.getOpenId());
			pstmt.setInt(2, prizeInfo.getIsGet());
			pstmt.setString(3, current);
			i = pstmt.executeUpdate();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			try {
				pstmt.close();
				conn.close();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		System.out.println("得奖记录：" + i);
		return i;
	}

	public static boolean getUserInfoById(String openId) {
		Connection conn = DBConnection.getConnection();
		boolean result = fasle;
		String sql = "select * from user_info where openid='" + openId + "'";
		System.out.println("sql:" + sql);
		PreparedStatement pstmt = null;
		String openid = null;
		try {
			pstmt = conn.prepareStatement(sql);
			ResultSet rs = pstmt.executeQuery();
			while (rs.next()) {
				openid = rs.getString("openid");
				result = true;
			}

		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {

		}
		System.out.println("openid:" + result);
		return result;
	}

/*	public static void main(String[] args) {
		getUserInfoById("oWZC8wqaSfH-TTqzfWuBSte-5TiI");
	}*/
}
