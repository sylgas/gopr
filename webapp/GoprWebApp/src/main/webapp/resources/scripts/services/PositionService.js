function positionService(angular) {

    var http;
    var positionService = {};

    positionService.getAllByAction = function(id, successCallback, errorCallback) {
        $.get("http://localhost:8090/api/positions/action_all", id).success(successCallback).error(errorCallback)
    };

    positionService.getAllByActionFromDate = function(positionsData, successCallback, errorCallback) {
        $.get("http://localhost:8090/api/positions/action", positionsData).success(successCallback).error(errorCallback)
    };

    function PositionService($http) {
        http = $http;
        return positionService;
    }

    return {
        start: function (App) {
            App.factory('PositionService', ['$http', PositionService]);
            return PositionService;
        }
    };

}

define(['angular'], positionService);