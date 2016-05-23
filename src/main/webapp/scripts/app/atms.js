/*!
 * @author Jonathan Leijendekker
 * Date: 1/21/2016
 * Time: 11:58 AM
 */


angular.module('ATMS', ['ui.router', 'angular-loading-bar', 'ngAnimate', 'angularMoment', 'ui.bootstrap', 'ncy-angular-breadcrumb'])
    .config(['$locationProvider', 'cfpLoadingBarProvider', '$stateProvider', '$httpProvider', function ($locationProvider, cfpLoadingBarProvider, $stateProvider, $httpProvider) {

        cfpLoadingBarProvider.includeSpinner = false;
        cfpLoadingBarProvider.latencyThreshold = 1;

        $locationProvider.html5Mode(true).hashPrefix('!');

        $stateProvider.state('site', {
            abstract: true,
            views: {
                'navbar': {
                    templateUrl: 'scripts/components/navbar/navbar.html'
                },
                'header': {
                    controller: 'HeaderController',
                    templateUrl: 'scripts/components/header/header.html'
                }
            }
        });

        $httpProvider.interceptors.push('userInterceptor');
    }])
    .run(['$rootScope', '$window', 'Account', 'Principal', 'Auth', function ($rootScope, $window, Account, Principal, Auth) {

        $rootScope.$on('$stateChangeStart', function (event, toState, toParams, fromState, fromParams) {
            $rootScope.toState = toState;
            $rootScope.toParams = toParams;

            Auth.authorize()
                .then(function () {
                    $rootScope.currentUser = Principal.getIdentity();
                });
        });

        $rootScope.$on('$stateChangeSuccess', function (event, toState, toParams, fromState, fromParams) {

            var pageTitle;

            if (toState.data.pageTitle) {
                pageTitle = toState.data.pageTitle;
            } else {
                pageTitle = "Activity Tracking and Monitoring System";
            }

            $window.document.title = "ATMS - " + pageTitle;
        });

    }]);