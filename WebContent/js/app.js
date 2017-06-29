angular.module('blablaExchange', ["ngRoute","angularCSS"])
.config(function($routeProvider) {
	$routeProvider
	.when("/", {
		controller: "listCtrl",
		controllerAs: "vm",
		templateUrl: "home.html",
		css: "../css/styleProfile.css",
		resolve: {
			delay: function($q, $timeout) {
				var delay = $q.defer();
				$timeout(delay.resolve, 100);
				return delay.promise;
			}
		}
	})
	
	.when("/EditProfile", {
		controller: "profileCtrl",
		controllerAs: "vm", 
		templateUrl: "editProfile.html",
		css: "../css/styleRegister.css",
		resolve: {
			delay: function($q, $timeout) {
				var delay = $q.defer();
				$timeout(delay.resolve, 100);
				return delay.promise;
			}
		}
	})
	.when("/Register", {
		controller: "usersCtrl",
		controllerAs: "vm", 
		templateUrl: "register.html",
		css: "../css/styleRegister.css",
		resolve: {
			delay: function($q, $timeout) {
				var delay = $q.defer();
				$timeout(delay.resolve, 100);
				return delay.promise;
			}
		}
	})
	.when("/UserProfile/:ID", {
		controller: "detailsCtrl",
		controllerAs: "vm", 
		templateUrl: "userProfile.html",
		css: "../css/stylesheet.css",
		resolve: {
			delay: function($q, $timeout) {
				var delay = $q.defer();
				$timeout(delay.resolve, 100);
				return delay.promise;
			}
		}
	})
	.otherwise({
        templateUrl: "http404.html",
    	css: "../css/styleIndex.css"

	});
	
})



.factory("usersFactory", function($http){
	var url = 'https://localhost:8443/blablaExchange/rest/users/';
    var interfaz = {
    	leerUsuarios: function(){
    		return $http.get(url)
    			.then(function(response){
    				return response.data;
    			});
    	},
    	leerUsuario : function(idu){
    		var urlidu = url + idu;
            return $http.get(urlidu)
            	.then(function(response){
            		return response.data;
         		});
    	},
    	leerUsuarioSession : function(){
    		var urlcurrent = url+"current";
            return $http.get(urlcurrent)
            	.then(function(response){
            		
            		return response.data;
         		});
    	},
		leerUsuarioLogin: function(user){
			return $http.post(url, user)
			.then(function(response){
				return response.data;
			});
		},
    	actualizarUsuario : function(user){
    		var urlidu = url+user.idu;
            return $http.put(urlidu, user)
            	.then(function(response){
      				 return response.status;
  				});                   
    	},
    	nuevoUsuario:  function(user){
    		return $http.post(url,user)
            	.then(function(response){
            		return response.status;
     			});
    	}, 
        eliminarUsuario : function(idu){
        	var urlidu = url+idu;
            return $http.delete(urlidu)
            	.then(function(response){
            		return response.status;
            	});
        }				  
    }
    return interfaz;
})
.factory("languagesFactory", function($http){
	var url = 'https://localhost:8443/blablaExchange/rest/languages/';
    var interfaz = {
    	leerIdiomas: function(){
    		return $http.get(url)
    			.then(function(response){
    				return response.data;
    			});
    	},
    	leerIdioma : function(idl){
    		var urlidl = url + idl;
            return $http.get(urlidl)
            	.then(function(response){
            		return response.data;
         		});
    	}
    }
    return interfaz;
})
.factory("usersLanguageFactory", function($http){
	var url = 'https://localhost:8443/blablaExchange/rest/usersLanguages/';
    var interfaz = {
    	leerUsuariosconIdiomas: function(){
    		return $http.get(url)
    			.then(function(response){
    				return response.data;
    			});
    	},
    	leerUsuarioConIdioma : function(idu){
    		var urlidu = url +"users?userid="+ idu;
            return $http.get(urlidu)
            	.then(function(response){
            		return response.data;
         		});
    	},
    	leerUsuarioConIdiomaFiltrado: function(level,idl){
    		var urlidu = url +"filter?level="+ level+"&idl="+idl;
            return $http.get(urlidu)
            	.then(function(response){
            		return response.data;
         		});
    	},
    	actualizarUsuarioConIdioma : function(userLanguage){
    		var urlidu = url + userLanguage.idl;
            return $http.put(urlidu, userLanguage)
            	.then(function(response){
      				 return response.status;
  				});                   
    	},
    	nuevoUsuarioConIdioma: function(user){
    		return $http.post(url,user)
            	.then(function(response){
            		return response.status;
     			});
    	}, 
        eliminarUsuarioConIdioma : function(idl){
        	var urlidl = url+idl;
            return $http.delete(urlidl)
            	.then(function(response){
            		return response.status;
            	});
        }				  
    }
    return interfaz;
})


