/*!
 * @author Jonathan Leijendekker
 * Date: 2/18/2016
 * Time: 8:55 AM
 */

angular.module("ATMS")
    .factory('DateUtil', function() {
        return {
            dateNowInterval: function(startDate) {
                var x = moment().diff(moment(startDate));

                var magicNumber = (1000 * 3600);

                var hourDiff = x / magicNumber;

                return Math.round(hourDiff * 100) / 100;
            }
        }
    })