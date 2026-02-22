<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
    <%@include file="cabecera.jsp" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<meta http-equiv="X-UA-Compatible" content="IE=edge">
<meta name="viewport" content="width=device-width, initial-scale=1, maximum-scale=1, user-scalable=no">
<meta name="description" content="Login">
<meta name="author" content="Ricardo Cardona">
<title>Login</title>

    <%		
		String mensajeIt = (request.getAttribute("mensaje") == null) ? "" : (String)request.getAttribute("mensaje");
    	String logo = (request.getAttribute("logo")== null) ? "" : (String)request.getAttribute("logo");
	%>
	
</head>
<body class="hold-transition login-page">   


<div class="login-box">

		<!-- Logo del login -->
		<div class="login-logo">
			<img src="<%=logo %>" class="img-rounded">	
		</div>
	<div class="login-box-body">
		<h1 class="login-box-msg">Iniciar Sesión</h1>
			<form class="form-signin" method="post" action="loginController" id="myForm" name="myForm">
				<div class="form-group has-feedback">
					<input type="text" id="inputUser" name="inputUser" class="form-control" placeholder="Usuario" required autofocus>
					<span class="glyphicon glyphicon-user form-control-feedback"></span>
		 		</div>
		 		<div class="form-group has-feedback">
		 			<input type="password" id="inputPass" name="inputPass" class="form-control" placeholder="Contraseña" required>
		 			<span class="glyphicon glyphicon-lock form-control-feedback"></span>
		 		</div>
          <% if(!mensajeIt.equals("")) {%> 
        	
        	<div class="callout callout-danger" style="display:block;"><%= mensajeIt %></div>
        <%}%>
        <div class="row">
			<div class="col-xs-8">
			</div>
			<div class="col-xs-4">
				<button class="btn btn-lg btn-primary btn-block  btn-flat" type="submit">Login</button>
			</div>
        </div> <!-- row -->
      </form>
    </div> <!-- login-box-body -->
    
	</div> <!-- login-box -->

</body>
</html>