/*!
 * @author Jonathan Leijendekker
 * Date: 2/9/2016
 * Time: 1:25 PM
 */

angular.module("ATMS")
    .config(['$stateProvider', function ($stateProvider) {

        $stateProvider
            .state('vertical', {
                parent: 'site',
                url: '/verticals',
                data: {
                    authorities: [],
                    pageTitle: "Verticals"
                },
                ncyBreadcrumb: {
                    label: "Verticals"
                },
                views: {
                    "content@": {
                        controller: 'VerticalController',
                        templateUrl: 'scripts/app/vertical/vertical.html'
                    }
                },
                resolve: {
                    VerticalList: ['$q', 'Vertical', function ($q, Vertical) {
                        var deferred = $q.defer();

                        Vertical.getVerticals().then(function (data) {
                            deferred.resolve(data);
                            console.log(data.data.content);

                        }, function (error) {
                            deferred.resolve(error);
                        });

                        return deferred.promise;
                    }]
                }
            })

    }]);
      
      