.factory("commentsFactory", function($http) {
	var url = "https://localhost:8443/blablaExchange/rest/comments/"

	var interfaz = {
			leerComentarios: function(){
				return $http.get(url)
				.then(function(response){
					return response.data;
				});
			},
			leerComentariosSender: function(id){
				var urlids = url + "sender?senderid=" + ids;
				return $http.get(urlids)
				.then(function(response){
					return response.data;
				});
			},
			
			leerComentariosReceiver: function(idr){
				var urlidr = url + "receiver?receiverid=" + idr;
				return $http.get(urlidr)
				.then(function(response){
					return response.data;
				});
			},
			
			insertarComentario: function(comment){
				return $http.post(url, comment)
				.then(function(response){
					return response.status;
				});
			},
			
			eliminarComentario: function(idc){
				var urlidc = url + idc;
				return $http.delete(urlidc)
				.then(function(response){
					return response.status;
				});
			},
			
			editarComentario: function(comment){
				var urlid = url + comment.idc;
				delete comment.dateStamp;
				delete comment.timeStamp;
				return $http.put(urlid,comment)
				.then (function(response){
					return response;
				});
			}
			
	}

	return interfaz;
})

.controller("listCtrl", function(usersFactory,usersLanguageFactory,languagesFactory,$routeParams,$route,$location,$scope){
    var vm = this;
    vm.users=[];
    vm.userFiltrado = {};
    vm.userSession={};
    vm.users_languages=[];
    vm.usersFiltrados=[];
    vm.filterNivel="";
    vm.filterComunicacion="";
    vm.filterIDL="";
    vm.filterLocalizacion="";
    vm.localizaciones = new Array();
    vm.error="";
 
    vm.languages=[];
    vm.funciones = {
		
    	rutaActual : function(ruta){
				return $location.path() == ruta;
			},
		leerUsuarioSession : function(){
			usersFactory.leerUsuarioSession()
			.then(function(respuesta){
    			console.log("Trayendo usuario de la session: ", respuesta);
    			$scope.session=respuesta;
    			vm.userSession = respuesta;
    		}, function(respuesta){
    			console.log("Error obteniendo usuario de la sesion");
    		})
		},
		obtenerUsuarios : function() {
    		usersFactory.leerUsuarios()
				.then(function(respuesta){
	    			console.log("Trayendo todos los usuarios: ", respuesta);
	    			vm.users = respuesta;
	    			angular.forEach(vm.users,function(eachUser){
	    	            usersLanguageFactory.leerUsuarioConIdioma(eachUser.idu)
	    	            .then(function(respuesta){
	    	                eachUser.languages=respuesta;
	    	                angular.forEach(eachUser.languages,function(eachUserLang){
	    	    	            languagesFactory.leerIdioma(eachUserLang.idl)
	    	    	            .then(function(respuesta){
	    	    	                eachUserLang.idioma = respuesta.langname;
	    	    	              })
	    	    	          })
	    	              })
	    	          })
	    	          vm.usersFiltrados=vm.users;
	    			vm.funciones.leerUsuarioSession();
	    		}, function(respuesta){
	    			console.log("Error obteniendo usuarios");
	    		})
	    	
		},
		obtenerUsuario : function(id) {
    		usersFactory.leerUsuario(id)
				.then(function(respuesta){
	    			console.log("Trayendo todas los usuarios: ", respuesta);
	    			vm.userFiltrado = respuesta;
	    		}, function(respuesta){
	    			console.log("Error obteniendo usuarios");
	    		})
	    	
		},
		obtenerLocalizaciones : function() {
    		usersFactory.leerUsuarios()
				.then(function(respuesta){
					vm.users = respuesta;
	    			angular.forEach(vm.users,function(eachUser){
	    				if(!vm.localizaciones.includes(eachUser.localizacion)){
	    					vm.localizaciones.push(eachUser.localizacion);
	    				}
	    		    })
	    			
	    		}, function(respuesta){
	    			console.log("Error obteniendo usuarios");
	    		})
	    	
		},
		obtenerIdiomas : function() {
    		languagesFactory.leerIdiomas()
				.then(function(respuesta){
	    			console.log("Trayendo todos los idiomas: ", respuesta);
	    			vm.languages = respuesta;
	    		}, function(respuesta){
	    			console.log("Error obteniendo idiomas");
	    		})
		},
		obtenerIdiomasFiltrados : function(level,filterIDL) {
			vm.usersFiltrados=[];

    		usersLanguageFactory.leerUsuarioConIdiomaFiltrado(level,filterIDL)
				.then(function(respuesta){
					vm.users_languages = respuesta;
	    			angular.forEach(vm.users_languages,function(eachUserLanguage){
	    				 usersLanguageFactory.leerUsuarioConIdioma(eachUserLanguage.idu)
		    	            usersFactory.leerUsuario(eachUserLanguage.idu)
		    	            		.then(function(respuesta){
		    	            			console.log("Filtro a ",respuesta);
		    	            			vm.usersFiltrados.push(respuesta);
		    	            			angular.forEach(vm.usersFiltrados,function(eachUser){
		    	    	    	            usersLanguageFactory.leerUsuarioConIdioma(eachUser.idu)
		    	    	    	            .then(function(respuesta){
		    	    	    	                eachUser.languages=respuesta;
		    	    	    	                angular.forEach(eachUser.languages,function(eachUserLang){
		    	    	    	    	            languagesFactory.leerIdioma(eachUserLang.idl)
		    	    	    	    	            .then(function(respuesta){
		    	    	    	    	            	console.log("lo hago");
		    	    	    	    	                eachUserLang.idioma = respuesta.langname;
		    	    	    	    	              })
		    	    	    	    	          })
		    	    	    	              })
		    	    	    	          })
		    	            			
	    		    })
				})
				

		    		}, function(respuesta){
		    			console.log("Error obteniendo las relaciones de idiomas y usuarios");
	    		})
	    	
		},
		obtenerUsuariosConIdiomas : function() {	
			usersLanguageFactory.leerUsuariosconIdiomas()
				.then(function(respuesta){
	    			console.log("Trayendo las relaciones de idiomas y usuarios ", respuesta);
	    			vm.users_languages = respuesta;
	    			angular.forEach(vm.users_languages,function(eachUserLang){
	    	            languagesFactory.leerIdioma(eachUserLang.idl)
	    	            .then(function(respuesta){
	    	                console.log("Trayendo idioma: ", respuesta);
	    	                eachUserLang.idioma = respuesta.langname;
	    	              })
	    	          })
	    		}, function(respuesta){
	    			console.log("Error obteniendo las relaciones de idiomas y usuarios");
	    		})
		},
		obtenerTodosLosUsuarios: function(){
			vm.funciones.obtenerUsuarios();
			vm.funciones.obtenerIdiomas();
			vm.funciones.obtenerUsuariosConIdiomas();
			vm.funciones.obtenerLocalizaciones();
		},

    }
	vm.funciones.obtenerTodosLosUsuarios();
})

