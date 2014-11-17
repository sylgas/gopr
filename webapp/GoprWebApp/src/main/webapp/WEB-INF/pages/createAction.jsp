<!DOCTYPE html>
<%@ page language="java" pageEncoding="UTF-8"%>
<%@ page contentType="text/html;charset=UTF-8" %>
<div class="col-md-9 pull-left" id="mapDiv" data-dojo-type="dijit/layout/ContentPane"></div>
<div class="col-md-3" data-ng-init="init()" id="menuDiv" ng-model="menuDiv" ng-controller="CreateActionController">
    <label for="actionName">Nazwa akcji</label>
    <input id="actionName" type="email" class="form-control" ng-model="actionName" placeholder="Nazwa akcji">
    <br>
    <div class="" id="areaDrawDiv" ng-model="areaDrawDiv">
        <label>Dodaj obszar</label>
        <button class="btn btn-default" id="Polygon">Wybierz punkty</button><br>
        <button class="btn btn-default" id="FreehandPolygon">Zaznacz obszar</button>
        <label>Dodaj kształt</label>
        <button class="btn btn-default" id="Rectangle">Prostokąt</button><br>
        <button class="btn btn-default" id="Triangle">Trójkąt</button>
        <button class="btn btn-default" id="Circle">Okrąg</button>
        <button class="btn btn-default" id="Ellipse">Elipsa</button>
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