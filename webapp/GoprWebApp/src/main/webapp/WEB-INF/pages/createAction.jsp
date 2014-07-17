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

<style>
    #menuDiv {
        height: 100%;
        width: 18%;
        margin-right: 1%;
        margin-left: 1%;
        float: right;
    }

    #areaDrawDiv {
        color: #444;
        height: auto;
        width: auto;
        out: 100%;
        border: solid 2px #666;
        border-radius: 4px;
        background-color: #fff;
        margin: 2%;
        padding: 2%;
    }

    html {
        height: 100%
    }

    body {
        height: 100%;
        margin: 0;
        padding: 0;
    }

    #mapDiv {
        height: 100%;
        width: 80%;
        float: left;
    }

    button {
        display: block;
        width: 100%;
    }
</style>

<script src="http://js.arcgis.com/3.10/"></script>
<script src="http://ajax.googleapis.com/ajax/libs/jquery/1/jquery.min.js"></script>


<script>
    var map, tb, editToolbar, ctxMenuForGraphics;
    var selected;

    require([
        "esri/map", "esri/toolbars/draw", "esri/toolbars/edit",
        "esri/symbols/SimpleLineSymbol", "esri/symbols/SimpleFillSymbol",
        "esri/graphic", "esri/Color", "dojo/dom", "dojo/on",
        "dijit/Menu", "dijit/MenuItem", "dijit/MenuSeparator",

        "dijit/form/Button", "dijit/layout/BorderContainer", "dijit/layout/ContentPane",
        "dojo/domReady!"
    ], function (Map, Draw, Edit, SimpleLineSymbol, SimpleFillSymbol, Graphic, Color, dom, on, Menu, MenuItem, MenuSeparator) {

        map = new Map("mapDiv", {
            basemap: "satellite",
            zoom: 5,
            center: [19.91, 50.06]
        });
        map.on("load", initToolbarAndContextMenu);

        function initToolbarAndContextMenu() {
            createToolbarAndContextMenu();
            initToolbar();
        }

        function createToolbarAndContextMenu() {
            editToolbar = new Edit(map);

            map.on("click", function (evt) {
                editToolbar.deactivate();
            });

            createGraphicsMenu();
        }

        function createGraphicsMenu() {
            ctxMenuForGraphics = new Menu({});
            ctxMenuForGraphics.addChild(new MenuItem({
                label: "Edit",
                onClick: function () {
                    if (selected.geometry.type !== "point") {
                        editToolbar.activate(Edit.EDIT_VERTICES, selected);
                    } else {
                        alert("Not implemented");
                    }
                }
            }));

            ctxMenuForGraphics.addChild(new MenuItem({
                label: "Move",
                onClick: function () {
                    editToolbar.activate(Edit.MOVE, selected);
                }
            }));

            ctxMenuForGraphics.addChild(new MenuItem({
                label: "Rotate/Scale",
                onClick: function () {
                    if (selected.geometry.type !== "point") {
                        editToolbar.activate(Edit.ROTATE | Edit.SCALE, selected);
                    } else {
                        alert("Not implemented");
                    }
                }
            }));

            ctxMenuForGraphics.addChild(new MenuSeparator());
            ctxMenuForGraphics.addChild(new MenuItem({
                label: "Delete",
                onClick: function () {
                    map.graphics.remove(selected);
                }
            }));

            ctxMenuForGraphics.startup();

            map.graphics.on("mouse-over", function (evt) {
                selected = evt.graphic;

                ctxMenuForGraphics.bindDomNode(evt.graphic.getDojoShape().getNode());
            });

            map.graphics.on("mouse-out", function (evt) {
                ctxMenuForGraphics.unBindDomNode(evt.graphic.getDojoShape().getNode());
            });
        }

        var fillSymbol = new SimpleFillSymbol(
                SimpleLineSymbol.STYLE_SOLID,
                new SimpleLineSymbol(
                        SimpleLineSymbol.STYLE_SOLID,
                        new Color('#000'),
                        2
                ),
                new Color([58, 58, 58, 0.1])
        );

        function initToolbar() {
            tb = new Draw(map);
            tb.on("draw-end", addGraphic);

            on(dom.byId("areaDrawDiv"), "click", function (evt) {
                if (evt.target.id === "areaDrawDiv") {
                    return;
                }
                var tool = evt.target.id.toLowerCase();
                map.disableMapNavigation();
                tb.activate(tool);
            });
        }

        function addGraphic(evt) {
            tb.deactivate();
            map.enableMapNavigation();

            var newGraphic = new Graphic(evt.geometry, fillSymbol);
            map.graphics.add(newGraphic);
        }

    });
</script>

<script type="text/javascript">
    function back() {
        window.location.href = "http://localhost:8080";
    }

    function startAction() {
        geometryList = Array();
        for (i = 1; i < map.graphics.graphics.length; ++i) {
            geometryList[i - 1] = {areaId: i, area: map.graphics.graphics[i].geometry.toJson()};
        }

        console.log(geometryList);
        var geometries = JSON.stringify({ geometries: geometryList });
        console.log(geometries);

        $.post("http://localhost:8080/rest/layer/send", { actionId: 1, geometries: geometries })
                .done(function (response) {
                    if (response != true)
                        alert("Wystąpił błąd z połączeniem z serwerem!");
                    window.location.href = "http://localhost:8080/action";
                })
                .fail(function () {
                    alert("Wystąpił błąd z połączeniem z serwerem!");
                });
    }
</script>
</head>

<body>

<div id="menuDiv">

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