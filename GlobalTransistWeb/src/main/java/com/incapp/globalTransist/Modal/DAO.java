package com.incapp.globalTransist.Modal;

import java.io.InputStream;
import java.util.Date;
import java.sql.*;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;


public class DAO {
	private Connection c;
	public DAO() throws ClassNotFoundException, SQLException {
		Class.forName("com.mysql.cj.jdbc.Driver");
		c=DriverManager.getConnection("jdbc:mysql://localhost:3306/globaltransist_servers","root","unnati");
	}
	public void closeConnection() throws SQLException{
		c.close();
	}
	public String userLogin(String email,String password) throws SQLException{
		PreparedStatement p=c.prepareStatement("select * from users where email=? and password=?");
		p.setString(1, email);
		p.setString(2, password);
		ResultSet rs=p.executeQuery();
		if(rs.next()) {
			return rs.getString("name");
		}else {
			return null;
		}
	}
	public String driverLogin(String name,String license) throws SQLException{
		PreparedStatement p=c.prepareStatement("select * from drivers where name=? and lic=?");
		p.setString(1, name);
		p.setString(2, license);
		ResultSet rs=p.executeQuery();
		if(rs.next()) {
			System.out.println("ho");
			return rs.getString("name");
		}else {
			return null;
		}
	}
	public String adminLogin(String id,String password) throws SQLException{
		PreparedStatement p=c.prepareStatement("select * from admin where id=? and password=?");
		p.setString(1, id);
		p.setString(2, password);
		ResultSet rs=p.executeQuery();
		if(rs.next()) {
			return rs.getString("name");
		}else {
			return null;
		}
	}
	public boolean registerUser(HashMap<String,Object> user) throws SQLException{
		PreparedStatement p=c.prepareStatement("update users set name=?,phone=?,address=?,password=?,photo=?,status='Activated' where email=?");
		p.setString(1, (String)user.get("name"));
		p.setString(2, (String)user.get("phone"));
		p.setString(3, (String)user.get("address"));
		p.setString(4, (String)user.get("password"));
		p.setBinaryStream(5,(InputStream)user.get("photo"));
		p.setString(6, (String)user.get("email"));
		int x=p.executeUpdate();
		if(x!=0) {
			p=c.prepareStatement("insert into addresses (email,address) values (?,?)");
			p.setString(1, (String)user.get("email"));
			p.setString(2, (String)user.get("address"));
			p.executeUpdate();
			return true;
		}
		else
			return false;
	}
	public int sendOTPUser(String email) throws SQLException{
		try {
			//Generate 4 digit random number
			int otp=(int)(Math.random()*9000 +1000);
			
			
			//mail send
			String sub="OTP from Global Transist";
			String body="Your OTP is "+otp;
			SendMail.sendMail(email, sub, body);
			//mail code ends
			
			PreparedStatement p=c.prepareStatement("insert into users (email,otp,status) values (?,?,'Pending')");
			p.setString(1, email);
			p.setInt(2,otp);
			p.executeUpdate();
			return otp;
		}catch (SQLIntegrityConstraintViolationException e) {
			return 0;
		}
	}
	public void addToCart(String uemail,String item_name) throws SQLException{
		PreparedStatement p=c.prepareStatement("select * from cart where email=?");
		p.setString(1, uemail);
		ResultSet rs=p.executeQuery();
		if(rs.next()) {
			String i=rs.getString("items");
			p=c.prepareStatement("update cart set items=? where email=?");
			p.setString(1, i+","+item_name);
			p.setString(2, uemail);
			p.executeUpdate();
		}else {
			p=c.prepareStatement("insert into cart (email,items) values (?,?)");
			p.setString(1, uemail);
			p.setString(2, item_name);
			p.executeUpdate();
		}
	}
	public void orderPlaced(String uemail,String address) throws SQLException{
		String[] items=getAllCartItemsByEmail(uemail);
		String orderItems="";
		int total=0;
		for(String item:items){
			int price=getItemPrice(item);
			total+=price;
			orderItems += ","+item+"-"+price;
		}
		orderItems=orderItems.substring(1,orderItems.length());
		PreparedStatement p=c.prepareStatement("insert into orders (email,address,total,items,order_date,status) values (?,?,?,?,CURRENT_DATE,'Pending')");
		p.setString(1, uemail);
		p.setString(2, address);
		p.setInt(3, total);
		p.setString(4, orderItems);
		p.executeUpdate();
		p=c.prepareStatement("delete from cart where email=?");
		p.setString(1, uemail);
		p.executeUpdate();
	}
	public void addAddress(String uemail,String address) throws SQLException{
		PreparedStatement p=c.prepareStatement("insert into addresses (email,address) values (?,?)");
		p.setString(1, uemail);
		p.setString(2, address);
		p.executeUpdate();
	}
	public void removeFromCart(String uemail,String item_name) throws SQLException{
		PreparedStatement p=c.prepareStatement("select * from cart where email=?");
		p.setString(1, uemail);
		ResultSet rs=p.executeQuery();
		if(rs.next()) {
			String items=rs.getString("items");
			String i[]=items.split(",");
			ArrayList<String> newItems=new ArrayList<>();
			for (int x=0;x<i.length;x++) {
				newItems.add(i[x]);
			}
			newItems.remove(item_name);
			items="";
			for (String string : newItems) {
				items+=","+string;
			}
			items=items.substring(1, items.length());
			p=c.prepareStatement("update cart set items=? where email=?");
			p.setString(1, items);
			p.setString(2, uemail);
			p.executeUpdate();
		}
	}
	public int countCart(String uemail) throws SQLException{
		PreparedStatement p=c.prepareStatement("select * from cart where email=?");
		p.setString(1, uemail);
		ResultSet rs=p.executeQuery();
		if(rs.next()) {
			String items=rs.getString("items");
			String i[]=items.split(",");
			return i.length;
		}else {
			return 0;
		}
	}
	public String[] getAllCartItemsByEmail(String uemail) throws SQLException{
		PreparedStatement p=c.prepareStatement("select * from cart where email=?");
		p.setString(1, uemail);
		ResultSet rs=p.executeQuery();
		if(rs.next()) {
			String all_items=rs.getString("items");
			String i[]=all_items.split(",");
			return i;
		}else {
			return null;
		}
	}
	public int getItemPrice(String item_name) throws SQLException{
		PreparedStatement p=c.prepareStatement("select price from items where name=?");
		p.setString(1, item_name);
		ResultSet rs=p.executeQuery();
		if(rs.next()) {
			return rs.getInt("price");
		}else {
			return 0;
		}
	}
	public boolean verifyOTPUser(String email,int otp) throws SQLException{
		PreparedStatement p=c.prepareStatement("select * from users where email=? and otp=?");
		p.setString(1, email);
		p.setInt(2, otp);
		ResultSet rs=p.executeQuery();
		if(rs.next())
			return true;
		else
			return false;
	}
	public boolean addItem(String name,int price,String vehicle_number,String category, InputStream photo) throws SQLException{
		try {
			PreparedStatement p=c.prepareStatement("insert into items(name,price,vehicle_number,category,photo) values (?,?,?,?,?)");
			p.setString(1, name);
			p.setInt(2, price);
			p.setString(3,vehicle_number);
			p.setString(4,category);
			p.setBinaryStream(5, photo);
			p.executeUpdate();
			return true;
		}catch (SQLIntegrityConstraintViolationException e) {
			e.printStackTrace();
			return false;
		}
	}
	public boolean addDriver(String name,String phone,String lic,String address, InputStream photo) throws SQLException{
		try {
			PreparedStatement p=c.prepareStatement("insert into drivers(name,phone,lic,address,photo,source_date,destination_date) values (?,?,?,?,?,'2000-01-26','2000-01-26')");
			p.setString(1, name);
			p.setString(2, phone);
			p.setString(3, lic);
			p.setString(4, address);
			p.setBinaryStream(5, photo);
			
			p.executeUpdate();
			return true;
		}catch (SQLIntegrityConstraintViolationException e) {
			return false;
		}
	}
	public void deleteItem(String name) throws SQLException{
		PreparedStatement p=c.prepareStatement("delete from items where name=?");
		p.setString(1, name);
		p.executeUpdate();
	}
	public void deleteDriver(String name) throws SQLException{
		PreparedStatement p=c.prepareStatement("delete from drivers where name=?");
		p.setString(1, name);
		p.executeUpdate();
	}
	public void changeOrderStatus(int id,String status) throws SQLException{
		PreparedStatement p=c.prepareStatement("update  orders set status=? where id=?");
		p.setString(1, status);
		p.setInt(2, id);
		p.executeUpdate();
	}
	public void updateEnquiryStatus(int id) throws SQLException{
		PreparedStatement p=c.prepareStatement("update  enquiry set status='Done' where id=?");
		p.setInt(1, id);
		p.executeUpdate();
	}
	public void addEnquiry(String name,String phone) throws SQLException{
		PreparedStatement p=c.prepareStatement("insert into enquiry (name,phone,status,enquiry_date) values (?,?,'Pending',CURRENT_DATE)");
		p.setString(1, name);
		p.setString(2, phone);
		p.executeUpdate();
	}
	public ArrayList<HashMap> getAllItems() throws SQLException{
		PreparedStatement p=c.prepareStatement("select * from items");
		ResultSet rs=p.executeQuery();
		ArrayList<HashMap> items=new ArrayList<>();
		while(rs.next()) {
			HashMap item=new HashMap();
			item.put("name", rs.getString("name"));
			item.put("price", rs.getInt("price"));
			items.add(item);
		}
		return items;
	}
	public ArrayList<HashMap> getAllDrivers() throws SQLException{
		PreparedStatement p=c.prepareStatement("select * from drivers");
		ResultSet rs=p.executeQuery();
		ArrayList<HashMap> items=new ArrayList<>();
		while(rs.next()) {
			HashMap item=new HashMap();
			item.put("name", rs.getString("name"));
			item.put("phone", rs.getString("phone"));
			item.put("license", rs.getString("lic"));
			item.put("address", rs.getString("address"));
			item.put("source date",rs.getString("source_date"));
			item.put("destination date", rs.getString("destination_date"));
			//System.out.println(rs.getString("source_date"));
			items.add(item);
		}
		return items;
	}
	public ArrayList<HashMap> getOrdersByEmail(String email) throws SQLException{
		PreparedStatement p=c.prepareStatement("select * from orders where email=? order by order_date DESC");
		p.setString(1, email);
		ResultSet rs=p.executeQuery();
		ArrayList<HashMap> orders=new ArrayList<>();
		while(rs.next()) {
			HashMap order=new HashMap();
			order.put("id", rs.getInt("id"));
			order.put("address", rs.getString("address"));
			order.put("items", rs.getString("items"));
			order.put("total", rs.getInt("total"));
			order.put("status", rs.getString("status"));
			order.put("order_date", rs.getString("order_date"));
			orders.add(order);
		}
		return orders;
	}
	
