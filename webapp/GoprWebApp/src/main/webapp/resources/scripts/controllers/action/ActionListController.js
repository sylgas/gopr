function actionListController(angular) {
    var scope, state;
    var ActionService;

    function gotActions(actions) {
        scope.actions = actions;
    }

    function actionStarted(actionId) {
        state.go("action", {id: actionId, reload: true});
    }

    function startAction(actionId) {
        ActionService.start(actionId, actionStarted);
    }

    function ActionListController($scope, $state, actionService) {
        scope = $scope;
        state = $state;
        ActionService = actionService;
        scope.startAction = startAction;
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