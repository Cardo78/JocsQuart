<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@ include file="cabecera.jsp" %>

<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>EDITAR JUGADOR</title>
<%
Usuario user = null;
if(session.equals(null)){
	response.sendRedirect("./loginController");
}else{
	user = (Usuario) session.getAttribute("usuario");
	if(user == null){
		response.sendRedirect("./loginController");
	}else{
		if(!(user.getTipo() == "ADMIN" || user.getTipo() == "NORMAL")){
			response.sendRedirect("./loginController");
		}
	}
}

String id = (request.getAttribute("id")== null )? "": (String)request.getAttribute("id");
String nombre = (request.getAttribute("nombre") == null ) ? "": (String)request.getAttribute("nombre");
String apellidos = (request.getAttribute("apellidos") == null ) ? "": (String)request.getAttribute("apellidos");
String dni = (request.getAttribute("dni") == null ) ? "": (String)request.getAttribute("dni");
String email = (request.getAttribute("email") == null ) ? "": (String)request.getAttribute("email");
String telefono = (request.getAttribute("telefono") == null ) ? "": (String)request.getAttribute("telefono");
String cp = (request.getAttribute("cp") == null ) ? "": (String)request.getAttribute("cp");
String pulsera = (request.getAttribute("pulsera") == null ) ? "": (String)request.getAttribute("pulsera");
boolean infantil = false;
if(request.getAttribute("infantil") != null){
	infantil = (boolean)request.getAttribute("infantil");
}

%>
</head>
<body class="hold-transition sidebar-mini layout-boxed">
<div class="content-wrapper">
		<section class="content-header">
	    	<h1>Edición Usuario</h1>		
		</section>
		
		<section class="content">
			<div class="box box-primary">
    			<div class="box-header with-border">
    				<h3 class="box-title">Editar:</h3>
    			</div> <!-- box-header -->
    			<div class="box-body">
    			<form class="form-signin" method="post" action="jugadorController" id="formJugador" name="formJugador">
					<div class="form-group" style="display: none">
						<label for="id">ID:</label>
						<input type="text" class="form-contol" id="id" name="id" placeholder="Id"	
						value="<%=id%>">
					</div>
					<div class="form-group">
						<label for="nombre">Nombre*: (obligatorio)</label> 
						<input type="text" class="form-control" id="nombre" name="nombre" placeholder="Nombre" 
						value="<%=nombre%>" required>
					</div>
					<div class="form-group">
						<label for="apellido1">Apellidos*: (obligatorio)</label> 
						<input type="text" class="form-control" id="apellidos" name="apellidos"
						placeholder="Primer apellido" value="<%=apellidos%>" required>
					</div>
					<div class="form-group">
						<label for="email">e-mail:</label> 
						<input type="text" class="form-control" id="email" name="email" placeholder="e-mail"
						value="<%=email%>">
					</div>
					<div class="form-group">
						<label for="telefono">Teléfono:</label> 
						<input type="text" class="form-control" id="telefono" name="telefono"
						placeholder="Teléfono" value="<%=telefono%>">
					</div>
					<div class="form-group">
						<label for="cp">Código postal*: (obligatorio)</label> 
						<input type="number" class="form-control" id="cp" name="cp" placeholder="Código postal"
						value="<%=cp%>" required>
					</div>
					<div class="form-group">
						<!-- <label for="dni">DNI*:	 (Si el usuario no tiene DNI todavía, guardar su fecha de nacimiento con el siguiente formato: ddmmyyyy)</label> -->
						<div class="col-md-6 form-group">
						 	<label for="dni">DNI:* (obligatorio, aplicar el valor que os digan cuando es infantil)</label>
							<input type="text" class="form-control" id="dni" name="dni" placeholder="DNI"
							value="<%=dni%>" required>
						</div>
						<div class="col-md-6">   						
    						<div class="form-check">
								<label>¿Es Infantil? </label> 
								<input type="checkbox" placeholder="Pasaporte Infantil" id="infantil" name="infantil" <%if(infantil) {%>checked <%} %>/>
							</div>
							<div class="form-check">
								<label>¿Usuario Preinscrito? </label>
								<input type="checkbox" id="preinscrito" name="preinscrito"/>		
							</div>
						</div>	
						<div class="col-md-12">
													
						</div>											
					</div>
					<div class="form-group">
						<label for="pulsera">Pulsera:</label> 
						<input type="text" class="form-control" id="pulsera" name="pulsera"
						value="<%=pulsera%>" placeholder="Escanear código" required
						pattern=".*\S+.*">
												
					</div>
    				<div class="form-group checkbox col-md-12" align="center">
						<input type="checkbox" id="aceptacion" name="aceptacion" />
						<label for="aceptacion">El usuario ha leído y acepta los términos de la política de Privacidad. Dichos términos puede leerse de forma física o a través del siguiente enlace:</label>
						<a href="./docs/LOPD.pdf">Política de Privacidad</a>					
					</div>
					<button type="submit" class="btn btn-success" id="btnConfirmar" name="action" value="save" disabled>Confirmar</button>
					<button type="submit" class="btn btn-danger" id="btnCancelar" name="action" value="cancel" formnovalidate="formnovalidate">Cancelar</button>
				</form>
    			</div> <!-- box-body -->
    		</div> <!-- box -->
		</section>
	</div>
	
	<script src="./assets/js/jquery.scannerdetection.js"></script>
	<script type="text/javascript">

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
	
	//Script para permitir modificar el ID de la pulsera
	$( document ).ready(function() {		
		$("li").removeClass("active");
		$("#liInscripciones").addClass("active");
		var pulsera = "<%=pulsera%>";
		//Todos los campos son editables excepto la pulsera que solo podrán editarla los VLADA						
		if(pulsera==""){
				$("#pulsera").removeAttr( "readonly" );
		}
		//Si la pulsera ya ha sido asignada, bloqueamos el botón "Confirmar"
		else {				
			if(user.getTipo() == "ADMIN"){
				$("#pulsera").removeAttr( "readonly" );
			}
			else{
				$("#pulsera").attr( "readonly", "readonly" );
			}				
		}						
	});
	
	//Script para la aceptación de los términos de LOPD
	$("#aceptacion").click(function() {
		if($("#aceptacion:checked").val())				
			document.getElementById('btnConfirmar').disabled = false;
		else
			document.getElementById('btnConfirmar').disabled = true;
	});
	
	</script>
</body>
</html>