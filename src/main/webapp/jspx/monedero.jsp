<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1" import="dto.Jeseta, java.util.*"%>
<%@ include file="cabecera.jsp"%>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Monedero</title>
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
%>

</head>
<body class="hold-transition sidebar-mini layout-boxed">
	<div class="content-wrapper">
		<section class="content-header">
			<h1>
				CRYPTOJESETAS <small>Tu monedero con CryptoJesetas</small>
			</h1>
		</section>
		<section class="content">
			<!-- Tabla con las jesetas y su descripcion -->
			<div class="col-md-12 col-xs-12">
			<%if(jesetas != null) {%>
				<div class="box box-default">
					<div class="box-header with-border">
	        	   		<h3 class="box-title">Tus Jesetas:</h3>
	        		</div><!-- box-header -->
	        		<div class="box-body">
	        			<div id="tablaJesetas" class="dataTables_wrapper form-inline dt-bootstrap">
	        				<table id="tablaJesetas" class="table table-striped w-auto table-bordered table-hover dataTable" role="grid">
	            			<thead>
	   							<tr role="row">
	   							<th class="sorting_asc" aria-sort="acending" aria-label="Nombre"
	   							colspan="1" rowspan="1" tabindex="0">Jeseta</th>
	   							<th style="display:none" class="sorting" aria-label="nJeseta" 
	   							colspan="1" rowspan="1" tabindes="0">NºJeseta</th>
	   							<th class="sorting" aria-label="Imagen" 
	   							colspan="1" rowspan="1" tabindes="0">Descripcion</th>
	   							<th style="display:none" class="sorting" aria-label="premiado" 
	   							colspan="1" rowspan="1" tabindes="0">Premiado</th>
	   							
	   							</tr>
	   						</thead>				
							<tbody>
							<%for(Jeseta j : jesetas){ 
								int id = j.getIdJeseta();
								if(id > 0) {
								%>
								<tr class="odd table-info">
									<td class="w-25"><img src="<%=j.getImagen()%>" class="img-fluid img-joc" alt="<%=j.getIdJeseta() %>"></td>	
									<td class="sorting_1" style="display:none"><%=j.getIdJeseta() %></td> 
									<td><%=j.getDescripcion() %></td>
									<td style="display:none"><%=j.isUsado()%></td>												
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

		</section>
	<script src="plugins/datatables/jquery.dataTables.min.js"></script>
	<script src="plugins/datatables/dataTables.bootstrap.min.js"></script>
</body>
</html>