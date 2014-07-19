<html ng-app>
<head>
    <meta charset="UTF-8"/>
    <title>Akcja poszukiwawcza</title>
    <script type="text/javascript"
            src="//ajax.googleapis.com/ajax/libs/angularjs/1.0.7/angular.min.js"></script>

    <link rel="stylesheet" href="http://js.arcgis.com/3.9/js/esri/css/esri.css">
    <style>
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

        #menuDiv {
            height: 100%;
            width: 18%;
            margin-right: 1%;
            margin-left: 1%;
            float: right;
        }

        .tittle {
            font-size: 22px;
            font-weight: bold;
        }

        button {
            display: block;
            width: 100%;
        }

    </style>

    <script src="http://ajax.googleapis.com/ajax/libs/jquery/1/jquery.min.js"></script>
    <script>var dojoConfig = {parseOnLoad: true};</script>
    <script src="http://js.arcgis.com/3.9/"></script>
    <script>
        var map, usersActual, usersInPaths, polyline, index;

        require([
            "esri/map", "esri/symbols/SimpleLineSymbol", "esri/symbols/SimpleFillSymbol",
            "esri/geometry/Polygon",
            "esri/SpatialReference", "esri/geometry/Polyline","esri/geometry/Point", "esri/graphic", "esri/Color",
            "esri/symbols/SimpleMarkerSymbol", "esri/symbols/CartographicLineSymbol",
            "dojo/domReady!"
        ], function (Map, SimpleLineSymbol, SimpleFillSymbol, Polygon,
            SpatialReference, Polyline, Point, Graphic, Color,
            SimpleMarkerSymbol, CartographicLineSymbol) {

            map = new Map("mapDiv", {
                basemap: "streets",
                zoom: 5,
                center: [19.91, 50.06]
            });

            var markerSymbol = new SimpleMarkerSymbol(SimpleMarkerSymbol.STYLE_CIRCLE,             //style
                    10,                                                                            //size
                    new SimpleLineSymbol(SimpleLineSymbol.STYLE_SOLID, new Color([0,0,0]), 1),     //outline
                    new Color([255,255,255,0.8]));                                                 //color

            var lineSymbol = new CartographicLineSymbol(
                    CartographicLineSymbol.STYLE_SOLID,                                            //style
                    new Color([0,0,0]),                                                            //color
                    2,                                                                             //width
                    CartographicLineSymbol.CAP_ROUND,                                              //cap
                    CartographicLineSymbol.JOIN_ROUND,                                             //join
                    10);                                                                           //miter limit

            function displayAreas(data){
                var fillSymbol = new SimpleFillSymbol(
                        SimpleLineSymbol.STYLE_SOLID,
                        new SimpleLineSymbol(
                                SimpleLineSymbol.STYLE_SOLID,
                                new Color('#000'),
                                2
                        ),
                        new Color([58, 58, 58, 0.1])
                );
                for (i = 0; i < data.geometries.length; i++) {
                    var polygon = new Polygon(data.geometries[i].area);
                    var newGraphic = new Graphic(polygon, fillSymbol);
                    map.graphics.add(newGraphic);
                }
            }
            function displayPoints(data, index){
                map.graphics.remove(polyline);
                for (i = 0; i < data.length; i++){
                    for (j = 0; j < data[i].positions.length; j++){
                        if (usersActual[data[i].userId]){
                            map.graphics.remove(usersActual[data[i].userId]);
                        }

                        var point = new Point(data[i].positions[j].x,
                                data[i].positions[j].y ,map.spatialReference);

                        var graphic = new Graphic(point, markerSymbol);
                        usersActual[data[i].userId] = graphic;
                        map.graphics.add(graphic);

                        if (!usersInPaths[data[i].userId]
                                && !polyline.geometry.paths[usersInPaths[data[i].userId]]){
                            polyline.geometry.addPath([[point.x, point.y]]);
                            usersInPaths[data[i].userId] = index;
                            index++;
                        } else {
                            polyline.geometry.insertPoint(
                                    usersInPaths[data[i].userId],
                                    polyline.geometry.paths[usersInPaths[data[i].userId]].length,
                                    new Point(point.x, point.y));
                        }
                    }
                }
                map.graphics.add(polyline);
                return index;
            }

            map.on("load", initAction);

            function initAction() {
                index = 0;
                usersActual = {}  //Dictionary[userId] = Graphic with point
                usersInPaths = {} //Dictionary[userId] = pathId
                polyline = new Graphic(new Polyline(map.spatialReference), lineSymbol);
                map.graphics.add(polyline);

                $.getJSON("http://localhost:8080/rest/layer/get", {actionId: 1})
                        .done(function (data) {
                            displayAreas(data);
                        })
                        .fail(function () {
                            alert("Wystąpił błąd podczas połączenia z serwerem!");
                        });

                $.getJSON("http://localhost:8080/rest/point/getAllPoints", {actionId: 1})
                        .done(function (data){
                            index = displayPoints(data, index);
                        })
                        .fail(function () {
                            alert("Wystąpił błąd podczas połączenia z serwerem!");
                        });

                window.setInterval(function () {
                    $.getJSON("http://localhost:8080/rest/point/get", {actionId: 1, dateTime: new Date().getTime()})
                            .done(function (data){
                                index = displayPoints(data, index);
                            })
                            .fail(function () {
                                alert("Wystąpił błąd podczas połączenia z serwerem!");
                            });
                }, 3000); // repeat forever, polling every 3 seconds
            }
        });
    </script>

    <script type="text/javascript">
        function back() {
            window.location.href = "http://localhost:8080";
        }
    </script>

</head>

<body>

<div id="menuDiv">
    <br/>

    <div class="tittle">
        Akcja
    </div>

    <button onclick="back()">Wróć</button>
</div>

<div id="mapDiv"/>

</body>

</html>
