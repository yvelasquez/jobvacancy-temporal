'use strict';

angular.module('jobvacancyApp').controller('CompanyDialogController',
    ['$scope', '$stateParams', '$modalInstance', 'entity', 'Company', 'User', 'Offer',
        function($scope, $stateParams, $modalInstance, entity, Company, User, Offer) {

        $scope.company = entity;
        $scope.users = User.query();
        $scope.offers = Offer.query();
        $scope.load = function(id) {
            Company.get({id : id}, function(result) {
                $scope.company = result;
            });
        };

        var onSaveFinished = function (result) {
            $scope.$emit('jobvacancyApp:companyUpdate', result);
            $modalInstance.close(result);
        };

        $scope.save = function () {
            if ($scope.company.id != null) {
                Company.update($scope.company, onSaveFinished);
            } else {
                Company.save($scope.company, onSaveFinished);
            }
        };

        $scope.clear = function() {
            $modalInstance.dismiss('cancel');
        };
}]);
