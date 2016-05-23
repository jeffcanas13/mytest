/*!
 * @author Jonathan Leijendekker
 * Date: 2/11/2016
 * Time: 3:10 PM
 */

angular.module('ATMS')
    .controller('HomeController', ['$scope', '$rootScope', '$interval', 'Milestone', 'Activity', 'Project', 'CurrentActivity', 'AssignedMilestones', 'LastProjects', 'Tasks', function ($scope, $rootScope, $interval, Milestone, Activity, Project, CurrentActivity, AssignedMilestones, LastProjects, Tasks) {

        var taskInProgressTimer;

        $rootScope.currentActivity = CurrentActivity.data;
        $scope.assignedMilestones = AssignedMilestones.data;
        $scope.lastProjects = LastProjects.data;
        $scope.tasks = Tasks.data;
        $scope.taskToPerform = [];

        $scope.pagination = {
            maxSize: 5,
            lastProjects: {
                totalItems: $scope.lastProjects ? $scope.lastProjects.TotalRows : 0,
                pageNumber: 1
            },
            assignedMilestones: {
                totalItems: $scope.assignedMilestones ? $scope.assignedMilestones.TotalRows : 0,
                searchQuery: '',
                pageNumber: 1
            }
        };

        $scope.getAssignedMilestones = function (searchQuery, pageNumber) {

            Milestone.getAssignedMilestones(searchQuery, pageNumber)
                .then(function (data) {
                    console.log("GET ASSIGNED MILESTONE SUCCESS")
                    console.log(data);

                    $scope.assignedMilestones = data.data;

                    $scope.taskToPerform = [];

                    $scope.pagination.assignedMilestones.totalItems = $scope.assignedMilestones ? $scope.assignedMilestones.TotalRows : 0;

                }, function (error) {
                    console.log("GET ASSIGNED MILESTONE ERROR");
                    console.log(error);
                })
        };

        $scope.taskIn = function (milestoneId, taskId) {

            Activity.taskIn(milestoneId, taskId)
                .then(function (data) {
                    console.log("TASK IN SUCCESS");
                    console.log(data);

                    $scope.setActivity(data.data, data.data.dateTimeStart);

                }, function (error) {
                    console.log("TASK IN ERROR");
                    console.log(error);
                });

        };

        $scope.taskOut = function (milestoneActivityId) {

            Activity.taskOut(milestoneActivityId)
                .then(function (data) {
                    console.log("TASK OUT SUCCESS");
                    console.log(data);

                    $scope.setActivity(null, null);

                }, function (error) {
                    console.log("TASK OUT ERROR");
                    console.log(error);
                });

        };

        $scope.taskPause = function (milestoneActivityId) {

            Activity.taskPause(milestoneActivityId)
                .then(function (data) {
                    console.log("TASK PAUSE SUCCESS");
                    console.log(data);

                    $scope.setActivity(data.data, data.data.dateTimeStart);

                }, function (error) {
                    console.log("TASK PAUSE ERROR");
                    console.log(error);
                });

        };

        $scope.setActivity = function (content, startTime) {

            $interval.cancel(taskInProgressTimer);

            $rootScope.currentActivity = content;

            if (startTime)
                $scope.startTaskInProgress(startTime);

            $scope.reloadLastProjects($scope.pagination.lastProjects.pageNumber = 1);

        };

        $scope.startTaskInProgress = function (time) {

            var dateTimeFormat = "YYYY-MM-DD HH:mm:ss";
            var timeFormat = "HH:mm:ss";

            var now = moment();
            var then = moment(time);

            var ms = moment(now, dateTimeFormat).diff(moment(then, dateTimeFormat));
            var d = moment.duration(ms, "milliseconds").format(timeFormat, {trim: false});

            $rootScope.currentActivity.taskInProgress = d;

            taskInProgressTimer = $interval(function () {

                now = moment();
                ms = moment(now, dateTimeFormat).diff(moment(then, dateTimeFormat));
                d = moment.duration(ms, "milliseconds").format(timeFormat, {trim: false});

                $rootScope.currentActivity.taskInProgress = d;

            }, 1000);

            $scope.$on('$destroy', function () {
                if (taskInProgressTimer)
                    $interval.cancel(taskInProgressTimer);
            })

        };

        $scope.reloadLastProjects = function (pageNumber) {

            Project.getLastProjects(pageNumber)
                .then(function (data) {

                    console.log("GET LAST PROJECTS SUCCESS");
                    console.log(data);

                    $scope.lastProjects = data.data;

                    $scope.pagination.lastProjects.totalItems = $scope.lastProjects ? $scope.lastProjects.TotalRows : 0;

                }, function (error) {
                    console.log("GET LAST PROJECTS ERROR");
                    console.log(error);
                });

        };

        if (CurrentActivity.data)
            $scope.startTaskInProgress(CurrentActivity.data.dateTimeStart);

    }]);
    
