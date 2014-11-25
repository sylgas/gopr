(function() {
    'use strict';

    define('angular', function() {
        if (angular) { return angular; }
        return {};
    });

    require([
        'dojo/ready',
        '/resources/bootstrap.js'
    ], function(ready, bootstrap) {
        ready(function () {
            bootstrap.start();
        });
    });

}).call(this);