.controller("detailsCtrl", function(usersFactory,usersLanguageFactory,languagesFactory,commentsFactory,$routeParams,$route,$location,$scope){
    var vm = this;
    vm.user={};
    vm.user_languages=[];
    vm.mensajes= new Array();
    vm.languages=[];
    vm.comments=[];
    vm.comentario={};
    vm.userSession={};
    vm.editable=false;
    vm.funciones = {
    		obtenerDatosUsuario : function(id) {
        		usersFactory.leerUsuario(id)
    				.then(function(respuesta){
    	    			console.log("Trayendo usuario de la session: ", respuesta);
    	    			vm.user = respuesta;
    	    			vm.funciones.obtenerIdiomaUsuario(vm.user.idu);
    	    			vm.funciones.obtenerComentariosUsuario(vm.user.idu);
    	    		}, function(respuesta){
    	    			console.log("Error obteniendo usuario de la sesion");
    	    		})
        		languagesFactory.leerIdiomas()
    				.then(function(respuesta){
    	    			console.log("Trayendo todos los idiomas: ", respuesta);
    	    			vm.languages = respuesta;
    	    		}, function(respuesta){
    	    			console.log("Error obteniendo idiomas");
    	    		})
    	    	usersFactory.leerUsuarioSession()
					.then(function(respuesta){
	    			console.log("Trayendo usuario de la session: ", respuesta);
	    			vm.userSession = respuesta;
	    		}, function(respuesta){
	    			console.log("Error obteniendo usuario de la sesion");
	    		})
   	 
    		},
		obtenerIdiomaUsuario : function(idu) {
			usersLanguageFactory.leerUsuarioConIdioma(idu)
			.then(function(respuesta){
				console.log("Trayendo las relaciones de idiomas y usuarios ", respuesta);
				vm.user_languages = respuesta;
				angular.forEach(vm.user_languages,function(eachUserLang){
		            languagesFactory.leerIdioma(eachUserLang.idl)
		            .then(function(respuesta){
		                console.log("Trayendo idioma: ", respuesta);
		                eachUserLang.idioma = respuesta.langname;
		              })
					})
				})
			},
		obtenerComentariosUsuario : function(id) {
			commentsFactory.leerComentariosReceiver(id)
				.then(function(respuesta){
	    			console.log("Trayendo los comentarios del usuario ", respuesta);
	    			vm.comments = respuesta;
	    		angular.forEach(vm.comments,function(eachComment){
			            usersFactory.leerUsuario(eachComment.sender)
			            .then(function(respuesta){
			    			var date = new Date(eachComment.timeStamp);
			    			eachComment.fecha= date;  
			                console.log("Trayendo nombreUsuario: ", respuesta);
			                eachComment.senderUsername = respuesta.username;
			              })
	    		})
			})
		},
		addComentario : function(id) {
		    vm.mensajes= new Array();
			vm.comentario.receiver = id;
			vm.comentario.sender = vm.userSession.idu;
			commentsFactory.insertarComentario(vm.comentario)
				.then(function(respuesta){
	    			console.log("Añadido comentario ", respuesta);
	    			vm.funciones.obtenerComentariosUsuario(id);
				}, function(respuesta){
					vm.mensajes=respuesta.data.userMessage.toString().split(".");
					console.log("Error añadiendo comentario");
    		})
		},
		deleteComentario : function(id) {
			commentsFactory.eliminarComentario(id)
			.then(function(respuesta){
    			console.log("Eliminado comentario del usuario", respuesta);
    			vm.funciones.obtenerComentariosUsuario(vm.user.idu);
			}, function(respuesta){
    			console.log("Error eliminando comentario");
    		})
		},
		editComentario : function() {
			console.log("Editando comentario del usuario con texto", vm.comentario.text);
		    vm.mensajes= new Array();			
			var time = new Date().getTime();
			var date = new Date(time);
			vm.comentario.timestamp= date;  
			var valorComentario=vm.comentario.text;
			commentsFactory.editarComentario(vm.comentario)
				.then(function(respuesta){
	    			console.log("Editando comentario del usuario ", respuesta);
	    			vm.editable=false;
					vm.mensajes.push("El comentario se ha editado correctamente");			
	    			vm.funciones.obtenerComentariosUsuario(vm.user.idu);
				}, function(respuesta){
					vm.comentario.text=valorComentario;
					vm.mensajes=respuesta.data.userMessage.toString().split(".");
	    			console.log("Error editando comentario");
	    		})
		},
		activarEditar : function(comment) {
			vm.comentario=comment;
			vm.editable=true;
		}
	
		
    }
    if (!($routeParams.ID==undefined)) vm.funciones.obtenerDatosUsuario($routeParams.ID);
})



