<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1" import="dto.Jeseta, dto.Jugador, java.util.*"%>
<%@ include file="cabecera.jsp"%>    
<!DOCTYPE html>
<html>
<head>
<meta charset="ISO-8859-1">
<title>Sorteo</title>
<%
	Usuario user = null;
	if (session.equals(null)) {
		response.sendRedirect("./loginController");
	}else {
		user = (Usuario) session.getAttribute("usuario");
		if (user == null) {
			response.sendRedirect("./loginController");
		}
	}
	
	String mensajeIt = request.getAttribute("mensaje") == null ? "" : (String) request.getAttribute("mensaje");
	String tipoMensaje = request.getAttribute("tipoMensaje") == null ? "" : (String) request.getAttribute("tipoMensaje");
	Jugador jugador = (Jugador)request.getAttribute("jugador");
	Jeseta jeseta = (Jeseta)request.getAttribute("jeseta");
%>
</head>
<body class="hold-transition sidebar-mini layout-boxed">
	<div class="content-wrapper">
	<section class="content-header">
			<h1>
				SORTEO JESTA
			</h1>
		</section>
		<section class="content">		
		
		<!-- Boton sacar boleto -->
		<div class="col-md-12 col-xs-12 form-group">

			<div class="col-md-1"></div>
			<div class="col-md-10 col-xs-12">
				<button class="btn btn-block btn-success btn-sorteo" id="btnTransferir" name="action" value="revisar"><h1>OBTENER PREMIO</h1></button>
			</div>
			<div class="col-md-1"></div>								
		</div>

<!-- Boton revisar premiados -->
	<div class="col-md-12 col-xs-12 form-group">
		<form class="form-signin" method="post" action="sorteoController" id="obtenerListado" name="obtenerListado">
			<div class="col-md-6"></div>
			<div class="col-md-6 col-xs-12">
				<button type="submit" class="btn btn-lg btn-primary btn-block btn-custom" name="action" value="resultados">RESULTADOS</button> 									
			</div>
		</form>
	</div>
	
<!-- Mensaje Jugador ganador de Jeseta si existe -->			
		<%if(jugador!=null){ %>
		<div class="col-md-12 col-xs-12"> <!-- column 6 Editor de usuarios -->
			<div class="box box-default">
    			<div class="box-header with-border">
					<h3 class="box-title"><%= mensajeIt %></h3>
				</div>
    			<div class="box-body">   	
    				<div class="form-control col-md-12">
		 				<p>Pulsera: 
		 				<label style="zoom:4"><%= jugador.getPulsera() %></label></p>
		 				<p>Nombre: 
		  				<label style="zoom:4"><%= jugador.getNombre() %></label></p>
		  				<p>A traves de la Jeseta: 
		  				<label style="zoom:3"><%= jeseta.getDescripcion() %></label></p>	  							  		
					</div>		
    			</div>
    		</div>
    	</div>
    		
		<%}%>	
	
<!-- Imagen Sorteo iluminado en base a si hay informacion de jugador o no -->




</section>		
</div>
	
<script type="text/javascript">
	const btnSorteo = document.querySelector('.btn-sorteo');     

    btnSorteo.addEventListener('click', function(e){    	
    	btnSorteo.disabled = true;
    	console.log("botoncito pulsadito");
    	setTimeout(aplicarTiempo, 1000);    	    
    	
    }); 
    
    function aplicarTiempo() {
    	console.log("esperado 1 segundos");
    	$form = $("<form method='post' action='sorteoController'></form>");
        $form.append('<input type="hidden" name="action" id="action" value="premio" />');    	
        $('body').append($form);
    	$form.submit();  	  	
  	  
  	}
    
</script>
</body>
</html>