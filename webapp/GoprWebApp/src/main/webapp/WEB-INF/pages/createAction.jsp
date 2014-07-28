<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">

<!--The viewport meta tag is used to improve the presentation and behavior of the samples
  on iOS devices-->
<meta name="viewport" content="initial-scale=1, maximum-scale=1,user-scalable=no">
<title>Tworzenie akcji poszukiwawczej</title>

<link rel="stylesheet" href="http://js.arcgis.com/3.10/js/dojo/dijit/themes/claro/claro.css">
<link rel="stylesheet" href="http://js.arcgis.com/3.10/js/esri/css/esri.css">
<link rel="stylesheet" type="text/css" href="/styles/createAction.css" media="screen" />

<script src="http://js.arcgis.com/3.10/"></script>
<script src="http://ajax.googleapis.com/ajax/libs/jquery/1/jquery.min.js"></script>


<script src="/scripts/createAction.js"></script>
</head>

<body>

<div id="menuDiv">
    Podaj nazwę akcji: <input id="actionName" type="text">
    <div>Dodaj obszar:</div>

    <div id="areaDrawDiv">
        <div>Dowolny wielokąt:</div>
        <button id="Polygon">Wybierz punkty</button>
        <button id="FreehandPolygon">Zaznacz obszar</button>
        <div>Prosty kształt:</div>
        <button id="Rectangle">Prostokąt</button>
        <button id="Triangle">Trójkąt</button>
        <button id="Circle">Okrąg</button>
        <button id="Ellipse">Elipsa</button>
    </div>

    <button onclick="startAction()">Rozpocznij akcję</button>
    <button onclick="back()">Wróć</button>

</div>

<div id="mapDiv" data-dojo-type="dijit/layout/ContentPane"></div>

</body>
</html>