.controller("profileCtrl", function(usersFactory,usersLanguageFactory,languagesFactory,$routeParams,$route,$location,$scope,$window){
    var vm = this;
    vm.user={};
    vm.user_languages=[];
    vm.userLanguageAdd={};
    vm.mensajes= new Array();
    vm.userLanguageAdd.level="A1";
    vm.userLanguageModificado={};
    vm.password = "";
    vm.Rpassword = "";
    vm.languages=[];
    vm.funciones = {
    		obtenerDatosUsuario : function() {
    			usersFactory.leerUsuarioSession()
    				.then(function(respuesta){
    	    			vm.user = respuesta;
    	    	            usersLanguageFactory.leerUsuarioConIdioma(vm.user.idu)
    	    	            .then(function(respuesta){
    	    	               vm.user.languages=respuesta;
    	    	                angular.forEach(vm.user.languages,function(eachUserLang){
    	    	    	            languagesFactory.leerIdioma(eachUserLang.idl)
    	    	    	            .then(function(respuesta){
    	    	    	                eachUserLang.idioma = respuesta.langname;
    	    	    	              })
    	    	    	          })
    	    	              })
    	    		}, function(respuesta){
    	    			console.log("Error obteniendo usuarios");
    	    		}),
    	    		languagesFactory.leerIdiomas()
    				.then(function(respuesta){
    	    			console.log("Trayendo todos los idiomas: ", respuesta);
    	    			vm.languages = respuesta;
    	    		}, function(respuesta){
    	    			console.log("Error obteniendo idiomas");
    	    		}),
    	    		usersFactory.leerUsuarioSession()
					.then(function(respuesta){
	    			console.log("Trayendo usuario de la session: ", respuesta);
	    			vm.userSession = respuesta;
	    		}, function(respuesta){
	    			console.log("Error obteniendo usuario de la sesion");
	    		})
    	    	
    		},
    	obtenerIdiomaUsuario : function(idu) {
		usersLanguageFactory.leerUsuarioConIdioma(idu)
		.then(function(respuesta){
			console.log("Trayendo las relaciones de idiomas y usuarios ", respuesta);
			vm.user.languages = respuesta;
			angular.forEach(vm.user.languages,function(eachUserLang){
	            languagesFactory.leerIdioma(eachUserLang.idl)
	            .then(function(respuesta){
	                eachUserLang.idioma = respuesta.langname;
	                console.log("Trayendo todos los idiomas",respuesta);
	              })
				})
			})
		},
    	obtenerUsuario : function(idu) {

    		usersFactory.leerUsuario(id)
				.then(function(respuesta){
	    			console.log("Obteniendo usuario: ", respuesta);
	    			vm.user = respuesta;
	    			vm.funciones.obtenerIdiomaUsuario(vm.user.idu);
	    		}, function(respuesta){
	    			console.log("Obteniendo usuario");
	    		})
	 
    		},
		obtenerIdiomas : function() {
    		languagesFactory.leerIdiomas()
				.then(function(respuesta){
	    			console.log("Trayendo todos los idiomas: ", respuesta);
	    			vm.languages = respuesta;
	    		}, function(respuesta){
	    			console.log("Error obteniendo idiomas");
	    		})
		},
		modificarUsuario : function(){
		    vm.mensajes= new Array();
		 if(vm.password != "" && vm.password==vm.Rpassword){
			 vm.user.password = vm.password;
			usersFactory.actualizarUsuario(vm.user)
				.then(function(respuesta){
					console.log("Editando Usuario", respuesta);
					vm.mensajes.push("El usuario se ha editado correctamente");			
					vm.funciones.obtenerUsuario(vm.user.idu);
				}, function(respuesta){
					vm.mensajes=respuesta.data.userMessage.toString().split(".");
    			console.log("Error editando el usuario");
    		})
			 }else{
					vm.mensajes.push("Las contraseñas no coinciden");			
			 }
		},
		borrarUsuario: function(){
			usersFactory.eliminarUsuario(vm.user.idu)
			.then(function(respuesta){
				console.log("Eliminado Usuario", respuesta);
				$window.location.href = "../LogoutServlet";
				
			});
		},
		addIdioma: function(){
			vm.userLanguageAdd.idu=vm.user.idu;
			usersLanguageFactory.nuevoUsuarioConIdioma(vm.userLanguageAdd)
				.then(function(respuesta){
						console.log("Añadido Idioma", respuesta);
						vm.funciones.obtenerIdiomaUsuario(vm.user.idu);
				}, function(respuesta){
	    			console.log("Error añadiendo idioma del usuario");
	    		})
				 
			},
		editarIdioma: function(idl,level){
			vm.userLanguageModificado.idl=idl;
			vm.userLanguageModificado.level=level;
			vm.userLanguageModificado.idu=vm.user.idu;
			usersLanguageFactory.actualizarUsuarioConIdioma(vm.userLanguageModificado)
				.then(function(respuesta){
						console.log("Idioma editado", respuesta);
						vm.funciones.obtenerIdiomaUsuario(vm.user.idu);
	    		}, function(respuesta){
	    			console.log("Error editando idioma del usuario");
	    		})
				 
			},
		deleteIdioma: function(){
			usersLanguageFactory.eliminarUsuarioConIdioma(vm.userLanguageAdd.idl)
				.then(function(respuesta){
						console.log("ID de idioma a eliminar -> "+vm.userLanguageAdd.idl);
						console.log("Idioma Eliminado", respuesta);
						vm.funciones.obtenerIdiomaUsuario(vm.user.idu);
	    		}, function(respuesta){
	    			console.log("Error eliminando idioma del usuario");
	    		})
				 
			}
    }
    
	vm.funciones.obtenerDatosUsuario();
})



