/*!
 * @author Jonathan Leijendekker
 * Date: 3/10/2016
 * Time: 12:50 PM
 */

angular.module('ATMS')
    .factory('Auth', ['$q', '$http', '$state', '$rootScope', 'Principal', function ($q, $http, $state, $rootScope, Principal) {
        return {
            login: function (user) {
                var deferred = $q.defer();

                $http({
                    method: 'POST',
                    url: "api/login",
                    data: user
                }).then(function (data) {
                    Principal.identity()
                        .then(function (account) {
                            deferred.resolve(data);
                        })
                })

                return deferred.promise;
            },
            logout: function () {
                return $http({
                    method: 'POST',
                    url: 'api/logout'
                });
            },
            authorize: function () {
                return Principal.identity()
                    .then(function () {
                        var isAuthenticated = Principal.isAuthenticated();

                        if (isAuthenticated && $rootScope.toState.name === 'login') {
                            $state.go('home');
                        }

                        if ($rootScope.toState.data.authorities && $rootScope.toState.data.authorities.length > 0 && !Principal.hasAnyAuthority($rootScope.toState.data.authorities)) {
                            if (isAuthenticated) {
                                $state.go('accessdenied');
                            }
                            else {
                                $rootScope.previousStateName = $rootScope.toState;
                                $rootScope.previousStateNameParams = $rootScope.toParams;

                                $state.go('login');
                            }
                        }
                    })
            }
        }
    }])