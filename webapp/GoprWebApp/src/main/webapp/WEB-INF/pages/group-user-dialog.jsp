<div class="modal-content">
    <div class="modal-header">
        <h3 class="modal-title">{{user.login}}</h3>
    </div>
    <div class="modal-body form-horizontal">
        <div class="form-group">
            <label for="userPhone" class="col-sm-3 control-label">Numer telefonu:</label>
            <div class="col-sm-8">
                <input class="form-control" id="userPhone" placeholder="Telefon" ng-model="user.phone">
            </div>
        </div>
        <div class="modal-footer">
            <button class="btn btn-primary" ng-click="save()">Zapisz</button>
            <button class="btn btn-warning" ng-click="cancel()">Anuluj</button>
        </div>
    </div>
</div>