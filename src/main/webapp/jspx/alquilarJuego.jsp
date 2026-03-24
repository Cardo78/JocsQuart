<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"
	import="dto.JuegoBase, dto.Juego, java.util.*"%>
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

	Juego juego = (Juego) request.getAttribute("juego");
	String ean13 = (String) request.getAttribute("ean13");
	boolean alquilerExistente = (boolean) request.getAttribute("alquilerExistente");
	List<Boolean> pulserasConAlquilerB = (List<Boolean>) request.getAttribute("pulserasConAlquilerB");
	List<String> pulserasConAlquilerS = (List<String>) request.getAttribute("pulserasConAlquilerS");
	String jesetas = (String) request.getAttribute("jesetas");
%>

</head>
<body class="hold-transition sidebar-mini layout-boxed">
<div class="content-wrapper">
	<section class="content-header">
	<h1>
			GESTIÓN DE ALQUILERES <small>Permite alquilar juegos o
				proceder a la devolución de estos.</small>
	</h1>
	</section>
		
	<section class="content">
	<div class="col-md-2"></div>			    	
	<form class="form-signin" method="post" action="alquilerController"
			id="buscadorForm" name="buscadorForm">
		<div class="col-md-8 col-xs-12">
			<div class="box box-default">
				<div class="box-header with-border">
					<h3 class="box-title">Codigo de Juego:</h3>
				</div>
				<div class="box-body">
					<%
						if (ean13 == null) {
								
					%>
				<label for="nombre">Código:</label> <input type="text"
						class="form-control" id="codigo" name="codigo"
						placeholder="Código">
					<%	} else {
					%>
				<label for="nombre">Código:</label> <input type="text"
						class="form-control" id="codigo" name="codigo"
						placeholder="Código" value="<%=ean13%>">
					<%	}	%>
				<%	if (!mensajeIt.equals("")) { %>
					<div class="<%=tipoMensaje%> inner" style="text-align: center"
					align="center">
					<h2><%=mensajeIt%></h2>
					</div>
				<%	}	%>
				</div> <!-- BoxBody -->
				<div class="box-footer">
					<div class="col-md-6 col-xs-12">
						<button type="submit" class="btn btn-lg btn-primary btn-block"
								name="action" value="find">Buscar</button>
					</div>
				</div><!-- box-footer -->					
			</div><!-- box -->
			<!-- col -->
		</div>
	</form>    	
	<div class="col-md-2">
	<% if(jesetas !=null){ %>
		<div class="bg-green-active inner" style="text-align: center" align="center">
			<h3>Recuerda, has dado <%=jesetas%> jesetas en anterior devolución</h3>
		</div>
	<% } %>
	</div>
	
	<%
		if (juego!=null) {
			if(juego.getId() > 0){
	%>
    <div class="row">
	<input type="hidden" name="maxJugadores" id="maxJugadores" value="<%=juego.getMaxjugadores()%>">
	
	<!-- Parte de abajo muestra la info del juego -->
	<div class="col-md-6 col-xs-12">
		<div class="box box-default">
			<div class="box-header with-border">
				<h3 class="box-title">Resultados:</h3>
			</div><!-- box-header -->
			<div class="box-body">
				<img src="<%=juego.getRutaImagen()%>" class="img-responsive" alt="<%=juego.getNombre()%>">
				<div id="tablaJuegos_wrapper" class="dataTables_wrapper form-inline dt-bootstrap">						
						<table id="tablaJuegos" class="table table-bordered table-hover dataTable" role="grid">
							<thead>
								<tr role="row">
									<th class="sorting_asc" aria-sort="acending"
										aria-label="Nombre" colspan="1" rowspan="1" tabindex="0">Nombre</th>
									<th class="sorting" aria-label="Duracion" colspan="1"
										rowspan="1" tabindes="0">Duración</th>
									<th class="sorting" aria-label="Maximo" colspan="1" rowspan="1"
										tabindes="0">Máx. jug.</th>
									<th class="sorting" aria-label="Pasaporte" colspan="1"
										rowspan="1" tabindes="0">Pasaporte</th>
								</tr>
							</thead>
							<tbody>
								<tr>
									<td style="display: none"><%=juego.getId()%></td>
									<td class="sorting_1"><%=juego.getNombre()%></td>
									<td><%=juego.getDuracion()%></td>
									<td style="display: none"><%=juego.getBgg()%></td>
									<td><%=juego.getMaxjugadores()%></td>
									<td><%=juego.isPasaporte()%></td>
								</tr>
							</tbody>
						</table>
				</div><!-- TablaJuegos -->
			</div><!-- BoxBody -->
    	</div><!-- box -->
	</div> <!-- Col 12 -->

	<div class="col-md-6">
		<form class="form-signin" method="post" action="alquilerController"
						id="alquilerForm" name="alquilerForm">
			<input type="hidden" name="maxJugadores" id="maxJugadores"
							value="<%=juego.getMaxjugadores()%>"> <input
							type="hidden" name="ean13" id="ean13" value="<%=ean13%>">
			<%
				for (int i = 0; i < juego.getMaxjugadores(); ++i) {
			%>
			<div class="box box-default">
				<div class="box-body">
					<label for="nombre">Código:</label>
						<%
							if (pulserasConAlquilerS == null) {
						%>
					<input type="text" class="form-control" id="pulsera<%=i%>"
										name="pulsera<%=i%>" placeholder="Pulsera">
						<%
							} else {
						%>
					<input type="text" class="form-control" id="pulsera<%=i%>"
										name="pulsera<%=i%>" placeholder="Pulsera"
										value="<%=pulserasConAlquilerS.get(i)%>">

							<%
							if (pulserasConAlquilerB.get(i)) {
							%>
							<button type="submit" class="btn btn-lg btn-primary btn-block"
										name="action"
										value="forzarDevolucion<%=pulserasConAlquilerS.get(i)%>">Cerrar Alquiler Anterior</button>

						<%	}
						}
						%>
				</div><!-- box-body -->
			</div><!-- box -->
						<%
							}
						%>
			<div class="col-md-6 col-xs-12">
					<button type="submit" class="btn btn-lg btn-primary btn-block"
								name="action" value="alquilar">Terminar</button>
			</div>
		</form>
	</div><!-- col -->
	</div><!-- row -->
			
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
			<%if(ean13 == null){ %>
	  			document.getElementById("codigo").focus();
			<%} %>
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