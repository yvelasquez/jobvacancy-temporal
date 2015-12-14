'use strict';

angular.module('jobvacancyApp')
    .factory('Application', function ($resource, DateUtils) {
        return $resource('api/applications/:id', {}, {
        });
    });
