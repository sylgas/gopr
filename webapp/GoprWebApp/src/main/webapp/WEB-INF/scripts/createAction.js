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
        center: [19.91, 50.06]  //Krk
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
            label: "Edytuj",
            onClick: function () {
                if (selected.geometry.type !== "point")
                    editToolbar.activate(Edit.EDIT_VERTICES, selected);
            }
        }));

        ctxMenuForGraphics.addChild(new MenuItem({
            label: "Przesuń",
            onClick: function () {
                editToolbar.activate(Edit.MOVE, selected);
            }
        }));

        ctxMenuForGraphics.addChild(new MenuItem({
            label: "Skaluj lub obróć",
            onClick: function () {
                if (selected.geometry.type !== "point")
                    editToolbar.activate(Edit.ROTATE | Edit.SCALE, selected);
            }
        }));

        ctxMenuForGraphics.addChild(new MenuSeparator());
        ctxMenuForGraphics.addChild(new MenuItem({
            label: "Usuń",
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

function back() {
    window.location.href = "http://localhost:8090";
}

function startAction() {
    geometryList = Array();
    for (i = 1; i < map.graphics.graphics.length; ++i) {
        geometryList[i - 1] = {areaId: i, area: map.graphics.graphics[i].geometry.toJson()};
    }

    console.log(geometryList);
    var geometries = JSON.stringify({ geometries: geometryList });
    console.log(geometries);

    $.post("http://localhost:8090/rest/layer/send", { actionName: document.getElementById("actionName").value, actionId: 1, geometries: geometries })
        .done(function (response) {
            if (response != true)
                alert("Wystąpił błąd z połączeniem z serwerem ale przeszlo!");
            window.location.href = "http://localhost:8090/action/";
        })
        .fail(function () {
            alert("Wystąpił błąd z połączeniem z serwerem!");
        });
}