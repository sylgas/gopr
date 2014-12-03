function createActionController(angular) {

    var map, toolbar, contextMenu;
    var scope, state, modal;
    var MapManager;

    function setActionGroups() {

        var actionId;

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
                actionId = response;

                var geometries = MapManager.getGeometries();
                for(var j = 1; j < map.graphics.graphics.length; j++) {
                    $.post("http://localhost:8090/api/area/send", {
                        actionId: actionId,
                        numberInAction: geometries[j].numberInAction,
                        name: geometries[j].geometryName,
                        geometry: JSON.stringify(map.graphics.graphics[j].geometry)
                    })
                        .done(function (response) {
                            if (response != true)
                                alert("Wystąpił błąd z połączeniem z serwerem ale przeszlo!");
                            if(j == map.graphics.graphics.length)
                                state.go("action-groups", {id: actionId, reload: true});
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

    function editArea(index) {
        var modalInstance = modal.open({
            templateUrl: 'area-edit-dialog',
            controller: 'AreaController',
            resolve: {
                area: function () {
                    return scope.areas[index];
                }
            }
        });

        modalInstance.result.then(function (editedArea) {
            var geometries = MapManager.getGeometries();
            geometries[index].geometryName = editedArea.geometryName;
            geometries[index].numberInAction = editedArea.numberInAction
        }, function () {
            console.log('Modal dismissed at: ' + new Date());
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

        changeAreaProperties: function changeAreaProperties(index) {
            //editArea(index)
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
        scope.setActionGroups = setActionGroups;
        scope.editArea = editArea;
        scope.selected = null;
    }

    function CreateActionController($scope, $state, $modal, MapFactory) {
        MapManager = MapFactory;
        state = $state;
        modal = $modal;
        scope = $scope;
        scope.init = init;
        scope.action = {};
    }

    return {
        start: function (App) {
            App.controller('CreateActionController', ['$scope', '$state', '$modal', 'MapFactory', CreateActionController]);
            return CreateActionController;
        }
    };
}

define(['angular', 'esri/map', "esri/toolbars/draw", "esri/toolbars/edit", "esri/symbols/SimpleLineSymbol", "esri/symbols/SimpleFillSymbol",
    "esri/graphic", "esri/Color", "dojo/dom", "dojo/on", "dijit/Menu", "dijit/MenuItem", "dijit/MenuSeparator", "dijit/form/Button",
    "dijit/layout/BorderContainer", "dijit/layout/ContentPane", "dojo/domReady!"], createActionController);