package com.incapp.globalTransist.Controllers;
import com.incapp.globalTransist.Modal.DAO;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;



/**
 * Servlet implementation class AdminLogin
 */
@WebServlet("/DriverLogin")
public class DriverLogin extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#service(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void service(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		try {
			String name=request.getParameter("name");
			String license=request.getParameter("license");
			DAO db=new DAO();
			String dname=db.driverLogin(name,license);
			//System.out.println(name+"  "+license+" "+dname);
			db.closeConnection();
			HttpSession session=request.getSession();
			if(dname!=null) {
				session.setAttribute("name", dname);
				session.setAttribute("license", license);
				response.sendRedirect("DriverHome.jsp");
			}else {
				session.setAttribute("msg", "Invalid Credentials!");
				response.sendRedirect("Driver.jsp");
			}
		}catch (Exception e) {
			e.printStackTrace();
			response.sendRedirect("ExpPage.jsp");
		}
	}

}
