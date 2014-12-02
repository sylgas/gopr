(function() {
    "use strict";

    define([
        'angular',
        '/resources/scripts/MapFactory.js',
        '/resources/scripts/CreateActionController.js',
        '/resources/scripts/ActionController.js',
        '/resources/scripts/ActionGroupsController.js',
        '/resources/scripts/ActionListController.js',
        '/resources/scripts/GroupUserController.js',
        '/resources/scripts/AreaController.js'
    ], function(angular, MapFactory, CreateActionController, ActionController, ActionGroupsController, ActionListController, GroupUserController, AreaController) {

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
                    .state('group-user-dialog', {
                        url: '/action/:id/group/user',
                        templateUrl: 'group-user-dialog',
                        controller: 'GroupUserController'
                    })
                    .state('area-dialog', {
                        url: '/action/:id/group/user',
                        templateUrl: 'area-dialog',
                        controller: 'AreaController'
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

            angular.bootstrap(document.body, ['app']);
            return App;
        }
        return { start: init };
    });

}).call(this);