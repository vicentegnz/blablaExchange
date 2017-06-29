<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>

<!DOCTYPE html>
<html lang="es">
<head>
<title>BlaBlaExchange</title>
<meta charset="utf-8">
<link rel="stylesheet" type="text/css" href="css/styleIndex.css">
<link rel="stylesheet" type="text/css" href="css/styleRegister.css">
<link rel="stylesheet" type="text/css" href="css/navbar.css">
</head>

<body>

	<header id="main-header">
		<nav>
			<ul class="topnav" id="myTopnav">
				<li id="inicioLink"><a href="LoginServlet">BlaBlaExchange</a></li>
				<li><a href="#openLogin">Iniciar Sesion</a></li>
			</ul>
		</nav>
	</header>

	<div id="main-content">

		<section class="login">
			<div id="openLogin" class="modalDialog">
				<div class="container">
					<div class="cuadroLogin">
						<a href="#close" title="Close" class="close">X</a>
						<form method="POST" action="LoginServlet">

							<div class="imgcontainer">
								<img src="imagenes/login.png" alt="Avatar" class="avatar">
							</div>

							<div class="container_">
								<label><b>Log in</b></label> <input type="text"
									placeholder="Nombre de usuario" name="username" required>
								<input type="password" placeholder="Contraseña" name="password"
									required>

								<button class="button" type="submit">Inicio de Sesión</button>

							</div>
						</form>
					</div>
				</div>
			</div>

		</section>


		<section class="register_content">
			<div class="container">
				<div id="register" class="animate_form">
					<form method="POST" action="RegisterServlet">
						<h1>Datos Personales</h1>

						<div class="imgcontainer">
							<img src="imagenes/login.png" alt="Avatar" class="avatar">
						</div>
						<c:if test="${messages != null }">
							<c:forEach var="message" items="${messages}">
								<li>${message}</li>
							</c:forEach>
						</c:if>
						<p>
							<label for="username">Nombre de Usuario</label> <input
								id="username" name="username" required="required" type="text" />
						</p>
						<p>
							<label for="email">Email</label> <input id="email" name="email"
								required="required" type="text" />
						</p>
						<p>
							<label for="password">Contraseña</label> <input id="password"
								name="password" required="required" type="password" />
						</p>
						<p>
							<label for="rpassword">Confirmar la contraseña</label> <input
								id="rpassword" name="rpassword" required="required"
								type="password" />
						</p>
						<p>
							<label for="localizacion">Localizacion</label> <input
								id="localizacion" name="localizacion" required="required"
								type="text" />
						</p>
						<p>
							<label for="tipoIntercambio">Tipo de intercambio</label> <select
								id="tipoIntercambio" name="tipoIntecambio" class="soflow">
								<option value="whtasapp" selected="selected">Whatsapp</option>
								<option value="facetoface">Face to Face</option>
								<option value="viainternet">Via internet</option>
							</select>
						</p>
						<p>
							<button class="buttonConfirmar" type="submit" value="Sign up">
								Confirmar Datos</button>
						</p>
					</form>

				</div>

			</div>
		</section>
	</div>
	<!-- / #main-content -->

	<footer id="main-footer">
		<p>Página web desarrollada por Vicente González para las
			asignatura de Programación en Internet. &copy; 2017</p>
	</footer>
	<!-- / #main-footer -->
</body>
</html>