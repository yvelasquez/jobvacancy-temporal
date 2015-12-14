'use strict';

angular.module('jobvacancyApp')
    .config(function ($stateProvider) {
        $stateProvider
            .state('publicoffer', {
                parent: 'site',
                url: '/publicoffers',
                data: {
                    authorities: []
                },
                views: {
                    'content@': {
                        templateUrl: 'scripts/app/entities/publicoffer/publicoffer.html',
                        controller: 'PublicOfferController'
                    }
                },
                resolve: {

                }
            });
    });
