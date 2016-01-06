package com.ujs.dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 * 连接数据库
 * @author ChenXu
 *
 */
public class DBConnection {
    	private static final String JDBC_DRIVER="com.mysql.jdbc.Driver";
    	private static final String DB_URL="jdbc:mysql://120.27.106.117/wechat";
	
		private static final String USERNAEM="yuancheng";
		private static final String PASSWORD="123456";
		private static Connection conn=null;
		public static Connection getConnection(){
			try {
				//注册jdbc驱动
				Class.forName(JDBC_DRIVER);
				
				conn=DriverManager.getConnection(DB_URL,USERNAEM,PASSWORD);
				System.out.println("连接成功");
			} catch (ClassNotFoundException | SQLException e) {
				// TODO Auto-generated catch block
				System.out.println("连接不成功");
				e.printStackTrace();
			}finally {
			}
				
			
			return conn;
		}
		
		public static void main(String[] args){
			getConnection();
		}
}
