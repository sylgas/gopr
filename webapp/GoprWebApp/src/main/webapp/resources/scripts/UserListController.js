function userListController(angular) {
    var scope, state, modal;

    function addUser() {
        var modalInstance = modal.open({
            templateUrl: 'user-add-dialog',
            controller: 'UserController'
        });

        modalInstance.result.then(function (user) {
            $.post("http://localhost:8090/api/user", {
                name: user.name,
                surname: user.surname,
                login: user.login,
                password: user.password,
                nick: user.nick,
                phone: user.phone
            })
            .done(function (response) {
                scope.users.push(response);
            })
            .fail(function () {
                alert("Wystąpił błąd z połączeniem z serwerem!");
            });
        });
    }

    function UserListController($scope, $state, $modal) {
        scope = $scope;
        state = $state;
        modal = $modal;
        scope.addUser = addUser;
        $.get("http://localhost:8090/api/user/")
            .done(function (response) {
                scope.users = response;
                scope.$apply()
            })
            .fail(function () {
                alert("Wystąpił błąd z połączeniem z serwerem!");
            });
    }

    return {
        start: function (App) {
            App.controller('UserListController', ['$scope', '$state', '$modal', UserListController]);
            return UserListController;
        }
    };
}

define(['angular'], userListController)