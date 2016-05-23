/*!
 * @author Jonathan Leijendekker
 * Date: 3/2/2016
 * Time: 11:28 AM
 */

angular.module('ATMS')
    .directive('menuToggle', function () {
        return {
            restrict: 'A',
            link: function (scope, elem, attrs) {

                elem.bind('click', function () {
                    angular.element(attrs.menuToggle).toggleClass('toggled');
                })

            }
        }
    })