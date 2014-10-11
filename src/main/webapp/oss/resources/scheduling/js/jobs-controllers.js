'use strict';

/* Controllers */


function JobListCtrl($scope, Job, JobDescriptor) {
    $scope.jobs = Job.find();
    $scope.jobDescriptors = JobDescriptor.find();
    $scope.newJob = function(){
        location.href = '#/job-new';
    }
}
JobListCtrl.$inject = ['$scope', 'Job', 'JobDescriptor'];

function JobNewCtrl() {}
JobNewCtrl.$inject = [];


function JobEditCtrl() {
}
JobEditCtrl.$inject = [];
