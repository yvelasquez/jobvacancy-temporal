'use strict';

angular.module('jobvacancyApp')
    .controller('CompanyDetailController', function ($scope, $rootScope, $stateParams, entity, Company, User, Offer) {
        $scope.company = entity;
        $scope.load = function (id) {
            Company.get({id: id}, function(result) {
                $scope.company = result;
            });
        };
        var unsubscribe = $rootScope.$on('jobvacancyApp:companyUpdate', function(event, result) {
            $scope.company = result;
        });
        $scope.$on('$destroy', unsubscribe);

    });
