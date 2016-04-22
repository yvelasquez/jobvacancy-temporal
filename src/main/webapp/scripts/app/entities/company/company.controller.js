'use strict';

angular.module('jobvacancyApp')
    .controller('CompanyController', function ($scope, Company) {
        $scope.companys = [];
        $scope.loadAll = function() {
            Company.query(function(result) {
               $scope.companys = result;
            });
        };
        $scope.loadAll();

        $scope.delete = function (id) {
            Company.get({id: id}, function(result) {
                $scope.company = result;
                $('#deleteCompanyConfirmation').modal('show');
            });
        };

        $scope.confirmDelete = function (id) {
            Company.delete({id: id},
                function () {
                    $scope.loadAll();
                    $('#deleteCompanyConfirmation').modal('hide');
                    $scope.clear();
                });
        };

        $scope.refresh = function () {
            $scope.loadAll();
            $scope.clear();
        };

        $scope.clear = function () {
            $scope.company = {
                name: null,
                reputation: null,
                registrationDate: null,
                id: null
            };
        };
    });
