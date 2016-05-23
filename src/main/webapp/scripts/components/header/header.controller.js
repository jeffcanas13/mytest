/*!
 * @author Jonathan Leijendekker
 * Date: 3/10/2016
 * Time: 12:49 PM
 */

angular.module('ATMS')
    .controller('HeaderController', ['$scope', '$state', 'Auth', function ($scope, $state, Auth) {

        $scope.logout = function () {
            Auth.logout()
                .then(function (data) {
                    console.log("LOGOUT SUCCESS");
                    console.log(data);
                    $state.go('login');
                }, function (error) {
                    console.log("LOGOUT ERROR");
                    console.log(error);
                });
        }

    }]);