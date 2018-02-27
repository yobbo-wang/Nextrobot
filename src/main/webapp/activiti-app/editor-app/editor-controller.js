'use strict';

angular.module('activitiModeler')
.controller('EditorUnsavedChangesPopupCrtl', ['$rootScope', '$scope', '$http', '$location', '$window', function ($rootScope, $scope, $http, $location, $window) {
   
	$scope.ok = function () {
		if ($scope.handleResponseFunction) {
			$scope.handleResponseFunction(true);

            // Also clear any 'onbeforeunload', added by oryx
            $window.onbeforeunload = undefined;
		}
		$scope.$hide();
	};

	$scope.cancel = function () {
		if ($scope.handleResponseFunction) {
			$scope.handleResponseFunction(false);
		}
        $scope.$hide();
	};
}]);

activitiModule
.directive('autoFocus', ['$timeout', '$parse', function($timeout, $parse) {
    return {
        restrict: 'AC',
        compile: function($element, attr) {

            return function(_scope, _element, _attrs) {
                var firstChild = (_attrs.focusFirstChild !== undefined);
                $timeout(function () {
                    if (firstChild) {
                        // look for first input-element in child-tree and focus that
                        var inputs = _element.find('input');
                        if (inputs && inputs.length > 0) {
                            inputs[0].focus();
                        }
                    } else {
                        // Focus element where the directive is put on
                        _element[0].focus();
                    }
                }, 100);
            }
        }
    };
}]);
