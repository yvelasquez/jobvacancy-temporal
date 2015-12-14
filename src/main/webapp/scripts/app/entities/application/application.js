'use strict';

angular.module('jobvacancyApp')
    .config(function ($stateProvider) {
        $stateProvider
            .state('application', {
                parent: 'publicoffer',
                url: '/{id}/apply',
                data: {
                    authorities: []
                },
                onEnter: ['$stateParams', '$state', '$modal', function($stateParams, $state, $modal) {
                    $modal.open({
                        templateUrl: 'scripts/app/entities/application/application-dialog.html',
                        controller: 'ApplicationDialogController',
                        size: 'lg',
                        resolve: {
                            entity: function () {
                                return {fullname: null, email: null, offerId: $stateParams.id};
                            }
                        }
                    }).result.then(function(result) {
                        $state.go('publicoffer', null, { reload: true });
                    }, function() {
                        $state.go('publicoffer');
                    })
                }]
            });
    });
