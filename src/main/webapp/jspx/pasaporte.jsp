<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1" import="dto.Pasaporte, java.util.*"%>
<%@ include file="cabecera.jsp" %>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<!-- CSS Principal -->	
<link rel="stylesheet" type="text/css" href="./css/style.css" />

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
    <%if(pasaportes != null){ %>
        <%int orden = 0; %>     
        
        <% for (Pasaporte juego : pasaportes){%>   
        
            <%-- DETECCIÓN DE CAMBIO DE EDITORIAL --%>
            <% if(juego.getEditorial().getOrden() > orden) { %>
                <%-- Si no es la primera editorial, cerramos los divs anteriores --%>
                <% if(orden != 0){ %>
                        </div> <!-- Cierre de row de juegos -->
                    </div> <!-- Cierre de box-body -->
                </div> <!-- Cierre de box -->
                <%} %>
                
                <% orden = juego.getEditorial().getOrden(); %>
                <div class="box box-default collapsed-box">
    			<%-- Aplicamos la nueva clase de header ampliado --%>
    				<div class="box-header with-border box-header-editorial">
        
				        <div style="display: flex; align-items: center;">
            				<h3 class="box-title-grande"><%=juego.getEditorial().getNombre()%></h3>	            
    				        <%-- Logo reescalado --%>
            				<img class="edit-logo-reescalado" 
                 				src="<%=juego.getEditorial().getLogo() %>" 
                 				alt="Logo <%=juego.getEditorial().getNombre()%>" />
        				</div>

        			<div class="box-tools pull-right" >
            		<%-- Botón de minimizar con zoom para que no quede pequeño en el header grande --%>
            			<button class="btn btn-box-tool" data-widget="collapse" 
                    		style="zoom: 2.5; color: #dd4b39; padding: 5px;">
                		<i class="fa fa-plus"></i>
            			</button>
        			</div>              
    			</div>
                <div class="box-body">
                    <div class="row"> <!-- Este row contendrá los 4 juegos por línea -->
            <%} %>
            
            <%-- ESTRUCTURA DE CADA JUEGO (Igual que en Disponibles) --%>
            <div class="col-md-3 col-sm-6 col-xs-12">
                <div class="card-juego"> <!-- Clase definida en tu CSS principal -->
                    <div class="row" style="width: 100%; margin: 0; display: flex; align-items: center;">
                        
                        <!-- Lado Izquierdo: Contenedor de Imagen -->
                        <div class="col-md-5 col-xs-4" style="padding: 0;">
                            <a href="<%=juego.getBgg() %>" target="_blank">
                            <div class="img-juego-contenedor">
    							<img src="<%=juego.getRutaImagen() %>" 
         						alt="<%=juego.getNombre()%>" 
         						<%-- Inyectamos la clase 'grayscale' solo si NO está jugado --%>
         						class="img-juego-fija <% if(!juego.isJugado()){ %> grayscale <% } %>" 
    							/>
							</div>                            
                            </a>
                        </div>

                        <!-- Lado Derecho: Detalles en lista -->
                        <div class="col-md-7 col-xs-8" style="padding-right: 0;">
                            <h5 class="juego-titulo"> <!-- Sugiero añadir esta clase para el control del texto -->
                                <%=juego.getNombre()%>
                            </h5>
                            <ul class="lista-detalles"> <!-- Clase definida en tu CSS principal -->
                                <li><i class="fa fa-clock-o"></i> <%=juego.getDuracion()%>'</li>
                                <li><i class="fa fa-users"></i> 1-<%=juego.getMaxjugadores() %></li>
                                <li><i class="fa fa-child"></i> <%=juego.getEdad() %>+</li>
                            </ul>
                        </div>
                    </div>
                </div>
            </div>
            
        <%} %>
            <%-- Cerramos la última editorial abierta --%>
                </div> <!-- Cierre de row -->
            </div> <!-- Cierre de box-body -->
        </div> <!-- Cierre de box -->
    <%} %>          
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