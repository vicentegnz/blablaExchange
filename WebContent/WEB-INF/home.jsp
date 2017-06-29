<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html>
<html lang="es">
<head>
<title>BlaBlaExchange</title>
<meta charset="UTF-8">
<link rel="stylesheet" type="text/css"
	href="${pageContext.request.contextPath}/css/styleIndex.css">
<link rel="stylesheet" type="text/css"
	href="${pageContext.request.contextPath}/css/styleRegister.css">
<link rel="stylesheet" type="text/css"
	href="${pageContext.request.contextPath}/css/styleProfile.css">
<link rel="stylesheet" type="text/css"
	href="${pageContext.request.contextPath}/css/navbar.css">
</head>

<body>

	<header id="main-header">
		<nav>
			<ul class="topnav" id="myTopnav">
				<li id="inicioLink"><a href="ListUserLanguagesServlet">Bienvenido,
						${user.username}</a></li>
				<li><a href="${pageContext.request.contextPath}/LogoutServlet">Cerrar
						Sesion</a>
				<li><a href="EditProfileServlet">Mi cuenta</a>
				<li class="active"><a href="ListUserLanguagesServlet">Usuarios</a></li>

			</ul>
		</nav>
	</header>

	<section class="busqueda">
		<div class="container_">
			<h1>INTERCAMBIO DE IDIOMAS</h1>
			<form class="form_centered" method="GET"
				action="ListUserLanguagesServlet">
				<p>
					<input type="text" id="search" name="search"
						placeholder="Busqueda...">
				</p>
				<button class="btn_primary" type="submit">Buscar</button>
			</form>


		</div>
	</section>
	<div class="separacion"></div>
	<section class="contenido_usuarios">
		<div class="filtro_usuarios">
			<h2>Filtro:</h2>
			<form method="GET" action="ListUserLanguagesServlet">
				<span class="important">¿Que idioma quieres practicar?</span>
				<p>
					<select class="soflow" name="idiomasFiltro" id="idiomasFiltro">
						<c:forEach var="language" items="${languages}">
							<option value="${language.langname}">${language.langname}</option>
						</c:forEach>
					</select>
				</p>
				<span class="important">¿Que nivel exiges?</span>
				<p>
					<select class="soflow" name="nivelIdiomasFiltro"
						id="nivelIdiomasFiltro">
						<option value="A1" selected="selected">A1</option>
						<option value="A2">A2</option>
						<option value="B1">B1</option>
						<option value="B2">B2</option>
						<option value="C1">C1</option>
						<option value="C2">C2</option>
						<option value="Native">Native</option>

					</select>
				</p>
				<span class="important">¿Por donde quieres comunicarte?</span>
				<p>
				<select class="soflow" name="comunicacionFiltro"
						id="comunicacionFiltro">
						<option value="Whatsapp" selected="selected">Whatsapp</option>
						<option value="Skype">Skype</option>
						<option value="FACE to FACE">Face to Face</option>
					</select>
				</p>
				<input class="btn_primary" type="submit" value="Filtrar"> <input
					type="hidden" id="stateFilter" name="stateFilter" value="active">
				<input type="hidden" id="idu" name="idu" value="${user.idu}">


			</form>


		</div>
		<ul class="lista_usuarios">
			<c:if test="${users.isEmpty()}">
				<h2>${message}</h2>
			</c:if>

			<c:forEach var="userP" items="${users}">
				<c:if test="${userP.username != user.username }">
					<li class="usuario">
						<div class="items-usuario" id="imagen-valoracion">
							<div class="imgcontainer">
								<img src="${pageContext.request.contextPath}/imagenes/login.png"
									alt="Avatar" class="avatar">
							</div>
							<h1 class="important">${userP.username}</h1>
							<!--  	<div class="estrellas">
									<a href="#" data-value="1" title="Votar con 1 estrellas">&#9733;</a>
									<a href="#" data-value="2" title="Votar con 2 estrellas">&#9733;</a>
									<a href="#" data-value="3" title="Votar con 3 estrellas">&#9733;</a>
									<a href="#" data-value="4" title="Votar con 4 estrellas">&#9733;</a>
									<a href="#" data-value="5" title="Votar con 5 estrellas">&#9733;</a>
								</div>-->
						</div>

						<div class="items-usuario" id="informacion">
							<p>
								<span class="important">Localización: </span>${userP.localizacion} 
							</p>
							<p>
								<span class="important">Idiomas: </span>
								<c:forEach var="userLanguage" items="${usersLanguages}">
									<c:if test="${userLanguage.first.username == userP.username}">
										<p>${userLanguage.second.langname}-
											${userLanguage.third.level}</p>
									</c:if>
								</c:forEach>

							</p>
							<p>
								<span class="important">Preferencia: </span>${userP.comunicacion} 
							</p>

						</div>


						<div class="items-usuario" id="botones">

							<!--  
								<input class="btn_primary" type=button
									onClick="location.href='#openMD'" value='Enviar Mensaje'>-->
							<input class="btn_primary" type=button
								onClick="location.href='<c:url value='ProfileServlet?idu=${userP.idu}'/>'"
								value='Ver Perfil'>

						</div>
					</li>
				</c:if>
			</c:forEach>


		</ul>

	</section>

	<!-- / #main-content -->

	<footer id="main-footer">
		<p>Página web desarrollada por Vicente González para las
			asignatura de Programación en Internet &copy; 2017</p>
	</footer>
	<!-- / #main-footer -->
</body>
</html>