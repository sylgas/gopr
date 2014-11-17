<!DOCTYPE html>
<%@ page language="java" pageEncoding="UTF-8"%>
<%@ page contentType="text/html;charset=UTF-8" %>
<div data-ng-init="initAction()" ng-controller="ActionController">
    <div id="mapDiv"></div>
    <div id="menuDiv">
        <div class="tittle">
            Akcja
        </div>
        <button class="btn btn-default" onclick="displayInfo()">Szczegóły akcji</button>
        <a class="btn btn-default" ui-sref="/">Wróć</a>
    </div>
</div>
