<!DOCTYPE html>
<%@ page language="java" pageEncoding="UTF-8" %>
<%@ page contentType="text/html;charset=UTF-8" %>
<div id="container" ng-controller="ActionListController" class="col-md-6">
    <table class="table table-striped table-bordered table-hover table-condensed">
        Lista akcji:
        <tr ng-repeat="action in actions">
            <td>{{action.id}}</td>
            <td>{{action.name}}</td>
            <td class="col-md-2">
                <a class="btn btn-primary" ui-sref="action-groups({id: {{action.id}}})">Przydziel grupy</a>
            </td>
        </tr>
    </table>
</div>
