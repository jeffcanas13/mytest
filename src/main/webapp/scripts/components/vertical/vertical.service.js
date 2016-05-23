angular.module('ATMS')
    .factory('Vertical', ['$http', function ($http) {
        return {
            getVerticals: function () {
                return $http({
                    method: 'GET',
                    url: 'api/v1/verticals'
                })
            },
        
        	saveVertical: function(vertical){
        		return $http({
        			method: 'POST',
        			url: 'api/v1/verticals',
        			data: vertical
        		})
        	}
        }
    }])
