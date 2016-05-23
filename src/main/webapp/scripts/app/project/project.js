/*!
 * @author Jonathan Leijendekker
 * Date: 2/9/2016
 * Time: 1:25 PM
 */

angular.module("ATMS")
    .config(['$stateProvider', function ($stateProvider) {

        $stateProvider
            .state('project', {
                parent: 'site',
                url: '/projects',
                page: {
                    title: "Projects"
                },
                views: {
                    "content@": {
                        controller: 'ProjectController',
                        templateUrl: 'scripts/app/project/project.html'
                    }
                },
                resolve:{
                ProjectList: ['$q', 'Project', function ($q, Project) {
                    var deferred = $q.defer();

                    Project.getProjects().then(function (data) {
                        deferred.resolve(data);
                    }, function (error) {
                        deferred.resolve(error);
                    });

                    return deferred.promise;
                }]
                
            }        
            })
            .state('project.new', {
            	parent: 'project',
            	url: '/new',
            	page: {
            		title: "Projects"
            	},
            	views: {
            		"content@": {
            			controller: 'ProjectNewController',
            			templateUrl: 'scripts/app/project/project-new.html'
            		}
            	}
            })
    
    }]);
      
      