/*!
 * @author Jonathan Leijendekker
 * Date: 3/2/2016
 * Time: 1:04 PM
 */

angular.module('ATMS')
    .directive('sidebarMenu', function () {
        return {
            restrict: 'A',
            link: function (scope, elem, attrs) {

                elem.find('li ul').slideUp();

                elem.find('li').bind('click', function () {

                    var $this = angular.element(this);

                    if ($this.is('active')) {
                        angular.element('ul', this).slideUp();
                        $this.removeClass('nv');
                        $this.addClass('vn');
                    } else {
                        elem.find('li ul').slideUp();
                        $this.removeClass('vn');
                        $this.addClass('nv');
                        angular.element('ul', this).slideDown();
                    }

                })

            }
        }
    })