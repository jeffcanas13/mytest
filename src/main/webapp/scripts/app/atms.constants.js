/*!
 * @author Jonathan Leijendekker
 * Date: 2/18/2016
 * Time: 11:42 AM
 */

angular.module("ATMS")
    .constant('angularMomentConfig', {
        timezone: 'Asia/Manila'
    })
    .run(function () {
        moment.tz.setDefault("Asia/Manila");
    })