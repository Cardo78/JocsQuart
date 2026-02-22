<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1" import="dto.Jugador, java.util.*"%>
<%@ include file="cabecera.jsp"%>

<!DOCTYPE html">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Buscar Jugador</title>
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
	String tipoMensaje = request.getAttribute("tipoMensaje") == null
			? ""
			: (String) request.getAttribute("tipoMensaje");
	String nombre = request.getAttribute("nombre") == null ? "" : (String) request.getAttribute("nombre");
	String apellidos = request.getAttribute("apellidos") == null
			? ""
			: (String) request.getAttribute("apellidos");
	ArrayList<Jugador> jugadores = (ArrayList<Jugador>) request.getAttribute("jugadores");
%>
</head>
<body class="hold-transition sidebar-mini layout-boxed">
	<div class="content-wrapper">
		<section class="content-header">
			<h1>
				BUSCADOR JUGADORES <small>Permite Buscar Jugadores
					Inscritos. Recomendable buscar antes de crear uno nuevo.</small>
			</h1>
		</section>
		<section class="content">
			<div class="row">
				<div class="col-md-4 col-xs-12">
					<div class="box box-default">
						<div class="box-header with-border">
							<h3 class="box-title">Buscar:</h3>
							<div class="box-tools pull-right">
								<button class="btn btn-box-tool"
									data-originial-title="minimizar" data-widget="collapse"
									data-toggle="tooltip" title="minimizar">
									<i class="fa fa-minus"></i>
								</button>
							</div>
						</div>
						<!-- box-header -->
						<div class="box-body">
							<form class="form-signin" method="post"
								action="jugadorController" id="buscadorForm" name="buscadorForm">
								<div class="form-group">
									<input type="text" class="form-control" id="nombre"
										name="nombre" placeholder="Nombre" value="<%=nombre%>">
								</div>
								<!-- form group-->
								<div class="form-group">
									<input type="text" class="form-control" id="apellidos"
										name="apellidos" placeholder="Primer apellido"
										value="<%=apellidos%>">
								</div>
								<!-- form group-->
								<div class="form-group has-feedback">
									<input type="text" class="form-control" id="dni" name="dni"
										placeholder="DNI">
								</div>
								<!-- form group-->
								<%
									if (!mensajeIt.equals("")) {
								%>
								<div class="callout <%=tipoMensaje%>" style="display: block;"><%=mensajeIt%></div>
								<%
									}
								%>

								<div class="box-footer">
									<button type="submit" class="btn btn-primary" name="action"
										value="find">Buscar</button>
									<button type="submit" class="btn btn-primary" name="action"
										value="new">Nuevo</button>
								</div>
							</form>
						</div>
						<!-- box-body -->
					</div>
					<!-- box -->
				</div>

				<div class="col-md-8 col-xs-12">

					<div class="box box-default">
						<div class="box-header with-border">
							<h3 class="box-title">Resultados:</h3>
						</div>
						<%	if (jugadores != null) { %>
						<!-- box-header -->
						<div class="box-body">
							<table id="myTable" class="table table-hover">
								<thead>
									<tr>
										<th>Nombre</th>
										<th>Apellidos</th>
										<th>DNI</th>
										<th>e-mail</th>
									</tr>
								</thead>
								<tbody>
									<%
										for (Jugador player : jugadores) {
												String dni = (player.getDni() == null) ? "" : player.getDni();
												String apellido2 = (player.getApellidos() == null) ? "" : player.getApellidos();
												String email = (player.getMail() == null) ? "" : player.getMail();												
									%>
									<tr class="clickable-row">
										<td><%=player.getNombre()%></td>
										<td><%=player.getApellidos()%></td>
										<td><%=dni%></td>
										<td><%=email%></td>
										<td style="display: true"><%=player.getTelefono()%></td>
										<td style="display: none"><%=player.getCp()%></td>
										<td style="display: none"><%=player.getPulsera()%></td>
										<td style="display: none"><%=player.isInfantil() %></td> <!-- Jesta2023 Infantil -->										
										<td style="display: none"><%=player.getId()%></td>
									</tr>
									<%
										}
									%>
								</tbody>
							</table>
						</div>
						<!-- box-body -->
						<% 
						} 
						%>
					</div>
					<!-- box -->

				</div>
				<form class="form-signin" method="post" action="jugadorController"
					id="myForm3" name="myForm3">
					<div style="display: none">
						<input type="hidden" name="action" id="action"> <input
							type="hidden" name="nombreT" id="nombreT">
						<button type="submit" class="btn btn-lg btn-primary btn-block"
							name="action" value="open"></button>
					</div>
				</form>
			</div>
		</section>
	</div>
	<script type="text/javascript">
		jQuery(document)
				.ready(
						function($) {
							$("li").removeClass("active");
							$("#liInscripciones").addClass("active");
							$(".clickable-row")
									.click(
											function() {
												var index = ($(this).index()) + 1;
												var name = $('.table tr:eq(' + index + ') td:eq(' + 0 + ')').text();
												var apellidos = $('.table tr:eq(' + index + ') td:eq(' + 1 + ')').text();
												var dni = $('.table tr:eq(' + index	+ ') td:eq(' + 2 + ')').text();
												var email = $('.table tr:eq(' + index + ') td:eq(' + 3 + ')').text();
												var telefono = $('.table tr:eq(' + index + ') td:eq(' + 4 + ')').text();
												var cp = $('.table tr:eq(' + index + ') td:eq(' + 5 + ')').text();
												var pulsera = $('.table tr:eq(' + index	+ ') td:eq(' + 6 + ')').text();
												var infantil = $('.table tr:eq(' + index + ') td:eq(' + 7 + ')').text(); <!-- Jesta2023 Infantil -->
												var id = $('.table tr:eq(' + index + ') td:eq(' + 8 + ')').text();												

												$form = $("<form method='post' action='jugadorController'></form>");
												$form.append('<input type="hidden" name="action" value="open" />');
												$form.append('<input type="hidden" name="nombre" value="' + name + '" />');
												$form.append('<input type="hidden" name="apellidos" value="' + apellidos + '" />');
												$form.append('<input type="hidden" name="dni" value="' + dni + '" />');
												$form.append('<input type="hidden" name="email" value="' + email + '" />');
												$form.append('<input type="hidden" name="telefono" value="' + telefono + '" />');
												$form.append('<input type="hidden" name="cp" value="' + cp + '" />');
												$form.append('<input type="hidden" name="pulsera" value="' + pulsera + '" />');
												$form.append('<input type="hidden" name="infantil" value="' + infantil + '" />');
												$form.append('<input type="hidden" name="id" value="' + id + '" />');
												$('body').append($form);
												$form.submit();
											});
						});
	</script>
</body>
</html>