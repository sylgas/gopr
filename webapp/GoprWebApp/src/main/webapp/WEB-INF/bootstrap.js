(function() {
    "use strict";

    define([
        'angular',
        './scripts/MapFactory.js',
        './scripts/CreateActionController.js',
        './scripts/ActionController.js',
        './scripts/ActionGroupsController.js'
    ], function(angular, MapFactory, CreateActionController, ActionController, ActionGroupsController) {

        function init() {
            var App = angular.module('app', ['ui.bootstrap', 'ngTable', 'ui.router']);
            App.config(function($stateProvider, $urlRouterProvider) {
                $urlRouterProvider.otherwise('/');

                $stateProvider
                    .state('home', {
                        url: '/'
                    })
                    .state('createAction', {
                        url: "/createAction",
                        templateUrl: "createAction",
                        controller: 'CreateActionController'
                    })
                    .state('action', {
                        url: '/action/:id',
                        templateUrl: 'action',
                        controller: 'ActionController'
                    })
                    .state('setActionGroups', {
                        url: '/action/:id/group',
                        templateUrl: 'setActionGroups',
                        controller: 'ActionGroupsController'
                    })
            });
            MapFactory.start(App);
            CreateActionController.start(App);
            ActionController.start(App);
            ActionGroupsController.start(App);

            angular.bootstrap(document.body, ['app']);
            return App;
        }
        return { start: init };
    });

}).call(this);