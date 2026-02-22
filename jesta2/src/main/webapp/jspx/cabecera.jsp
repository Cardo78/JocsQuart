<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1" import="dto.Usuario, dto.enums.TipoUser"%>
<!DOCTYPE html">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<meta http-equiv="X-UA-Compatible" content="IE=edge">
<meta name="viewport"
	content="width=device-width, initial-scale=1, maximum-scale=1, user-scalable=no">
<meta name="description" content="MenuPrincipal">
<meta name="author" content="Ricardo Cardona">
 	
<!-- Fuentes Awesome -->
<link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/4.7.0/css/font-awesome.min.css">

<!-- Doc Awesome https://www.w3schools.com/icons/fontawesome_icons_intro.asp -->
<!-- Bootstrap -->
<link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.4.0/css/bootstrap.min.css">

<!-- AdminLTE -->
<link rel="stylesheet" href="./css/AdminLTE/AdminLTE.min.css"> 
<!-- AdminLTE Skin -->
<link rel="stylesheet" href="./css/AdminLTE/skins/_all-skins.css">
	 		
<link rel="icon" href="./images/jocsquart-logo.png">

<!-- CSS Principal -->	
<link rel="stylesheet" type="text/css" href="./css/style.css" />


<!-- Scripts -->
  	<script src="https://ajax.googleapis.com/ajax/libs/jquery/3.4.1/jquery.min.js"></script>
  	<script src="https://maxcdn.bootstrapcdn.com/bootstrap/3.4.0/js/bootstrap.min.js"></script>
  
<!-- AdminLTE App -->
	<script src="./assets/js/adminlte.min.js"></script>
	<script src="./assets/js/app.min.js"></script>	 		

<%
	String color = "skin-red";

	Usuario login = null;
	if (session != null) {
		login = (Usuario) session.getAttribute("usuario");
		if (login != null) {
			switch (login.getTipo()) {
				case "NORMAL" :
					%><input type="hidden" id="userMode" value="normal"><%
				color = login.getColor();
				break;
				case "ADMIN" :
					%><input type="hidden" id="userMode" value="admin"><%
				color = login.getColor();
				break;
				case "JUGADOR" :
					%><input type="hidden" id="userMode" value="jugador"><%
				color = login.getColor();
				break;
				default :
					%><input type="hidden" id="userMode" value=""><%
				break;
			}
		}
	}
	String urlindex = (session.getAttribute("urlindex") == null)
			? ""
			: (String) session.getAttribute("urlindex");
	String imglogopeq = (session.getAttribute("logopeq") == null)
			? ""
			: (String) session.getAttribute("logopeq");
	String titulo = (session.getAttribute("nombre") == null) ? "" : (String) session.getAttribute("nombre");	
	
	
%>


</head>
<body class="sidebar-mini <%=color%>">

<!-- Menu superior -->
	<header class="main-header">
	
	<!-- Logo -->
	<a class="logo hidden-xs" href="<%=urlindex%>">
		<span class="logo-lg"><img class="img-responsive" src="<%=imglogopeq%>" class="rounded"></span>		 
	</a>
	
	<nav class="navbar navbar-static-top" role="navigation">
	
	<!-- Boton de plegar menu -->
	
		<a class="sidebar-toggle" href="#" data-toggle="offcanvas" role="button">
			<span class="sr-only">Toggle navigation</span>
		</a> 
		          
		<!-- Navbar Menu Derecha -->
        <div class="navbar-custom-menu">
          	<ul class="nav navbar-nav">
            	<li class="dropdown user user-menu">
            		<a href="#" class="dropdown-toggle" data-toggle="dropdown">
            			<span class="glyphicon glyphicon-user hidden-xs"><%if(login!=null){%><%=login.getUser()%><%}%></span>	
            		</a>
            	</li>
           		<li id="menuLogout"><a id="liLogoutIcon" href="./menuController?section=10"><span class="glyphicon glyphicon-log-out"></span></a></li>
           		<!-- <li id="menuConfig"><a id="liConfig" href="./menuController?section=12"><span class="fa fa-gears"></span></a></li>  -->
            </ul>
        </div><!-- /.navbar-custom-menu -->          
    </nav><!-- navbar -->
    </header>
    
    <!-- Menu Lateral -->
	<aside class="main-sidebar">
	<section class="sidebar" style="height: auto;">
	<!-- Menu de opción -->
		<ul class="sidebar-menu">
			<li class="header">MENU PRINCIPAL</li>
			<li class="nav-item" class="active">			
				<a class="nav-link" id="liInicio" href="./menuController?section=0"><i class="fa fa-home"></i><span>Inicio</span></a></li>  		
          	<li class="nav-item">
          		<a class="nav-link" id="liLogin" href="./menuController?section=1"><i class="fa fa-sign-in"></i><span>Log In</span></a></li>	
          	<li class="nav-item">
          	   	<a class="nav-link" id="liInscripciones" href="./menuController?section=2"><i class="fa fa-user-plus"></i><span>Inscripciones</span></a></li>	
          	<li class="nav-item">
          		<a class="nav-link" id="liJuegos" href="./menuController?section=3"><i class="fa fa-ra"></i><span>Gestión de juegos</span></a></li>   
          	<li class="nav-item">
          		<a class="nav-link" id="liPasaporte" href="./menuController?section=4"><i class="fa fa-address-card"></i><span> Pasaporte</span></a></li>  
          	<!--  Modificado REQ2023 para la comprobacion de juegos disponibles -->	
          	<li class="nav-item">
          		<a class="nav-link" id="liDisponible" href="./menuController?section=7"><i class="fa fa-search"></i><span> Disponibles</span></a></li>          	   		        
          	<li class="nav-item">
          		<a class="nav-link" id="liAlquiler" href="./menuController?section=5"><i class="fa fa-laptop"></i><span>Alquileres</span></a></li>
          	<!--  Anadido REQ2023 para la revision de las Jesetas Virtuales -->
          	<li class="nav-item">
          		<a class="nav-link" id="liMonedero" href="./menuController?section=6"><i class="fa fa-money"></i><span> CryptoJesetas</span></a></li>
            <li class="nav-item">
          		<a class="nav-link" id="liTransacciones" href="./menuController?section=8"><i class="fa fa-exchange" aria-hidden="true"></i><span> Transacciones</span></a></li>
          	<li class="nav-item">
          		<a class="nav-link" id="liSorteo" href="./menuController?section=9"><i class="fa fa-trophy" aria-hidden="true"></i><span> Sorteo</span></a></li>
        	<li class="nav-item">
          		<a class="nav-link" id="liControl" href="./menuController?section=11"><i class="fa fa-street-view"></i><span> Localizador</span></a></li>          		          	
          	<li class="nav-item">
          		<a class="nav-link" id="liAdministracion" href="./menuController?section=12"><i class="fa fa-gear"></i><span> Administracion</span></a></li>          	
          	<li class="nav-item">
          	   	<a class="nav-link" id="liLogout" href="./menuController?section=10"><i class="fa fa-sign-out"></i><span>Log Out</span></a></li>
		</ul>
	</section>
	</aside>
		
