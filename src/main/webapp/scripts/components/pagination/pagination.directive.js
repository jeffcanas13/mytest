/*!
 * @author Jonathan Leijendekker
 * Date: 3/3/2016
 * Time: 1:37 PM
 */

angular.module('ATMS')
    .directive('pagination', function () {
        return {
            restrict: 'E',
            scope: {
                ngModel: '=',
                itemsPerPage: '=',
                maxSize: '=',
                totalItems: '=',
                ngChange: '&',
                pageSize: '='
            },
            templateUrl: 'scripts/components/pagination/pagination.html',
            link: function(scope) {

                scope.$watch('ngModel', function() {
                    scope.ngChange();
                })

            }
        }
    });