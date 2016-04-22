'use strict';

describe('Company Detail Controller', function() {
    var $scope, $rootScope;
    var MockEntity, MockCompany, MockUser, MockOffer;
    var createController;

    beforeEach(inject(function($injector) {
        $rootScope = $injector.get('$rootScope');
        $scope = $rootScope.$new();
        MockEntity = jasmine.createSpy('MockEntity');
        MockCompany = jasmine.createSpy('MockCompany');
        MockUser = jasmine.createSpy('MockUser');
        MockOffer = jasmine.createSpy('MockOffer');
        

        var locals = {
            '$scope': $scope,
            '$rootScope': $rootScope,
            'entity': MockEntity ,
            'Company': MockCompany,
            'User': MockUser,
            'Offer': MockOffer
        };
        createController = function() {
            $injector.get('$controller')("CompanyDetailController", locals);
        };
    }));


    describe('Root Scope Listening', function() {
        it('Unregisters root scope listener upon scope destruction', function() {
            var eventType = 'jobvacancyApp:companyUpdate';

            createController();
            expect($rootScope.$$listenerCount[eventType]).toEqual(1);

            $scope.$destroy();
            expect($rootScope.$$listenerCount[eventType]).toBeUndefined();
        });
    });
});
