<!DOCTYPE html>
<%@ page language="java" pageEncoding="UTF-8"%>
<%@ page contentType="text/html;charset=UTF-8" %>
<html lang="pl">
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=utf-8">
    <meta name="viewport" content="initial-scale=1, maximum-scale=1,user-scalable=no">
    <script src="/bower_components/angular/angular.min.js"></script>
    <script src="/bower_components/jquery/dist/jquery.min.js"></script>
    <script src="/bower_components/ng-table/ng-table.min.js"></script>
    <script src="/bower_components/angular-ui-router/release/angular-ui-router.js"></script>
    <link rel="stylesheet" href="http://js.arcgis.com/3.10/js/dojo/dijit/themes/claro/claro.css">
    <link rel="stylesheet" href="http://js.arcgis.com/3.10/js/esri/css/esri.css">
    <link rel="stylesheet" type="text/css" href="/styles/createAction.css" media="screen" />
    <link href="/styles/action.css" rel="stylesheet" type="text/css" media="screen" />
    <script src="http://js.arcgis.com/3.10/"></script>
</head>
<body>
<nav class="navbar navbar-default" role="navigation">
    <div class="container-fluid">
        <!-- Brand and toggle get grouped for better mobile display -->
        <div class="navbar-header">
            <button type="button" class="navbar-toggle collapsed" data-toggle="collapse" data-target="#bs-example-navbar-collapse-1">
                <span class="sr-only">Toggle navigation</span>
                <span class="icon-bar"></span>
                <span class="icon-bar"></span>
                <span class="icon-bar"></span>
            </button>
            <a class="navbar-brand" href="#"><span class="glyphicon glyphicon-tree-conifer"></span></a>
        </div>

        <!-- Collect the nav links, forms, and other content for toggling -->
        <div class="collapse navbar-collapse" id="bs-example-navbar-collapse-1">
            <ul class="nav navbar-nav">
                <li class="active"><a ui-sref="actionList">Lista akcji</a></li>
                <li><a ui-sref="createAction">Stwórz akcję</a></li>
                <li><a ui-sref="#">Wczytaj akcję</a></li>
            </ul>
        </div><!-- /.navbar-collapse -->
    </div><!-- /.container-fluid -->
</nav>
<div ui-view class="col-md-12"></div>

</body>

<link rel="stylesheet" href="/bower_components/bootstrap/dist/css/bootstrap.css"/>
<script src="/bower_components/angular-bootstrap/ui-bootstrap-tpls.min.js"></script>
<script src="main.js"></script>

</html>