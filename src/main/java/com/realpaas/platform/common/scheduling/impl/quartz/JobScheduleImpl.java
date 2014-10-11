/**
 * Copyright (c) 2012, RealPaaS Technologies, Ltd. All rights reserved.
 */
package com.realpaas.platform.common.scheduling.impl.quartz;

import com.realpaas.platform.common.scheduling.JobScheduler;
import com.realpaas.platform.common.scheduling.impl.AbstractJobSchedule;
import com.realpaas.platform.common.scheduling.impl.store.ScheduledJobStore;
import com.realpaas.platform.common.scheduling.state.ScheduledJobState;

/**
 * <p>
 * 
 * <dl>
 * <dt><b>Examples:</b></dt>
 * <p>
 * <pre>
 * 
 * </pre>
 * 
 * <p><dt><b>Immutability:</b></dt> 
 * <dd>
 * 	<b>IMMUTABLE</b> and <b>MUTABLE</b>
 * </dd>
 * 
 * <p><dt><b>Thread Safety:</b></dt> 
 * <dd>
 * 	<b>NOT-THREAD-SAFE</b> and <b>NOT-APPLICABLE</b> (for it will never be used on multi-thread occasion.)
 * </dd>
 * 
 * <p><dt><b>Serialization:</b></dt>
 * <dd>
 * 	<b>NOT-SERIALIIZABLE</b> and <b>NOT-APPLICABLE</b> (for it have no need to be serializable.)
 * </dd>
 * 
 * <p><dt><b>Design Patterns:</b></dt>
 * <dd>
 * 	
 * </dd>
 * 
 * <p><dt><b>Change History:</b></dt>
 * <dd>
 * 	Date		Author		Action
 * </dd>
 * <dd>
 * 	Nov 16, 2012	henryleu	Create the class
 * </dd>
 * 
 * </dl>
 * @author	henryleu Email/MSN: hongli_leu@126.com
 */
public class JobScheduleImpl extends AbstractJobSchedule{

    private JobScheduleImpl() {
        super();
    }
    
    public static class Builder{
        
        private ScheduledJobState state;
        
        private ScheduledJobStore store;
        
        private JobScheduler jobScheduler;
        
        private Builder(JobScheduler jobScheduler, ScheduledJobStore store){
            this.jobScheduler = jobScheduler;
            this.store = store;
        }
        
        public static Builder newInstance(JobScheduler jobScheduler, ScheduledJobStore store){
            return new Builder( jobScheduler, store );
        }
        
        public Builder setState(ScheduledJobState state){
            this.state = state;
            return this;
        }
        
        public JobScheduleImpl build(){
            JobScheduleImpl instance = null;
            if(state==null){
                throw new IllegalStateException();
            }
            
            if(store==null){
                throw new IllegalStateException();
            }
            
            if(jobScheduler==null){
                throw new IllegalStateException();
            }
            
            instance = new JobScheduleImpl();
            instance.setState( state );
            instance.setStore( store );
            instance.setJobScheduler( jobScheduler );
            
            state = null;
            return instance;
        }
    }
}

