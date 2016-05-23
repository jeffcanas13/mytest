/*!
 * @author Jonathan Leijendekker
 * Date: 2/5/2016
 * Time: 12:08 PM
 */

angular.module("ATMS")
    .factory('Principal', ['$q', 'Account', function ($q, Account) {
        var _identity,
            _authenticated = false;

        return {
            isAuthenticated: function () {
                return _authenticated;
            },
            hasAuthority: function (authority) {
                if (!_authenticated) {
                    return $q.when(false);
                }

                return this.identity().then(function (_id) {
                    return _id.authorities && _id.authorities.indexOf(authority).authority !== -1;
                }, function (err) {
                    return false;
                });
            },
            hasAnyAuthority: function (authorities) {
                if (!_authenticated || !_identity || !_identity.authorities) {
                    return false;
                }

                for (var i = 0; i < authorities.length; i++) {
                    if (_identity.authorities.indexOf(authorities[i]).authority !== -1) {
                        return true;
                    }
                }

                return false;
            },
            authenticate: function (identity) {
                _identity = identity;
                _authenticated = identity !== null;
            },
            getIdentity: function () {
                return _identity;
            },
            identity: function () {
                var deferred = $q.defer();

                Account.get()
                    .then(function (account) {
                        _identity = account.data;
                        _authenticated = true;
                        deferred.resolve(_identity);
                    })
                    .catch(function () {
                        _identity = null;
                        _authenticated = false;
                        deferred.resolve(_identity);
                    });
                return deferred.promise;
            }
        }
    }]);