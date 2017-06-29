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
	href="${pageContext.request.contextPath}/css/styleProfile.css">
<link rel="stylesheet" type="text/css"
	href="${pageContext.request.contextPath}/css/styleProfileUser.css">
<link rel="stylesheet" type="text/css"
	href="${pageContext.request.contextPath}/css/navbar.css">
</head>

<body>

	<header id="main-header">
		<nav>
			<ul class="topnav" id="myTopnav">
				<li id="inicioLink"><a href="ListUserLanguagesServlet">BlaBlaExchange</a></li>
				<li><a href="${pageContext.request.contextPath}/LogoutServlet">Cerrar
						Sesion</a>
				<li><a href="EditProfileServlet">Mi cuenta</a>
				<li><a href="ListUserLanguagesServlet">Usuarios</a></li>
			</ul>
		</nav>
	</header>

	<div id="main-content">
		<section class="contenido_usuarios">

			<div class="lista_usuarios">
				<div class="usuario">
					<div class="items-usuario" id="imagen-valoracion">
						<div class="imgcontainer">
							<img src="${pageContext.request.contextPath}/imagenes/login.png"
								alt="Avatar" class="avatar">
						</div>
						<h1 class="important">${userProfile.username}</h1>

					</div>

					<div class="items-usuario" id="informacion">

						<p>
							<span class="important">Localización: </span>
							${userProfile.localizacion}
						</p>

						<span class="important">Idiomas: </span>
						<c:forEach var="userLanguage" items="${usersLanguages}">
							<p>${userLanguage.second.langname}-${userLanguage.third.level}</p>
						</c:forEach>

						<p>
							<span class="important">Preferencia: </span>${userProfile.comunicacion}
						</p>

					</div>


					<div class="items-usuario" id="valoraciones"></div>

				</div>
			</div>
		</section>

		<section class="muro_usuario">
			<h3>${messages}</h3>
			<form action="AddCommentUserServlet" method="POST">
				<c:if test="${permise=='noEditable'}">
					<textarea class="comentario" name="comentario" rows="4" cols="50">Escribe aquí...
					</textarea>
					<input class="btn_primary" type="submit" value="Dejar comentario">
					<input type="hidden" name="idu" value="${userProfile.idu}">


				</c:if>
			</form>
			<form action="EditCommentUserServlet" method="POST">

				<c:if test="${permise=='editable'}">
					<textarea class="comentario" name="comentario" rows="4" cols="50">${commentEdit.text}
					</textarea>
					<input class="btn_primary" type="submit" value="Editar comentario">
					<input type="hidden" name="idu" value="${commentEdit.receiver}">
					<input type="hidden" name="idc" value="${commentEdit.idc}">

				</c:if>
			</form>


			<div class="container">
				<div class="comments-container">
					<h1>Comentarios</h1>
					<ul id="lista_comentarios" class="lista_comentarios">
						<c:forEach var="comment" items="${commentsUser}">
							<li>
								<div class="comentarios_primerNivel">
									<!-- Avatar -->
									<div class="comment-avatar">
										<img
											src="${pageContext.request.contextPath}/imagenes/login.png"
											alt="avatar">
									</div>
									<!-- Contenedor del Comentario -->
									<div class="comment-box">
										<div class="comment-head">
											<c:if test="${comment.first.sender == userProfile.idu }">
												<h6 class="comment-name by-author">${userProfile.username}</h6>
											</c:if>
											<c:if test="${comment.first.sender != userProfile.idu }">
												<h6 class="comment-name">${comment.second.username}</h6>
											</c:if>
											<span>${comment.third }</span>
											<c:if test="${comment.first.sender == user.idu}">
												<form class="form-Icon" method="POST"
													action="DeleteCommentUserServlet">
													<input class="icon-comment" type="image"
														src="${pageContext.request.contextPath}/imagenes/iconDelete.png"
														alt="Submit"> <input type="hidden" name="idu"
														value="${userProfile.idu}"> <input type="hidden"
														name="idc" value="${comment.first.idc}">
												</form>
											</c:if>
											<c:if test="${comment.first.sender == user.idu}">

												<form class="form-Icon" method="GET"
													action=" EditCommentUserServlet">
													<input class="icon-comment" type="image"
														src="${pageContext.request.contextPath}/imagenes/iconEdit.png"
														alt="Submit"> <input type="hidden" name="idu"
														value="${userProfile.idu}"> <input type="hidden"
														name="idc" value="${comment.first.idc}">

												</form>
											</c:if>

										</div>
										<div class="comment-content">${comment.first.text}</div>
									</div>
								</div>
							</li>
						</c:forEach>
					</ul>
				</div>







			</div>
		</section>
	</div>
	<!-- / #main-content -->

	<footer id="main-footer">
		<p>Página web desarrollada por Vicente González para las
			asignatura de Programación en Internet. &copy; 2017</p>
	</footer>
</body>
</html>