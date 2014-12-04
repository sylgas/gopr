<!DOCTYPE html>
<%@ page language="java" pageEncoding="UTF-8" %>
<%@ page contentType="text/html;charset=UTF-8" %>
<div class="modal-content">
  <div class="modal-header">
    <h3 class="modal-title">Nowy użytkownik</h3>
  </div>
  <div class="modal-body form-horizontal">
    <div class="form-group">
      <label for="userName" class="col-md-3 control-label">Imię:</label>
      <div class="col-md-7">
        <input class="form-control" id="userName" placeholder="Imię" ng-model="user.name">
      </div>
    </div>
    <div class="form-group">
      <label for="userSurname" class="col-md-3 control-label">Nazwisko:</label>
      <div class="col-md-7">
        <input class="form-control" id="userSurname" placeholder="Nazwisko" ng-model="user.surname">
      </div>
    </div>
    <div class="form-group">
      <label for="userLogin" class="col-md-3 control-label">Login:</label>
      <div class="col-md-7">
        <input class="form-control" id="userLogin" placeholder="Login" ng-model="user.login">
      </div>
    </div>
    <div class="form-group">
      <label for="userPassword" class="col-md-3 control-label">Hasło:</label>
      <div class="col-md-7">
        <input class="form-control" id="userPassword" placeholder="Hasło" ng-model="user.password">
      </div>
    </div>
    <div class="form-group">
      <label for="userNick" class="col-md-3 control-label">Nick:</label>
      <div class="col-md-7">
        <input class="form-control" id="userNick" placeholder="Nick" ng-model="user.nick">
      </div>
    </div>
    <div class="form-group">
      <label for="userPhone" class="col-md-3 control-label">Numer telefonu:</label>
      <div class="col-md-7">
        <input class="form-control" id="userPhone" placeholder="Numer telefonu" ng-model="user.phone">
      </div>
    </div>
    <div class="modal-footer">
      <button class="btn btn-warning" ng-click="cancel()">Anuluj</button>
      <button class="btn btn-primary" ng-click="save()"
              ng-disabled="user.name == null || user.surname == null || user.login == null || user.password == null || user.nick == null || user.phone == null">
        Zapisz
      </button>
    </div>
  </div>
</div>