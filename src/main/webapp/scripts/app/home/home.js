/*!
 * @author Jonathan Leijendekker
 * Date: 2/9/2016
 * Time: 1:25 PM
 */

angular.module("ATMS")
    .config(['$stateProvider', function ($stateProvider) {

        $stateProvider
            .state('home', {
                parent: 'site',
                url: '/',
                data: {
                    authorities: ['ADMIN'],
                    pageTitle: "Home"
                },
                ncyBreadcrumb: {
                    label: "Home"
                },
                views: {
                    "content@": {
                        controller: 'HomeController',
                        templateUrl: 'scripts/app/home/home.html'
                    }
                },
                resolve: {
                    CurrentActivity: ['$q', 'Activity', function ($q, Activity) {
                        var deferred = $q.defer();

                        Activity.getCurrentActivity()
                            .then(function (data) {
                                deferred.resolve(data);
                            }, function (error) {
                                deferred.resolve(error);
                            });

                        return deferred.promise;
                    }],
                    AssignedMilestones: ['$q', 'Milestone', function ($q, Milestone) {
                        var deferred = $q.defer();

                        Milestone.getAssignedMilestones()
                            .then(function (data) {
                                deferred.resolve(data);
                            }, function (error) {
                                deferred.resolve(error);
                            });

                        return deferred.promise;
                    }],
                    LastProjects: ['$q', 'Project', function ($q, Project) {
                        var deferred = $q.defer();

                        Project.getLastProjects()
                            .then(function (data) {
                                deferred.resolve(data);
                            }, function (error) {
                                deferred.resolve(error);
                            });

                        return deferred.promise;
                    }],
                    Tasks: ['$q', 'Task', function ($q, Task) {
                        var deferred = $q.defer();

                        Task.getTasks()
                            .then(function (data) {
                                deferred.resolve(data);
                            }, function (error) {
                                deferred.resolve(error);
                            });

                        return deferred.promise;
                    }]
                }
            })

    }]);
      
      