.controller("mainAppCtrl", function(usersFactory,$route,$templateCache,$rootScope){
    var vm = this;
    vm.user={};
    vm.funciones = {
		obtenerUsuario : function() {
			usersFactory.leerUsuarioSession()
				.then(function(respuesta){
					vm.user = respuesta
					console.log("Trayendo user con id: ", vm.user.idu," Respuesta: ", respuesta);
    			}, function(respuesta){
    				console.log("Error:", respuesta.status);
    			})
		}
    }
	vm.funciones.obtenerUsuario();
})

	    		//	vm.languages = respuesta;
// filtrarPorNivelIdioma : function(level,filterIDL) {
// vm.usersFiltrados=[];
//
// var insertado;
// angular.forEach(vm.users,function(eachUser){
// insertado=false;
// angular.forEach(vm.users,function(eachUser){
// usersLanguageFactory.leerUsuarioConIdioma(eachUser.idu,level,filterIDL)
// .then(function(respuesta){
// eachUser.languages=respuesta;
// angular.forEach(eachUser.languages,function(eachLanguage){
// languagesFactory.leerIdioma(eachLanguage.idl)
// .then(function(respuesta){
// eachLanguage.idioma = respuesta.langname;
// vm.usersFiltrados.push(eachUser);
//
// // if((level===undefined) && !insertado){
// // insertado=true;
// // vm.usersFiltrados.push(eachUser);
// // }else{
// // if(eachLanguage.level>=level && !insertado){
// // if(filterIDL===undefined){
// // insertado=true;
// // vm.usersFiltrados.push(eachUser);
// // }else{
// // if(eachLanguage.idl==filterIDL){
// // insertado=true;
// // vm.usersFiltrados.push(eachUser);
// // }
// // }
// // }
// // }
//					
//				
// })
// })
// })
// })
