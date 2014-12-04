<!DOCTYPE html>
<%@ page language="java" pageEncoding="UTF-8" %>
<%@ page contentType="text/html;charset=UTF-8" %>
<div ng-controller="CreateActionController">
    <div class="col-md-10 pull-left" id="mapDiv" data-dojo-type="dijit/layout/ContentPane"></div>
    <div class="col-md-2" data-ng-init="init()" id="menuDiv">
        <div class="form-group">
            <label for="actionName">Nazwa akcji</label>
            <input id="actionName" class="form-control" ng-model="action.name" placeholder="Nazwa akcji"/>
        </div>
        <div class="form-group">
            <label for="actionDescription">Opis akcji</label>
            <textarea id="actionDescription" rows="5" class="form-control" ng-model="action.description"
                      placeholder="Opis akcji"></textarea>
        </div>
        <div class="form-group">
            <label for="areaDrawDiv" class="pull-left">Dodaj obszar</label>

            <div id="areaDrawDiv" class="btn-group-vertical col-md-10 col-md-offset-2">
                <button class="btn btn-default" id="Polygon">Wybierz punkty</button>
                <button class="btn btn-default" id="FreehandPolygon">Zaznacz obszar</button>
            </div>
        </div>
        <div class="form-group">
            <label for="areasTable"><b>Zapisane obszary</b></label>
            <table id="areasTable" ng-model="table" class="table table-striped table-condensed col-md-12" ng-show="areas">
                <thead>
                <tr>
                    <th>Numer</th>
                    <th>Nazwa</th>
                </tr>
                </thead>
                <div>
                    <tr ng-repeat="(index, area) in areas" ng-click="selectArea($index)"
                        ng-dblclick="changeAreaProperties($index)"
                        ng-show="areas" ng-class="{'info': $index === selected}">
                        <td ng-model="area.numberInAction">{{area.numberInAction}}</td>
                        <td ng-model="area.geometryName">{{area.geometryName}}</td>
                        <td class="col-md-1">
                            <a ng-click="editArea(index)"><span class="glyphicon glyphicon-edit"></span></a>
                        </td>
                    </tr>
                </div>
            </table>
            <p ng-hide="areas">Brak</p>
        </div>

        <div class="btn-group-vertical col-md-7 col-md-offset-5">
            <button class="btn btn-default" ui-sref='home'>Wróć</button>
            <button class="btn btn-primary" ng-click="createAction()"
                    ng-disabled="action.name == null || action.description == null || areas == null">
                Dalej
            </button>
        </div>
    </div>
</div>