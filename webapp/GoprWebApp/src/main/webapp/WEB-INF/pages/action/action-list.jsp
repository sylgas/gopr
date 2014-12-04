<!DOCTYPE html>
<%@ page language="java" pageEncoding="UTF-8" %>
<%@ page contentType="text/html;charset=UTF-8" %>
<div id="container" ng-controller="ActionListController" class="col-md-10 col-md-offset-1">
    <h3>Tworzone akcje</h3>
    <table class="table table-striped table-hover table-condensed" ng-show="(actions | filter:{groups: '!'}).length > 0">
        <tr>
            <th>Id</th>
            <th>Nazwa</th>
            <th>Opis</th>
            <th></th>
        </tr>
        <tr ng-repeat="action in (actions | filter:{groups: '!'})">
            <td>{{action.id}}</td>
            <td>{{action.name}}</td>
            <td>{{action.description}}</td>
            <td class="col-md-2">
                <a class="btn-sm btn-primary" ui-sref="action-groups({id: {{action.id}}})">Przydziel grupy</a>
            </td>
        </tr>
    </table>
    <p ng-hide="(actions | filter:{groups: '!'}).length > 0">Brak</p>
    <h3>Utworzone akcje</h3>
    <table class="table table-striped table-hover table-condensed" ng-show="(actions | filter:{groups: '!!'}).length > 0">
        <tr>
            <th>Id</th>
            <th>Nazwa</th>
            <th>Opis</th>
            <th></th>
        </tr>
        <tr ng-repeat="action in (actions | filter:{groups: '!!'})">
            <td>{{action.id}}</td>
            <td>{{action.name}}</td>
            <td>{{action.description}}</td>
            <td class="col-md-2">
                <a class="btn-sm btn-primary" ui-sref="action({id: {{action.id}}})">Rozpocznij</a>
                <!--TODO: preview/start-->
            </td>
        </tr>
    </table>
    <p ng-hide="(actions | filter:{groups: '!!'}).length > 0">Brak</p>
</div>
