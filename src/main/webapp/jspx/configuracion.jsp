<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1" import="dto.Ubicacion, dto.Juego, dto.Editorial, dto.enums.Color, java.util.*"%>
<%@include file="cabecera.jsp" %>

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Configuracion</title>

<% 
	Usuario user = null;
	if(session.equals(null)){
		response.sendRedirect("./loginController");
	}else{
		user = (Usuario) session.getAttribute("usuario");
		if(user == null){
			response.sendRedirect("./loginController");
		}else{
			if(!(user.getTipo() == "ADMIN")){
				response.sendRedirect("./loginController");
			}
		}
	}
	
	String mensajeIt = request.getAttribute("mensaje") == null ? "" : (String)request.getAttribute("mensaje");
	String tipoMensaje = request.getAttribute("tipoMensaje") == null ? "" : (String) request.getAttribute("tipoMensaje");
	
	String imagen = (session.getAttribute("logo") == null) ? "" : (String)session.getAttribute("logo");
	ArrayList<Ubicacion> ubicaciones = (ArrayList<Ubicacion>) request.getAttribute("ubicaciones");
	ArrayList<Usuario> usuarios = (ArrayList<Usuario>) request.getAttribute("usuarios");
	ArrayList<Editorial> editoriales = (ArrayList<Editorial>) request.getAttribute("editoriales");	
	List<Color> colores = Arrays.asList(Color.values());
	
	int idUser = (request.getAttribute("idUser") == null) ? -1 : (int)request.getAttribute("idUser");	
	String nomUser = (request.getAttribute("nomUser") == null) ? "": (String)request.getAttribute("nomUser");
	String passUser = (request.getAttribute("passUser") == null) ? "": (String)request.getAttribute("passUser");
	String colorUser = (request.getAttribute("colorUser") == null) ? Color.BLACK.getCSSClass() : (String)request.getAttribute("colorUser");
	int cryptoJesetas = (request.getAttribute("jesetasUser") == null) ? 0 : (int)request.getAttribute("jesetasUser");
	
	int idEditorial = (request.getAttribute("idEditorial") == null) ? -1 : (int)request.getAttribute("idEditorial");
	String nomEditorial = (request.getAttribute("nomEditorial") == null) ? "": (String)request.getAttribute("nomEditorial");
	String logoEditorial = (request.getAttribute("logoEditorial") == null) ? "": (String)request.getAttribute("logoEditorial");
	int ordenEditorial = (request.getAttribute("ordenEditorial") == null) ? 99 : (int)request.getAttribute("ordenEditorial");
	
	int cryptoAdmin = (request.getAttribute("jesetasAdmin") == null) ? 0 : (int)request.getAttribute("jesetasAdmin");

	
 %>

