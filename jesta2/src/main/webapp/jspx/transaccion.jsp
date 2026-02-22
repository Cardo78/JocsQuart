<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1" import="dto.Jeseta, java.util.*"%>
<%@ include file="cabecera.jsp"%>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<!-- CSS Principal -->	
<link rel="stylesheet" type="text/css" href="./css/style.css" />

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
	String pulsera = request.getAttribute("pulsera") == null ? "" : (String) request.getAttribute("pulsera");
	ArrayList<Jeseta> jesetas = (ArrayList<Jeseta>) request.getAttribute("jesetas");
	String resultadoJesetas = request.getAttribute("resultadoJesetas") == null ? "" : (String) request.getAttribute("resultadoJesetas");
%>

</head>
<body class="hold-transition sidebar-mini layout-boxed">
	<div class="content-wrapper">
		<section class="content-header">
			<h1>
				TRANSFERENCIAS <small>Sistema de transferencia de CryptoJesetas</small>
			</h1>
		</section>
		<section class="content">
	
		
		<!-- Mensaje de informacion de error  -->
		<div id="mensajeDiv">
				<%if(!mensajeIt.equals("") && resultadoJesetas.equals("")){ %>
					<div class="<%=tipoMensaje%> inner" style="text-align:center" align="center">
			  			<h3><%= mensajeIt %></h3>
					</div>	
				<%}%>	
		</div>
									
		<!-- Mensaje de Confirmacion traspaso -->
		<%
		if (!resultadoJesetas.equals("")) {
		%>
		<div class="col-md-12">		
		<div class="box box-default">
			<div class="box-header with-border">
				<h3 class="box-title">¡Confirmación de Transferencia!</h3>
			</div>
			<div class="box-body">
			<form class="form-signin" method="post" action="transaccionController">
				<div class="<%=tipoMensaje%> inner" style="text-align: center" align="center">
					<h2><%=mensajeIt%></h2>
				</div>
				<input type="hidden" name="resultadoJesetas" id="resultadoJesetas" value="<%=resultadoJesetas %>" />
				<input type="hidden" name="idpulsera" id="idpulsera" value="<%=pulsera %>" />
				<div class="box-footer">
				<div class="col-md-6 col-xs-12">
						<button type="submit" class="btn btn-lg btn-primary btn-success" id="btnConfirmar" name="action" value="confirmar">Confirmar Transacción</button>						
						<button type="submit" class="btn btn-lg btn-warning" id="btnCancelar" name="action" value="cancel" formnovalidate="formnovalidate">Cancelar Transacción</button>						
					</div>
				</div><!-- box-footer -->	
			</form>			
			</div><!-- box-body -->
		</div> <!-- Box Default -->
		</div>			
		<% } %>	
											
		<!-- Parametro de pulsera para realizar transferencia -->	
		<div class="col-md-8 col-xs-9 form-group">
			<label class="col-sm-4 control-label hidden-xs">Nº de Pulsera: </label>
			<div class="col-sm-8 col-xs-12">
    			<input type="number" class="form-control" id="idpulsera" name="idpulsera" 
    					placeholder="Nº Pulsera* (Obligatorio)" value="<%=pulsera%>" required>
    		</div>							
		</div>
		<div class="col-md-2 col-xs-2">
			<button class="btn btn-success btn-transferencia" id="btnTransferir" name="action" value="revisar">Transferir</button>
		</div>			
		
		<!-- Tabla con las jesetas y su descripcion -->
		<div class="col-md-12 col-xs-12">
		<%if(jesetas != null) {%>
		<table id="tablaJesetas" name="tablaJesetas" class="table table-striped table-bordered" role="grid">
	       	<thead>
	   			<tr class="tr-header">
	   				<th class="hidden-xs">Nº Referencia</th>
	   				<th>Jeseta</th>
	   				<th>Descripcion</th>	   							
	   				<th>Seleccionar</th>	   							
	   			</tr>
	   		</thead>				
			<tbody>
			<%for(Jeseta j : jesetas){ %>
				<tr class="tr-row">
					<td class="hidden-xs td-jeseta id-jeseta"><%=j.getIdJeseta() %></td> 
					<td class="td-jeseta"><img src="<%=j.getImagen()%>" class="img-fluid img-joc" alt="<%=j.getIdJeseta() %>"></td>	
					<td class="td-jeseta desc-jeseta"><%=j.getDescripcion() %></td>								
					<td class="td-jeseta check-jeseta"><input class="form-check-input checkbox-xl" type="checkbox" id="seleccionado" value="<%=j.isSeleccionado()%>"/></td>
				</tr>
			<%} %>	
			</tbody>
			</table>
			<%} %>		
			</div>		
	<!-- </form>  -->
</section>
</div>
<script type="text/javascript">
const btnTransferir = document.querySelector('.btn-transferencia');
//const formTransfer = document.querySelector('.form--transferencia');

btnTransferir.addEventListener('click', function(e){
	const tablaJesetas = document.getElementById("tablaJesetas");
	const tableRows = document.querySelectorAll('#tablaJesetas tr.tr-row');	
	
	var pulsera = document.getElementById("idpulsera").value;
	let jesetasSeleccionadas = [];	
	
	// Recorremos las filas que tengan el class="tr-row"
	for(let i=0; i<tableRows.length; i++) {
	  const row = tableRows[i];
	  const status = row.querySelector('input[type="checkbox"]:checked');	  
	  if(status != null){
	  	if(status.checked){		  
		  const id = row.querySelector('.id-jeseta');
		  console.log('Jeseta: ', id.innerText);
		  jesetasSeleccionadas.push(id.innerText);
	  	}
	  }
	  // Para modificar un estado:
	  // status.innerText = 'offline';
	}
	const cadenaJesetas = jesetasSeleccionadas.join('-');
	console.log(cadenaJesetas);
		
	$form = $("<form method='post' action='transaccionController' id='revisar' name='revisar'></form>");
	$form.append('<input type="hidden" name="idpulsera" id="idpulsera" value="' + pulsera + '" />');	
	$form.append('<input type="hidden" name="resultadoJesetas" id="resultadoJesetas" value="' + cadenaJesetas + '" />');	
	$form.append('<input type="hidden" name="action" id="action" value="revisar" />');
	$('body').append($form);
    $form.submit(); 
});


</script>		
		
</body>
</html>