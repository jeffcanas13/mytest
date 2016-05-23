/*!
 * @author Jonathan Leijendekker
 * Date: 1/28/2016
 * Time: 8:12 AM
 */

angular.module("ATMS")
    .config(['$stateProvider', function ($stateProvider) {

        $stateProvider
            .state('login', {
                url: '/login',
                data: {
                    authorities: [],
                    pageTitle: "Login"
                },
                ncyBreadcrumb: {
                    label: "Login"
                },
                views: {
                    "content@": {
                        controller: "LoginController",
                        templateUrl: "scripts/app/login/login.html"
                    }
                }
            })

    }]);
