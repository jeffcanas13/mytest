/*!
 * @author Jonathan Leijendekker
 * Date: 1/28/2016
 * Time: 8:15 AM
 */

angular.module("ATMS")
    .controller("LoginController", ['$scope', '$state', 'Auth', function ($scope, $state, Auth) {

        $scope.user = {};

        $scope.login = function () {

            Auth.login($scope.user)
                .then(function (data) {
                    console.log("LOGIN SUCCESS");
                    console.log(data);
                    $scope.status = data;
                    $state.go('home');
                }, function (error) {
                    console.log("LOGIN ERROR");
                    console.log(error);
                    $scope.status = error;
                });
        };

    }]);
