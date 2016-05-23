/*!
 * @author Jonathan Leijendekker
 * Date: 3/7/2016
 * Time: 10:00 AM
 */

angular.module('ATMS')
    .factory('userInterceptor', ['$q', '$injector', function ($q, $injector) {
        return {
            response: function (response) {
                //console.log("RESPONSE SUCCESS");
                //console.log(response);

                /*if (response.data.user)
                 $rootScope.currentUser = response.data.user;
                 else if ((response.data.content && response.data.user == null) || response.config.url == 'api/logout')
                 $rootScope.currentUser = null;*/

                return response
            },
            responseError: function (error) {
                console.log("RESPONSE ERROR");
                console.log(error);

                if (error.status == 500) {
                    $injector.get('$state').transitionTo('error');
                }

                /*if (error.data.user)
                 $rootScope.currentUser = error.data.user;
                 else if (!error.data.errors && error.data.user == null)
                 $rootScope.currentUser = null;*/

                return $q.reject(error);
            }
        }
    }]);