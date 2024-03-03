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
@WebServlet("/AddItem")
@MultipartConfig
public class AddItem extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#service(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void service(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		try {
			String name=request.getParameter("name");
			int price=Integer.parseInt(request.getParameter("price"));
			
			String vehicle_number=request.getParameter("vehicle_number");
			String category=request.getParameter("category");
			Part p=request.getPart("photo");
			InputStream photo=p.getInputStream();
			
			DAO db=new DAO();
			boolean result=db.addItem(name,price,vehicle_number,category, photo);
			
			db.closeConnection();
			HttpSession session=request.getSession();
			if(result) {
				session.setAttribute("msg", "Item Successfully Added!");
				response.sendRedirect("AdminHome.jsp");
			}else {
				session.setAttribute("msg", "Item Already Exist!");
				response.sendRedirect("AdminHome.jsp");
			}
		}catch (Exception e) {
			e.printStackTrace();
			response.sendRedirect("ExpPage.jsp");
		}
	}

}