	public ArrayList<HashMap> getOrdersByName(String name, String license) throws SQLException{
		PreparedStatement p=c.prepareStatement("select * from allocatedriver where driver_name=? and driver_license_number=?");
		p.setString(1, name);
		p.setString(2, license);
		ResultSet rs=p.executeQuery();
		
		ArrayList<HashMap> orders=new ArrayList<>();
		while(rs.next()) {
			System.out.println("jin");
			HashMap order=new HashMap();
			order.put("id", rs.getInt("id"));
			order.put("vehicle_name", rs.getString("vehicle_name"));
			order.put("source_address", rs.getString("source_address"));
			order.put("journey_start_date", rs.getString("journey_start_date"));
			order.put("destination_address", rs.getString("destination_address"));
			order.put("journey_end_date", rs.getString("journey_end_date"));
			System.out.println( rs.getInt("id"));
			orders.add(order);
		}
		return orders;
	}
	public ArrayList<HashMap> getOrders() throws SQLException{
		PreparedStatement p=c.prepareStatement("select * from orders order by order_date DESC");
		ResultSet rs=p.executeQuery();
		ArrayList<HashMap> orders=new ArrayList<>();
		while(rs.next()) {
			HashMap order=new HashMap();
			order.put("id", rs.getInt("id"));
			order.put("email", rs.getString("email"));
			order.put("address", rs.getString("address"));
			order.put("items", rs.getString("items"));
			order.put("total", rs.getInt("total"));
			order.put("status", rs.getString("status"));
			order.put("order_date", rs.getString("order_date"));
			orders.add(order);
		}
		return orders;
	}
	public ArrayList<HashMap> getAllEnquiry() throws SQLException{
		PreparedStatement p=c.prepareStatement("select * from enquiry order by enquiry_date DESC");
		ResultSet rs=p.executeQuery();
		ArrayList<HashMap> enquiries=new ArrayList<>();
		while(rs.next()) {
			HashMap enquiry=new HashMap();
			enquiry.put("id", rs.getInt("id"));
			enquiry.put("name", rs.getString("name"));
			enquiry.put("phone", rs.getString("phone"));
			enquiry.put("status", rs.getString("status"));
			enquiry.put("enquiry_date", rs.getString("enquiry_date"));
			enquiries.add(enquiry);
		}
		return enquiries;
	}
	public ArrayList<HashMap> getItemsLikeName(String name) throws SQLException{
		PreparedStatement p=c.prepareStatement("select * from items where name like ?");
		p.setString(1,"%"+name+"%");
		ResultSet rs=p.executeQuery();
		ArrayList<HashMap> items=new ArrayList<>();
		while(rs.next()) {
			HashMap item=new HashMap();
			item.put("name", rs.getString("name"));
			item.put("price", rs.getInt("price"));
			items.add(item);
		}
		return items;
	}
	public ArrayList<String> getAddressByEmail(String email) throws SQLException {
		PreparedStatement p=c.prepareStatement("select address from addresses where email=?");
		p.setString(1, email);
		ResultSet rs=p.executeQuery();
		ArrayList<String> addresses=new ArrayList<>();
		while(rs.next()) {
			addresses.add(rs.getString("address"));
		}
		return addresses;
	}
	
