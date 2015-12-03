'use strict';

angular.module('jobvacancyApp').controller('OfferDialogController',
    ['$scope', '$stateParams', '$modalInstance', 'entity', 'Offer', 'User',
        function($scope, $stateParams, $modalInstance, entity, Offer, User) {

        $scope.offer = entity;
        $scope.users = User.query();
        $scope.load = function(id) {
            Offer.get({id : id}, function(result) {
                $scope.offer = result;
            });
        };

        var onSaveFinished = function (result) {
            $scope.$emit('jobvacancyApp:offerUpdate', result);
            $modalInstance.close(result);
        };

        $scope.save = function () {
            if ($scope.offer.id != null) {
                Offer.update($scope.offer, onSaveFinished);
            } else {
                Offer.save($scope.offer, onSaveFinished);
            }
        };

        $scope.clear = function() {
            $modalInstance.dismiss('cancel');
        };
}]);
