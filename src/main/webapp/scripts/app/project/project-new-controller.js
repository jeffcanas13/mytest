/*!
 * @author Jonathan Leijendekker
 * Date: 2/11/2016
 * Time: 3:10 PM
 */

angular.module('ATMS')
    .controller('ProjectNewController', ['$scope', '$rootScope', '$interval', 'Project', 'ProjectList', function ($scope, $rootScope, $interval, Project, ProjectList) {

       
        
//        $scope.taskToPerform = [];
        $scope.projectList = ProjectList.data.content;
        
//        $scope.tasks = Tasks.data.content;
        $scope.bigCurrentPage = 1;
        $scope.recordPerPage = 10;

        $scope.pagination = {
            maxSize: 10,
            
            projectList: {
                totalItems: $scope.projectList ? $scope.projectList.TotalRows : 0,
                searchQuery: '',
                pageNumber: 1
            }
        };

        $scope.getProjects = function (searchQuery, pageNumber) {

            Project.getProjects(searchQuery, pageNumber).then(function (data) {
                console.log("GET ASSIGNED MILESTONE SUCCESS")
                console.log(data.data.content);
                console.log($scope.projectList);
                $scope.projectList = data.data.content;

//                $scope.taskToPerform = [];
//                
                $scope.pagination.projectList.totalItems = $scope.projectList? $scope.projectList.TotalRows : 0;

            }, function (error) {
                console.log("PROJECT LIST ERROR");
                console.log(error);
            })
        };

       
    }]);
    
