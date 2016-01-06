package com.ujs.wexin;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.sun.org.apache.xml.internal.serialize.Printer;
import com.ujs.dao.DbUtil;
import com.ujs.po.PrizeInfo;

import sun.print.PrinterJobWrapper;

/**
 * Servlet implementation class HonBaoServlet
 */

public class HonBaoServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public HonBaoServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		System.out.println("进入抢红包");
		
		//是否得到红包
		Boolean isGet=false;
		int isget=0;
		//session
		HttpSession sesssion=request.getSession();
		Double count=Math.random()*10;
		PrintWriter out=response.getWriter();
		if(count>5){
			isGet=true;   //得到红包
			sesssion.setAttribute("isGet", isGet);
			isget=1;
			out.print(isGet);
		}
		else{
			isGet=false;
			isget=0;
			sesssion.setAttribute("isGet", isGet);
			out.print(isGet);
		}
		String openId=(String)request.getSession().getAttribute("openId");
		PrizeInfo prizeInfo=new PrizeInfo();
		prizeInfo.setIsGet(isget);
		prizeInfo.setOpenId(openId);
		
		//插入得奖信息
		DbUtil.insertPrizeInfo(prizeInfo);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}
