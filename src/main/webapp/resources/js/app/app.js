var app = angular.module("app", ["ngRoute", "controllers"]);

app.config(function($routeProvider) {
	$routeProvider
		.when("/", {
			templateUrl: "resources/templates/_list.html",
			controller:"MainController"
		})
		.when("/create", {
			templateUrl: "resources/templates/_create.html",
			controller: "VeiculoController"
		})
		.when("/edit/:id", {
			templateUrl: "resources/templates/_edit.html",
			controller: "EditVeiculoController"
		})
		.when("/delete/:id", {
			controller: "RemoveController"
		})
		.otherwise({
			//templateUrl: "/app/views/body.html",
			controller: "MainController"
		});
});