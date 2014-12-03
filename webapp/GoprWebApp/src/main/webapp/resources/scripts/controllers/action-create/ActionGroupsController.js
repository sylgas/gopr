function actionGroupsController(angular) {
    var scope, state, stateParams;
    var actionId;
    var MapManager, ActionService, GroupService, UserService;
    var modal;

    function gotUsers(users) {
        scope.users = users;
    }

    function displayUsers() {
        UserService.getAll(gotUsers);
    }

    function gotActionGroups(groups) {
        scope.groups = groups;
    }

    function displayActionGroups() {
        GroupService.getByActionId(actionId, gotActionGroups)
    }

    function gotAction(action) {
        scope.action = action;
        setTimeout(function () {
            MapManager.displayAreas(action.areas);
        }, 2000);
    }

    function initMap() {

        geometriesOnMap = {};    //Dictionary[geometryNumberInAction] = graphic with area index on map
        groupAreas = {};         //Dictionary[groupId] = List<Graphics of areas index on map>
        userPaths = {};          //Dictionary[userInActionId] = graphic with path index on map
        userLocalizations = {};   //Dictionary[userInActionId] = graphic with localization index on map


        ActionService.get(actionId, gotAction);
    }

    function addUserToGroup(user) {
        scope.users.splice(scope.users.indexOf(user), 1);
        scope.group.users.push(user);
    }

    function removeUserFromGroup(user) {
        scope.group.users.splice(scope.group.users.indexOf(user), 1);
        scope.users.push(user);
    }

    function userInActionCreated(user) {
        scope.group.users.splice(scope.group.users.indexOf(user), 1)
    }

    function saveGroupParticipants(group) {
        scope.group.users.forEach(function (user, index) {
            var userData = {
                userId: user.id,
                phone: user.phone,
                groupId: group.id
            };
            UserService.createForAction(userData, userInActionCreated);
        })
    }

    function clearCreateGroupPanel() {
        scope.group = {};
        scope.group.users = [];
        scope.$apply()
    }

    function displayGroup(group) {
        scope.groups.push(group);
    }

    function groupCreated(group) {
        scope.action.areas.splice(scope.action.areas.indexOf(scope.group.area), 1);
        group.users = scope.group.users;
        saveGroupParticipants(group);
        displayGroup(group);
        clearCreateGroupPanel();
    }

    function createGroup() {
        var groupData = {
            actionId: actionId,
            name: scope.group.name,
            areaId: scope.group.area
        };
        GroupService.create(groupData, groupCreated);
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

    function ActionGroupsController($scope, $state, $stateParams, $modal, mapFactory, actionService, groupService, userService) {
        MapManager = mapFactory;
        ActionService = actionService;
        GroupService = groupService;
        UserService = userService;
        scope = $scope;
        state = $state;
        actionId = $stateParams.id;
        modal = $modal;
        scope.map = MapManager.generateDrawableMap('mapDiv');
        scope.initMap = initMap;
        scope.addUserToGroup = addUserToGroup;
        scope.removeUserFromGroup = removeUserFromGroup;
        scope.createGroup = createGroup;
        scope.group = {};
        scope.group.users = [];
        scope.groups = [];
        scope.editUser = editUser;
        scope.startAction = startAction;
        displayUsers();
        displayActionGroups();
    }

    return {
        start: function (App) {
            App.controller('ActionGroupsController', ['$scope', '$state', '$stateParams', '$modal',
                'MapFactory', 'ActionService', 'GroupService', 'UserService', ActionGroupsController]);
            return ActionGroupsController;
        }
    };
}

define(['angular'], actionGroupsController);
