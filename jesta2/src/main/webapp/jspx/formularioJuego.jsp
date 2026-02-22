<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1" import="dto.JuegoBase, dto.Ubicacion, dto.Editorial, dto.Juego, java.util.*"%>
<%@ include file="cabecera.jsp"%>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Formulario Juego</title>

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
	String mensajeEAN = request.getAttribute("mensajeEAN") == null ? "" : (String) request.getAttribute("mensajeEAN");
	String tipoMensaje = request.getAttribute("tipoMensaje") == null ? "" : (String) request.getAttribute("tipoMensaje");
	ArrayList<Ubicacion> ubicaciones = (ArrayList<Ubicacion>) request.getAttribute("ubicaciones");
	ArrayList<Editorial> editoriales = (ArrayList<Editorial>) request.getAttribute("editoriales");
	ArrayList<Juego> codigos = (ArrayList<Juego>) request.getAttribute("codigos");
	
	int id = (request.getAttribute("id") == null )? 0 : (int)request.getAttribute("id");
	String nombre = (request.getAttribute("nombre") == null) ? "": (String)request.getAttribute("nombre");
	int duracion = (request.getAttribute("duracion") == null) ? 45: (int)request.getAttribute("duracion");
	int maxjugadores = (request.getAttribute("max") == null) ? 10: (int)request.getAttribute("max");
	String imagen = (request.getAttribute("imagen") == null) ? "": (String)request.getAttribute("imagen");
	String bgg = (request.getAttribute("bgg") == null) ? "": (String)request.getAttribute("bgg");
	int idEditorial = (request.getAttribute("idEditorial") == null) ? 0: (int)request.getAttribute("idEditorial");
	boolean pasaporte = false;
	if(request.getAttribute("pasaporte") != null){
		pasaporte = (boolean)request.getAttribute("pasaporte");
	}
	boolean infantil = false;
	if(request.getAttribute("infantil") != null){
		infantil = (boolean)request.getAttribute("infantil");
	}
	int edad = (request.getAttribute("edad") == null)? 0 : (int)request.getAttribute("edad");

%>
</head>


