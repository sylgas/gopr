(function() {
    "use strict";

    define([
        'angular',
        './resources/scripts/factories/MapFactory.js',
        './resources/scripts/controllers/action-create/CreateActionController.js',
        './resources/scripts/controllers/action/ActionController.js',
        './resources/scripts/controllers/action-create/ActionGroupsController.js',
        './resources/scripts/controllers/action/ActionListController.js',
        './resources/scripts/controllers/action-create/GroupUserController.js',
        './resources/scripts/controllers/action-create/AreaController.js',
        './resources/scripts/controllers/user/UserListController.js',
        './resources/scripts/controllers/user/UserController.js'
    ], function(angular, MapFactory, CreateActionController, ActionController, ActionGroupsController, ActionListController, GroupUserController, AreaController, UserListController, UserController) {

        function init() {
            var App = angular.module('app', ['ui.bootstrap', 'ngTable', 'ui.router']);
            App.config(function($stateProvider, $urlRouterProvider, $locationProvider) {
                $urlRouterProvider.otherwise('/');

                $stateProvider
                    .state('home', {
                        url: '/'
                    })
                    .state('action-create', {
                        url: "/action",
                        templateUrl: "action-create",
                        controller: 'CreateActionController'
                    })
                    .state('action-list', {
                        url: '/actions',
                        templateUrl: 'action-list',
                        controller: 'ActionListController'
                    })
                    .state('action', {
                        url: '/action/:id',
                        templateUrl: 'action',
                        controller: 'ActionController'
                    })
                    .state('action-groups', {
                        url: '/action/:id/group',
                        templateUrl: 'action-groups',
                        controller: 'ActionGroupsController'
                    })
                    .state('user-list', {
                        url: '/users',
                        templateUrl: 'user-list',
                        controller: 'UserListController'
                    });
                //$locationProvider.html5Mode(true);
            });
            MapFactory.start(App);
            CreateActionController.start(App);
            ActionController.start(App);
            ActionGroupsController.start(App);
            ActionListController.start(App);
            GroupUserController.start(App);
            AreaController.start(App);
            UserListController.start(App);
            UserController.start(App);

            angular.bootstrap(document.body, ['app']);
            return App;
        }
        return { start: init };
    });
}).call(this);