package com.ujs.wexin;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.ujs.dao.DbUtil;
import com.ujs.po.PrizeInfo;

import sun.dc.pr.PRError;

/**
 * Servlet implementation class IsGetHb
 */

public class IsGetHb extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public IsGetHb() {
		super();
		// TODO Auto-generated constructor stub
	}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		// TODO Auto-generated method stub

	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		// TODO Auto-generated method stub
		System.out.println("进入判断前台后台数据是否一致");

		PrintWriter out = response.getWriter();
		Boolean isGet = (Boolean) request.getSession().getAttribute("isGet");
		
		System.out.println("中奖与否:" + isGet);

		if (isGet != null && isGet) {
			request.getSession().removeAttribute("isGet");
		
			out.print(true);
		} 
		else {
			System.out.println("未中奖");
	
			out.print(false);
		}
		
		
		doGet(request, response);
	}

}
