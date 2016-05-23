/*!
 * @author Jonathan Leijendekker
 * Date: 3/10/2016
 * Time: 1:32 PM
 */

angular.module('ATMS')
    .factory('Account', ['$http', function ($http) {
        return {
            get: function () {
                return $http({
                    method: 'GET',
                    url: 'api/account'
                });
            }
        }
    }])