<div class="modal-content">
    <div class="modal-header">
        <h3 class="modal-title">Obszar: {{editedArea}}</h3>
    </div>
    <div class="modal-body form-horizontal">
        <div class="form-group">
            <label for="areaNumberInAction" class="col-md-3 control-label">Numer:</label>
            <div class="col-md-7">
                <input class="form-control" id="areaNumberInAction" placeholder="Numer" ng-model="editedArea.numberInAction">
            </div>
        </div>
        <div class="form-group">
            <label for="areaName" class="col-md-3 control-label">Nazwa:</label>
            <div class="col-md-7">
                <input class="form-control" id="areaName" placeholder="Nazwa" ng-model="editedArea.geometryName">
            </div>
        </div>
        <div class="modal-footer">
            <button class="btn btn-primary" ng-click="save()">Zapisz</button>
            <button class="btn btn-warning" ng-click="cancel()">Anuluj</button>
        </div>
    </div>
</div>