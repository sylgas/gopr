function areaController(angular) {
    var scope;
    var modal;

    function save () {
        modal.close(scope.area);
    }

    function cancel() {
        modal.dismiss('cancel');
    }

    function AreaController($scope, $modalInstance, area) {
        scope = $scope;
        modal = $modalInstance;
        scope.area = area;
        $scope.save = save;
        $scope.cancel = cancel;
    }

    return {
        start: function (App) {
            App.controller('AreaController', ['$scope', '$modalInstance', 'area', AreaController]);
            return AreaController;
        }
    };
}

define(['angular'], areaController);
