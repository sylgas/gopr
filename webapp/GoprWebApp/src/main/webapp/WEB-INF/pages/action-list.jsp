<!DOCTYPE html>
<%@ page language="java" pageEncoding="UTF-8" %>
<%@ page contentType="text/html;charset=UTF-8" %>
<div id="container" ng-controller="ActionListController">
    <table class="table table-striped table-bordered table-hover table-condensed">
        Lista akcji:
        <tr ng-repeat="action in actions">
            <td>{{action.id}}</td>
            <td>{{action.name}}</td>
            <td>
                <button class="btn-xs btn-primary" ui-sref="action-groups({id: {{action.id}}})">Szczegóły</button>
            </td>
        </tr>
    </table>
</div>
