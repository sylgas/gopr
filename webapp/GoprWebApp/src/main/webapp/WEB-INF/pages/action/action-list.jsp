<!DOCTYPE html>
<%@ page language="java" pageEncoding="UTF-8" %>
<%@ page contentType="text/html;charset=UTF-8" %>
<div id="container" ng-controller="ActionListController" class="col-md-6">
    <h3>Tworzone akcje</h3>
    <table class="table table-striped table-bordered table-hover table-condensed">
        <tr ng-repeat="action in (actions | filter:{groups: '!'})">
            <td>{{action.id}}</td>
            <td>{{action.name}}</td>
            <td>{{action.description}}</td>
            <td class="col-md-2">
                <a class="btn btn-primary" ui-sref="action-groups({id: {{action.id}}})">Przydziel grupy</a>
            </td>
        </tr>
    </table>
    <h3>Nierozpoczęte akcje</h3>
    <table class="table table-striped table-bordered table-hover table-condensed">
        <tr ng-repeat="action in (actions | filter:{groups: '!!'})">
            <td>{{action.id}}</td>
            <td>{{action.name}}</td>
            <td>{{action.description}}</td>
            <td class="col-md-2">
                <a class="btn btn-primary" ui-sref="action({id: {{action.id}}})">Rozpocznij</a>
                <!--TODO: preview/start-->
            </td>
        </tr>
    </table>
</div>
