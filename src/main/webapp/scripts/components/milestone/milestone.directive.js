/*!
 * @author Jonathan Leijendekker
 * Date: 2/12/2016
 * Time: 11:32 AM
 */

angular.module('ATMS')
    .directive('milestoneTask', function () {
        return {
            restrict: 'A',
            link: function (scope, elem, attrs) {

                elem.bind('click', function () {
                    if (attrs.milestoneTask == 'in') {
                        scope.taskIn(attrs.assignedMilestone, attrs.task);
                        scope.taskToPerform[scope.$index] = null;
                    }
                    else if (attrs.milestoneTask == 'out')
                        scope.taskOut(attrs.milestoneActivity);
                    else if (attrs.milestoneTask == 'pause')
                        scope.taskPause(attrs.milestoneActivity);
                })

            }
        }
    })