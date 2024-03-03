package com.incapp.globalTransist.Controllers;
import com.incapp.globalTransist.Modal.DAO;

import java.io.IOException;
import java.io.InputStream;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.text.DateFormat;
import java.text.SimpleDateFormat;

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
@WebServlet("/UpdateVehicle")
@MultipartConfig
public class UpdateVehicle extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#service(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void service(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		try {
			//int id=Integer.parseInt(request.getParameter("id"));
			String vehicle_name=request.getParameter("vehicle_name");
			String driver_name=request.getParameter("driver_name");
			String source_address=request.getParameter("source_address");
			String destination_address=request.getParameter("destination_address");
			String journey_start_date=request.getParameter("journey_start_date");
			String journey_end_date=request.getParameter("journey_end_date");
			DAO db=new DAO();
			
			String driver_license_number = db.licNumber(driver_name);
			System.out.println(journey_start_date);
			
			//Date dateFormatted = dateFormat.parse(journey_start_date);
		    db.updateVehicledriver(driver_name,vehicle_name,source_address,destination_address,journey_start_date,journey_end_date,driver_license_number);
			//db.updateDrivers(journey_start_date, journey_end_date, vehicle_driver);
		    db.closeConnection();
			HttpSession session=request.getSession();
			session.setAttribute("msg", "Updated Successfully!");
			response.sendRedirect("AllocateVehicle.jsp");
		}catch (Exception e) {
			e.printStackTrace();
			response.sendRedirect("ExpPage.jsp");
		}
	}

}
