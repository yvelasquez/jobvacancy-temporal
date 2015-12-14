'use strict';

angular.module('jobvacancyApp')
    .controller('PublicOfferController', function ($scope, PublicOffer, ParseLinks) {
        $scope.jobOffers = [];
        $scope.loadAll = function() {
            PublicOffer.query(function(result) {
                $scope.jobOffers = result;
            });
        };
        $scope.loadAll();
    });