</body>

<script type="text/javascript">
	
	$(document).ready(function() {
		var tipoUser = $("#userMode").val();

		if (tipoUser == null) {
			$("#liInicio").css("display", "");
			$("#liLogin").css("display", "");
			$("#liConfig").css("display", "");
			$("#liInscripciones").css("display", "none");
			$("#liLogout").css("display", "none");			
			$("#liLogoutIcon").css("display", "none");
			$("#liJuegos").css("display", "none");
			$("#liAlquiler").css("display", "none");
			$("#liDisponible").css("display", "none");
			$("#liMonedero").css("display", "none");
			$("#liTransacciones").css("display", "none");
			$('#liSorteo').css("display", "none");
			$('#liControl').css("display", "none");
			$('#liAdministracion').css("display", "none");
			liSorteo
		} else {
		switch (tipoUser) {
			case "normal":
				$("#liInicio").css("display", "none");
				$("#liLogin").css("display", "none");
				$("#liConfig").css("display", "none");
				$("#liInscripciones").css("display", "");
				$("#liLogout").css("display", "");
				$("#liLogoutIcon").css("display", "");
				$("#liJuegos").css("display", "");
				$("#liAlquiler").css("display", "");
				$("#liDisponible").css("display", "none");
				$("#liMonedero").css("display", "none");
				$("#liTransacciones").css("display", "");
				$("#liSorteo").css("display", "none");
				$('#liControl').css("display", "");
				$('#liAdministracion').css("display", "none");
				break;
			case "admin":
				$("#liInicio").css("display", "");
				$("#liLogin").css("display", "none");
				$("#liConfig").css("display", "");
				$("#liInscripciones").css("display", "");
				$("#liLogout").css("display", "");
				$("#liLogoutIcon").css("display", "");
				$("#liJuegos").css("display", "");
				$("#liAlquiler").css("display", "");
				$("#liDisponible").css("display", "none");
				$("#liMonedero").css("display", "none");
				$("#liTransacciones").css("display", "");
				$("#liSorteo").css("display", "");
				$('#liControl').css("display", "");
				$('#liAdministracion').css("display", "");
				break;
			case "jugador":
				$("#liInicio").css("display", "");
				$("#liLogin").css("display", "none");
				$("#liConfig").css("display", "none");
				$("#liInscripciones").css("display", "none");
				$("#liLogout").css("display", "");
				$("#liLogoutIcon").css("display", "");
				$("#liJuegos").css("display", "none");
				$("#liAlquiler").css("display", "none");
				$("#liDisponible").css("display", "");
				$("#liMonedero").css("display", "");
				$("#liTransacciones").css("display", "");
				$("#liSorteo").css("display", "none");
				$('#liControl').css("display", "none");
				$('#liAdministracion').css("display", "none");
				break;
			case "":
				$("#liInicio").css("display", "none");
				$("#liLogin").css("display", "");
				$("#liConfig").css("display", "");
				$("#liInscripciones").css("display", "none");
				$("#liLogout").css("display", "none");
				$("#liLogoutIcon").css("display", "none");
				$("#liJuegos").css("display", "none");
				$("#liAlquiler").css("display", "none");
				$("#liDisponible").css("display", "none");
				$("#liMonedero").css("display", "none");
				$("#liTransacciones").css("display", "none");
				$("#liSorteo").css("display", "none");
				$('#liControl').css("display", "none");
				$('#liAdministracion').css("display", "none");
				break;
			}
		}
	});
	
</script>
</html>