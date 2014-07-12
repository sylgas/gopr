<!DOCTYPE html>
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=utf-8">

    <!--The viewport meta tag is used to improve the presentation and behavior of the samples 
      on iOS devices-->
    <meta name="viewport" content="initial-scale=1, maximum-scale=1,user-scalable=no">
    <title>Shapes and Symbols</title>

    <link rel="stylesheet" href="http://js.arcgis.com/3.9/js/esri/css/esri.css">

    <style>
        #info {
            top: 20px;
            color: #444;
            height: auto;
            font-family: arial;
            right: 20px;
            margin: 5px;
            padding: 10px;
            position: absolute;
            width: 115px;
            z-index: 40;
            border: solid 2px #666;
            border-radius: 4px;
            background-color: #fff;
        }
        html, body, #mapDiv {
            padding:0;
            margin:0;
            height:100%;
        }
        button {
            display: block;
        }
    </style>

    <script src="http://js.arcgis.com/3.9/"></script>
    <script>
        var map, tb;

        require([
            "esri/map", "esri/toolbars/draw",
            "esri/symbols/SimpleMarkerSymbol", "esri/symbols/SimpleLineSymbol",
            "esri/symbols/PictureFillSymbol", "esri/symbols/CartographicLineSymbol",
            "esri/graphic",
            "esri/Color", "dojo/dom", "dojo/on", "dojo/domReady!"
        ], function(
                Map, Draw,
                SimpleMarkerSymbol, SimpleLineSymbol,
                PictureFillSymbol, CartographicLineSymbol,
                Graphic,
                Color, dom, on
                ) {
            map = new Map("mapDiv", {
                basemap: "streets",
                center: [-25.312, 34.307],
                zoom: 3
            });
            map.on("load", initToolbar);

            // markerSymbol is used for point and multipoint, see http://raphaeljs.com/icons/#talkq for more examples
            var markerSymbol = new SimpleMarkerSymbol();
            markerSymbol.setPath("M16,4.938c-7.732,0-14,4.701-14,10.5c0,1.981,0.741,3.833,2.016,5.414L2,25.272l5.613-1.44c2.339,1.316,5.237,2.106,8.387,2.106c7.732,0,14-4.701,14-10.5S23.732,4.938,16,4.938zM16.868,21.375h-1.969v-1.889h1.969V21.375zM16.772,18.094h-1.777l-0.176-8.083h2.113L16.772,18.094z");
            markerSymbol.setColor(new Color("#00FFFF"));

            // lineSymbol used for freehand polyline, polyline and line.
            var lineSymbol = new CartographicLineSymbol(
                    CartographicLineSymbol.STYLE_SOLID,
                    new Color([255,0,0]), 10,
                    CartographicLineSymbol.CAP_ROUND,
                    CartographicLineSymbol.JOIN_MITER, 5
            );

            // fill symbol used for extent, polygon and freehand polygon, use a picture fill symbol
            // the images folder contains additional fill images, other options: sand.png, swamp.png or stiple.png
            var fillSymbol = new PictureFillSymbol(
                    "images/mangrove.png",
                    new SimpleLineSymbol(
                            SimpleLineSymbol.STYLE_SOLID,
                            new Color('#000'),
                            1
                    ),
                    42,
                    42
            );

            function initToolbar() {
                tb = new Draw(map);
                tb.on("draw-end", addGraphic);

                // event delegation so a click handler is not
                // needed for each individual button
                on(dom.byId("info"), "click", function(evt) {
                    if ( evt.target.id === "info" ) {
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
                var symbol;
                if ( evt.geometry.type === "point" || evt.geometry.type === "multipoint") {
                    symbol = markerSymbol;
                } else if ( evt.geometry.type === "line" || evt.geometry.type === "polyline") {
                    symbol = lineSymbol;
                }
                else {
                    symbol = fillSymbol;
                }

                map.graphics.add(new Graphic(evt.geometry, symbol));
            }
        });
    </script>
</head>

<body>

<div id="info">
    <div>Select a shape then draw on map to add graphic</div>
    <button id="Point">Point</button>
    <button id="Multipoint">Multipoint</button>
    <button id="Line">Line</button>
    <button id="Polyline">Polyline</button>
    <button id="FreehandPolyline">Freehand Polyline</button>
    <button id="Triangle">Triangle</button>
    <button id="Extent">Rectangle</button>
    <button id="Circle">Circle</button>
    <button id="Ellipse">Ellipse</button>
    <button id="Polygon">Polygon</button>
    <button id="FreehandPolygon">Freehand Polygon</button>
</div>

<div id="mapDiv"></div>

</body>
</html>