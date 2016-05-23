/*!
 * @author Jonathan Leijendekker
 * Date: 2/23/2016
 * Time: 1:54 PM
 */

angular.module('ATMS')
    .factory('Activity', ['$http', function ($http) {
        return {
            taskIn: function (assignedMilestoneId, taskId) {
                return $http({
                    method: 'POST',
                    url: 'api/v1/home/activity/in',
                    data: {
                        assignedMilestoneId: assignedMilestoneId,
                        taskId: taskId
                    }
                })
            },
            taskOut: function (milestoneActivityId) {
                return $http({
                    url: 'api/v1/home/activity/out',
                    method: 'POST',
                    data: milestoneActivityId
                })
            },
            taskPause: function (milestoneActivityId) {
                return $http({
                    url: 'api/v1/home/activity/pause',
                    method: 'POST',
                    data: milestoneActivityId
                })
            },
            getCurrentActivity: function () {
                return $http({
                    method: 'GET',
                    url: 'api/v1/home/currentactivity'
                })
            }
        }
    }])