'use strict';

angular.module('jobvacancyApp')
    .config(function ($stateProvider) {
        $stateProvider
            .state('offer', {
                parent: 'entity',
                url: '/offers',
                data: {
                    authorities: ['ROLE_USER'],
                    pageTitle: 'Offers'
                },
                views: {
                    'content@': {
                        templateUrl: 'scripts/app/entities/offer/offers.html',
                        controller: 'OfferController'
                    }
                },
                resolve: {
                }
            })
            .state('offer.detail', {
                parent: 'entity',
                url: '/offer/{id}',
                data: {
                    authorities: ['ROLE_USER'],
                    pageTitle: 'Offer'
                },
                views: {
                    'content@': {
                        templateUrl: 'scripts/app/entities/offer/offer-detail.html',
                        controller: 'OfferDetailController'
                    }
                },
                resolve: {
                    entity: ['$stateParams', 'Offer', function($stateParams, Offer) {
                        return Offer.get({id : $stateParams.id});
                    }]
                }
            })
            .state('offer.new', {
                parent: 'offer',
                url: '/new',
                data: {
                    authorities: ['ROLE_USER'],
                },
                onEnter: ['$stateParams', '$state', '$modal', function($stateParams, $state, $modal) {
                    $modal.open({
                        templateUrl: 'scripts/app/entities/offer/offer-dialog.html',
                        controller: 'OfferDialogController',
                        size: 'lg',
                        resolve: {
                            entity: function () {
                                return {
                                    title: null,
                                    location: null,
                                    description: null,
                                    id: null
                                };
                            }
                        }
                    }).result.then(function(result) {
                        $state.go('offer', null, { reload: true });
                    }, function() {
                        $state.go('offer');
                    })
                }]
            })
            .state('offer.edit', {
                parent: 'offer',
                url: '/{id}/edit',
                data: {
                    authorities: ['ROLE_USER'],
                },
                onEnter: ['$stateParams', '$state', '$modal', function($stateParams, $state, $modal) {
                    $modal.open({
                        templateUrl: 'scripts/app/entities/offer/offer-dialog.html',
                        controller: 'OfferDialogController',
                        size: 'lg',
                        resolve: {
                            entity: ['Offer', function(Offer) {
                                return Offer.get({id : $stateParams.id});
                            }]
                        }
                    }).result.then(function(result) {
                        $state.go('offer', null, { reload: true });
                    }, function() {
                        $state.go('^');
                    })
                }]
            });
    });
