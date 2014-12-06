function actionController(angular, SimpleLineSymbol, Polyline, Point, Graphic, Color,
                          SimpleMarkerSymbol, CartographicLineSymbol) {

    var scope, stateParams;
    var actionData;
    var groupAreas, geometriesOnMap, userPaths, userLocalizations;
    var MapManager, ActionService, PositionService;
    var lastAskTime;

    function gotAction(action) {
        setTimeout(function () {
            MapManager.displayAreas(action.areas);
        }, 3000);
    }

    function gotPositions(data) {
        lastAskTime = new Date().getTime();
        displayPoints(data);
    }

    function initAction() {
        scope.map = MapManager.generateDrawableMap('mapDiv');

            geometriesOnMap = {};    //Dictionary[geometryNumberInAction] = graphic with area index on map
            groupAreas = {};         //Dictionary[groupId] = List<Graphics of areas index on map>
            userPaths = {};          //Dictionary[userInActionId] = graphic with path index on map
            userLocalizations = {};   //Dictionary[userInActionId] = graphic with localization index on map

            ActionService.get(stateParams.id, gotAction);
            PositionService.getAllByAction({actionId: stateParams.id}, gotPositions);

            window.setInterval(function () {
                var positionsData = {
                    actionId: window.actionId,
                    dateTime: lastAskTime
                };
                PositionService.getAllByActionFromDate(positionsData, gotPositions);
            }, 5000); // repeat forever, polling every 5 seconds
    }

    function displayGroups(groups) {
        var table = document.getElementById("groupsTable");

        for (var i = 0; i < groups.length; i++) {
            getGroupAreas(groups[i]);
            var users = getGroupUsers(groups[i].userInActionIdList);

            var row = table.insertRow(table.rows.length);
            row.onclick = function () {
                selectGroupAreas(this);
            };
            row.ondblclick = function () {
                sendMsgToGroup(this.cells[0].innerHTML);
            };
            var cell0 = row.insertCell(0);
            var cell1 = row.insertCell(1);
            cell0.style.display = "none";

            cell0.innerHTML = groups[i].id;
            cell1.colSpan = 2;
            cell1.innerHTML = "GRUPA " + (i + 1).toString();

            for (var j = 0; j < users.length; j++) {
                var row = table.insertRow(table.rows.length);
                row.onclick = function () {
                    selectUserPath(this);
                };
                row.ondblclick = function () {
                    sendMsgToUser(this.cells[0].innerHTML);
                };
                var cell0 = row.insertCell(0);
                var cell1 = row.insertCell(1);
                var cell2 = row.insertCell(2);
                cell0.style.display = "none";

                cell0.innerHTML = users[j].userInActionId;
                cell1.innerHTML = users[j].nick;
                cell2.innerHTML = users[j].phoneNumber;
            }
        }
    }

    function displayPoints(data) {
        for (var i = 0; i < data.length; i++)
            displayUserPath(data[i].userInActionId, data[i].positions);
    }

    function displayUserPath(userInActionId, positions) {
        positions.sort(
            function (a, b) {
                return a.dateTime - b.dateTime
            }
        );

        if (positions.length < 1)
            return;

        updateUserLocalization(userInActionId, positions[positions.length - 1]);
        updateUserPath(userInActionId, positions);
    }

    function updateUserLocalization(userInActionId, lastPosition) {
        var point = new Point([lastPosition.longitude, lastPosition.latitude]);

        if (!userLocalizations[userInActionId]) {
            var graphic = new Graphic(point, markerSymbol);
            scope.map.graphics.add(graphic);
            userLocalizations[userInActionId] = scope.map.graphics.graphics.indexOf(graphic);
        } else {
            scope.map.graphics.graphics[userLocalizations[userInActionId]].setGeometry(point);
        }
    }

    function updateUserPath(userInActionId, positions) {
        if (!userPaths[userInActionId]) {
            var polylineGraphic = new Graphic(new Polyline([[positions[0].longitude, positions[0].latitude]]), lineSymbol);
            scope.map.graphics.add(polylineGraphic);
            userPaths[userInActionId] = scope.map.graphics.graphics.indexOf(polylineGraphic);
        }

        var polyline = scope.map.graphics.graphics[userPaths[userInActionId]].geometry;
        for (var i = 0; i < positions.length; i++) {
            polyline.insertPoint(0,
                polyline.paths[0].length,
                new Point([positions[i].longitude, positions[i].latitude]));
        }
        scope.map.graphics.graphics[userPaths[userInActionId]].setGeometry(polyline);
    }


    function getGroupAreas(group) {
        areas = [];
        for (var i = 0; i < group.geometryNumberInActionList.length; i++) {
            for (var j = 0; j < actionData.geometries.length; j++) {
                if (actionData.geometries[j].numberInAction == group.geometryNumberInActionList[i])
                    if (geometriesOnMap[group.geometryNumberInActionList[i]])
                        areas.push(geometriesOnMap[group.geometryNumberInActionList[i]]);
                    else
                        console.log("getGroupAreas: geometry don't exists");
            }
        }
        if (groupAreas[group.id])
            console.log("getGroupAreas: groupAres duplicated");
        groupAreas[group.id] = areas;
        return areas;
    }

    function getGroupUsers(usersInActionIdList) {
        users = [];
        for (var i = 0; i < usersInActionIdList.length; i++) {
            for (var j = 0; j < actionData.users.length; j++) {
                if (actionData.users[j].userInActionId == usersInActionIdList[i])
                    users.push(actionData.users[j]);
            }
        }
        if (users.length < 1)
            console.log("getGroupUsers: empty group");
        return users;
    }

    function selectGroupAreas(tableRow) {
        var groupId = tableRow.cells[0].innerHTML;
        deselectAllGraphics();
        for (var i = 0; i < groupAreas[groupId].length; i++)
            scope.map.graphics.graphics[groupAreas[groupId][0]].setSymbol(selectedFillSymbol);
        tableRow.bgColor = "green";
    }

    function sendMsgToGroup(groupId) {
        console.log("msg to group " + groupId.toString());
    }

    function selectUserPath(tableRow) {
        var userInActionId = tableRow.cells[0].innerHTML;
        deselectAllGraphics();
        scope.map.graphics.graphics[userLocalizations[userInActionId]].setSymbol(selectedMarkerSymbol);
        scope.map.graphics.graphics[userPaths[userInActionId]].setSymbol(selectedLineSymbol);
        tableRow.bgColor = "green";
    }

    function sendMsgToUser(userInActionId) {
        console.log("msg to user " + userInActionId.toString());
    }

    var markerSymbol = new SimpleMarkerSymbol(SimpleMarkerSymbol.STYLE_CIRCLE,         //style
        10,                                                                            //size
        new SimpleLineSymbol(SimpleLineSymbol.STYLE_SOLID, new Color([0, 0, 0]), 1),     //outline
        new Color([255, 0, 0, 0.8]));                                                 //color

    var selectedMarkerSymbol = new SimpleMarkerSymbol(SimpleMarkerSymbol.STYLE_CIRCLE, //style
        10,                                                                            //size
        new SimpleLineSymbol(SimpleLineSymbol.STYLE_SOLID, new Color([0, 0, 0]), 1),     //outline
        new Color([0, 255, 0, 0.8]));                                                     //color


    var lineSymbol = new CartographicLineSymbol(
        CartographicLineSymbol.STYLE_SOLID,                                            //style
        new Color([0, 0, 0]),                                                            //color
        2,                                                                             //width
        CartographicLineSymbol.CAP_ROUND,                                              //cap
        CartographicLineSymbol.JOIN_ROUND,                                             //join
        10);                                                                           //miter limit

    var selectedLineSymbol = new CartographicLineSymbol(
        CartographicLineSymbol.STYLE_SOLID,                                            //style
        new Color([0, 255, 0]),                                                          //color
        2,                                                                             //width
        CartographicLineSymbol.CAP_ROUND,                                              //cap
        CartographicLineSymbol.JOIN_ROUND,                                             //join
        10);

    function dipsplayInfo() {
        //TODO: pokazać jakiś formularz z danymi akcji z możliwością edycji kilku
        alert("TODO: pokazać jakiś formularz z danymi akcji z możliwością edycji kilku");
    }

    function ActionController($scope, $stateParams, mapFactory, actionService, positionService) {
        MapManager = mapFactory;
        ActionService = actionService;
        PositionService = positionService;
        scope = $scope;
        stateParams = $stateParams;
        window.actionId = stateParams.id;
        scope.initAction = initAction
    }

    return {
        start: function (App) {
            App.controller('ActionController', ['$scope', '$stateParams', 'MapFactory', 'ActionService', 'PositionService', ActionController]);
            return ActionController;
        }
    };

}

define(['angular', "esri/symbols/SimpleLineSymbol", "esri/geometry/Polyline", "esri/geometry/Point", "esri/graphic", "esri/Color", "esri/symbols/SimpleMarkerSymbol",
    "esri/symbols/CartographicLineSymbol", "dojo/domReady!"], actionController);