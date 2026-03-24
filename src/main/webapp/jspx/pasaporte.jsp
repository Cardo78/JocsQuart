<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1" import="dto.Pasaporte, java.util.*"%>
<%@ include file="cabecera.jsp" %>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>PASAPORTE</title>
<%
	Usuario user = (Usuario) session.getAttribute("usuario");
	ArrayList<Pasaporte> pasaportes = (ArrayList<Pasaporte>) request.getAttribute("pasaporte");
	String pulsera = request.getAttribute("pulsera") == null ? "" : (String) request.getAttribute("pulsera");
	String jesetas = request.getAttribute("jesetas") == null ? "0" : (String) request.getAttribute("jesetas");
	String nombreJugador = request.getAttribute("nombreJugador") == null ? "" : (String)request.getAttribute("nombreJugador");
	String editorial = "";
	boolean infantil = request.getAttribute("infantil") == null ? false : (boolean)request.getAttribute("infantil");
%>
</head>
<body class="hold-transition sidebar-mini layout-boxed">
	<div class="content-wrapper">
		<section class="content-header">
		<%if(user == null || user.getTipo() != "JUGADOR"){ %>
		<div class="form-check">
  			<input class="form-check-input" type="checkbox" style="zoom:2" value="<%=infantil %>" id="infantil" onchange="javascript:checkPassport()" <%if(infantil) {%>checked <%} %>>
  			<label class="form-check-label" for="flexCheckDefault" style="zoom:2">Pasaporte infantil</label>
		</div>
		<%} %>
		<!-- Jesta 2023, comprobacion del pasaporte por sesion -->
			<%if(user != null && user.getTipo() != "JUGADOR") {%>
			
			<form class="form-horizontal" role="form" method="post" action="pasaporteController" id="formConsulta" name="formConsulta">
				<div class="row">
				<div class="col-md-2 col-xs-12">
				<h1>PASAPORTE</h1>
				</div>				
				<div class="col-md-8 col-xs-10 form-group">
					<label class="col-sm-4 control-label hidden-xs">Introduce Nº de Pulsera: </label>
					<div class="col-sm-8 col-xs-12">
    				<input type="text" class="form-control" id="pulsera" name="pulsera" 
    						placeholder="Nº Pulsera* (Obligatorio)" value="<%=pulsera%>" required>
    				</div>							
				</div>
				<div class="col-md-2 col-xs-2">
					<button type="submit" class="btn btn-success" id="btnBuscar" name="action" value="consulta">Buscar</button>
				</div>
				</div>	
			</form>
			
			<%} %>
		</section>	
		<section class="content">
		<!-- Consultamos para crear pasaporte -->
		<%if(pasaportes!=null){ %>
			<!-- Creamos un orden 0 para iniciar las cajas -->
			<%int orden = 0; %>		
			<!-- Por cada juego de pasaporte vamos a comprobar si la editorial es distinta -->
			<% for (Pasaporte juego : pasaportes){%>   
				<!-- Si hay cambio de orden se cierra la caja anterior y se crea una nueva caja -->	
				<% if(juego.getEditorial().getOrden() > orden) { %>
					<!-- Si no es la primera, se cierra la caja -->
					<%  if(orden!=0){ %>
						</div> <!-- div class body -->
					</div> <!-- div class box -->
					<%} %>
					<!-- Se crea la caja -->
					<% orden = juego.getEditorial().getOrden(); %>
					<div class="box box-default collapsed-box">
					<div class="box-header with-border">
					<h3 class="box-title"><%=juego.getEditorial().getNombre()%></h3>
					<img class="edit-logo" src="<%=juego.getEditorial().getLogo() %>" />
						<div class="box-tools pull-right">
							<button class="btn btn-box-tool" data-originial-title="minimizar" data-widget="collapse" data-toggle="tooltip" title="minimizar" style="zoom:2; color:red">
								<i class="fa fa-plus"></i>
							</button>
						</div>				
					</div> <!-- div class header -->
				<div class="box-body">
					<div class="row">										
				<%} %>
				
				
				
				<div class="col-md-3 col-xs-12 center">.
					
  						<div class="row">
  							<div class="col-md-4 col-xs-6">						
    						<a href="<%=juego.getBgg() %>" target="_blank">
								<img src="<%=juego.getRutaImagen() %>" alt="<%=juego.getNombre()%>" class="img-responsive img-thumbnail border" <%if(!juego.isJugado()){%> style="filter:grayscale(100%)"<%}%> />
							</a>
							</div>
							<div class="col-md-8 col-xs-6">			
								<h4 class="card-title"><%=juego.getNombre()%></h5>
								<div class="form-group">
								<div class="col-md-4 col-xs-4 icon-desc" style="padding: 0px;"><i class="fa fa-clock-o" aria-hidden="true"></i><span> <%=juego.getDuracion()%>' </span></div>
								<div class="col-md-4 col-xs-4 icon-desc" style="padding: 0px;"><i class="fa fa-users" aria-hidden="true"></i><span> 1-<%=juego.getMaxjugadores() %></span></div>
								<div class="col-md-4 col-xs-4 icon-desc" style="padding: 0px;"><i class="fa fa-child" aria-hidden="true"></i><span> <%=juego.getEdad() %></span></div>
								</div>
							</div>
  						</div>
  					</div>
				
			<%} %>
				</div>
				</div> <!-- div class body -->
			</div> <!-- div class box -->
		<%} %>			
				

		<div class="box box-default">
		<div class="col-md-12">			
			<div class="bg-green-active inner" style="text-align: center" align="center">	
					<h2><%=jesetas %> jesetas</h2>		
			</div>
		</div>	
		</div>
</section>
</div>
<script type="text/javascript">
function checkPassport(){	
	var element = document.getElementById("infantil");
	$form = $("<form method='post' action='pasaporteController' id='formConsulta' name='formConsulta'></form>");
	$form.append('<input type="hidden" name="infantil" id="infantil" value="' + element.checked + '" />');	
	$('body').append($form);
    $form.submit();     
}
</script>
</body>
</html>