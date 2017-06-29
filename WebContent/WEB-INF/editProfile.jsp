<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html>
<html lang="es">
<head>
<title>BlaBlaExchange</title>
<meta charset="utf-8">
<link rel="stylesheet" type="text/css"
	href="${pageContext.request.contextPath}/css/styleIndex.css">
<link rel="stylesheet" type="text/css"
	href="${pageContext.request.contextPath}/css/styleRegister.css">
<link rel="stylesheet" type="text/css"
	href="${pageContext.request.contextPath}/css/navbar.css">
</head>

<body>

	<header id="main-header">
		<nav>
			<ul class="topnav" id="myTopnav">
				<li id="inicioLink"><a href="ListUserLanguagesServlet">BlaBlaExchange,
						${user.username}</a></li>
				<li><a href="${pageContext.request.contextPath}/LogoutServlet">Cerrar
						Sesion</a>
				<li class="active"><a href="EditProfileServlet">Mi cuenta</a>
				<li><a href="ListUserLanguagesServlet">Usuarios</a></li>
			</ul>
		</nav>
	</header>

	<div id="main-content">
		<section class="register_content">
			<div class="container">
				<div id="register" class="animate_form">
					<h1>Datos Personales</h1>
					<form method="POST" action="EditProfileServlet" autocomplete="on">

						<div class="imgcontainer">

							<img src="${pageContext.request.contextPath}/imagenes/login.png"
								alt="Avatar" class="avatar">
						</div>
						<c:if test="${messages != null }">
							<c:forEach var="message" items="${messages}">
								<li>${message}</li>
							</c:forEach>
						</c:if>
						<p>
							<label for="username">Nombre de Usuario</label> <input
								id="username" name="username" required="required" type="text"
								value="${user.username}" />
						</p>
						<p>
							<label for="email">Email</label> <input id="email" name="email"
								required="required" type="text" value="${user.email}" />
						</p>
						<p>
							<label for="password">Contraseña</label> <input id="password"
								name="password" required="required" type="password" />
						</p>
						<p>
							<label for="rpassword">Confirmar contraseña</label> <input
								name="rpassword" id="rpassword" required="required"
								type="password" />
						</p>
						<p>
							<label for="localizacion">Localizacion</label> <input
								id="localizacion" name="localizacion" required="required"
								type="text" value="${user.localizacion}" />
						</p>
						<p>
							<label for="tipoIntercambio">Tipo de intercambio</label> <select
								name="tipoIntercambio" id="tipoIntercambio" class="soflow">
								<option value="Whatsapp" selected="selected">Whatsapp</option>
								<option value="FACE to FACE">FACE to FACE</option>
								<option value="Skype">Skype</option>
							</select>
						</p>

						<p>
							<button class="buttonConfirmar" type="submit" value="Sign up">
								Confirmar Datos</button>

						</p>
					</form>

				</div>



				<div id="addLanguage" class="animate_form">
					<form method="POST" action="AddLanguageServlet">
						<h1>¿Desea añadir algún idioma?</h1>
						<p>
							<label>¿Que idioma estas aprendiendo?</label> <select
								id="idiomaHablado" name="idiomaHablado" class="soflow">
								<c:forEach var="language" items="${languages}">
									<option value="${language.idl}">${language.langname}</option>
								</c:forEach>
							</select>
						</p>
						<p>
							<label for="nivelIdioma" data-icon="p">¿Que nivel tienes?</label>
							<select class="soflow" name="nivelIdioma" id="nivelIdioma">
								<option value="A1" selected="selected">A1</option>
								<option value="A2">A2</option>
								<option value="B1">B1</option>
								<option value="B2">B2</option>
								<option value="C1">C1</option>
								<option value="C2">C2</option>
								<option value="Native">Native</option>

							</select>

						</p>
						<p>
							<button class="buttonConfirmar" type="submit" value="Sign up">
								Añadir Idioma</button>
						</p>
						<h1>Idiomas</h1>

						<c:if test="${userLanguages != null }">
							<c:forEach var="userLanguage" items="${userLanguages}">
								<p>${userLanguage.first.langname}-${userLanguage.second}</p>
							</c:forEach>
						</c:if>
					</form>
					<form method="POST" action="DeleteLanguageServlet">
						<c:if test="${userLanguages != null }">
							<h1>¿Deseas eliminar algún idioma?</h1>
							<select id="deleteIdioma" name="deleteIdioma" class="soflow">
								<c:forEach var="userLanguage" items="${userLanguages}">
									<option value="${userLanguage.first.langname}">${userLanguage.first.langname}
									</option>
								</c:forEach>
							</select>
						</c:if>
						<button class="delete_button2" type="submit">Eliminar
							Idioma</button>
					</form>




				</div>


			</div>


		</section>
		<section class="seccionBaja">
			<form method="POST" action="DeleteUserServlet">
				<button class="delete_button2" type="submit">Darse de baja</button>
			</form>
		</section>

	</div>
	<!-- / #main-content -->

	<footer id="main-footer">
		<p>Página web desarrollada por Vicente González para las
			asignatura de Programación en Internet &copy; 2017</p>
	</footer>
</body>
</html>