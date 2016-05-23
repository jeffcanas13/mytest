/*!
 * @author Jonathan Leijendekker
 * Date: 2/11/2016
 * Time: 3:10 PM
 */

angular.module('ATMS')
    .controller('VerticalController', ['$scope', '$rootScope', '$interval', 'Vertical', 'VerticalList', function ($scope, $rootScope, $interval, Vertical, VerticalList) {
    	$scope.verticalList = VerticalList.data.content;
    	
    	$scope.listVerticals = function(){
    		Vertical.getVerticals().then(function(data){
    			
    			console.log("GET VERTICALS SUCCESS");
    			console.log(data);
    			
    			$scope.verticalList = data.data.content;
    		}, function(error){
    			console.log("GET VERTICALS ERROR");
    			console.log(error);
    		})
    	};
    	
    	$scope.saveVertical = function(){
    		Vertical.saveVertical($scope.vertical).then(function(data){
    				
    			console.log("SAVE VERTICALS SUCCESS");
    			console.log(data);
    			
    			$scope.listVerticals();
    			$scope.vertical = null;
    		}, function(error){
    			console.log("SAVE VERTICALS ERROR");
    			console.log(error);
    		})
    	};
    	
    }]);
    
