'use strict';


// Declare app level module which depends on filters, and services
angular.module('jobs', []).
  config(['$routeProvider', function($routeProvider) {
    $routeProvider.when('/jobs', {templateUrl: 'job-list.html', controller: JobListCtrl});
    $routeProvider.when('/job-list', {templateUrl: 'job-list.html', controller: JobListCtrl});
    $routeProvider.when('/job-create', {templateUrl: 'job-create.html', controller: MyCtrl1});
    $routeProvider.when('/job-update', {templateUrl: 'job-update.html', controller: MyCtrl2});
    $routeProvider.otherwise({redirectTo: '/jobs'});
  }]);
