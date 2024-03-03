package com.incapp.globalTransist.Controllers;
import com.incapp.globalTransist.Modal.DAO;
import java.io.IOException;
import java.io.InputStream;

import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.servlet.http.Part;



/**
 * Servlet implementation class AdminLogin
 */
@WebServlet("/AddDriver")
@MultipartConfig
public class AddDriver extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#service(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void service(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		try {
			String name=request.getParameter("name");
			String phone=request.getParameter("phone");
			String lic=request.getParameter("driving_license");
			String address=request.getParameter("address");
			
			Part p=request.getPart("photo");
			InputStream photo=p.getInputStream();
			
			DAO db=new DAO();
			//System.out.print(name+phone+lic+address);
			boolean result=db.addDriver(name, phone,lic,address, photo);
			db.closeConnection();
			HttpSession session=request.getSession();
			if(result) {
				session.setAttribute("msg", "Item Successfully Added!");
				response.sendRedirect("AddDrivers.jsp");
			}else {
				session.setAttribute("msg", "Item Already Exist!");
				response.sendRedirect("AddDrivers.jsp");
			}
		}catch (Exception e) {
			e.printStackTrace();
			response.sendRedirect("ExpPage.jsp");
		}
	}

}
