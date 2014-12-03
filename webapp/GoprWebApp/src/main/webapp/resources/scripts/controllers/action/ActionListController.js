function actionListController(angular) {
    var scope, state;

    function gotActions(actions) {
        scope.actions = actions;
    }
    function ActionListController($scope, $state, ActionService) {
        scope = $scope;
        state = $state;
        ActionService.getAll(gotActions)
    }

    return {
        start: function (App) {
            App.controller('ActionListController', ['$scope', '$state', 'ActionService', ActionListController]);
            return ActionListController;
        }
    };
}

define(['angular'], actionListController);