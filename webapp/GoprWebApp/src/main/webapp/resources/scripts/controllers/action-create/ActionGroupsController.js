function actionGroupsController(angular) {
    var scope, state;
    var actionId;
    var MapManager;
    var modal;

    function displayUsers() {
        $.get("http://localhost:8090/api/user/")
            .done(function (response) {
                scope.users = response;
                scope.$apply()
            })
            .fail(function () {
                alert("Wystąpił błąd z połączeniem z serwerem!");
            });
    }

    function initMap() {
        if (isNaN(parseInt(actionId)) || parseInt(actionId) == -1) {
            alert("ERROR  :(");
            //TODO: przenieść na stronę z errorem, którą też trzeba zrobić
            return;
        }

            geometriesOnMap = {};    //Dictionary[geometryNumberInAction] = graphic with area index on map
            groupAreas = {};         //Dictionary[groupId] = List<Graphics of areas index on map>
            userPaths = {};          //Dictionary[userInActionId] = graphic with path index on map
            userLocalizations = {};   //Dictionary[userInActionId] = graphic with localization index on map

            $.get("http://localhost:8090/api/action/" + actionId)
                .done(function (response) {
                    scope.action = response;
                    scope.$apply();
                    setTimeout(function() {
                        MapManager.displayAreas(response.areas);
                    }, 2000);
                })
                .fail(function () {
                    alert("Wystąpił błąd z połączeniem z serwerem!");
                });
    }

    function addUserToGroup(user) {
        scope.users.splice(scope.users.indexOf(user), 1);
        scope.group.users.push(user);
    }

    function removeUserFromGroup(user) {
        scope.group.users.splice(scope.group.users.indexOf(user), 1);
        scope.users.push(user);
    }

    function saveGroupParticipants(group) {
        scope.group.users.forEach(function(user, index) {
            $.post("http://localhost:8090/api/action/user", {
                userId: user.id,
                phone: user.phone,
                groupId: group.id
            })
                .done(function (response) {
                    scope.group.users.splice(scope.group.users.indexOf(user), 1)
                })
        })
    }

    function clearGroupUsersPanel() {
        scope.group = {};
        scope.group.users = []
    }

    function addGroup() {
        $.post("http://localhost:8090/api/group", {
            actionId: actionId,
            name: scope.group.name,
            areaId: scope.group.area
        })
            .done(function (group) {
                scope.action.areas.splice(scope.action.areas.indexOf(scope.group.area), 1);
                group.users = scope.group.users;
                saveGroupParticipants(group);
                scope.groups.push(group);
                scope.$apply();
                clearGroupUsersPanel();
            })
            .fail(function () {
                alert("Wystąpił błąd z połączeniem z serwerem!");
            });
    }

    function displayGroups() {
        $.get("http://localhost:8090/api/group/action/" + actionId)
            .done(function (response) {
                scope.groups = response;
                scope.$apply();
            })
            .fail(function () {
                alert("Wystąpił błąd z połączeniem z serwerem!");
            });
    }

    function editUser(index) {
        var user = scope.group.users[index];
        user.index = index;
        var modalInstance = modal.open({
            templateUrl: 'group-user-edit-dialog',
            controller: 'GroupUserController',
            resolve: {
                user: function () {
                    return user;
                }
            }
        });

        modalInstance.result.then(function (editedUser) {
            scope.group.users[user.index] = editedUser;
        }, function () {
            console.log('Modal dismissed at: ' + new Date());
        });
    }

    function startAction() {
        state.go("action", {id: actionId, reload: true});
    }

    function ActionGroupsController($scope, $state, $stateParams, $modal, mapFactory) {
        MapManager = mapFactory;
        scope = $scope;
        state = $state;
        actionId = $stateParams.id;
        modal = $modal;
        scope.map = MapManager.generateDrawableMap('mapDiv');
        scope.initMap = initMap;
        scope.addUserToGroup = addUserToGroup;
        scope.removeUserFromGroup = removeUserFromGroup;
        scope.addGroup = addGroup;
        scope.group = {};
        scope.group.users = [];
        scope.group.areas = {};
        scope.groups = [];
        scope.editUser = editUser;
        scope.startAction = startAction;
        displayUsers();
        displayGroups();
    }

    return {
        start: function (App) {
            App.controller('ActionGroupsController', ['$scope', '$state', '$stateParams', '$modal', 'MapFactory', ActionGroupsController]);
            return ActionGroupsController;
        }
    };
}

define(['angular'], actionGroupsController);
