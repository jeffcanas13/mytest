/*!
 * @author Jonathan Leijendekker
 * Date: 2/23/2016
 * Time: 1:55 PM
 */

angular.module('ATMS')
    .factory('Project', ['$http', function ($http) {
        return {
			getLastProjects: function(pageNumber) {
                return $http({
                    method: 'GET',
                    url: 'api/v1/home/lastprojects',
                    params: { 
                    	pageNumber: pageNumber 
                    }
                })
            },
            getProjects: function (searchQuery, pageNumber) {
                return $http({
                    method: 'GET',
                    url: 'api/v1/projects',
                    params: {
                        searchQuery: searchQuery,
                        pageNumber: pageNumber
                    }
                })
            },
            newProject: function (project) {
                return $http({
                    method: 'POST',
                    url: 'api/v1/projects',
                    params: project
                })
            }
        }
    }])