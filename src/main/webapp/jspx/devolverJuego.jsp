<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"
	import="dto.JuegoBase, dto.Juego, java.util.*, dto.Jugador, dto.Jeseta"%>
<%@ include file="cabecera.jsp"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Buscar Juego</title>

<%
	Usuario user = null;
	if (session.equals(null)) {
		response.sendRedirect("./loginController");
	} else {
		user = (Usuario) session.getAttribute("usuario");
		if (user == null) {
			response.sendRedirect("./loginController");
		} else {
			if (!(user.getTipo() == "ADMIN" || user.getTipo() == "NORMAL")) {
				response.sendRedirect("./loginController");
			}
		}
	}

	String mensajeIt = request.getAttribute("mensaje") == null ? "" : (String) request.getAttribute("mensaje");
	String tipoMensaje = request.getAttribute("tipoMensaje") == null
			? ""
			: (String) request.getAttribute("tipoMensaje");
	String juego = (String) request.getAttribute("juego");
	//int nJesetas = request.getAttribute("nJesetas") == null ? 0 : (Integer) request.getAttribute("nJesetas");
	ArrayList<Jugador> jugadores = (ArrayList<Jugador>) request.getAttribute("jugadores");
	ArrayList<Jeseta> jesetas = (ArrayList<Jeseta>) request.getAttribute("jesetas");
	String tiempo = request.getAttribute("tiempo") == null ? "" : (String) request.getAttribute("tiempo");	
%>

</head>
<body class="hold-transition sidebar-mini layout-boxed">
	<div class="content-wrapper">
	<section class="content-header">
		<%
		if (!tiempo.equals("")) {
		%>
		<div class="col-md-12">		
		<div class="box box-default">
			<div class="box-header with-border">
				<h3 class="box-title">¡Importante, Revisión de tiempo de devolución!</h3>
			</div>
			<div class="box-body">
			<form class="form-signin" method="post" action="devolverController">
				<div class="<%=tipoMensaje%> inner" style="text-align: center" align="center">
					<h2><%=mensajeIt%></h2>
				</div>
				<div class="box-footer">
				<div class="col-md-6 col-xs-12">
						<button type="submit" class="btn btn-lg btn-primary btn-success" id="btnConfirmar" name="action" value="confirmado">Confirmamos Jesetas</button>
						<button type="submit" class="btn btn-lg btn-danger" id="btnRechazar" name="action" value="rechazado">Rechazamos Jesetas</button>
						<button type="submit" class="btn btn-lg btn-warning" id="btnCancelar" name="action" value="cancel" formnovalidate="formnovalidate">Cancelar</button>						
					</div>
				</div><!-- box-footer -->	
			</form>			
			</div><!-- box-body -->
		</div> <!-- Box Default -->
		</div>			
		<%
			}
		else {
		%>	
		<div class="col-md-6">		
		<div class="box box-default">
			<div class="box-header with-border">
				<h3 class="box-title">Devolución:</h3>
			</div>
			<div class="box-body">
			    <div class="callout callout-info" style="display:block;"><h3>Juego <%= juego %> devuelto con éxito</h3></div>				
				<% for(Jugador j : jugadores) { %>	
					<div class="callout callout-success" style="display:block;">
							
						<h3> <%= j.getNombre() + " " + j.getApellidos()%></h3>
										
					</div>
				<% } %>
			</div><!-- box-body -->
		</div> <!-- Box Default -->
		</div>
	
		<div class="col-md-6">
		<form class="form-signin" method="post" action="alquilerController">	
		<div class="box box-default">
			<div class="box-header with-border">
				<h3 class="box-title">Total JESESTAS:</h3>
			</div>
			<div class="box-body">
				<div class="callout callout-info" style="display:block;"><h3>Juego <%= juego %> devuelto con éxito</h3></div>	
				<% if(jesetas != null) { %>			
				<% for(Jeseta jeseta : jesetas) { %>	
					<div class="callout callout-success" style="display:block;">
							
						<h3> <%= jeseta.getIdJeseta() + ": " + jeseta.getDescripcion()%></h3>
										
					</div>
				<% } %>
				<% } %>
				<!-- <h1 class="bg-blue-active center" style="text-align:center;" ><%//= nJesetas %> Jesetas</h1> -->
				<!-- <input type="hidden" name="jesetas" id="jesetas" value="<%//=nJesetas%>"> -->
			</div><!-- box-body -->
			<div class="box-footer">
				<button type="submit" class="btn btn-lg btn-primary btn-block" id="volver">Volver</button>
			</div>
		</div> <!-- Box Default -->
		</form>
		</div>
						
		<%
		if (!mensajeIt.equals("")) {
		%>
		<div class="col-md-12">
			<div class="<%=tipoMensaje%> inner" style="text-align: center"
				align="center">
				<h2><%=mensajeIt%></h2>
			</div>
		</div>
		<%
			}
		}
		%>


	</section>
	</div>

	<script src="plugins/datatables/jquery.dataTables.min.js"></script>
	<script src="plugins/datatables/dataTables.bootstrap.min.js"></script>
	<script type="text/javascript">
		window.onload = function() {
		  document.getElementById("volver").focus();
		};
		jQuery(document)
				.ready(
						function($) {
							$("li").removeClass("active");
							$("#liJuegos").addClass("active");
							$(".clickable-row")
									.click(
											function() {
												var index = ($(this).index()) + 1;
												var id = $(
														'.table tr:eq(' + index
																+ ') td:eq('
																+ 0 + ')')
														.text();
												$form = $("<form method='post' action='alquilerController'></form>");
												$form
														.append('<input type="hidden" name="action" value="open" />');
												$form
														.append('<input type="hidden" name="id" value="' + id + '" />');
												$('body').append($form);
												$form.submit();
											});
						});
	</script>
</body>
</html>