'use strict';

angular.module('jobvacancyApp')
    .config(function ($stateProvider) {
        $stateProvider
            .state('company', {
                parent: 'entity',
                url: '/companys',
                data: {
                    authorities: ['ROLE_USER'],
                    pageTitle: 'Companys'
                },
                views: {
                    'content@': {
                        templateUrl: 'scripts/app/entities/company/companys.html',
                        controller: 'CompanyController'
                    }
                },
                resolve: {
                }
            })
            .state('company.detail', {
                parent: 'entity',
                url: '/company/{id}',
                data: {
                    authorities: ['ROLE_USER'],
                    pageTitle: 'Company'
                },
                views: {
                    'content@': {
                        templateUrl: 'scripts/app/entities/company/company-detail.html',
                        controller: 'CompanyDetailController'
                    }
                },
                resolve: {
                    entity: ['$stateParams', 'Company', function($stateParams, Company) {
                        return Company.get({id : $stateParams.id});
                    }]
                }
            })
            .state('company.new', {
                parent: 'company',
                url: '/new',
                data: {
                    authorities: ['ROLE_USER'],
                },
                onEnter: ['$stateParams', '$state', '$modal', function($stateParams, $state, $modal) {
                    $modal.open({
                        templateUrl: 'scripts/app/entities/company/company-dialog.html',
                        controller: 'CompanyDialogController',
                        size: 'lg',
                        resolve: {
                            entity: function () {
                                return {
                                    name: null,
                                    reputation: null,
                                    registrationDate: null,
                                    id: null
                                };
                            }
                        }
                    }).result.then(function(result) {
                        $state.go('company', null, { reload: true });
                    }, function() {
                        $state.go('company');
                    })
                }]
            })
            .state('company.edit', {
                parent: 'company',
                url: '/{id}/edit',
                data: {
                    authorities: ['ROLE_USER'],
                },
                onEnter: ['$stateParams', '$state', '$modal', function($stateParams, $state, $modal) {
                    $modal.open({
                        templateUrl: 'scripts/app/entities/company/company-dialog.html',
                        controller: 'CompanyDialogController',
                        size: 'lg',
                        resolve: {
                            entity: ['Company', function(Company) {
                                return Company.get({id : $stateParams.id});
                            }]
                        }
                    }).result.then(function(result) {
                        $state.go('company', null, { reload: true });
                    }, function() {
                        $state.go('^');
                    })
                }]
            });
    });
