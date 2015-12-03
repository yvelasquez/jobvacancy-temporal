'use strict';

angular.module('jobvacancyApp')
    .controller('OfferDetailController', function ($scope, $rootScope, $stateParams, entity, Offer, User) {
        $scope.offer = entity;
        $scope.load = function (id) {
            Offer.get({id: id}, function(result) {
                $scope.offer = result;
            });
        };
        var unsubscribe = $rootScope.$on('jobvacancyApp:offerUpdate', function(event, result) {
            $scope.offer = result;
        });
        $scope.$on('$destroy', unsubscribe);

    });