<body>
<body class="hold-transition sidebar-mini layout-boxed">  
	<div class="content-wrapper">
		<section class="content-header">
	    	<h1>Edición Juego <small>Buscar el juego antes de crear un nuevo, ya que duplicar un juego de pasaporte, ¡la liaríamos y mucho! </small></h1>		
		</section>
		
		<section class="content">
			<div class="col-md-10"> <!-- column 10 Edición de juego -->
			<div class="box box-default">
    			<div class="box-header with-border">
    				<h3 class="box-title">Juego:</h3>
    			</div> <!-- box-header -->
    			<div class="box-body">
    			    <div id="mensajeDiv">
						<%if(!mensajeIt.equals("")){ %>
						<div class="<%=tipoMensaje%> inner" style="text-align:center" align="center">
			  				<h3><%= mensajeIt %></h3>
						</div>	
						<%}%>	
					</div>
    				<form class="form-horizontal" role="form" method="post" action="juegoController" id="myForm" name="myForm">
    				<div class="col-md-12">
    					<div class="form-group" style="display: none">
							<input type="text" class="form-contol" id="id" name="id" 
							placeholder="Id" value="<%=id%>">
						</div>
						
    					<div class="form-group col-md-12">
    					    <label>Nombre Juego: </label>
    						<input type="text" class="form-control" id="nombre" name="nombre" 
    						placeholder="Nombre*" value="<%=nombre%>" required>
						</div>
						
						<div class="form-group">
							<div class="col-md-4">								
								<label>Duración mínima: </label>
								<input type="number" class="form-control" id="duracion" name="duracion" placeholder="Duración en minutos*" value="<%=duracion%>" required>								
							</div>
							<div class="col-md-4">															
								<label>Máximo Nº de Jugadores(20 como máximo): </label>
								<input type="number" class="form-control" id="jugMax" name="jugMax"	placeholder="Número de jugadores Número de jugadores máximo*" value="<%=maxjugadores%>" required>								
							</div>
							<div class="col-md-4">															
								<label>Edad mínima jugador: </label>
								<input type="number" class="form-control" id="edad" name="edad"	placeholder="Edad mínima de jugador*" value="<%=edad%>" required>								
							</div>
						</div>
							
						<div class="row form-group">
							<div class="col-md-6">								
								<label>BGG: </label>
								<input type="text" class="form-control" id="bgg" name="bgg" placeholder="Link a la BGG" value="<%=bgg%>">									
							</div>
							<div class="col-md-3">								
								<label>Editorial:</label>
								<% if(editoriales!=null){%>    	
    							<select class="form-control" id="select_editorial" name="select_editorial" >  											
    								<% for (Editorial ed : editoriales) {%>
    									<option value="<%=ed.getId() %>"
    									<%if(ed.getId()==idEditorial){%>selected<%} %>><%=ed.getNombre() %></option>
    								<% } %>
    							</select>
    							<%} %>    						
    						</div>
    						<div class="col-md-3">    						
    							<div class="form-check">
    								<label>¿Es juego de Pasaporte? </label>
									<input type="checkbox" id="pasaporte" name="pasaporte" <%if(pasaporte) {%>checked <%} %>/>	
								</div>
								<div class="form-check">
									<label>¿Es juego de Pasaporte Infantil? </label>
									<input type="checkbox" id="infantil" name="infantil" onchange="javascript:checkPassport()" <%if(infantil) {%>checked <%} %>/>		
								</div>
							</div>
						</div>
						
						<div class="form-group col-md-12">
							<label>Imagen del juego:</label>			
							<input type="text" class="form-control" id="imagen" name="imagen" 
							value="<%=imagen%>" required>
						</div>
												
						<div class="form-group col-md-6">
								<!-- <button type="submit" class="btn btn-exclamation" onclick="javascript:showContent()">Mostrar Códigos </button>  -->
    							<input type="checkbox" id=boolUbicacion name="boolUbicacion" onchange="javascript:showContent()"  <%if(id > 0) {%>checked <%} %>/>
    							<span>Ver Codigos</span>
    					</div>
    					    					    											
						<p align="center">
							<button type="submit" class="btn btn-success" id="btnConfirmar" name="action" value="save">Confirmar</button>
							<button type="submit" class="btn btn-danger" id="btnCancelar" name="action" value="cancel" formnovalidate="formnovalidate">Cancelar</button>
						</p>
					</div>
					</form>		 											
    			</div> <!-- box-body -->
    		</div> <!-- box -->
    		</div> <!-- column 10 Edición de juego -->
    		<div class="col-md-2"><!-- column 2 Cuadro de Imagen del juego -->
    			<div class="box box-default">
    				<div class="box-header with-border">
    					<h3 class="box-title">Imagen:</h3>
    				</div> <!-- box-header -->
    				<div class="box-body">
    					<img src="<%=imagen%>" class="img-responsive" alt="<%=nombre%>">
    				</div>
    			</div>
    		</div> <!-- column 2 Cuadro de Imagen del juego -->
    		
    		<div class="col-md-10">
    			<div class="box box-default" id="div_ubicacion" style="display: <%if(id > 0) {%>block <%} else {%>none <%}%> ">
    				<div class="box-header with-border">
    					<h3 class="box-title">Códigos de Juegos:</h3>
    				</div> <!-- box-header -->
    				<div class="box-body"> 			
    						<!--  CODIGOS DE BARRAS EDICION -->
    					    <% if(codigos!=null){ %>
    							<% for (Juego joc : codigos){%>
    							<form class="form-horizontal" role="form" method="post" action="juegoController" id="myFormCodigo" name="myFormCodigo" novalidate="novalidate">  
    								<div class="form-group">
    									<div class="form-group" style="display: none">
											<input type="text" class="form-contol" id="id" name="id" value="<%=joc.getId()%>">
										</div>
										<div class="form-group" style="display: none">
											<input type="text" class="form-contol" id="idBase" name="idBase" value="<%=id%>">
										</div>
    									<div class="col-md-5">				
											<label>Codigo*: (Obligatorio)</label>
											<input type="text" id="codigo" name="codigo" class="form-control" value="<%=joc.getEan13()%>" 
											placeholder="Escanear código" pattern=".*\S+.*" required>								
										</div>										
										<div class="col-md-3">
											<label>Ubicación:</label>
											<% if(ubicaciones!=null){%>    	
    											<select class="form-control" id="select_ubicacion" name="select_ubicacion" >  											
    											<% for (Ubicacion t : ubicaciones) {%>
    												<option value="<%=t.getId() %>"
    												<%if(t.getId()==joc.getUbicacion().getId()){%>selected<%} %>><%=t.getNombre() %></option>
    											<% } %>
    											</select>
    										<%} %>
    									</div>
    									<div class="col-md-2">	
    										<label>Retirado </label>
											<input type="checkbox" id="retirado" name="retirado" <%if(joc.isEntregado()){%> checked <%}%> /> 
    									</div>
    									<div class="col-md-2">
    										<button type="submit" class="btn btn-success" id="btnModCodi" name="action" value="editar">Modificar</button>
    									</div>
    								</div>
    						
    							</form>	
    							<%} %>					    					 						
    						<%} %>   
    						<form class="form-horizontal" role="form" method="post" action="juegoController" id="nuevoCodigo" name="nuevoCodigo" novalidate="novalidate">   	
    						  			
    						<!--  CODIGOS DE BARRAS NUEVO -->
    						<div class="form-group">
    						    <div class="form-group" style="display: none">
									<input type="text" class="form-contol" id="idBase" name="idBase" value="<%=id%>">
								</div>
    							<div class="col-md-5">										
    								<label>Codigo*: (obligatorio)</label>
									<input type="text" class="form-control" id="ean13" name="ean13" placeholder="Escanear código">
								</div>
								<div class="col-md-3">
									<label>Ubicación:</label>
										<% if(ubicaciones!=null) {%>    					
    									<select id="select_ubicacion" class="form-control" name="select_ubicacion">
    										<% for (Ubicacion t : ubicaciones) {%>
    										<option value="<%=t.getId() %>"
    										<%if(t.getId()==1){%>selected<%} %>><%=t.getNombre() %></option>
    										<% } %>
    									</select>    									
    									<% } %>
    								</div>
    							<div class="col-md-2">
    								<label>Retirado </label>
									<input type="checkbox" id="retirado" name="retirado" disabled/>
    							</div>
    							<div class="col-md-2">
    								<button type="submit" class="btn btn-success" id="btnGrabaCodigo" name="action" value="nuevoCodigo">Guardar</button>
    							</div>
    								
		    				</div>
		    				
    						<%if(!mensajeEAN.equals("")){ %>
    						<div id="mensajeDiv">
    							<div class="<%=tipoMensaje%> inner" style="text-align:center" align="center"><h3><%= mensajeEAN %></h3></div>
    						</div>					
							<%}%>	
										
		    				<!-- VENTANA MODAL DE NUEVAS UBICACIONES -->	    				
    		    			<div id="ntipo" class="modal modal-primary" style="display: none;">
								<div class="modal-dialog">
									<div class="modal-content">
										<div class="modal-header">
        									<a data-dismiss="modal" class="close">×</a>
			        						<h3>Nueva Ubicación</h3>
     									</div>
									</div>
								<div class="modal-body">
									<p>
        							<label>Nombre:</label>
        							<input type="text" class="form-control" id="nombreUbicacion" name="nombreUbicacion" 
    								placeholder="Nombre*" value="" required>    								
    								</p>            
    							</div>
    							<div class="modal-footer">
    								<button class="btn btn-outline pull-left" type="button" data-dismiss="modal">Cerrar</button>
        							<button type="button" class="btn btn-outline" onclick="anyadirUbicacion()" data-dismiss="modal">Confirmar</button>
    							</div>
    						</div>
						</div><!-- Div Class Tipo -->	
    					</form>
    				</div>
    			</div>
    		</div> <!-- column 12 Códigos del Juego -->
    		
    	</section>
    </div>

