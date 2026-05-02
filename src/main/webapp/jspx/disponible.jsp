<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1" import="dto.Pasaporte, java.util.*"%>
<%@ include file="cabecera.jsp" %>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<!-- CSS Principal -->	
<link rel="stylesheet" type="text/css" href="./css/style.css" />

<title>DISPONIBLES</title>
<%			
	Usuario user = (Usuario) session.getAttribute("usuario");
	//Integer cuenta = request.getAttribute("contador") !=  null ? (Integer) request.getAttribute("contador") : 0;
	//ArrayList<Ubicacion> ubicaciones = (ArrayList<Ubicacion>) request.getAttribute("ubicaciones");
	ArrayList<Pasaporte> disponibles = (ArrayList<Pasaporte>) request.getAttribute("disponibles");
	boolean infantil = request.getAttribute("infantil") == null ? false : (boolean)request.getAttribute("infantil");
	ArrayList<String> cantidad = (ArrayList<String>) request.getAttribute("cantidad");
%>
</head>

<body class="hold-transition sidebar-mini layout-boxed"> 
	<div class="content-wrapper">
		<section class="content-header">				
		
		<div class="col-md-12 col-xs-12 form-group">			
			<form class="form-signin" method="post" action="disponibleController" id="buscadorForm" name="buscadorForm">
			<div class="form-group">
				<div class="col-md-6 form-group">				 	
				 	<label for="descripcion">Descripción Juego</label>
					<input type="text" class="form-control" id="descripcion" name="descripcion" placeholder="descripcion">
				</div>
				<div class="col-md-2 form-group">											
					<label>N. de jugadores:</label>
					<input type="number" class="form-control" id="jugadores" name="jugadores" placeholder="jugadores">												
				</div>
				<div class="col-md-2 form-group">
					<label>Máxima Duración:</label>
					<input type="number" class="form-control" id="duracion" name="duracion" placeholder="duracion">												
				</div>
				<div class="col-md-2 form-group">
					<label>Total Juegos a Mostrar:</label>
					<input type="number" class="form-control" id="contador" name="contador" placeholder="contador">												
				</div>
				<div class="col-md-12 form-group">
					<div class="form-check col-md-4 col-xs-12">
						<label style="font-size:18">Pasaporte Infantil</label>
						<input class="form-check-input" type="checkbox" placeholder="Pasaporte Infantil" style="zoom:2" value="<%=infantil %>" id="infantil" name="infantil" <%if(infantil) {%>checked <%} %>>
					</div>
					<div class="form-group col-md-4 col-xs-12" >
						<label style="font-size:18">Mostrar ya jugados</label>					 						
						<input class="form-check-input" type="checkbox" placeholder="yajugados" id="jugados" name="jugados" style="zoom:2">
					</div>
					<div class="col-md-4">			
						<button type="submit" class="btn btn-primary btn-lg" name="action"	value="find">Buscar</button>
					</div>								
				</div>
			</div>

			</form>
		</div>
		

<%if(disponibles != null) { %>
    <div class="row">
        <% for (Pasaporte juego : disponibles){%>
            <div class="col-md-3 col-sm-6 col-xs-12">
                <div class="card-juego">
                    <div class="row" style="width: 100%; margin: 0; display: flex; align-items: center;">
                        
                        <!-- Columna Izquierda: Contenedor de Imagen -->
                        <div class="col-md-5 col-xs-4" style="padding: 0;">
                            <a href="<%=juego.getBgg() %>" target="_blank">
                                <div class="img-juego-contenedor">
                                    <img src="<%=juego.getRutaImagen() %>" 
                                         alt="<%=juego.getNombre()%>" 
                                         class="img-juego-fija <%if(!juego.isJugado()){%> grayscale <%}%>" />
                                </div>
                            </a>
                        </div>

                        <!-- Columna Derecha: Detalles -->
                        <div class="col-md-7 col-xs-8" style="padding-right: 0;">
                            <h5 style="margin: 0 0 5px 15px; font-weight: bold; color: #333; line-height: 1.2;">
                                <%=juego.getNombre()%>
                            </h5>
                            <ul class="lista-detalles">
                                <li><i class="fa fa-clock-o"></i> <%=juego.getDuracion()%>'</li>
                                <li><i class="fa fa-users"></i> 1-<%=juego.getMaxjugadores() %></li>
                                <li><i class="fa fa-child"></i> <%=juego.getEdad() %>+</li>
                            </ul>
                        </div>

                    </div>
                </div>
            </div>
        <%} %>
    </div>
<%} %>



		</section>
	</div>
	
<script src="https://code.jquery.com/jquery-3.2.1.js"></script>
<script type="text/javascript"></script>
</body>
</html>