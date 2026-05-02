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
		String mensajeIt = (request.getAttribute("mensaje")== null) ? "" : request.getAttribute("mensaje").toString();
    	String callout = (request.getAttribute("callout")== null) ? "" : request.getAttribute("callout").toString();
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
			<form class="form-signin" method="post" action="loginController" id="formLogin" name="myForm">
				<div class="form-group has-feedback">
					<input type="text" id="inputUser" name="inputUser" class="form-control" placeholder="Pulsera" required autofocus>
					<span class="glyphicon glyphicon-user form-control-feedback"></span>
		 		</div>
		 		<div class="form-group has-feedback">
		 			<input type="password" id="inputPass" name="inputPass" class="form-control" placeholder="Contraseña" required>
		 			<span class="glyphicon glyphicon-lock form-control-feedback"></span>
		 		</div>
		 	<div class="form-check">
  				<input class="form-check-input" type="checkbox" style="zoom:1" value="false" id="recuperar" onchange="javascript:recuperarClave()">
  				<label class="form-check-label" for="flexCheckDefault" style="zoom:1">Recuperar clave</label>
			</div>
        	<div class="callout <%= callout %>" style="display:block;"><%= mensajeIt %></div>
        	
    <div class="row row-button">
		<div class="col-xs-8" id="contenedorRecuperar" style="display: none; margin-top: 10px;">
        	<button class="btn btn-lg btn-warning btn-block btn-flat" 
                type="button" 
                onclick="enviarRecuperacion()">
            	Recuperar Clave
        	</button>
    	</div>
    	<div class="col-xs-4" style="margin-top: 10px;">
        	<button type="submit" class="btn btn-lg btn-primary btn-block btn-flat" id="btnLogin" name="action" value="loguear" >Login</button>        	
    	</div>
	</div>


 
      </form>
    </div> <!-- login-box-body -->
    
	</div> <!-- login-box -->

<script type="text/javascript">

function recuperarClave() {
    var check = document.getElementById("recuperar");
    var contenedor = document.getElementById("contenedorRecuperar");
    
    // Si el checkbox está marcado, mostramos el div que contiene el botón
    if (check.checked) {
        contenedor.style.setProperty("display", "block", "important");
    } else {
        contenedor.style.setProperty("display", "none", "important");
    }
}


function enviarRecuperacion() {
    var user = document.getElementById("inputUser").value; 
    if(user.trim() === "") {
        alert("Por favor, introduce tu número de pulsera.");
        return;
    }

    // Creamos un formulario temporal independiente para la recuperación
    var $form = $("<form method='post' action='loginController'></form>");
    $form.append('<input type="hidden" name="action" value="recuperar" />');
    $form.append('<input type="hidden" name="inputUser" value="' + user + '" />');
            
    $('body').append($form);
    $form.submit();
}

</script>

</body>
</html>