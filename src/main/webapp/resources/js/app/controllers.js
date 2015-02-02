var controllers = angular.module("controllers", []);

controllers.controller("MainController", ["$scope", "$rootScope" ,"$http", "$location", function($scope, $rootScope ,$http, $location) {
	$http.get("/crud-veiculos/veiculos").
		success(function(data, status, headers, config) {
			$rootScope.veiculos = data;
			$scope.veiculos = data
		}).error(function(data, status, headers, config) {
			alert("erro");
		})
	$scope.remover = function(id) {
		$http.delete("/crud-veiculos/veiculos/delete/"+id+"").
		success(function(data, status, headers, config) {
			alert("Veiculo deletado");
			//$location.path("/").replace();
			window.location = "/crud-veiculos";
		}).error(function(data, status, headers, config) {
			alert("erro");
		})
	}
}]);

controllers.controller("VeiculoController", ["$scope", "$rootScope" ,"$http", "$location", function($scope, $rootScope ,$http, $location) {
	$scope.save = function() {
		$http.post("/crud-veiculos/veiculos/save", $scope.veiculo, {
		}).
			success(function(data, status, headers, config) {
				alert("Veículo salvo com sucesso");
				var path = "/edit/"+data.id;
				$location.path(path).replace();
			}).error(function(data, status, headers, config) {
				alert("erro");
			})		
	}
	
}]);

controllers.controller("EditVeiculoController", ["$scope", "$rootScope" ,"$http", "$location", "$routeParams", function($scope, $rootScope ,$http, $location, $routeParams, $window) {
		$http.get("/crud-veiculos/veiculos/"+$routeParams.id+"").
			success(function(data, status, headers, config) {
				$rootScope.veiculo = data;
				$scope.veiculo = data
			}).error(function(data, status, headers, config) {
				alert("erro");
			})
		
		$scope.update = function() {
				$http.post("/crud-veiculos/veiculos/update/"+$scope.veiculo.id+"", {
					fabricante: $scope.veiculo.fabricante,
					modelo: $scope.veiculo.modelo,
					ano: $scope.veiculo.ano
				}).
				success(function(data, status, headers, config) {
					alert("Veículo alterado com sucesso");
					$location.path("/").replace();
				}).error(function(data, status, headers, config) {
					alert("erro");
				})
	}
}]);

controllers.controller("RemoveController", ["$scope", "$rootScope" ,"$http", "$location", "$routeParams", "$window",  function($scope, $rootScope ,$http, $location, $routeParams, $window) {
	if($window.confirm("você tem certeza?")) {
		$http.delete("/crud-veiculos/delete/"+$routeParams.id+"").
			success(function(data, status, headers, config) {
				alert("Veiculo deletado");
			}).error(function(data, status, headers, config) {
				alert("erro");
			})
	}
}]);