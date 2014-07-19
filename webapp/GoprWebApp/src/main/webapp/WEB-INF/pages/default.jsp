<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<html ng-app>
<head>
    <meta charset="UTF-8"/>
    <title>GOPR</title>

    <style>
        button {
            display: block;
            width: 100%;
            height: 15%;
            font-weight: bold;
            font-size: 30px;
        }

    </style>

    <script type="text/javascript">
        function createAction() {
            window.location.href = "http://localhost:8080/createAction";
        }

        function loadActions() {
            //TODO: wyswietlanie listy otwartych akcji
        }
    </script>

</head>

<body>

<h1>System wspomagający akcje poszukiwawcze GOPR</h1>

<button onclick="createAction()">Stwórz akcję</button>
<button onclick="loadActions()">Wczytaj akcję</button>

</body>
</html>
