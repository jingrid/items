'use strict';


// Declare app level module which depends on filters, and services
angular.module('jobs', ['jobs.services', 'jobDescriptors.services']).
  config(['$routeProvider', function($routeProvider) {
    $routeProvider.when('/jobs', {templateUrl: '../resources/scheduling/html/job-list.html', controller: JobListCtrl});
    $routeProvider.when('/job-list', {templateUrl: '../resources/scheduling/html/job-list.html', controller: JobListCtrl});
    $routeProvider.when('/job-new', {templateUrl: '../resources/scheduling/html/job-new.html', controller: JobNewCtrl});
    $routeProvider.when('/job-edit', {templateUrl: '../resources/scheduling/html/job-edit.html', controller: JobEditCtrl});
    $routeProvider.otherwise({redirectTo: '/jobs'});
  }]);
