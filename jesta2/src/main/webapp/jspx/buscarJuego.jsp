<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1" import="dto.JuegoBase, java.util.*"%>
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
	String tipoMensaje = request.getAttribute("tipoMensaje") == null ? "" : (String) request.getAttribute("tipoMensaje");
	
	ArrayList<JuegoBase> juegos = (ArrayList<JuegoBase>) request.getAttribute("juegos");
%>

</head>
<body class="hold-transition sidebar-mini layout-boxed">
	<div class="content-wrapper">
		<section class="content-header">
			<h1>
				BUSCADOR de JUEGOS <small>Permite obtener juegos añadidos y poder modificar los datos con un clic en el seleccionado.</small>
			</h1>
		</section>
		<section class="content">
			<div class = "col-md-2">
			</div>
			
			<form class="form-signin" method="post" action="juegoController" id="buscadorForm" name="buscadorForm">
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
							name="action" value="find">Buscar</button></div>						
							<div class="col-md-6 col-xs-12">
							<button type="submit" class="btn btn-lg btn-primary btn-block btn-custom" name="action" value="new" 
							<%if(user.getTipo() != "ADMIN"){%> 
								disabled 
							<%} %>>Nuevo</button></div>
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
	        	   		<h3 class="box-title">Resultados:</h3>
	        		</div><!-- box-header -->
	        		<div class="box-body">
	        			<div id="tablaJuegos_wrapper" class="dataTables_wrapper form-inline dt-bootstrap">
	        				<table id="tablaJuegos" class="table table-striped w-auto table-bordered table-hover dataTable" role="grid">
	            			<thead>
	   							<tr role="row">
	   							<th class="sorting_asc" aria-sort="acending" aria-label="Nombre"
	   							colspan="1" rowspan="1" tabindex="0">Nombre</th>
	   							<th class="sorting" aria-label="Duracion" 
	   							colspan="1" rowspan="1" tabindes="0">Duración</th>
	   							<th class="sorting" aria-label="Imagen" 
	   							colspan="1" rowspan="1" tabindes="0">Máx. jug.</th>
	   							<th class="sorting" aria-label="Maximo" 
	   							colspan="1" rowspan="1" tabindes="0">Pasaporte</th>
	   							<th class="sorting" aria-label="Pasaporte" 
	   							colspan="1" rowspan="1" tabindes="0">Imagen</th>
	   							</tr>
	   						</thead>				
							<tbody>
							<%for(JuegoBase j : juegos){ 
								int id = j.getIdBase();
								if(id > 0) {
								%>
								<tr class="clickable-row odd table-info">
									<td style="display: none"><%=j.getIdBase() %></td> 
									<td class="sorting_1"><%=j.getNombre() %></td>
									<td><%=j.getDuracion() %></td>
									<td style="display:none"><%=j.getBgg() %>
									<td style="display:none"><%=j.getRutaImagen() %></td>
									<td><%=j.getMaxjugadores() %></td>
									<td><%=j.isPasaporte() %></td>
									<td class="w-25"><img src="<%=j.getRutaImagen()%>" class="img-fluid img-joc" alt="<%=j.getNombre() %>"></td>				
								</tr>
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

	
	<form class="form-signin" method="post" action="juegoController" id="AbrirForm" name="abrirForm">
		<div style="display: none">
			<input type="hidden" name="action" id="action"> 
			<input type="hidden" name="nombreT" id="nombreT">
			<button type="submit" class="btn btn-lg btn-primary btn-block" name="action" value="open"></button>
		</div>
	</form>
	</div>
	
	<div class="col-md-2"></div>
	</section>
	</div>
	
	<script src="plugins/datatables/jquery.dataTables.min.js"></script>
	<script src="plugins/datatables/dataTables.bootstrap.min.js"></script>
	<script type="text/javascript">
		jQuery(document).ready(function($) {
			$("li").removeClass("active");
			$("#liJuegos").addClass("active");
			$(".clickable-row").click(
					function() {
						var index = ($(this).index()) + 1;
						var id = $('.table tr:eq(' + index + ') td:eq(' + 0 + ')').text();
						$form = $("<form method='post' action='juegoController'></form>");
						$form.append('<input type="hidden" name="action" value="open" />');
						$form.append('<input type="hidden" name="id" value="' + id + '" />');
						$('body').append($form);
						$form.submit();
						});
			});
	</script>
</body>
</html>