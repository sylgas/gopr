function userService(angular) {

    var http;
    var userService = {};
    var baseUrl = "/api/user/";

    userService.create = function (userData, successCallback, errorCallback) {
        http.post(baseUrl, $.param(userData)).success(successCallback).error(errorCallback);
    };

    userService.createForAction = function (userData, successCallback, errorCallback) {
        http.post("/api/action/user", $.param(userData)).success(successCallback).error(errorCallback);
    };

    userService.getAll = function (successCallback, errorCallback) {
        http.get(baseUrl).success(successCallback).error(errorCallback);
    };

    function UserService($http) {
        http = $http;
        return userService;
    }

    return {
        start: function (App) {
            App.factory('UserService', ['$http', UserService]);
            return UserService;
        }
    };
}

define(['angular'], userService);