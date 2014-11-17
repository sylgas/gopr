<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<table class="table table-striped table-bordered table-hover table-condensed">
    <tr>
        <th></th>
        <th>Id</th>
        <th>Imię</th>
        <th>Nazwisko</th>
        <th>Login</th>
        <th>Cośtam</th>
    </tr>
    <tr ng-repeat="action in actions">
        <td><button class="btn-xs glyphicon glyphicon-arrow-left" ng-click="removeFromGroup(groupUser)"></button></td>
        <td>{{action.id}}</td>
        <td>{{groupUser.firstname}}</td>
        <td>{{groupUser.lastname}}</td>
        <td>{{groupUser.login}}</td>
        <td><button class="btn-xs btn-primary">Szczegóły</button></td>
    </tr>
</table>