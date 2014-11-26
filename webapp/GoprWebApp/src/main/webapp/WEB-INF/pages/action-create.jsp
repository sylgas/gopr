<!DOCTYPE html>
<%@ page language="java" pageEncoding="UTF-8"%>
<%@ page contentType="text/html;charset=UTF-8" %>
<div ng-controller="CreateActionController">
<div class="col-md-10 pull-left" id="mapDiv" data-dojo-type="dijit/layout/ContentPane"></div>
<div class="col-md-2" data-ng-init="init()" id="menuDiv">
    <label for="actionName">Nazwa akcji</label>
    <input id="actionName" class="form-control" ng-model="action.name" placeholder="Nazwa akcji"/>
    <label for="actionDescription">Opis akcji</label>
    <input id="actionDescription" class="form-control" ng-model="action.description" placeholder="Opis akcji"/>
    <br>
    <div class="" id="areaDrawDiv" ng-model="areaDrawDiv">
        <label>Dodaj obszar</label>
        <div class="btn-group">
            <button class="btn btn-default" id="Polygon">Wybierz punkty</button><br>
            <button class="btn btn-default" id="FreehandPolygon">Zaznacz obszar</button>
        </div>
        <label>Dodaj kształt</label>
        <div class="btn-group-justified">
            <button class="btn btn-default" id="Rectangle">Prostokąt</button><br>
            <button class="btn btn-default" id="Triangle">Trójkąt</button>
            <button class="btn btn-default" id="Circle">Okrąg</button>
            <button class="btn btn-default" id="Ellipse">Elipsa</button>
        </div>
    </div>
    <br>
    <table ng-model="table" class="table table-striped table-condensed">
        <thead>
        <tr>
            <th>Numer</th>
            <th>Nazwa</th>
            <th>Opis</th>
        </tr>
        </thead>
        <tr ng-repeat="area in areas" ng-click="selectArea($index)" ng-dblclick="changeAreaProperties($index)"
            ng-class="{'info': $index === selected}">
            <td ng-model="area.number">{{$index + 1}}</td>
            <td ng-model="area.numberInAction">{{area.numberInAction}}</td>
            <td ng-model="area.geometryName">{{area.geometryName}}</td>
        </tr>
    </table>
    <p class="btn-group pull-right">
        <a class="btn btn-primary" ng-click="startAction()">Start</a>
        <a class="btn btn-default" ui-sref='home'>Wróć</a>
    </p>
</div>
    </div>