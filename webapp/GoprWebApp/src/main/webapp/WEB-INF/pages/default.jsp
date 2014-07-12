<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
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

        #noFileErrorDiv {
            color: red;
            font-style: italic
        }

        #chooseFileDiv {
            font-size: 22px;
            font-weight: bold;
        }

    </style>

    <script>var dojoConfig = {parseOnLoad: true};</script>
    <script src="http://js.arcgis.com/3.9/"></script>

    <script>
        dojo.require("esri.map");

        var map;

        function init() {
            map = new esri.Map("mapDiv", {
                basemap: "streets",
                zoom: 5,
                center: [19.91, 50.06]
            });
        }

        dojo.ready(init);
    </script>

</head>

<body>

<div id="menuDiv">
    <br/>

    <div id=uploadFormDiv>
        <form:form method="post" enctype="multipart/form-data"
                   modelAttribute="uploadedFile" action="fileUpload.htm">

            <div id="chooseFileDiv">Wybierz plik z warstwą:</div>
            <input id="layerUploader" type="file" name="file" title="Wybierz plik z warstwą" style="width: 95%"/>

            <div id="noFileErrorDiv">
                <form:errors path="file"/>
            </div>
            <input type="submit" value="Załaduj" title="Załaduj" style="width: 95%"/>
        </form:form>
    </div>
</div>

<div id="mapDiv"/>

</body>

</html>