<script src="assets/js/ie10-viewport-bug-workaround.js"></script>
<script src="assets/js/jasny-bootstrap.min.js"></script>
<script src="assets/js/ie-emulation-modes-warning.js"></script>
<script src="assets/js/jquery.scannerdetection.js"></script>


<script type="text/javascript">
$(document).ready(function() {
	$("#nombreUbicacion").val($("#select_ubicacion option:first").text());
	
	$( "#select_ubicacion" ).change(function() {
		$("#nombreUbicacion").val($("#select_ubicacion option:selected").text());			
	});
	$("li").removeClass("active");
	$("#liJuegos").addClass("active");	
});

	$(document).scannerDetection({
		timeBeforeScanTest: 200, // wait for the next character for upto 200ms
		startChar: [120], // Prefix character for the cabled scanner (OPL6845R)
		endChar: [13], // be sure the scan is complete if key 13 (enter) is detected
		avgTimeByChar: 40, // it's not a barcode if a character takes longer than 40ms
		//onComplete: function(barcode, qty){ alert(1); }, // main callback function
		onKeyDetect: function(event){
			console.log(event.which);
			if(event.which == 13){
				$("#juego").removeAttr( "readonly" );
				$("#juego").focus();
			}
			return false;
		}
	});	
	
	function checkPassport(){
		element = document.getElementById("pasaporte");
		check = document.getElementById("infantil");
		if(check.checked){
			element.checked = true;
		}
	}
	
	function showContent(){		
		element = document.getElementById("div_ubicacion");
		check = document.getElementById("boolUbicacion");
		if(check.checked){
			element.style.display='block';
		}
		else{
			element.style.display='none';
		}
	}
	

	
	function editarCodigo(idJuego){
		debugger;
		$form = $("<form method='post' action='juegoController'></form>");
		$form.append('<input type="hidden" name="action" value="editar" />');
		$form.append('<input type="hidden" name="codigo" value="' + idJuego + '" />');	
		$('body').append($form);
        $form.submit(); 
	}
	
</script>

</body>
</body>
</html>