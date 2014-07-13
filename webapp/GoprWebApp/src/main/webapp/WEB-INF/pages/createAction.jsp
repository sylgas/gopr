<!DOCTYPE html>
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=utf-8">

    <!--The viewport meta tag is used to improve the presentation and behavior of the samples 
      on iOS devices-->
    <meta name="viewport" content="initial-scale=1, maximum-scale=1,user-scalable=no">
    <title>Tworzenie akcji poszukiwawczej</title>

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

    <script src="http://ajax.googleapis.com/ajax/libs/jquery/1/jquery.min.js"></script>
    <script src="http://js.arcgis.com/3.10/"></script>
    <script>
        var map, tb;

        require([
            "esri/map", "esri/toolbars/draw",
            "esri/symbols/SimpleLineSymbol",
            "esri/graphic", "esri/symbols/SimpleFillSymbol",
            "esri/Color", "dojo/dom", "dojo/on", "dojo/domReady!"
        ], function(
                Map, Draw,
                SimpleLineSymbol,
                Graphic, SimpleFillSymbol,
                Color, dom, on
                ) {
            map = new Map("mapDiv", {
                basemap: "streets",
                center: [-25.312, 34.307],
                zoom: 3
            });
            map.on("load", initToolbar);

            // fill symbol used for extent, polygon and freehand polygon, use a picture fill symbol
            // the images folder contains additional fill images, other options: sand.png, swamp.png or stiple.png
            var fillSymbol = new SimpleFillSymbol(
                    SimpleLineSymbol.STYLE_SOLID,
                    new SimpleLineSymbol(
                            SimpleLineSymbol.STYLE_SOLID,
                            new Color('#000'),
                            2
                    ),
                    new Color([58,58,58,0.1])
            );

            function initToolbar() {
                tb = new Draw(map);
                tb.on("draw-end", addGraphic);

                // event delegation so a click handler is not
                // needed for each individual button
                on(dom.byId("areaDrawDiv"), "click", function(evt) {
                    if ( evt.target.id === "areaDrawDiv" ) {
                        return;
                    }
                    var tool = evt.target.id.toLowerCase();
                    map.disableMapNavigation();
                    tb.activate(tool);
                });
            }

            function addGraphic(evt) {
                //deactivate the toolbar and clear existing graphics
                tb.deactivate();
                map.enableMapNavigation();

                // figure out which symbol to use
                var symbol = fillSymbol;

                map.graphics.add(new Graphic(evt.geometry, symbol));
            }
        });
    </script>

    <script type="text/javascript">
        function back() {
            window.location.href = "http://localhost:8080";
        }

        function startAction() {
            //alert(String(map.graphics.graphics.length));
            //var posting = $.post( "http://localhost:8080/rest/layer/send", { graphics : map.graphics, actionId : 1 } );
            var list = new Array();
            list[0] = "cze";
            list[1] = "yo";


            //var grap = map.graphics.graphics[1];

            $.post( "http://localhost:8080/rest/layer/send", { graphics : list, actionId : 1 });

            //window.location.href = "http://localhost:8080/action";
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
    <button id="Triangle">Trójkąt</button>
    <button id="Extent">Prostokąt</button>
    <button id="Circle">Okrąg</button>
    <button id="Ellipse">Elipsa</button>
</div>

    <button onclick="startAction()">Rozpocznij akcję</button>
    <button onclick="back()">Wróć</button>

</div>

<div id="mapDiv"></div>

</body>
</html>