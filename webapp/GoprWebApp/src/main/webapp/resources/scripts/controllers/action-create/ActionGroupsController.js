function actionGroupsController(angular) {
    var scope, state;
    var actionId;
    var MapManager, ActionService, GroupService, UserService;
    var modal;

    function gotUsers(users) {
        scope.users = users;
        scope.usersTableParams.reload();
    }

    function displayUsers() {
        UserService.getAll(gotUsers);
    }

    function getById(array, id) {
        return $.grep(array, function (e) {
            return e.id == id;
        })[0]
    }

    function gotActionGroups(groups) {
        scope.groups = groups;
        angular.forEach(groups, function (group) {
            scope.action.areas.splice(scope.action.areas.indexOf(scope.group.area), 1);
            angular.forEach(group.actionUsers, function (actionUser) {
                scope.users.splice(scope.users.indexOf(getById(scope.users, actionUser.user.id)), 1);
            })
        })
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
        ActionService.get(actionId, gotAction);
    }

    function addUserToGroup(user) {
        scope.users.splice(scope.users.indexOf(user), 1);
        scope.usersTableParams.reload();
        scope.group.users.push(user);
    }

    function removeUserFromGroup(user) {
        scope.group.users.splice(scope.group.users.indexOf(user), 1);
        scope.users.push(user);
        scope.usersTableParams.reload();
    }

    function userInActionCreated(user) {
        scope.group.users.splice(scope.group.users.indexOf(user), 1)
    }

    function saveGroupUsers(group) {
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
        displayUsers();
    }

    function groupCreated(group) {
        scope.action.areas.splice(scope.action.areas.indexOf(scope.group.area), 1);
        saveGroupUsers(group);
        displayActionGroups();
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

    function ActionGroupsController($scope, $state, $stateParams, $modal, ngTableParams, $filter, mapFactory, actionService, groupService, userService) {
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
        scope.editUser = editUser;
        scope.startAction = startAction;
        scope.users = [];
        displayActionGroups();
        displayUsers();
        scope.usersTableParams = new ngTableParams({
            page: 1,
            count: 5
        }, {
            counts: [],
            total: scope.users.length,
            getData: function ($defer, params) {
                var filteredData = params.filter() ?
                    $filter('filter')(scope.users, params.filter()) :
                    scope.users;
                params.total(filteredData.length);
                $defer.resolve(filteredData.slice((params.page() - 1) * params.count(), params.page() * params.count()));
            }
        })
    }

    return {
        start: function (App) {
            App.controller('ActionGroupsController', ['$scope', '$state', '$stateParams', '$modal', 'ngTableParams', '$filter',
                'MapFactory', 'ActionService', 'GroupService', 'UserService', ActionGroupsController]);
            return ActionGroupsController;
        }
    };
}

define(['angular'], actionGroupsController);
