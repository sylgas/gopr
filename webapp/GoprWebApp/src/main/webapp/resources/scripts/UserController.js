function userController(angular) {
    var scope;
    var modal;

    function save () {
        modal.close(scope.user);
    }

    function cancel() {
        modal.dismiss('cancel');
    }

    function UserController($scope, $modalInstance) {
        scope = $scope;
        modal = $modalInstance;
        scope.user = {};
        scope.save = save;
        scope.cancel = cancel;
    }

    return {
        start: function (App) {
            App.controller('UserController', ['$scope', '$modalInstance', UserController]);
            return UserController;
        }
    };
}

define(['angular'], userController);
