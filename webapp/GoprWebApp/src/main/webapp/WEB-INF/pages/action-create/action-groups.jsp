<!DOCTYPE html>
<%@ page language="java" pageEncoding="UTF-8" %>
<%@ page contentType="text/html;charset=UTF-8" %>
<div data-ng-init="initMap()" class="col-md-12">
    <div class="col-md-7">
        <h1>{{action.name}}</h1>
        <div id="mapDiv"></div>
    </div>
    <div class="col-md-5">
        <h4>Nowa grupa</h4>
        <table ng-table="usersTableParams" show-filter="true" class="table table-striped table-hover table-condensed">
            <tr ng-repeat="user in ($data)">
                <td data-title="'Nick'" filter="{'nick': 'text'}">{{user.nick}}</td>
                <td data-title="'Imię'" filter="{'name': 'text'}">{{user.name}}</td>
                <td data-title="'Nazwisko'" filter="{'surname': 'text'}">{{user.surname}}</td>
                <td>
                    <a ng-click="addUserToGroup(user)">
                        <span class="glyphicon glyphicon-plus"></span>
                    </a>
                </td>
            </tr>
        </table>
        <form class="form-horizontal" role="form">
            <h4>Wybrani użytkownicy</h4>
            <table class="table table-striped table-hover table-condensed" ng-show="group.users.length > 0">
                <tr>
                    <th>Nick</th>
                    <th>Imię</th>
                    <th>Nazwisko</th>
                    <th>Telefon</th>
                    <th></th>
                </tr>
                <tr ng-repeat="groupUser in group.users" ng-show="group.users">
                    <td>{{groupUser.nick}}</td>
                    <td>{{groupUser.name}}</td>
                    <td>{{groupUser.surname}}</td>
                    <td>{{groupUser.phone}}</td>
                    <td class="col-md-1">
                        <a ng-click="editUser($index)"><span class="glyphicon glyphicon-edit"></span></a>
                        <a ng-click="removeUserFromGroup(groupUser)"><span class="glyphicon glyphicon-remove"></span></a>
                    </td>
                </tr>
            </table>
            <p class="col-md-12 pull-left" ng-hide="group.users.length > 0">Brak</p>
            <div class="form-group">
                <label for="groupName" class="col-sm-4 control-label">Nazwa:</label>

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
                            {{area.name}}({{area.number}})
                        </option>
                    </select>
                </div>
            </div>
            <div class="form-group">
                <label for="comment" class="col-sm-4 control-label">Komentarz:</label>

                <div class="col-sm-8">
                    <input class="form-control" id="comment" placeholder="Komentarz" ng-model="group.comment">
                </div>
            </div>
            <div class="form-group">
                <div class="col-sm-offset-10 col-sm-2 btn-group">
                    <button type="submit" class="btn btn-default" ng-click=createGroup()>Zapisz</button>
                </div>
            </div>
        </form>
        <h4>Dodane grupy</h4>
        <table class="table table-striped table-hover table-condensed" ng-show="groups.length > 0">
            <tr>
                <th>Nazwa</th>
                <th>Obszar</th>
                <th>Uczestnicy</th>
            </tr>
            <tr ng-repeat="group in groups">
                <td>{{group.name}}</td>
                <td>
                    <div ng-repeat="area in group.areas">
                    {{area.name}}
                </div>
                </td>
                <td>
                    <div ng-repeat="actionUser in group.actionUsers">
                        {{actionUser.user.nick}}
                    </div>
                </td>
            </tr>
        </table>
        <p class="col-md-12 pull-left" ng-hide="groups.length > 0">Brak</p>
        <div class="btn-group-vertical col-md-4 col-md-offset-8">
            <button class="btn btn-default" ui-sref='action-list'>Zapisz</button>
            <button class="btn btn-primary" ng-click="startAction()">Rozpocznij akcję</button>
        </div>
    </div>
</div>