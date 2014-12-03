function actionListController(angular) {
    var scope, state;

    function ActionListController($scope, $state) {
        scope = $scope;
        state = $state;
        $.get("http://localhost:8090/api/action")
            .done(function (response) {
                scope.actions = response;
                scope.$apply()
            })
            .fail(function () {
                alert("Wystąpił błąd z połączeniem z serwerem!");
            });
    }

    return {
        start: function (App) {
            App.controller('ActionListController', ['$scope', '$state', ActionListController]);
            return ActionListController;
        }
    };

}

define(['angular'], actionListController);