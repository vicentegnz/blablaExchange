<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html>
<html lang="en">
<head>
<meta charset="UTF-8">
<link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/css/styleIndex.css">
<link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/css/navbar.css">
<title>Ups tenemos un problema</title>
</head>

<body>

	<header id="main-header">
		<nav>
			<ul class="topnav" id="myTopnav">
				<li id="inicioLink"><a href="${pageContext.request.contextPath}/LoginServlet">BlaBlaExchange</a></li>
				<li><a href="<c:url value="${pageContext.request.contextPath}/RegisterServlet"/>">Registrarse</a></li>
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
								<img src="${pageContext.request.contextPath}/imagenes/login.png" alt="Avatar" class="avatar">
							</div>

							<div class="container_">
								<label><b>Log in</b></label> <input type="text"
									placeholder="Nombre de Usuario" name="username" required>
								<input type="password" placeholder="Contraseña" name="password"
									required>

								<button class="button" type="submit">Inicio de Sesión</button>



							</div>
						</form>
					</div>
				</div>
			</div>

		</section>
		<section class="error404">
			<div class="container">
				<h1>
					Recurso no Encontrado <small>Error 404</small>
				</h1>

				<h4>El recurso no está disponible en estos momentos pero puede
					estar disponible en un futuro</h4>


			</div>

		</section>
	</div>
	<!-- / #main-content -->

	<footer id="main-footer">
		<p>Página web desarrollada por Vicente González para las
			asignatura de Programación en Internet &copy; 2017</p>
	</footer>

</body>
</html>
