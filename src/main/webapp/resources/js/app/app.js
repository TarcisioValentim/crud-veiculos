var app = angular.module("app", ["ngRoute", "controllers", 'naif.base64']);

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

app.directive('fileUpload', function () {
    return {
        scope: true,        //create a new scope
        link: function (scope, el, attrs) {
            el.bind('change', function (event) {
                var file = event.target.file;
                //iterate files since 'multiple' may be specified on the element
                scope.$emit("fileSelected", { file: file });
            });
        }
    };
});
