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
    	String user = (request.getAttribute("user")== null) ? "" : (String)request.getAttribute("user").toString();
	%>
	
</head>
<body class="hold-transition recovery-page">   


<div class="login-box">

		<!-- Logo del login -->
	<div class="login-logo">
		<img src="<%=logo %>" class="img-rounded">	
	</div>
	<div class="login-box-body">
		<h1 class="login-box-msg">Recuperar Clave</h1>		
		<div class="callout <%= callout %>" style="display:block;"><%= mensajeIt %></div>
		<form class="form-signin" method="post" action="loginController" id="guardar">
		   <div class="form-group has-feedback">
				<input type="text" id="inputUser" name="inputUser" class="form-control" placeholder="Pulsera" 
				value="<%=user%>" required autofocus>
				<span class="glyphicon glyphicon-user form-control-feedback"></span>
		 	</div>
		 	<div class="form-group has-feedback">
    			<input type="text" id="inputDNI" name="inputDNI" class="form-control" placeholder="DNI" required>
    			<span class="glyphicon glyphicon-user form-control-feedback"></span>
			</div>
			<div class="form-group has-feedback">
    			<input type="password" id="inputPass" name="inputPass" class="form-control" placeholder="Contraseña" required>
    			<span class="glyphicon glyphicon-lock form-control-feedback"></span>
			</div>
			<div class="form-group has-feedback">
    			<input type="password" id="inputPassR" name="inputPassR" class="form-control" placeholder="Repetir Contraseña" required>
    			<span class="glyphicon glyphicon-lock form-control-feedback"></span>
			</div>

    	<div class="col-xs-12" style="margin-top: 10px;">
        	<button type="submit" class="btn btn-lg btn-primary btn-block btn-flat" id="btnConfirmar" name="action" value="actualizar">Recuperar</button>
    	</div>
</form>


        </div> <!-- row -->

    </div> <!-- login-box-body -->
    
</div> <!-- login-box -->

<script type="text/javascript">
//Seleccionamos los elementos
const pass = document.getElementById("inputPass");
const passR = document.getElementById("inputPassR");

function verificarIgualdad() {
    if (pass.value !== passR.value) {
        // Si no son iguales, marcamos el campo como inválido
        // Esto hará que el navegador muestre un mensaje y no envíe el form
        passR.setCustomValidity("Las contraseñas deben coincidir.");
    } else {
        // Si son iguales, eliminamos cualquier mensaje de error
        passR.setCustomValidity("");
    }
}

// Escuchamos el evento de teclado en ambos campos
pass.onkeyup = verificarIgualdad;
passR.onkeyup = verificarIgualdad;
</script>

</body>
</html>