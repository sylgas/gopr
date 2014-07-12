<html ng-app>
<head>
    <meta charset="UTF-8"/>
    <title>GOPR webApp</title>
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
            width: 19%;
            float: right;
        }

        .tittle {
            font-size: 22px;
            font-weight: bold;
        }

    </style>

    <script src="http://ajax.googleapis.com/ajax/libs/jquery/1/jquery.min.js"></script>
    <script>var dojoConfig = {parseOnLoad: true};</script>
    <script src="http://js.arcgis.com/3.9/"></script>
    <!--script type="text/javascript" src="http://serverapi.arcgisonline.com/jsapi/arcgis/?v=2.2"></script-->
    <script>
        dojo.require("esri.map");
        dojo.require("esri.layers.FeatureLayer");

        var map;

        function init() {
//            map = new esri.Map("mapDiv", {
//                basemap: "streets",
//                zoom: 5,
//                center: [19.91, 50.06]
//            });

            map = new esri.Map("mapDiv");
            //create and add new layer
            var layer = new esri.layers.ArcGISTiledMapServiceLayer("http://server.arcgisonline.com/ArcGIS/rest/services/World_Street_Map/MapServer");
            map.addLayer(layer);

            $.getJSON("http://localhost:8080/layer", function (data) {
                console.log(data);
                var jsonFS = data['jsonFS'];
                var jsonLD = data['jsonLD'];

                var fs = new esri.tasks.FeatureSet(jsonFS);

                var featureCollection = {
                    layerDefinition: jsonLD,
                    featureSet: fs
                };

                console.log(featureCollection);

                var jsonfl = new esri.layers.FeatureLayer(featureCollection, {
                    mode: esri.layers.FeatureLayer.MODE_SNAPSHOT,
                    outFields: ["*"],
                    id: "jsonfl"
                });
                console.log(jsonfl);
                map.addLayer(jsonfl);
            });
        }

        dojo.ready(init);
    </script>

    <script type="text/javascript">
        function chooseLayer() {
            window.location.href = "http://localhost:8080";
        }
    </script>

</head>

<body>

<div id="menuDiv">
    <br/>

    <div class="tittle">
        Wyświetlana jest:
    </div>
    <div class="tittle">
        ${layerName}
    </div>

    <br/>
    <input type="button" onclick="chooseLayer()" style="width: 95%" value="Wróć"/>
</div>

<div id="mapDiv"/>

</body>

</html>
