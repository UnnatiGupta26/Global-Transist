 <%@page import="java.util.HashMap"%>
<%@page import="java.util.ArrayList"%>
<%@page import="com.incapp.globalTransist.Modal.DAO"%>
<%
	String name=(String)session.getAttribute("name");
	if(name==null){
		session.setAttribute("msg", "Please Login First!");
		response.sendRedirect("index.jsp");
	}else{
%>

<!DOCTYPE html>
<html>

<head>
  <title>GlobalTransist</title>
  <link rel="icon" href="resources/icon_transmit_icon-icons.com_54870.png" />

  <meta name="viewport" content="width=device-width, initial-scale=1">

  <script src="https://code.jquery.com/jquery-3.6.0.min.js"></script>
  <script src="https://cdn.jsdelivr.net/npm/bootstrap@4.6.1/dist/js/bootstrap.bundle.min.js"></script>
  <link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/bootstrap@4.6.1/dist/css/bootstrap.min.css" />

  <!-- font-awesome -->
  <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.2.1/css/all.min.css" integrity="sha512-MV7K8+y+gLIBoVD59lQIYicR65iaqukzvf/nwasF0nqhPay5w/9lJmVM2hMDcnK1OnMGCdVK+iQrJ7lzPJQd1w==" crossorigin="anonymous" referrerpolicy="no-referrer" />
  <script src="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.2.1/js/all.min.js" integrity="sha512-rpLlll167T5LJHwp0waJCh3ZRf7pO6IT1+LZOhAyP6phAirwchClbTZV3iqL3BMrVxIYRbzGTpli4rfxsCK6Vw==" crossorigin="anonymous" referrerpolicy="no-referrer"></script>
  
  <!-- Lightbox CSS & Script -->
  <script src="resources/lightbox/ekko-lightbox.js"></script>
  <link rel="stylesheet" href="resources/lightbox/ekko-lightbox.css"/>

  <!-- AOS CSS & Script -->
  <script src="resources/aos/aos.js"></script>
  <link rel="stylesheet" href="resources/aos/aos.css"/>

  <!-- custom css -->
  <link rel="stylesheet" href="resources/custom.css">

  <meta name="author" content="Unnati Gupta" />
  <meta name="description" content="This is a website for E-Logistics." />
  <meta name="keywords" content="" />
</head>

<body>
	
	<%
		String msg=(String)session.getAttribute("msg");
	  	if(msg!=null){
	%>
			<p class="text-center bg-warning p-2"> <%=msg%> </p>
	<%  		
		  session.setAttribute("msg",null);
	  	}
	%>
	
    <nav class="navbar navbar-expand-sm container my-3">
        <a href="AdminHome.jsp" class="navbar-brand">
          <img src="resources/icon_transmit_icon-icons.com_54870.png" height="35px" alt="">
          <span style="color:purple">Global</span><span  class="text-warning">Transist</span>
        </a>
        <button class="navbar-toggler" type="button" data-toggle="collapse" data-target="#my-navbar">
      <i class="fa-solid fa-bars"></i>
        </button>
        <div class="collapse navbar-collapse" id="my-navbar">
            <ul class="navbar-nav mx-auto p-2">
              <li>
                <a href="AdminHome.jsp">Home</a>
              </li>
              
               <li>
                <a href="AddDrivers.jsp">Drivers</a>
              </li>
              
              <li>
                <a href="AllocateVehicle.jsp">Allocate-Vehicle</a>
              </li>
              
              <li>
                <a href="Enquiry.jsp">Enquiry</a>
              </li>
              <li>
                <a href="OrdersAdmin.jsp">Orders</a>
              </li>
            </ul>
            Welcome: <b> <%=name %> </b>
            <a id="call" class="bg-danger" href="Logout">Logout</a>
        </div>
    </nav>
    <section class="container">
      	<div class="row">
      		<div class="col-sm p-3 bg-light">
      			<h5 class="text-center">Add New Vehicle</h5>
      			<hr/>
      			<form action="AddItem" method="post" enctype="multipart/form-data">
                  <input class="form-control" type="text" name="name" maxlength="40" placeholder="Vehicle Name" required />
                  <br/>
                  <input class="form-control" type="number" name="price" max="60000" min="1" placeholder="Vehicle Price per Km" required />
                  
                  <br/>
                  <input class="form-control" type="text" name="vehicle_number" maxlength="10"  placeholder="Vehicle number" required />
                   <br/>
                   <input class="form-control" type="text" name="category" placeholder="category" required/>
    
                   <br/>
                  

                  <input class="form-control" type="file" name="photo" required />
                  <br/>
                  <button class="btn btn-primary">Add</button>
              </form>
      		</div>
      	      		
      		
      		<div class="col-sm p-3 bg-info">
      			<h5 class="text-center">All Vehicles</h5>
      			
      			<hr/>
      			<h6>All prices are based per Km</h6>
  
      			<%
      				DAO db=new DAO();
      				ArrayList<HashMap> items=db.getAllItems();
      				db.closeConnection();
      				for(HashMap item:items){
      			%>
      			
      					<div class="bg-light p-2 m-2">
      						<p>
      							<img src="GetItemPhoto?name=<%= item.get("name")  %>" height="50px" />
      							Name: <b><%= item.get("name")  %></b>
      							Price: <b><%= item.get("price")  %></b>
      							<a href="DeleteItem?name=<%= item.get("name")  %>" class="btn btn-sm btn-danger mx-2">Delete</a>
      						</p>
      					</div>
      			<%	
      				}
      			%>
      		</div>
      	</div>
      	
    </section>
    
    <footer class="bg-dark p-5 text-center">
        <a href="index.html">
          <img src="resources/icon_transmit_icon-icons.com_54870.png" height="30px" alt="">
          <span>Global</span>Transist
        </a>
        <br/>
        <p>&copy Rights Reserved</p>
        <p>
          <a href="https://www.facebook.com/incapp"><i class="fa-brands fa-facebook"></i></a>
          <a href="https://www.instagram.com/incapp.in"><i class="fa-brands fa-instagram"></i></a>
          <a href="https://www.youtube.com/incapp"><i class="fa-brands fa-youtube"></i></a>
        </p>
    </footer>
    <label id="top-button"><i class="fa-solid fa-circle-up fa-2x"></i></label>

    
</body>
<script>
    //script for scroll to top
    $("#top-button").click(function () {
        $("html, body").animate({scrollTop: 0}, 1000);
    });
</script>
</html>

<% } %>