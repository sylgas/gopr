function createActionController(angular) {

    var map, toolbar, contextMenu;
    var scope, state, modal;
    var MapManager, ActionService, AreaService;
    var actionId;

    function areasCreated() {
        state.go("action-groups", {id: actionId, reload: true});
    }

    function actionCreated(id) {
        actionId = id;
        var areasData = {
            actionId: actionId,
            geometries: MapManager.getGeometries(),
            graphics: map.graphics.graphics
        };
        AreaService.createAll(areasData, areasCreated);
    }

    function createAction() {
        var actionData = {
            name: scope.action.name,
            description: scope.action.description
        };
        ActionService.create(actionData, actionCreated)
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

        GeometryEx: function GeometryEx(numberInAction, geometryName) {
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
        scope.createAction = createAction;
        scope.editArea = editArea;
        scope.selected = null;
    }

    function CreateActionController($scope, $state, $modal, MapFactory, actionService, areaService) {
        MapManager = MapFactory;
        ActionService = actionService;
        AreaService = areaService;
        state = $state;
        modal = $modal;
        scope = $scope;
        scope.init = init;
        scope.action = {};
    }

    return {
        start: function (App) {
            App.controller('CreateActionController', ['$scope', '$state', '$modal', 'MapFactory', 'ActionService', 'AreaService', CreateActionController]);
            return CreateActionController;
        }
    };
}

define(['angular', 'esri/map', "esri/toolbars/draw", "esri/toolbars/edit", "esri/symbols/SimpleLineSymbol", "esri/symbols/SimpleFillSymbol",
    "esri/graphic", "esri/Color", "dojo/dom", "dojo/on", "dijit/Menu", "dijit/MenuItem", "dijit/MenuSeparator", "dijit/form/Button",
    "dijit/layout/BorderContainer", "dijit/layout/ContentPane", "dojo/domReady!"], createActionController);