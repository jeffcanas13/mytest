/*!
 * @author Jonathan Leijendekker
 * Date: 3/11/2016
 * Time: 11:11 AM
 */

angular.module('ATMS')
    .config(['$stateProvider', function ($stateProvider) {

        $stateProvider
            .state('error', {
                parent: 'site',
                url: '/error',
                data: {
                    authorities: [],
                    pageTitle: "Error"
                },
                ncyBreadcrumb: {
                    label: "Error"
                },
                views: {
                    'content@': {
                        templateUrl: 'scripts/app/error/error.html'
                    }
                }
            })
            .state('accessdenied', {
                parent: 'site',
                url: '/accessdenied',
                data: {
                    authorities: [],
                    pageTitle: 'Access Denied'
                },
                views: {
                    'content@': {
                        templateUrl: 'scripts/app/error/accessdenied.html'
                    }
                }
            });

    }])