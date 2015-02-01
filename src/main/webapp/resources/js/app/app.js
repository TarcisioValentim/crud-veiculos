var app = angular.module("app", ["ngRoute", "controllers"]);

app.config(function($routeProvider) {
	$routeProvider
		.when("/perfil", {
			templateUrl: "/app/views/vendas/partials/_perfil.html",
			controller:"UsuarioController"
		})
		.when("/vendas/login", {
			templateUrl: "/app/views/vendas/login.html",
			controller: "LoginController"
		})
		.when("/vendas", {
			templateUrl: "/app/views/vendas/index.html",
			controller: "MainController"
		})
		.otherwise({
			templateUrl: "/app/views/body.html",
			controller: "MainController"
		});
});