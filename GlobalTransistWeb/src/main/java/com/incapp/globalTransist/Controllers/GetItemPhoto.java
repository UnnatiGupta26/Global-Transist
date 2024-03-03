package com.incapp.globalTransist.Controllers;
import com.incapp.globalTransist.Modal.DAO;

import java.io.IOException;
import java.io.InputStream;
import java.sql.SQLException;

import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.servlet.http.Part;



/**
 * Servlet implementation class FileUpload
 */
@WebServlet("/GetItemPhoto")
@MultipartConfig
public class GetItemPhoto extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    
	protected void service(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		try {
			String name=request.getParameter("name");
			DAO db=new DAO();
			byte[] photo=db.getItemPhoto(name);
			db.closeConnection();
			
			response.getOutputStream().write(photo);
		} catch (Exception e) {
			e.printStackTrace();
		} 
		
	}
}
