function actionGroupsController(angular) {
    var map;
    var scope, state, stateParams;
    var actionId;
    var MapManager;
    var modal;

    function saveGroups(){
        //TODO: poparsować i wysłać podział na grupy
        state.go("actionGroups", {id: actionId, inherit: false});

    }

    function displayUsers() {
        $.get("http://localhost:8090/api/user/")
            .done(function (response) {
                scope.users = response
                scope.$apply()
            })
            .fail(function () {
                alert("Wystąpił błąd z połączeniem z serwerem!");
            });
    }

    function initMap() {
        if (isNaN(parseInt(stateParams.id)) || parseInt(stateParams.id) == -1) {
            alert("ERROR  :(");
            //TODO: przenieść na stronę z errorem, którą też trzeba zrobić
            return;
        }

            geometriesOnMap = {};    //Dictionary[geometryNumberInAction] = graphic with area index on map
            groupAreas = {};         //Dictionary[groupId] = List<Graphics of areas index on map>
            userPaths = {};          //Dictionary[userInActionId] = graphic with path index on map
            userLocalizations = {}   //Dictionary[userInActionId] = graphic with localization index on map

            $.get("http://localhost:8090/api/action/" + stateParams.id)
                .done(function (response) {
                    scope.action = response
                    scope.$apply()
                    setTimeout(function() {
                        MapManager.displayAreas(response.areas);
                    }, 2000);
                })
                .fail(function () {
                    alert("Wystąpił błąd z połączeniem z serwerem!");
                });
        displayUsers()
    }

    function addToGroup(user) {
        console.log(scope.users.indexOf(user));
        scope.users.splice(scope.users.indexOf(user), 1);
        scope.group.users.push(user);
    }

    function removeFromGroup(user) {
        scope.group.users.splice(scope.group.users.indexOf(user), 1);
        scope.users.push(user);
    }

    function saveGroupParticipants(group) {
        console.log(group)
        console.log(scope.group.users)
        scope.group.users.forEach(function(user, index) {
            console.log(user)
            $.post("http://localhost:8090/api/action/user", {
                userId: user.id,
                phone: user.phone,
                groupId: group.id
            })
                .done(function (response) {
                    console.log("got user in action")
                    console.log(response)
                    scope.group.users.splice(scope.group.users.indexOf(user), 1)
                })
        })
    }

    function addGroup() {
        console.log(scope.group.name)
        console.log(scope.group.area)

        $.post("http://localhost:8090/api/group", {
            actionId: stateParams.id,
            name: scope.group.name,
            areaId: scope.group.area
        })
            .done(function (response) {
                scope.action.areas.splice(scope.action.areas.indexOf(scope.group.area), 1);

                console.log(response)

                console.log(response.areas)
                saveGroupParticipants(response)
            })
            .fail(function () {
                alert("Wystąpił błąd z połączeniem z serwerem!");
            });
    }

    function editUser(index) {
        var user = scope.group.users[index]
        user.index = index
        var modalInstance = modal.open({
            templateUrl: 'group-user',
            controller: 'GroupUserController',
            resolve: {
                user: function () {
                    return user;
                }
            }
        });

        modalInstance.result.then(function (user) {
            scope.group.users[user.index] = user;
        }, function () {
            console.log('Modal dismissed at: ' + new Date());
        });
    };

    function ActionGroupsController($scope, $state, $stateParams, $modal, mapFactory) {
        MapManager = mapFactory;
        scope = $scope;
        state = $state;
        stateParams = $stateParams;
        modal = $modal;
        console.log($modal)
        scope.map = MapManager.generateDrawableMap('mapDiv');
        scope.initMap = initMap;
        scope.addToGroup = addToGroup;
        scope.removeFromGroup = removeFromGroup;
        scope.addGroup = addGroup;
        scope.group = {};
        scope.group.users = [];
        scope.group.areas = {};
        scope.editUser = editUser;
    }

    return {
        start: function (App) {
            App.controller('ActionGroupsController', ['$scope', '$state', '$stateParams', '$modal', 'MapFactory', ActionGroupsController]);
            return ActionGroupsController;
        }
    };

};

define(['angular'], actionGroupsController)
