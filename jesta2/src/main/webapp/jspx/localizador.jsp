<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"  import="dto.JuegoBase, dto.Juego, dto.Jugador, dto.Alquiler, java.util.*"%>
<%@ include file="cabecera.jsp"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="ISO-8859-1">
<title>Localizador Juego</title>
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
	String tipoMensaje = request.getAttribute("tipoMensaje") == null ? "" : (String) request.getAttribute("tipoMensaje");
	
	ArrayList<JuegoBase> juegos = (ArrayList<JuegoBase>) request.getAttribute("juegos");
	ArrayList<Alquiler> alquileres = (ArrayList<Alquiler>) request.getAttribute("alquileres");
	
	
%>

</head>
<body class="hold-transition sidebar-mini layout-boxed">
	<div class="content-wrapper">
		<section class="content-header">
			<h1>
				LOCALIZADOR de JUEGOS 
			</h1>
		</section>
		<section class="content">
			<div class = "col-md-2">
			</div>
			
			<form class="form-signin" method="post" action="disponibleController" id="buscadorForm" name="buscadorForm">
				<div class="col-md-8 col-xs-12">
					<div class="box box-default">
						<div class="box-header with-border">
							<h3 class="box-title">Buscar:</h3>
						</div>
						<div class="box-body">
							<label for="nombre">Nombre:</label> <input type="text"
							class="form-control" id="nombre" name="nombre" placeholder="Nombre">
						</div>
						<div class="box-footer">
							<div class="col-md-6 col-xs-12">
							<button type="submit" class="btn btn-custom btn-lg btn-primary btn-block"
							name="action" value="open">Buscar</button></div>						
							<div class="col-md-6 col-xs-12">
						</div> <!-- box-footer -->
					</div> <!-- box -->
				</div> <!-- col -->
			</form>
			
			<div class="col-md-2"></div>
			
			<div class="col-md-12 col-xs-12">
				<%if(!mensajeIt.equals("")){ %>
				<div class="<%=tipoMensaje%> inner" style="text-align:center" align="center">
			  				<h3><%=mensajeIt%></h3>
				</div>	
				<%}%>
			</div>
			
			<div class="col-md-12 col-xs-12">
				<%if(juegos != null) {%>
				<div class="box box-default">
					<div class="box-header with-border">
	        	   		<h3 class="box-title">Juegos:</h3>
	        		</div><!-- box-header -->
	        		<div class="box-body">
	        			<div id="tablaJuegos_wrapper" class="dataTables_wrapper form-inline dt-bootstrap">
	        				<table id="tablaJuegos" class="table table-striped w-auto table-bordered table-hover dataTable" role="grid">
	            			<thead>
	   							<tr role="row">
	   							<th class="sorting_asc" aria-sort="acending" aria-label="Nombre"
	   							colspan="1" rowspan="1" tabindex="0">Nombre</th>
	   							<th class="sorting" aria-label="Duracion" 
	   							colspan="1" rowspan="1" tabindes="0">Codigo Barras</th>
	   							<th class="sorting" aria-label="Imagen" 
	   							colspan="1" rowspan="1" tabindes="0">Alquilado</th>	   							
	   							</tr>
	   						</thead>				
							<tbody>
							<%for(JuegoBase j : juegos){ 
								for(Juego ean : j.getJuegos()){
									int id = j.getIdBase();
										if(id > 0) { %>
										<tr class="clickable-row odd table-info">
											<td style="display: none"><%=ean.getId() %></td> 
											<td class="sorting_1"><%=j.getNombre() %></td>
											<td><%=ean.getEan13() %></td>
											<td><%=ean.isAlquilado() %>															
										</tr>
										<%} %>
								<%} %>
							<%} %>
							</tbody>
					</table>
					</div> <!-- TablaJuegos -->
		        </div> <!-- box-body -->
	    	</div> <!-- box -->
				<%} %>
			</div>
			
			<div class="col-md-2"></div>
			<div class="col-md-8 col-xs-12">
				<%if(alquileres != null) {%>
				<div class="box box-default">
					<div class="box-header with-border">
	        	   		<h3 class="box-title">Jugadores con el juego:</h3>
	        		</div><!-- box-header -->
	        		<div class="box-body">
	        			<div id="tablaJuegos_wrapper" class="dataTables_wrapper form-inline dt-bootstrap">
	        				<table id="tablaJuegos" class="table table-striped w-auto table-bordered table-hover dataTable" role="grid">
	            			<thead>
	   							<tr role="row">
	   							<th class="sorting" aria-label="Hora Alquiler" 
	   							colspan="1" rowspan="1" tabindes="0">Hora entrega</th>
	   							<th class="sorting_asc" aria-sort="acending" aria-label="pulsera"
	   							colspan="1" rowspan="1" tabindex="0">Pulsera</th>
	   							<th class="sorting" aria-label="Jugador" 
	   							colspan="1" rowspan="1" tabindes="0">Nombre y Apellidos</th>	   								   							
	   							<th class="sorting" aria-label="DNI" 
	   							colspan="1" rowspan="1" tabindes="0">DNI</th>
	   							</tr>
	   						</thead>				
							<tbody>
							<%for(Alquiler alquiler : alquileres){ 	%>
								<tr class="odd table-info">									
									<td><%=alquiler.getIni() %></td>
									<td><%=alquiler.getJugador().getPulsera() %>
									<td class="sorting_1"><%=alquiler.getJugador().getNombre() %> <%=alquiler.getJugador().getApellidos() %></td>
									<td><%=alquiler.getJugador().getDni() %>																								
								</tr>
							<%} %>														
							</tbody>
					</table>
					</div> <!-- TablaJuegos -->
		        </div> <!-- box-body -->
	    	</div> <!-- box -->
				<%} %>
			</div>
	
	<div class="col-md-2"></div>
	</section>
	</div>
	
	<script src="plugins/datatables/jquery.dataTables.min.js"></script>
	<script src="plugins/datatables/dataTables.bootstrap.min.js"></script>
	<script type="text/javascript">
	jQuery(document).ready(function($) {
		$(".clickable-row").click(
				function() {
					var index = ($(this).index()) + 1;
					var id = $('.table tr:eq(' + index + ') td:eq(' + 0 + ')').text();
					var nombre = $('.table tr:eq(' + index + ') td:eq(' + 1 + ')').text();
					var ean = $('.table tr:eq(' + index + ') td:eq(' + 2 + ')').text();
					$form = $("<form method='post' action='disponibleController'></form>");
					$form.append('<input type="hidden" name="action" value="localizar" />');
					$form.append('<input type="hidden" name="id" value="' + id + '" />');
					$form.append('<input type="hidden" name="nombre" value="' + nombre + '" />');
					$form.append('<input type="hidden" name="ean" value="' + ean + '" />');					
					$('body').append($form);
					$form.submit();
					});
		});
	</script>
</body>
</html>