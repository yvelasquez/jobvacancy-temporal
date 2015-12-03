'use strict';

angular.module('jobvacancyApp')
    .controller('OfferController', function ($scope, Offer) {
        $scope.offers = [];
        $scope.loadAll = function() {
            Offer.query(function(result) {
               $scope.offers = result;
            });
        };
        $scope.loadAll();

        $scope.delete = function (id) {
            Offer.get({id: id}, function(result) {
                $scope.offer = result;
                $('#deleteOfferConfirmation').modal('show');
            });
        };

        $scope.confirmDelete = function (id) {
            Offer.delete({id: id},
                function () {
                    $scope.loadAll();
                    $('#deleteOfferConfirmation').modal('hide');
                    $scope.clear();
                });
        };

        $scope.refresh = function () {
            $scope.loadAll();
            $scope.clear();
        };

        $scope.clear = function () {
            $scope.offer = {
                title: null,
                location: null,
                description: null,
                id: null
            };
        };
    });
