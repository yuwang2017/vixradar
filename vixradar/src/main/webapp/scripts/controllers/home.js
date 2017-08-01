angular.module('OptionTradeAppModule').controller('homeController', function($scope, $http, UserService, EchoService) {
	
	 $scope.symbol = "VXX";
	 $scope.expire = "";
	 $scope.strategy = "single";
	 
	 $http.get("servlet/getOptionMeta?symbol=VXX")
		.then(function(response){
			$scope.meta = response.data;
			$scope.strikes1 = $scope.meta.strikes;
			$scope.strikes2 = $scope.meta.strikes;
		});

	 
	 //Bind function to scope
	 $scope.loadStock = function() {		 
		 var url = "servlet/getOptionMeta?symbol=" + $scope.symbol;
		 $http.get(url)
			.then(function(response){
				$scope.meta = response.data;
				$scope.strikes1 = $scope.meta.strikes;
				$scope.strikes2 = $scope.meta.strikes;
				$scope.chain = null;
				 $("#morris-line-chart").html("");
			});
	  };
	  
	  $scope.loadOptionChain = function() {		 
		  var url = "servlet/getOptionChain?symbol=" + $scope.symbol + "&expireDate=" + $scope.expire;
		  $http.get(url)
				.then(function(response){
					$scope.chain = response.data;
					$scope.meta.quote = $scope.chain.quote;
					$("#morris-line-chart").html("");
		  });
	  };
	  
	  $scope.calculateStrategy = function() {
		  var dataObj = {
				  symbol : $scope.symbol,
				  expire :  $scope.expire,
				  strategyType : $scope.strategy,
				  action1 : $scope.action1,
				  option1 : $scope.option1,
				  strike1 : $scope.strike1,
				  
				  action2 : $scope.action2,
				  option2 : $scope.option2,
				  strike2 : $scope.strike2				  					
		  };	
		  $("#morris-line-chart").html("");
		  $http.post("servlet/getOptionStrategyResults",dataObj)
			 	.then(function(success){
			 		$scope.morrisLineData = success.data;
			 		console.log($scope.morrisLineData);
			 		Morris.Line($scope.morrisLineData);
		  });
	  }
		
});