</head>
<body class="layout-boxed hold-transition">
	<div class="content-wrapper">
		<section class="content-header">
	    	<h1>Página Administrador</h1>		
		</section>
		
		<section class="content">
		
			<div id="mensajeDiv">
				<%if(!mensajeIt.equals("")){ %>
				<div class="<%=tipoMensaje%> inner" style="text-align:center" align="center">
					<h3><%= mensajeIt %></h3>
				</div>	
				<%}%>	
			</div>
			
			<div class="col-md-6 col-xs-12"> <!-- column 6 Editor de usuarios -->
			<div class="box box-default">
    			<div class="box-header with-border">
    				<h3 class="box-title">Editor de usuarios:</h3>
    			</div> <!-- box-header -->
    		<div class="box-body">   			
    			<form class="form-signin" method="post" action="configController" id="configControlUsuario" name="configControlUsuario">
    				    				
    				<%if(usuarios != null){ %>
    				<div class="col-md-12 form-group">
    					<label>Usuarios:</label>							    
    					<select class="form-control" id="select_usuario" name="select_usuario" onchange="javascript:changeUser()" >
    						<option value=""></option>  											
    						<% for (Usuario userSelect : usuarios) {%>
    							<option value="<%=userSelect.getId() %>" <%if(userSelect.getId()==idUser){%>selected<%} %>><%=userSelect.getUser() %></option>  
    						<%} %>  								
    					</select>    					    						    					
    				</div>
    				<%} %>
    				
    				 <div class="form-group" style="display: none">
							<input type="text" class="form-contol" id="idUser" name="idUser" 
							value="<%=idUser %>"/>
					</div>    				
    				<div class="form-group col-md-12">    					
    					<label for="nombre">Nombre Usuario: (obligatorio)</label> 
						<input type="text" class="form-control" id="nomUser" name="nomUser" placeholder="Nombre Usuario" 
						value="<%=nomUser %>"/>
    				</div>
    				<div class="form-group col-md-12">
    					<label for="nombre">Contraseña: (obligatorio)</label> 
						<input type="text" class="form-control" id="passUser" name="passUser" placeholder="Contraseña" 
						value="<%=passUser %>"/>
    				</div>
    				
    				<div class="form-group col-md-12">    					
    					<div class="col-md-6">								
							<label>Colores:</label>								    
    						<select class="form-control" id="select_color" name="select_color" >  											
    							<% for (Color coloraux : colores) {%>
    								<option value="<%=coloraux%>" <%if(coloraux.getCSSClass()==colorUser){%>selected<%} %>><%=coloraux.name() %></option>
    							<% } %>
    						</select>						
    					</div>
    					<div class="form-group col-md-6">
    						<label for="nombre">CryptoJesestas: </label> 
							<input type="text" class="form-control" id="jesetas" name="jesetas" placeholder="jesetas" value="<%=cryptoJesetas %>"/>
    					</div>					
    				</div>
    				<p align="right">
    					<button type="submit" class="btn btn-success" id="btnConfirmar" name="action" value="saveUser">Guardar</button>
						<button type="submit" class="btn btn-danger" id="btnCancelar" name="action" value="cancel" formnovalidate="formnovalidate">Cancelar</button>
					</p>
    			</form>
    		</div>
    	</div>
    	</div>
    	
    	<div class="col-md-6 col-xs-12"> <!-- column 6 Editor de Editoriales -->
		<div class="box box-default">
    		<div class="box-header with-border">
    			<h3 class="box-title">Editor de editoriales:</h3>
    		</div> <!-- box-header -->
    		<div class="box-body">   			
    			<form class="form-signin col-md-10" method="post" action="configController" id="configControlEditorial" name="configControlEditorial">    			
    			<%if(editoriales != null){ %>
    				<div class="form-group col-md-12">
    					<label>Editoriales:</label>							    
    					<select class="form-control" id="select_editorial" name="select_editorial" onchange="javascript:changeEditorial()" >
    					<option value=""></option>  	  											
    					<% for (Editorial edSelect : editoriales) {%>
    							<option value="<%=edSelect.getId() %>" <%if(edSelect.getId()==idEditorial){%>selected<%} %>><%=edSelect.getNombre() %></option> 
    					<%} %>    	   								
    					</select>
    				</div>
    				<%} %>
    				<div class="form-group" style="display: none">
						<input type="text" class="form-contol" id="idEditorial" name="idEditorial" value="<%=idEditorial %>"/>
					</div> 
    				<div class="form-group col-md-12">    					
    					<label for="nombre">Nombre Editorial: (obligatorio)</label> 
						<input type="text" class="form-control" id="nomEditorial" name="nomEditorial" placeholder="Nombre Editorial" 
						value="<%=nomEditorial %>" required>
    				</div>
    				<div class="form-group col-md-12">
    					<label for="nombre">Logo Editorial: </label> 
						<input type="text" class="form-control" id="logoEditorial" name="logoEditorial" placeholder="Url Logo Editorial" 
						value="<%=logoEditorial %>">
    				</div>
    				<div class="form-group col-md-12">
    					<label for="nombre">Orden de muestreo: </label> 
						<input type="text" class="form-control" id="ordenEditorial" name="ordenEditorial" placeholder="Orden de Muestreo Pasaporte" 
						value="<%=ordenEditorial %>">
    				</div>
    				    				
    				<p align="right">
    					<button type="submit" class="btn btn-success" id="btnConfirmar" name="action" value="saveEditorial">Guardar</button>
						<button type="submit" class="btn btn-danger" id="btnCancelar" name="action" value="cancel" formnov.alidate="formnovalidate">Cancelar</button>
					</p>
    			</form>
    			
    			<div class="col-md-2"><!-- column 2 Cuadro de Imagen del logo -->
    				<div class="form-group">    				
    					<h3 class="box-title">Imagen:</h3>    				    				
    					<img src="<%=logoEditorial%>" class="img-responsive" alt="<%=nomEditorial%>">    				
    				</div>
    			</div> <!-- column 2 Cuadro de Imagen del juego -->
    			
    		</div>
    	</div>
    </div>
    
    <div class="col-md-6 col-xs-12"> <!-- column 6 Editor de Jesetas -->
		<div class="box box-default">
    		<div class="box-header with-border">
    			<h3 class="box-title">Añadir Jesetas:</h3>
    		</div> <!-- box-header -->
    		<div class="box-body">
    			<form class="form-horizontal" role="form" method="post" action="configController" id="configControllerJesetas" name="configControllerJesetas">
    			<div class="form-group col-md-6"> 
    				<img src="<%=imagen%>"/>
    			</div>
    			<div class="form-group col-md-6">
    				<label for="nombre">CryptoJesetas: </label> 
					<input type="text" class="form-control" id="jesetas" name="jesetas" placeholder="jesetas" value="<%=cryptoAdmin %>"/>
    			</div>					    		
    			<p align="right">
    				<button type="submit" class="btn btn-success" id="btnConfirmar" name="action" value="anyadirJesetas">Guardar</button>
					<button type="submit" class="btn btn-danger" id="btnCancelar" name="action" value="cancel" formnovalidate="formnovalidate">Cancelar</button>
				</p>
				</form>
    		</div>
    	</div>   
    </div>
    
    <%if(ubicaciones != null) {%>
    <div class="col-md-6 col-xs-12">  	         	
    	<div class="box box-default">
    		<div class="box-header with-border">
    			<h3 class="box-title">Ubicaciones:</h3>
    		</div> <!-- box-header -->
    	</div>
    	<div class="box-body">
    		<form class="form-horizontal" role="form" method="post" action="configController" id="configControlUb" name="configUbicaciones">   
			<label>Ubicación:</label>
    		<select id="select_ubicacion" class="form-control" name="select_ubicacion">
    			<% for (Ubicacion t : ubicaciones) {%>
    			<option value="<%=t.getId() %>"	<%if(t.getId()==1){%>selected<%} %>><%=t.getNombre() %></option> 
    			<% } %>
    		</select>    													    									     	
      		<button type="submit" class="btn btn-success" id="btnConfirmar" name="action" value="disponibilidades">Cargar Disponibles</button>	
			<a data-toggle="modal" href="#ntipo" class="btn btn-primary btn-large">Nueva Ubicación</a>
			</form>
		</div>	
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
						<p><label>Nombre:</label>
        				<input type="text" class="form-control" id="nombreUbicacion" name="nombreUbicacion"	placeholder="Nombre*" value="" required>
    					</p>            
    				</div>
    				<div class="modal-footer">
    					<button class="btn btn-outline pull-left" type="button" data-dismiss="modal">Cerrar</button>
        				<button type="button" class="btn btn-outline" onclick="anyadirUbicacion()" data-dismiss="modal">Confirmar</button>
    				</div>
    			</div>
		</div><!-- Div Class Tipo -->

	</div>
	<% } %>

</section>
</div>

<script type="text/javascript">
	function anyadirUbicacion(){
		var sel = document.getElementById("select_ubicacion");
		var opt = document.createElement('option');
		opt.value = 0;
		opt.text = document.getElementById("nombreUbicacion").value;
		opt.selected = true;
		sel.appendChild(opt);
	}
	
	function changeUser(){		
		var userSelected = document.getElementById("select_usuario");
		var idselected = userSelected.value; 
		$form = $("<form method='post' action='configController'></form>");
		$form.append('<input type="hidden" name="action" value="cargaUser" />');
		$form.append('<input type="hidden" id="idUser" name="idUser" value="' + idselected + '" />');	
				
		$('body').append($form);
		$form.submit();
		
	}
	
	function changeEditorial(){
		var edSelected = document.getElementById("select_editorial");
		var idselected = edSelected.value; 
		$form = $("<form method='post' action='configController'></form>");
		$form.append('<input type="hidden" name="action" value="cargaEditorial" />');
		$form.append('<input type="hidden" id="idEditorial" name="idEditorial" value="' + idselected + '" />');	
				
		$('body').append($form);
		$form.submit();
	}
	</script>
	
	</body>
</html>