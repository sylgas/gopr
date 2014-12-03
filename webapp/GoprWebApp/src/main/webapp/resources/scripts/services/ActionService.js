function actionService(angular) {

    var http;
    var actionService = {};
    var baseUrl = "/api/action/";

    actionService.create = function (actionData, successCallback, errorCallback) {
        http.post(baseUrl, $.param(actionData)).success(successCallback).error(errorCallback);
    };

    actionService.get = function (id, successCallback, errorCallback) {
        http.get(baseUrl + id).success(successCallback).error(errorCallback);
    };

    actionService.getAll = function (successCallback, errorCallback) {
        http.get(baseUrl).success(successCallback).error(errorCallback);
    };

    function ActionService($http) {
        http = $http;
        return actionService;
    }

    return {
        start: function (App) {
            App.factory('ActionService', ['$http', ActionService]);
            return ActionService;
        }
    };
}

define(['angular'], actionService);