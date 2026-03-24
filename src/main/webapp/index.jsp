<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"
	import="dto.Editorial, java.util.*"%>
<%@ include file="jspx/cabecera.jsp"%>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<meta http-equiv="X-UA-Compatible" content="IE=edge">
<meta name="viewport"
	content="width=device-width, initial-scale=1, maximum-scale=1, user-scalable=no">
<meta name="description" content="Pagina Inicio">
<meta name="author" content="Ricardo Cardona">


<%
	String mensaje = (request.getAttribute("mensaje") == null) ? "" : (String) request.getAttribute("mensaje");
	String imagen = (session.getAttribute("logo") == null) ? "" : (String) session.getAttribute("logo");
	String tituloPagina = (session.getAttribute("nombre") == null) ? "" : (String) session.getAttribute("nombre");
	ArrayList<Editorial> editoriales = (ArrayList<Editorial>) request.getAttribute("editoriales");		
%>

<input type="hidden" id="index" value="<% if(editoriales != null ){ %>completo<% }else{ %>vacio<% }%>">

<title><%=tituloPagina%></title>
</head>

<body class="hold-transition sidebar-mini layout-boxed">
	<!-- <div class="jumbotron text-center">  -->
	<!-- <h1>Aplicación de Jocs Quart</h1>
	<p>Sistema de gestión de jugadores y alquileres</p> -->
	
	<div class="container-fluid">
	
	<div class="col-md-12 col-xs-12 text-center img-index">	
		<img src="images/Jesta.jpg" class="img-index img-responsive center-block" alt="Responsive image">	
				
	</div>
	<div class="col-md-12 col-xs-12 text-center img-index">
		<img src="images/patrocinadores.png" class="img-index img-responsive center-block" alt="Responsive image">
	</div>
	<div class="col-md-5 col-xs-12 text-center img-index">
		<img src="images/editoriales01.jpg" class="img-index img-responsive center-block" alt="Responsive image">
	</div>
	<div class="col-md-5 col-xs-12 text-center img-index">
		<img src="images/editoriales02.jpg" class="img-index img-responsive center-block" alt="Responsive image">
	</div>
	<div class="col-md-2 col-xs-6 text-center img-index">
		<img src="images/editoriales03.jpg" class="img-index img-responsive center-block" alt="Responsive image">
	</div>
	</div>
	
</body>

<script type="text/javascript">

window.onload=function() {
	var index = $("#index").val();
	console.log(index);
	if(index == "vacio"){
		console.log("ejecucion");
		//location.href="./menuController?section=0";
		//location.reload();				
	}
}

	$(document).ready(function() {
		$("li").removeClass("active");
		$("#liInicio").addClass("active");
	});
</script>
</html>
