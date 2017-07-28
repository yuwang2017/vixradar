angular.module('OptionTradeAppModule').factory('EchoService', function(){
	return {		
		echo : function(input) {
			return 'Hello! ' + input;
		},
		
		hello : function() {
			return 'Hello New World';
		}
	}	
});