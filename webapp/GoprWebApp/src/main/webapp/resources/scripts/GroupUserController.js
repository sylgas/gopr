function groupUserController(angular) {
    var scope;
    var modal;

    function save () {
        modal.close(scope.editedUser);
    }

    function cancel() {
        modal.dismiss('cancel');
    }

    function GroupUserController($scope, $modalInstance, user) {
        scope = $scope;
        modal = $modalInstance;
        scope.editedUser = angular.copy(user);
        $scope.save = save;
        $scope.cancel = cancel;
    }

    return {
        start: function (App) {
            App.controller('GroupUserController', ['$scope', '$modalInstance', 'user', GroupUserController]);
            return GroupUserController;
        }
    };
}

define(['angular'], groupUserController);
