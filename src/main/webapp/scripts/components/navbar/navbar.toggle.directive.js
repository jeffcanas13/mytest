/*!
 * @author Jonathan Leijendekker
 * Date: 3/2/2016
 * Time: 1:35 PM
 */

angular.module('ATMS')
    .directive('toggleNavbarCollapse', function () {
        return {
            restrict: 'A',
            link: function (scope, elem, attrs) {

                elem.bind('click', function() {
                    angular.element(attrs.toggleNavbarCollapse).removeClass('toggled');
                })

            }
        }
    })