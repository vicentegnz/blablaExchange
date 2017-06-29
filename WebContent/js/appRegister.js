angular.module('blablaExchangeRegister', [ "ngRoute", "angularCSS" ])
		.config(function($routeProvider) {
			$routeProvider.when("/Register", {
				controller : "usersRegisterCtrl",
				controllerAs : "vm",
				templateUrl : "register.html",
				css: "../css/styleRegister.css",
				resolve : {
					delay : function($q, $timeout) {
						var delay = $q.defer();
						$timeout(delay.resolve, 100);
						return delay.promise;
				}
			}
		})

})

.factory("usersRegisterFactory", function($http) {
	var url = 'https://localhost:8443/blablaExchange/rest/users/';
	var interfaz = {
		nuevoUsuario : function(user) {
			return $http.post(url, user)
				.then(function(response) {
					return response.status;
					});
				}
			}
		return interfaz;
})

.controller("usersRegisterCtrl",function(usersRegisterFactory, $route,$rootScope, $location,$window) {
	var vm = this;
	vm.user = {};
	vm.user.errors = [];
	vm.Rpassword;
	vm.comunicacion = "WhatsApp";
	vm.mensajes=new Array();
	vm.funciones = {
		registrarUsuario : function() {
			vm.mensajes = new Array();
			if (vm.user.password != "" && vm.user.password == vm.Rpassword) {
				vm.user.comunicacion=vm.comunicacion;
				usersRegisterFactory
					.nuevoUsuario(vm.user)
						.then(function(respuesta) {
							console.log("Añadido usuario ",respuesta);
							vm.mensajes.push("El usuario se ha registrado correctamente");			
							$window.history.back();
						},
						function(respuesta) {
							vm.mensajes=respuesta.data.userMessage.toString().split(".");
							console.log("Error añadiendo usuario");
						})
			}else{
				vm.mensaje = "Las contraseñas no coinciden";
			}
		},
		login : function() {
			$location="#openLogin";
		},
	
	}
})
