/*!
 * @author Jonathan Leijendekker
 * Date: 2/23/2016
 * Time: 1:56 PM
 */

angular.module('ATMS')
    .factory('Task', ['$http', function ($http) {
        return {
            getTasks: function () {
                return $http({
                    method: 'GET',
                    url: 'api/v1/home/tasks'
                })
            }
        }
    }])