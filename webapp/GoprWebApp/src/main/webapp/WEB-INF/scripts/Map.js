function Map(angular, Map, Draw, Edit, SimpleLineSymbol, SimpleFillSymbol, Graphic, Color,
                               dom, on, Menu, MenuItem, MenuSeparator) {

    var map, tb, editToolbar, ctxMenuForGraphics;
    var selected, geometries;
    var scope;
    function mapGen(elem) {
        return new Map(elem, {
            basemap: "streets",
            zoom: 5,
            center: [19.91, 50.06]  //Krk);
        });
    }

    function initToolbarAndContextMenu() {
        geometries = {};              //Dictionary[geometry id] = GeometryEx
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
            label: "Ustaw nazwę i numer",
            onClick: function () {
                if (selected.geometry.type !== "point") {
                    var index = getGraphicId(selected);
                    changeGeometryInfo(index);
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
    }

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
        var index = getGraphicId(newGraphic);
        if (geometries[index]) {
            console.log("addGraphic: duplicated index");
            //alert("Cos nie tak");
        }
        geometries[index] = new GeometryEx("-", "-");
        updateTable();
    }

    function deleteGraphic(graphic){
        var index = getGraphicId(graphic);
        map.graphics.remove(graphic);

        for (var i = index; i < Object.keys(geometries).length; i++)
            geometries[i] = geometries[i+1];

        delete geometries[Object.keys(geometries).length];
        updateTable();
    }

    function changeGeometryInfo(graphicId){
        console.log("in changeGeometryInfo " + graphicId);
        //TODO: zaimplementowac jakis dialog, zeby pobrac inta i stringa
        //TODO: ustawic geometry[index].numberInAction i geometry[index].geometryName
        //updateTable();
    }

    function updateTable(){
        scope.areas = geometries;
        scope.$apply()
    }

    function deselectAllGraphics(){
        for (var i = 1; i < map.graphics.graphics.length; i++)
            map.graphics.graphics[i].setSymbol(fillSymbol);
    }

    function selectGraphic(tableRow){
        deselectAllGraphics();
        var graphic = map.graphics.graphics[tableRow +1];
        graphic.setSymbol(selectedFillSymbol);
        scope.selected = tableRow;
    }

    function getGraphicId(graphic){
        var index = map.graphics.graphics.indexOf(graphic);
        return index;
    }

    //endregion

    //region usefull items

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

    //Structure to keep geometry info defined by user
    function GeometryEx(numberInAction, geometryName){
        this.numberInAction = numberInAction;
        this.geometryName = geometryName;
    }

    return {
        start: function (App) {
            App.factory('Map', ['$scope', Map]);
            return Map;
        }
    };
}

define(['angular', 'esri/map', "esri/toolbars/draw", "esri/toolbars/edit", "esri/symbols/SimpleLineSymbol", "esri/symbols/SimpleFillSymbol",
    "esri/graphic", "esri/Color", "dojo/dom", "dojo/on", "dijit/Menu", "dijit/MenuItem", "dijit/MenuSeparator", "dijit/form/Button",
    "dijit/layout/BorderContainer", "dijit/layout/ContentPane", "dojo/domReady!"], Map);