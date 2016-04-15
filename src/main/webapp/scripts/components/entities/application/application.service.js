'use strict';

angular.module('jobvacancyApp')
    .factory('Application', function ($resource, DateUtils) {
        return $resource('api/applications/:id', {}, {
            'query': { method: 'GET', isArray: true},
            'get': {
                method: 'GET',
                transformResponse: function (data) {
                    data = angular.fromJson(data);
                    return data;
                }
            }
        });
    });
