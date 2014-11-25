function defaultController(angular) {
    return {
        start: function (App) {
            App.controller('DefaultController', ['$scope', defaultController]);
            return defaultController;
        }
    };
};

define(['angular'], defaultController);