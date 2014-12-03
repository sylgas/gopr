<!DOCTYPE html>
<%@ page language="java" pageEncoding="UTF-8" %>
<%@ page contentType="text/html;charset=UTF-8" %>
<div id="container" ng-controller="ActionListController" class="col-md-6">
    <div class="col-md-10">
        <h3>Użytkownicy:</h3>
    </div>
    <div class="col-md-2">
        <button class="btn btn-primary" ng-click="addUser()">Nowy użytkownik</button>
    </div>
    <table class="table table-striped table-bordered table-hover table-condensed">
        <tr>
            <th>Id</th>
            <th>Nick</th>
            <th>Imię</th>
            <th>Nazwisko</th>
            <th>Login</th>
            <th>Telefon</th>
        </tr>
        <tr ng-repeat="user in users">
            <td>{{user.id}}</td>
            <td>{{user.nick}}</td>
            <td>{{user.name}}</td>
            <td>{{user.surname}}</td>
            <td>{{user.login}}</td>
            <td>{{user.phone}}</td>
        </tr>
    </table>
</div>
