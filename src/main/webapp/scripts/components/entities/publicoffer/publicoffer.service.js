'use strict';

angular.module('jobvacancyApp')
    .factory('PublicOffer', function ($resource, DateUtils) {
        return $resource('api/publicoffers/:id', {}, {
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
