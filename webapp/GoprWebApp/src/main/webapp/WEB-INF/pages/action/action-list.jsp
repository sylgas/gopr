<!DOCTYPE html>
<%@ page language="java" pageEncoding="UTF-8" %>
<%@ page contentType="text/html;charset=UTF-8" %>
<div id="container" ng-controller="ActionListController" class="col-md-10 col-md-offset-1">
    <h3>Lista akcji</h3>
    <table class="table table-striped table-hover table-condensed" ng-show="(actions).length > 0">
        <tr>
            <th>Id</th>
            <th>Nazwa</th>
            <th>Opis</th>
            <th></th>
        </tr>
        <tr ng-repeat="action in actions">
            <td>{{action.id}}</td>
            <td>{{action.name}}</td>
            <td>{{action.description}}</td>
            <td class="col-md-2">
                <div class="btn-group-vertical">
                    <a ng-hide="action.startDate" class="btn-sm btn-primary"
                       ui-sref="action-groups({id: {{action.id}}})">Przydziel grupy</a>
                    <a ng-show="action.groups && !action.startDate" class="btn-sm btn-primary" ng-click="startAction(action.id)">Rozpocznij</a>
                    <a ng-show="action.startDate" class="btn-sm btn-primary"
                       ui-sref="action({id: {{action.id}}})">Wznów</a>
                </div>
            </td>
        </tr>
    </table>
    <p ng-hide="actions.length > 0">Brak</p>
</div>
