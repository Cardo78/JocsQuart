<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1" import="dto.Jeseta, dto.Jugador, java.util.*"%>
<%@ include file="cabecera.jsp"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="ISO-8859-1">
<title>Resultado Sorteo</title>
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
	ArrayList<Jeseta> jesetas = (ArrayList<Jeseta>) request.getAttribute("jesetas");
	ArrayList<Jugador> jugadores = (ArrayList<Jugador>) request.getAttribute("jugadores");
	
%>
</head>
<body class="hold-transition sidebar-mini">
	<div class="content-wrapper">
		<section class="content-header">
			<h1>
				RESULTADO PREMIADOS <small>Relacion de premiados</small>
			</h1>
		</section>
		<section class="content">
		
		<!-- Mensaje de informacion -->
		<div id="mensajeDiv">
				<%if(!mensajeIt.equals("")){ %>
					<div class="<%=tipoMensaje%> inner" style="text-align:center" align="center">
			  			<h3><%= mensajeIt %></h3>
					</div>	
				<%}%>	
		</div>
		<!-- Lista de Jugadores con boletos premiados -->
		<%if(jesetas != null) {%>
		<table id="tablaJesetas" name="tablaJesetas" class="table table-striped table-bordered" role="grid">
	       	<thead>
	   			<tr class="tr-header">
	   				<th class="hidden-xs">Nº Referencia</th>	   				
	   				<th>Descripcion</th>
	   				<th>N Pulsera</th>	   							
	   				<th>Jugador</th>
	   				<th>Hora del premio</th>	   							
	   			</tr>
	   		</thead>				
			<tbody>
			<% 	for(int i=0; i<jesetas.size(); i++){ %>
					<tr class="tr-row">
					<td class="id-jeseta"><%=jesetas.get(i).getIdJeseta() %></td> 					
					<td class="desc-jeseta"><%=jesetas.get(i).getDescripcion() %></td>
					<td class="pulsera-jeseta"><%=jugadores.get(i).getPulsera() %></td>
					<td class="jugador-jeseta"><%=jugadores.get(i).getNombre() +  " " + jugadores.get(i).getApellidos()%></td>
					<td class="hora-jeseta"><%=jesetas.get(i).getFecha() %></td>
				</tr>
			<% } %>			
			</tbody>
			</table>
		<%} %>
		
		<form class="form-signin" method="post" action="sorteoController" id="obtenerListado" name="obtenerListado">
				<div class="col-md-8 col-xs-12">
					<h3 class="box-title">REFRESCO</h3>
					<button type="submit" class="btn btn-lg btn-primary btn-block btn-custom" name="action" value="resultados">
					REFRESCAR RESULTADOS </button> 									
				</div>
		</form>
		</section>
	</div>
<script type="text/javascript">
    var cont = 0;
    setInterval(function(){
    	$form = $("<form method='post' action='sorteoController'></form>");
    	$form.append('<input type="hidden" name="action" id="action" value="resultados" />');
    	$('body').append($form);
		$form.submit();
    },10000);    
</script>
    


</body>
</html>