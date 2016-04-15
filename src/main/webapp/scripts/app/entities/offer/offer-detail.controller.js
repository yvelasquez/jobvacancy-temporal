'use strict';

angular.module('jobvacancyApp')
    .controller('OfferDetailController', function ($scope, $rootScope, $stateParams, entity, Offer, User, Application) {
        $scope.offer = entity.offer;
        $scope.applications = entity.applications;
        $scope.load = function (id) {
            Offer.get({id: id}, function(result) {
                $scope.offer = result;
            });
            Application.get({id: id}, function(result) {
                $scope.applications = result;
            });
        };
        var unsubscribe = $rootScope.$on('jobvacancyApp:offerUpdate', function(event, result) {
            $scope.offer = result;
        });
        $scope.$on('$destroy', unsubscribe);

    });
