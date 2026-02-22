<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<meta http-equiv="X-UA-Compatible" content="IE=edge">
<meta name="viewport"
	content="width=device-width, initial-scale=1, maximum-scale=1, user-scalable=no">
<meta name="description" content="MenuPrincipal">
<meta name="author" content="Ricardo Cardona">
<title>Error Page</title>

  	<link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.4.0/css/bootstrap.min.css">
	
    <!-- AdminLTE -->
  	<link rel="stylesheet" href="./css/AdminLTE/AdminLTE.min.css"> 
	<!-- AdminLTE Skin -->
	<link rel="stylesheet" href="./css/AdminLTE/skins/_all-skins.css">
	<!-- Font Awesome -->
 	<link href="./css/font-awesome/css/font-awesome.min.css" rel="stylesheet"> 		
	<!-- CSS User -->
	<link rel="stylesheet" href="./css/style.css">

<% 
String mensaje = (request.getAttribute("mensaje") == null) ? "" : (String)request.getAttribute("mensaje"); 
%>
</head>
<body class="hold-transition skin-yellow sidebar-mini">
	<div class="content-wrapper backgroundwhite">
		<section class="content-header">
		<h1>Error page</h1>
		</section>
		<section class="content">
			<div class="error-page">

			<%if(mensaje.equals("")){ %>
						<h2 class="headline text-yellow">ERROR 404</h2>
			<% }%>
			<%if(!mensaje.equals("")) { %>
						<h2 class="headline text-red">ERROR 500</h2>
			<% }%>
				<div class="col-md-8">
					<div class="error-content">
					<%if(mensaje.equals("")){ %>
						<h3><i class="fa fa-warning text-yellow"></i>Página no encontrada!</h3>
						<p>No podemos encontrar la página a la que está intentando acceder.	Puede volver a acceder al panel principal: <a href="./index.jsp">Ir
						al inicio</a></p>
					<% }%>
					<%if(!mensaje.equals("")) { %>
						<h3><i class="fa fa-warning text-red"></i>Ooops! Algo ha cometido un error.</h3>
						<p><%=mensaje %>	.Puede volver a acceder al panel principal: <a href="./index.jsp">Ir
						al inicio</a></p>
					<%} %>
					</div>
				</div>
				<div class="col-md-4">	
				<img src="./images/paper.png"/>
				</div>
			</div>
		</section>
	</div>

</body>
</html>