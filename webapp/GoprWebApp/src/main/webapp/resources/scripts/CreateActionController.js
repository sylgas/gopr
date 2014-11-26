function createActionController(angular) {

    var map, toolbar, contextMenu;
    var scope, state;
    var MapManager;

    //endregion
/*
    function setActionGroups() {
        //if (!areGeometriesFine())
          //  return;

        geometryList = Array();
        for (i = 1; i <= Object.keys(geometries).length; i++) {
            //Parse GeometryEx structure
                geometryList[i - 1] = {
                numberInAction: geometries[i].numberInAction,
                name: geometries[i].geometryName,
                geometry: map.graphics.graphics[i].geometry.toJson()
            };
        }

        $.post("http://localhost:8090/action/", {
            name: scope.actionName,
            startDateTime: new Date().getTime(),
            geometries: JSON.stringify(geometryList),
            description: scope.actionDescription
        })
            .done(function (response) {
                if (isNaN(parseInt(response)) || parseInt(response) == -1){
                    alert("Wystąpił błąd z odpowiedzią od serwera!");
                    return;
                }
                sessionStorage.setItem("actionId", response);
                window.location.href = "http://localhost:8090/setActionGroups/";
            })
            .fail(function () {
                alert("Wystąpił błąd z połączeniem z serwerem!");
            });
    }*/

    function startAction() {

        var actionId;
        var geometries = MapManager.getGeometries();
        var geometryList = Array();
        for (i = 1; i <= Object.keys(geometries).length; i++) {
            //Parse GeometryEx structure
            geometryList[i - 1] = {
                numberInAction: geometries[i].numberInAction,
                name: geometries[i].geometryName
            };
        }
        $.post("http://localhost:8090/api/action", {
            name: scope.action.name,
            startDateTime: new Date().getTime(),
            description: scope.action.description
        })
            .done(function (response) {
                if (isNaN(parseInt(response)) || parseInt(response) == -1){
                    alert("Wystąpił błąd z odpowiedzią od serwera!");
                    return;
                }
                sessionStorage.setItem("actionId", response);
                actionId = response
                geometryList = Array();
                for (i = 1; i < map.graphics.graphics.length; ++i) {
                    geometryList[i - 1] = {areaId: i, area: map.graphics.graphics[i].geometry.toJson()};
                }

                for(var j = 0; j<geometryList.length; j++) {
                    $.post("http://localhost:8090/api/area/send", {
                        actionId: actionId,
                        geometries: JSON.stringify(geometryList[j])
                    })
                        .done(function (response) {
                            if (response != true)
                                alert("Wystąpił błąd z połączeniem z serwerem ale przeszlo!");
                            //window.location.href = "http://localhost:8090/action";
                            if(j == geometryList.length) {
                                state.go("action-groups", {id: actionId, reload: true});
                            }
                        })
                        .fail(function () {
                            alert("Wystąpił błąd z połączeniem z serwerem!");
                        });
                }
            })
            .fail(function () {
                alert("Wystąpił błąd z połączeniem z serwerem!");
            });
    }

    var mapFunctions = {
        updateTable: function updateTable(geometries) {
            scope.areas = MapManager.getGeometries();
            scope.$apply()
        },

        GeometryEx: function GeometryEx(numberInAction, geometryName){
            this.numberInAction = numberInAction;
            this.geometryName = geometryName;
        },

        changeAreaProperties: function changeAreaProperties(areaId) {
            console.log("in changeGeometryInfo " + graphicId);
            //TODO: zaimplementowac jakis dialog, zeby pobrac inta i stringa
            //TODO: ustawic geometry[index].numberInAction i geometry[index].geometryName
            //updateTable();
        },

        initToolbarAndContextMenu: function initToolbarAndContextMenu() {
            toolbar = MapManager.getToolbar();
            contextMenu = MapManager.getContextMenu();
        },

        selectTableRow: function selectTableRow(tableRow) {
            scope.selected = tableRow;
        }
    };

    function init() {
        map = MapManager.generateDrawableMap('mapDiv');
        map.on("load", mapFunctions.initToolbarAndContextMenu);
        MapManager.onAddOrDeleteArea(mapFunctions.updateTable);
        MapManager.onChangeAreaProperties(mapFunctions.changeAreaProperties);
        MapManager.onAreaSelect(mapFunctions.selectTableRow);
        MapManager.addGeometryEx(mapFunctions.GeometryEx);
        scope.selectArea = MapManager.selectArea;
        scope.changeAreaProperties = mapFunctions.changeAreaProperties;
        scope.startAction = startAction;
        //scope.setActionGroups = setActionGroups;
        scope.selected = null;
    }

    function CreateActionController($scope, $state, MapFactory) {
        MapManager = MapFactory;
        state = $state;
        scope = $scope;
        scope.init = init;
        scope.action = {};
    }

    return {
        start: function (App) {
            App.controller('CreateActionController', ['$scope', '$state', 'MapFactory', CreateActionController]);
            return CreateActionController;
        }
    };
}

define(['angular', 'esri/map', "esri/toolbars/draw", "esri/toolbars/edit", "esri/symbols/SimpleLineSymbol", "esri/symbols/SimpleFillSymbol",
    "esri/graphic", "esri/Color", "dojo/dom", "dojo/on", "dijit/Menu", "dijit/MenuItem", "dijit/MenuSeparator", "dijit/form/Button",
    "dijit/layout/BorderContainer", "dijit/layout/ContentPane", "dojo/domReady!"], createActionController);