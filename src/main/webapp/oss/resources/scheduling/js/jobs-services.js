'use strict';

/* Services */


// Demonstrate how to register services
// In this case it is a simple value service.
angular.module('myApp.services', []).
  value('version', '0.1');

angular.module('jobs.services', ['ngResource']).
    factory(
        'Job', 
        function($resource){
            return $resource(
                'all-jobs', 
                {}, 
                {
                    find: {method:'GET', params:{}, isArray:true}
                }
            );
        }
    );

angular.module('jobDescriptors.services', ['ngResource']).
    factory(
        'JobDescriptor', 
        function($resource){
            return $resource(
                'all-job-descriptors', 
                {}, 
                {
                    find: {method:'GET', params:{}, isArray:true}
                }
            );
        }
    );
