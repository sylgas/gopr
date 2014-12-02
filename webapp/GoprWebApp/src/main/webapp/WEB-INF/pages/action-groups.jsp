<!DOCTYPE html>
<%@ page language="java" pageEncoding="UTF-8" %>
<%@ page contentType="text/html;charset=UTF-8" %>
<div data-ng-init="initMap()" class="col-md-12">
    <div class="col-md-6">
        <h1 class="col-md-12">
            {{action.name}}
        </h1>

        <div class="col-md-12">
            <button class="btn btn-primary" ng-click="addUser()">Dodaj uzytkownika</button>
        </div>
        Uczestnicy:
        <table class="table table-striped table-bordered table-hover table-condensed">
            <tr>
                <th>Id</th>
                <th>Nick</th>
                <th>Imię</th>
                <th>Nazwisko</th>
                <th>Login</th>
                <th>Telefon</th>
                <th></th>
            </tr>
            <tr ng-repeat="user in users">
                <td>{{user.id}}</td>
                <td>{{user.nick}}</td>
                <td>{{user.name}}</td>
                <td>{{user.surname}}</td>
                <td>{{user.login}}</td>
                <td>{{user.phone}}</td>
                <td>
                    <a ng-click="addUserToGroup(user)">
                        <span class="glyphicon glyphicon-arrow-right"></span>
                    </a>
                </td>
            </tr>
        </table>
    </div>
    <div class="col-md-6">
        <form class="form-horizontal" role="form">
            Nowa grupa:
            <table class="table table-striped table-bordered table-hover table-condensed">
                <tr>
                    <th></th>
                    <th>Id</th>
                    <th>Nick</th>
                    <th>Imię</th>
                    <th>Nazwisko</th>
                    <th>Login</th>
                    <th>Telefon</th>
                    <th></th>
                </tr>
                <tr ng-repeat="groupUser in group.users">
                    <td>
                        <a ng-click="removeUserFromGroup(groupUser)">
                            <span class="glyphicon glyphicon-arrow-left"></span>
                        </a>
                    </td>
                    <td>{{groupUser.id}}</td>
                    <td>{{groupUser.nick}}</td>
                    <td>{{groupUser.name}}</td>
                    <td>{{groupUser.surname}}</td>
                    <td>{{groupUser.login}}</td>
                    <td>{{groupUser.phone}}</td>
                    <td class="col-md-1">
                        <a ng-click="editUser($index)"><span class="glyphicon glyphicon-edit"></span></a>
                    </td>
                </tr>
            </table>
            <div class="form-group">
                <label for="groupName" class="col-sm-4 control-label">Nazwa grupy</label>

                <div class="col-sm-8">
                    <input class="form-control" id="groupName" placeholder="Nazwa grupy" ng-model="group.name">
                </div>
            </div>
            <div class="form-group">
                <label for="area" class="col-sm-4 control-label">Obszar: </label>

                <div class="col-sm-8">
                    <select class="form-control" id="area" ng-model="group.area">
                        <option ng-repeat="area in action.areas"
                                value="{{area.id}}">
                            {{area.name}}
                        </option>
                    </select>
                </div>
            </div>
            <div class="form-group">
                <label for="comment" class="col-sm-4 control-label">Nazwa grupy</label>

                <div class="col-sm-8">
                    <input class="form-control" id="comment" placeholder="Komentarz" ng-model="group.comment">
                </div>
            </div>
            <div class="form-group">
                <div class="col-sm-offset-10 col-sm-2 btn-group">
                    <button type="reset" class="btn btn-default" ng-click=removeGroup()>Usuń</button>
                    <button type="submit" class="btn btn-default" ng-click=addGroup()>Dodaj</button>
                </div>
            </div>
        </form>
        Dodane grupy
        <table class="table table-striped table-bordered table-hover table-condensed">
            <tr>
                <th>Id</th>
                <th>Nazwa</th>
                <th>Obszar</th>
                <th>Uczestnicy</th>
            </tr>
            <tr ng-repeat="group in groups">
                <td>{{group.id}}</td>
                <td>{{group.name}}</td>
                <td>{{group.area.id}}</td>
                <td>{{group.users}}</td>
            </tr>
        </table>
        <div id="mapDiv"></div>
    </div>
    <div class="col-sm-offset-10 col-sm-2 btn-group pull-right">
        <button class="btn btn-primary" ng-click="startAction()">Rozpocznij akcję</button>
        <button class="btn btn-default" ui-sref='home'>Wróć</button>
    </div>

</div>