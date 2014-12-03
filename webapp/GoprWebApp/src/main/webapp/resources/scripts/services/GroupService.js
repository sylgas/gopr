function groupService(angular) {

    var http;
    var groupService = {};
    var baseUrl = "/api/group/";

    groupService.create = function (groupData, successCallback, errorCallback) {
        http.post(baseUrl, $.param(groupData)).success(successCallback).error(errorCallback);
    };

    groupService.getByActionId = function (actionId, successCallback, errorCallback) {
        http.get(baseUrl + "action/" + actionId).success(successCallback).error(errorCallback);
    };

    function GroupService($http) {
        http = $http;
        return groupService;
    }

    return {
        start: function (App) {
            App.factory('GroupService', ['$http', GroupService]);
            return GroupService;
        }
    };
}

define(['angular'], groupService);