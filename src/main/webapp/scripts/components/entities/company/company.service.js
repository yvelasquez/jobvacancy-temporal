'use strict';

angular.module('jobvacancyApp')
    .factory('Company', function ($resource, DateUtils) {
        return $resource('api/companys/:id', {}, {
            'query': { method: 'GET', isArray: true},
            'get': {
                method: 'GET',
                transformResponse: function (data) {
                    data = angular.fromJson(data);
                    data.registrationDate = DateUtils.convertLocaleDateFromServer(data.registrationDate);
                    return data;
                }
            },
            'update': {
                method: 'PUT',
                transformRequest: function (data) {
                    data.registrationDate = DateUtils.convertLocaleDateToServer(data.registrationDate);
                    return angular.toJson(data);
                }
            },
            'save': {
                method: 'POST',
                transformRequest: function (data) {
                    data.registrationDate = DateUtils.convertLocaleDateToServer(data.registrationDate);
                    return angular.toJson(data);
                }
            }
        });
    });
