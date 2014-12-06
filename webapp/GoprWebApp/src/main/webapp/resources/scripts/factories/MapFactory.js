function mapFactory(angular, Map, Draw, Edit, Polygon, SimpleLineSymbol, SimpleFillSymbol,
                    Graphic, Color, dom, on, Menu, MenuItem, MenuSeparator) {

    var map, geometries, editToolbar, ctxMenuForGraphics, toolbar,
        addOrDeleteAreaFunction, areaPropertiesFunction, selectGraphicFunction, GeometryEx;

    var mapFactory = {};

    var fillSymbol = new SimpleFillSymbol(
        SimpleLineSymbol.STYLE_SOLID,
        new SimpleLineSymbol(
            SimpleLineSymbol.STYLE_SOLID,
            new Color('#000'),
            2
        ),
        new Color([58, 58, 58, 0.1])
    );

    var selectedFillSymbol = new SimpleFillSymbol(
        SimpleLineSymbol.STYLE_SOLID,
        new SimpleLineSymbol(
            SimpleLineSymbol.STYLE_SOLID,
            new Color([0, 255, 0, 1]),
            2
        ),
        new Color([0, 255, 0, 0.1])
    );

    mapFactory.generateDrawableMap = function(elem) {
        geometries = {}; //Dictionary[geometry id] = GeometryEx
        map = new Map(elem, {
            basemap: "streets",
            zoom: 11,
            center: [19.91, 50.06]  //Krk);
        });
        return map;
    };

    mapFactory.getToolbar = function() {
        editToolbar = new Edit(map);
        map.on("click", function (evt) {
            deselectAllGraphics();
            editToolbar.deactivate();
        });
        toolbar = initToolbar();
        return toolbar;
    };

    function initToolbar() {
        toolbar = new Draw(map);
        toolbar.on("draw-end", addGraphic);

        on(dom.byId("areaDrawDiv"), "click", function (evt) {
            if (evt.target.id === "areaDrawDiv") {
                return;
            }
            var tool = evt.target.id.toLowerCase();
            map.disableMapNavigation();
            toolbar.activate(tool);
        });
        return toolbar
    }

    mapFactory.getContextMenu = function() {
        ctxMenuForGraphics = new Menu({});
        ctxMenuForGraphics.addChild(new MenuItem({
            label: "Ustaw nazwę i numer",
            onClick: function(){
                if (selected.geometry.type !== "point"){
                    var index = getGraphicId(selected);
                    areaPropertiesFunction(index);
                }
            }
        }));
        ctxMenuForGraphics.addChild(new MenuSeparator());
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
                if (selected.geometry.type !== "point")
                    deleteGraphic(selected);
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

        return ctxMenuForGraphics
    };

    function addGraphic(evt) {
        toolbar.deactivate();
        map.enableMapNavigation();
        var newGraphic = new Graphic(evt.geometry, fillSymbol);
        map.graphics.add(newGraphic);
        var index = getGraphicId(newGraphic);
        if (geometries[index]) {
            console.log("addGraphic: duplicated index");
            //alert("Cos nie tak");
        }
        geometries[index] = new GeometryEx(index, "obszar " + index);

        addOrDeleteAreaFunction();
    }

    function deleteGraphic(graphic){
        var index = getGraphicId(graphic);
        map.graphics.remove(graphic);

        for (var i = index; i < Object.keys(geometries).length; i++)
            geometries[i] = geometries[i+1];

        delete geometries[Object.keys(geometries).length];
        console.log("delete" + geometries)
        addOrDeleteAreaFunction();
    }

    function deselectAllGraphics(){
        for (var i = 1; i < map.graphics.graphics.length; i++)
            map.graphics.graphics[i].setSymbol(fillSymbol);
    }

    mapFactory.selectArea = function(tableRow){
        deselectAllGraphics();
        var graphic = map.graphics.graphics[tableRow +1];
        graphic.setSymbol(selectedFillSymbol);
        selectGraphicFunction(tableRow)
    };

    mapFactory.displayAreas = function(geometries){
        geometriesOnMap = {};
        for (var i = 0; i < geometries.length; i++) {
            var polygon = new Polygon(JSON.parse(geometries[i].data));
            var newGraphic = new Graphic(polygon, fillSymbol);
            map.graphics.add(newGraphic);
            geometriesOnMap[i] =
                map.graphics.graphics.indexOf(newGraphic);
        }
    };

    mapFactory.onAddOrDeleteArea = function(addOrDeleteAreaFun) {
        addOrDeleteAreaFunction = addOrDeleteAreaFun
    };

    mapFactory.onChangeAreaProperties = function(propertiesFun) {
        areaPropertiesFunction = propertiesFun
    };

    mapFactory.onAreaSelect = function(selectGraphicFun) {
        selectGraphicFunction = selectGraphicFun
    };

    function getGraphicId(graphic){
        return map.graphics.graphics.indexOf(graphic);
    }

    mapFactory.addGeometryEx = function(geometryEx) {
        GeometryEx = geometryEx
    };

    mapFactory.getGeometries = function() {
        return geometries
    };

    function MapFactory() {
        return mapFactory;
    }

    return {
        start: function (App) {
            App.factory('MapFactory', MapFactory);
        }
    };
}

define(['angular', 'esri/map', "esri/toolbars/draw", "esri/toolbars/edit", "esri/geometry/Polygon", "esri/symbols/SimpleLineSymbol", "esri/symbols/SimpleFillSymbol",
    "esri/graphic", "esri/Color", "dojo/dom", "dojo/on", "dijit/Menu", "dijit/MenuItem", "dijit/MenuSeparator", "dijit/form/Button",
    "dijit/layout/BorderContainer", "dijit/layout/ContentPane", "dojo/domReady!"], mapFactory);