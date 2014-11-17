function actionGroupsController(angular) {
    var map;
    var scope, state, stateParams;
    var actionId;
    var MapManager;

    function saveGroups(){
        //TODO: poparsować i wysłać podział na grupy
        state.go("actionGroups", {id: actionId, inherit: false});

    }

    function displayUsers() {
        $.get("http://localhost:8090/api/user/")
            .done(function (response) {

                console.log(response)
                scope.users = response
                scope.$apply()
            })
            .fail(function () {
                alert("Wystąpił błąd z połączeniem z serwerem!");
            });
    }

    function initMap() {
        scope.map = MapManager.generateDrawableMap('mapDiv');
        if (isNaN(parseInt(stateParams.id)) || parseInt(stateParams.id) == -1){
            alert("ERROR  :(");
            //TODO: przenieść na stronę z errorem, którą też trzeba zrobić
            return;
        }
        setTimeout(function() {

            geometriesOnMap = {};    //Dictionary[geometryNumberInAction] = graphic with area index on map
            groupAreas = {};         //Dictionary[groupId] = List<Graphics of areas index on map>
            userPaths = {};          //Dictionary[userInActionId] = graphic with path index on map
            userLocalizations = {}   //Dictionary[userInActionId] = graphic with localization index on map

            $.get("http://localhost:8090/action/get", {
                id: stateParams.id
            })
                .done(function (response) {
                    console.log(response.areas)
                    MapManager.displayAreas(response.areas);
                })
                .fail(function () {
                    alert("Wystąpił błąd z połączeniem z serwerem!");
                });
        }, 3000);
        displayUsers()
    }

    function addToGroup(user) {
        console.log(scope.users.indexOf(user));
        scope.users.splice(scope.users.indexOf(user), 1);
        scope.groupUsers.push(user);
    }

    function removeFromGroup(user) {
        scope.groupUsers.splice(scope.groupUsers.indexOf(user), 1);
        scope.users.push(user);
    }

    function addGroup() {
        $.post("http://localhost:8090/api/group", {
            actionId: stateParams.id,
            name: scope.groupName
        })
            .done(function (response) {
                console.log(response)

                console.log(response.areas)
            })
            .fail(function () {
                alert("Wystąpił błąd z połączeniem z serwerem!");
            });
    }

    function ActionGroupsController($scope, $state, $stateParams, mapFactory) {
        MapManager = mapFactory;
        scope = $scope;
        state = $state;
        stateParams = $stateParams;
        scope.initMap = initMap;
        scope.addToGroup = addToGroup;
        scope.removeFromGroup = removeFromGroup;
        scope.addGroup = addGroup;
        scope.groupUsers = []
    }

    return {
        start: function (App) {
            App.controller('ActionGroupsController', ['$scope', '$state', '$stateParams', 'MapFactory', ActionGroupsController]);
            return ActionGroupsController;
        }
    };

};

define(['angular'], actionGroupsController)
