<%@page import="com.incapp.globalTransist.Modal.DAO"%>
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
    DAO db=new DAO();
	String uemail=(String)session.getAttribute("uemail");
	if(uemail==null){
	%>
		<nav class="navbar navbar-expand-sm container my-3">
	        <a href="index.jsp" class="navbar-brand">
	          <img src="resources/icon_transmit_icon-icons.com_54870.png" height="35px" alt="">
     <span style="color:purple">Global</span><span  class="text-warning">Transist</span>
	        </a>
	        <button class="navbar-toggler" type="button" data-toggle="collapse" data-target="#my-navbar">
	      	<i class="fa-solid fa-bars"></i>
	        </button>
	        <div class="collapse navbar-collapse" id="my-navbar">
	            <ul class="navbar-nav mx-auto p-2">
	              <li>
	                <a href="index.jsp">Home</a>
	              </li>
	              <li>
	                <a href="Menu.jsp">Menu</a>
	              </li>
	              <li>
	                <a href="User.jsp">Sign In/Sign Up</a>
	              </li>
	              <li>
	                <a href="" data-toggle="modal" data-target="#adminLoginModal">Admin</a>
	              </li>
	              <li>
	                <a href=""  data-toggle="modal" data-target="#myModal">Get In Touch</a>
	              </li>
	            </ul>
	            <a id="call" href="tel:9811XXXXXX"><i class="fa-solid fa-mobile-screen-button"></i> 9811XXXXXX</a>
	        </div>
	    </nav>
	<%	
	}else{
		String name=(String)session.getAttribute("name");
		
	%>
		<nav class="navbar navbar-expand-sm container my-3">
	        <a href="UserHome.jsp" class="navbar-brand">
	          <img src="resources/icon_transmit_icon-icons.com_54870.png" height="35px" alt="">
	           <span style="color:purple">Global</span><span  class="text-warning">Transist</span>
	        </a>
	        <button class="navbar-toggler" type="button" data-toggle="collapse" data-target="#my-navbar">
	      	<i class="fa-solid fa-bars"></i>
	        </button>
	        <div class="collapse navbar-collapse" id="my-navbar">
	            <ul class="navbar-nav mx-auto p-2">
	              <li>
	                <a href="UserHome.jsp">Home</a>
	              </li>
	              <li>
	                <a href="Menu.jsp">Menu</a>
	              </li>
	              <li>
                <a href="OrdersAdmin.jsp">Orders</a>
              </li>
	              <li>
                <a href=""  data-toggle="modal" data-target="#myModal">Get In Touch</a>
              </li>
	            </ul>
	            <img src="GetUserPhoto?uemail=<%= uemail  %>" height="30px" style="border-radius:50%;margin:0px 5px;"/>
	            Welcome: <b> <%=name %> </b>
	            <a class="mx-2 btn btn-sm btn-warning" href="ViewCart.jsp">Cart [ <b><%= db.countCart(uemail) %></b> ]</a>
	            <a id="call" class="bg-danger" href="Logout">Logout</a>
	        </div>
	    </nav>
	<%	
	}
	%>
    <section>
      	<h1 class="p-5 bg-warning">Exception Occurred !!!!!</h1>
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

    <!-- Modal -->
    <div class="modal fade" id="myModal" tabindex="-1" aria-labelledby="exampleModalLabel" aria-hidden="true">
      <div class="modal-dialog">
        <div class="modal-content">
          <div class="modal-header bg-light">
            <h5 class="modal-title" id="exampleModalLabel">Get In Touch</h5>
            <button type="button" class="close" data-dismiss="modal" aria-label="Close">
              <span aria-hidden="true">&times;</span>
            </button>
          </div>
          <div class="modal-body">
              <form name="google-sheet">
                  <div class="row">
                      <div class="col-sm mt-2">
                          <input class="form-control" type="text" name="Name" pattern="[a-zA-Z ]+" maxlength="20" placeholder="Your Name" required />
                      </div>
                      <div class="col-sm mt-2">
                        <input class="form-control" type="tel" name="Phone" pattern="[0-9]+" maxlength="10" minlength="10" placeholder="Your Phone" required />
                    </div>
                    <div class="col-sm mt-2">
                      <button class="btn btn-primary">Submit</button>
                    </div>
                  </div>
              </form>
          </div>
        </div>
      </div>
    </div>
    <!-- Modal -->
    <div class="modal fade" id="adminLoginModal" tabindex="-1" aria-labelledby="exampleModalLabel" aria-hidden="true">
      <div class="modal-dialog">
        <div class="modal-content">
          <div class="modal-header bg-light">
            <h5 class="modal-title" id="exampleModalLabel">Admin Login</h5>
            <button type="button" class="close" data-dismiss="modal" aria-label="Close">
              <span aria-hidden="true">&times;</span>
            </button>
          </div>
          <div class="modal-body">
              <form action="AdminLogin" method="post">
                  <div class="row">
                      <div class="col-sm mt-2">
                          <input class="form-control" type="text" name="id" maxlength="20" placeholder="Admin ID" required />
                      </div>
                      <div class="col-sm mt-2">
                        <input class="form-control" type="password" name="password" maxlength="10" minlength="10" placeholder="Password" required />
                    </div>
                    <div class="col-sm mt-2">
                      <button class="btn btn-primary">Login</button>
                    </div>
                  </div>
              </form>
          </div>
        </div>
      </div>
    </div>
</body>
<script>
    AOS.init();
    //script for scroll to top
    $("#top-button").click(function () {
        $("html, body").animate({scrollTop: 0}, 1000);
    });
</script>
</script>
</html>