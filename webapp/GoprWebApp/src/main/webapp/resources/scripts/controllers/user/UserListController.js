function userListController(angular) {
    var scope, state, modal;
    var UserService;

    function userCreated(user) {
        scope.users.push(user);
    }

    function addUser() {
        var modalInstance = modal.open({
            templateUrl: 'user-add-dialog',
            controller: 'UserController'
        });

        modalInstance.result.then(function (user) {
            var userData = {
                name: user.name,
                surname: user.surname,
                login: user.login,
                password: user.password,
                nick: user.nick,
                phone: user.phone
            };
            UserService.create(userData, userCreated);
        });
    }

    function gotUsers(users) {
        scope.users = users;
    }

    function UserListController($scope, $state, $modal, userService) {
        UserService = userService;
        scope = $scope;
        state = $state;
        modal = $modal;
        scope.addUser = addUser;
        UserService.getAll(gotUsers);
    }

    return {
        start: function (App) {
            App.controller('UserListController', ['$scope', '$state', '$modal', 'UserService', UserListController]);
            return UserListController;
        }
    };
}

define(['angular'], userListController);