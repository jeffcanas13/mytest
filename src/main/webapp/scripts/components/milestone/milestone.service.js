/*!
 * @author Jonathan Leijendekker
 * Date: 2/11/2016
 * Time: 2:57 PM
 */

angular.module('ATMS')
    .factory('Milestone', ['$http', function ($http) {
        return {
            getAssignedMilestones: function (searchQuery, pageNumber) {
                return $http({
                    method: 'GET',
                    url: 'api/v1/home/assignedmilestones',
                    params: {
                        searchQuery: searchQuery,
                        pageNumber: pageNumber
                    }
                })
            }
        }
    }])

