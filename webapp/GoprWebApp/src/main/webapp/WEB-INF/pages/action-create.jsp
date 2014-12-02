<!DOCTYPE html>
<%@ page language="java" pageEncoding="UTF-8" %>
<%@ page contentType="text/html;charset=UTF-8" %>
<div ng-controller="CreateActionController">
    <div class="col-md-10 pull-left" id="mapDiv" data-dojo-type="dijit/layout/ContentPane"></div>
    <div class="col-md-2" data-ng-init="init()" id="menuDiv">
        <label for="actionName">Nazwa akcji</label>
        <input id="actionName" class="form-control" ng-model="action.name" placeholder="Nazwa akcji"/>
        <label for="actionDescription">Opis akcji</label>
        <input id="actionDescription" class="form-control" ng-model="action.description" placeholder="Opis akcji"/>
        <label for="areaDrawDiv" class="pull-left">Dodaj obszar</label>

        <div id="areaDrawDiv" class="btn-group col-md-10 btn-block pull-right">
            <button class="btn btn-default" id="Polygon">Wybierz punkty</button>
            <button class="btn btn-default" id="FreehandPolygon">Zaznacz obszar</button>
        </div>
        <table ng-model="table" class="table table-striped table-condensed col-md-12">
            <thead>
            <tr>
                <th>Numer</th>
                <th>Nazwa</th>
                <th>Opis</th>
            </tr>
            </thead>
            <div>
                <tr ng-repeat="area in areas" ng-click="selectArea($index)" ng-dblclick="changeAreaProperties($index)"
                    ng-show="areas" ng-class="{'info': $index === selected}">
                    <td ng-model="area.number">{{$index + 1}}</td>
                    <td ng-model="area.numberInAction">{{area.numberInAction}}</td>
                    <td ng-model="area.geometryName">{{area.geometryName}}</td>
                    <td class="col-md-1">
                        <a ng-click="editArea(area)"><span class="glyphicon glyphicon-edit"></span></a>
                    </td>
                </tr>
            </div>
            <tr ng-hide="areas">
                <td>-</td>
                <td>-</td>
                <td>-</td>
            </tr>
        </table>
        <div class="btn-group col-md-10 pull-right btn-block">
            <button class="btn btn-default" ui-sref='home'>Wróć</button>
            <button class="btn btn-primary" ng-click="setActionGroups()"
                    ng-disabled="action.name == null || action.description == null || areas == null">
                Dalej
            </button>
        </div>
    </div>
</div>