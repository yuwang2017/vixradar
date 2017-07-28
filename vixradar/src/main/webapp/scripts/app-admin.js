angular.module("OptionTradeAppModule", ["ngRoute", "ngAnimate", "ngSanitize", "mgcrea.ngStrap", "datatables"])
	.config(['$routeProvider', function($routeProvider){	
		$routeProvider
			.when("/home", {
				templateUrl: "views/dashboard.html",
				controller: "homeController"
			})
			.when("/employee", {
				templateUrl: "views/employee.html",
				controller: "employeeController"
			})	
			.when("/grids", {
				templateUrl: "views/grids.html",
				controller: "gridsController"
			})
			.when("/form", {
				templateUrl: "views/form.html",
				controller: "formController"
			})
			.otherwise ({
				redirectTo: "/home"
			});
	}]);

