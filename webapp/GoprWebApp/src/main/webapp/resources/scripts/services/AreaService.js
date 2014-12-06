function areaService(angular) {

    var http;
    var areaService = {};
    var baseUrl = "/api/area";

    areaService.createAll = function (data, successCallback, errorCallback) {
        var areaNumber;
        for (areaNumber = 1; areaNumber < data.graphics.length; areaNumber++) {
            var areaData = {
                actionId: data.actionId,
                numberInAction: data.geometries[areaNumber].numberInAction,
                name: data.geometries[areaNumber].geometryName,
                geometry: JSON.stringify(data.graphics[areaNumber].geometry)
            };
            $.post(baseUrl, $.param(areaData))
                .success(function (response) {
                    if (areaNumber == data.graphics.length) {
                        successCallback(response);
                    }
                })
                .error(errorCallback);
        }
    };

    function AreaService($http) {
        http = $http;
        return areaService;
    }

    return {
        start: function (App) {
            App.factory('AreaService', ['$http', AreaService]);
            return AreaService;
        }
    };
}

define(['angular'], areaService);