	public void updateVehicledriver(String driver_name,String vehicle_name,String source_address,String destination_address,String journey_start_date,String journey_end_date,String driver_license_number)throws SQLException
	{
		//SimpleDateFormat format = new SimpleDateFormat("YYYY-MM-DD");
		//Date parsed_sdate = format.parse(journey_start_date);
		
		PreparedStatement p=c.prepareStatement("insert into allocatedriver(driver_name,vehicle_name,source_address,destination_address,journey_start_date,journey_end_date,driver_license_number) values (?,?,?,?,?,?,?)");
		p.setString(1, driver_name);
		p.setString(2, vehicle_name);
		p.setString(3, source_address);
		p.setString(4, destination_address);
		p.setString(5, journey_start_date);
		p.setString(6,  journey_end_date);
		p.setString(7, driver_license_number);
		p.executeUpdate();
        p=c.prepareStatement("update drivers set source_date= ? , destination_date=? where name=?");
		
		p.setString(1, journey_start_date);
		p.setString(2,journey_end_date);
		p.setString(3, driver_name);
		p.executeUpdate();
		
		
		
	}
	
	public byte[] getItemPhoto(String name) throws SQLException {
		PreparedStatement p=c.prepareStatement("select photo from items where name=?");
		p.setString(1, name);
		ResultSet rs=p.executeQuery();
		if(rs.next()) 
			return rs.getBytes("photo");
		else
			return null;
	}
	public byte[] getDriverPhoto(String name) throws SQLException {
		PreparedStatement p=c.prepareStatement("select photo from drivers where name=?");
		p.setString(1, name);
		ResultSet rs=p.executeQuery();
		if(rs.next()) 
			return rs.getBytes("photo");
		else
			return null;
	}
	public String licNumber(String name) throws SQLException 
	{
		PreparedStatement p=c.prepareStatement("select lic from drivers where name=?");
		p.setString(1, name);
		
		ResultSet rs=p.executeQuery();
		if(rs.next()) {
			return rs.getString("lic");
		}
		else 
			return "ab00ab0000";
		 
		
	}
	public byte[] getUserPhoto(String email) throws SQLException 
	{
		PreparedStatement p=c.prepareStatement("select photo from users where email=?");
		p.setString(1, email);
		ResultSet rs=p.executeQuery();
		if(rs.next()) {
			byte[] photo=rs.getBytes("photo");
			if(photo.length==0) 
				return null;
			else
				return rs.getBytes("photo");
		}
		else
			return null;
	}
	
	
	
}
