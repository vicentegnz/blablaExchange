<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>

<!DOCTYPE html>
<html lang="es">
<head>
<title>blablaExchange</title>
<meta charset="utf-8">
<link rel="stylesheet" type="text/css" href="css/styleIndex.css">
<link rel="stylesheet" type="text/css" href="css/navbar.css">
</head>

<body>

	<header id="main-header">
		<nav>
			<ul class="topnav" id="myTopnav">
				<li class="active" id="inicioLink"><a href="#">BlaBlaExchange</a></li>
				<li><a href="RegisterServlet">Registrarse</a></li>
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

		<section class="main-image">

			<div class="container">

				<div class="main-image-info">

					<div class="items-main-image">
						<h1>¿BUSCAS CON QUIEN PRACTICAR TU SEGUNDA LENGUA?</h1>
						<p>Si eres una persona que quiere aprender o practicar un
							idioma, desde principiante hasta nivel avanzado, desde alguien
							que empieza desde cero, a aquellas que necesitan mejorar su nivel
							y no encuentran oportunidad para practicar, están en el sitio
							adecuado.</p>

						<input class="btn_primary" type=button
							onClick="location.href='RegisterServlet'" value='Comienza ahora'>


					</div>

				</div>

			</div>


		</section>

		<section class="unete">

			<div class="container">

				<div class="items-unete">

					<h2>¿Necesitas encontrar la manera de romper las barreras que
						te impiden aprender idiomas?</h2>

					<input class="button" type=button
						onClick="location.href='RegisterServlet'" value='Unirse'>


				</div>

			</div>


		</section>
		<section class="comoUsar">
			<div class="container">
				<div class="comoUsar-items">
					<h1>¿No sabes como funciona?</h1>
					<h2>No te asustes, con blabalaExchange es demasiado sencillo</h2>

					<div>
						<img class="avatar" src="imagenes/verification.png"
							alt="iconoLogin">
						<h3>1. Registrate</h3>
						<p class="secondary-text">Registrate con tus datos y
							selecciona los idiomas que hablas, y el que quieres aprender</p>
					</div>

					<div>
						<img class="avatar" src="imagenes/nativo.png" alt="SiluetaNativo">
						<h3>2. Busca un nativo</h3>
						<p class="secondary-text">Selecciona en el buscador tus
							preferencias y dale a buscar para encontrar tu compañero de
							idiomas</p>
					</div>

					<div>
						<img class="avatar" src="imagenes/chatting.png"
							alt="Personas hablando">
						<h3>3. Comienza a hablar</h3>
						<p class="secondary-text">Ya casi has acabado, solo te queda
							intercambiar palabras y aprender</p>
					</div>
				</div>
			</div>
		</section>
		<section class="help">
			<div class="background">
				<div class="container">
					<h1>Sumérgete en otra cultura</h1>
					<p>Cuando aprendes un idioma con gente de verdad, aprendes más
						que un idioma. Estarás expuesto a una cultura diferente y verás el
						mundo de otra forma.</p>

				</div>
			</div>

		</section>
		<section class="ventajas-item">
			<h1>¿Que ventajas nos ofrece blablaExchange?</h1>
			<div class="container">
				<div class="ventajas-items">
					<div>
						<img class="avatar" src="imagenes/presentation.png"
							alt="Profesor explicando">
						<p>ARTÍCULOS</p>
						<p class="secondary-text">Artículos realizados por los
							profesores para ayudar en el aprendizaje</p>

					</div>

					<div>
						<img class="avatar" src="imagenes/customer-service.png"
							alt="Preguntas entre usuarios">
						<p>PREGUNTAS</p>
						<p class="secondary-text">Haz preguntas sobre dudas o responde
							las dudas de los demás</p>

					</div>

					<div>
						<img class="avatar" src="imagenes/clipboard.png"
							alt="Cuadero de Notas">
						<p>SEGUIMIENTO TAREAS</p>
						<p class="secondary-text">Los usuarios registrados podrán
							corregir tus escritos en el idioma que aprendes, y tú corregirás
							otras redacciones en tu lengua materna</p>

					</div>

					<div>
						<img class="avatar" src="imagenes/reunion.png"
							alt="Reunion grupal">
						<p>DEBATES</p>
						<p class="secondary-text">Hablantes nativos en tu ciudad que
							desean intercambiar idiomas.</p>

					</div>

					<div>
						<img class="avatar" src="imagenes/video-chat.png"
							alt="Dos personas hablando por chat">
						<p>INTERCAMBIO DE IDIOMAS</p>
						<p class="secondary-text">Te ayudamos a romper las barreras
							que te impiden aprender idiomas..</p>
					</div>
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