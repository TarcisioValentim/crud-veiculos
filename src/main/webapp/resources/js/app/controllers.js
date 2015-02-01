var controllers = angular.module("controllers", []);

controllers.controller("MainController", ["$scope", "$rootScope" ,"$http", "$location", function($scope, $rootScope ,$http, $location) {
	alert("ok");
	$http.get("/usuario/logged").
		success(function(data, status, headers, config) {
			$rootScope.usuario = data;
		}).error(function(data, status, headers, config) {
			alert("erro");
		})
}]);