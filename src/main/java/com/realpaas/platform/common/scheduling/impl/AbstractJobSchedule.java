/**
 * Copyright (c) 2012, RealPaaS Technologies, Ltd. All rights reserved.
 */
package com.realpaas.platform.common.scheduling.impl;

import com.realpaas.platform.common.scheduling.JobSchedule;
import com.realpaas.platform.common.scheduling.JobScheduler;
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
public abstract class AbstractJobSchedule implements JobSchedule{

    private JobScheduler jobScheduler;
    
    private ScheduledJobState state;
    
    private ScheduledJobStore store;
    
    protected JobScheduler getJobScheduler() {
        return jobScheduler;
    }

    public void setJobScheduler(JobScheduler jobScheduler) {
        this.jobScheduler = jobScheduler;
    }

    protected ScheduledJobState getState() {
        return state;
    }

    public void setState(ScheduledJobState state) {
        this.state = state;
    }

    protected ScheduledJobStore getStore() {
        return store;
    }

    public void setStore(ScheduledJobStore store) {
        this.store = store;
    }

    @Override
    public String getId() {
        return getState().getId();
    }

    @Override
    public String getName() {
        return getState().getJob().getName();
    }

    @Override
    public void start() {
        boolean schedulerStarted = getJobScheduler().isStarted();
        if( !schedulerStarted ){
            state.setStarted( false );
            return;
        }
        
        ScheduledJobState dbState = store.load( getId() );
        boolean started = isStarted();
        if( dbState.isStarted() && started ){
            return;
        }
        else if( dbState.isStarted() && !started ){
            getJobScheduler().resumeJob( getId() );
            return;
        }
        else if( !dbState.isStarted() && started ){
            state = getStore().setStarted( getId(), true );
            return;
        }
        else if( !dbState.isStarted() && !started ){
            state = getStore().setStarted( getId(), true );
            getJobScheduler().resumeJob( getId() );
            return;
        }
    }

    @Override
    public void stop() {
        boolean schedulerStarted = getJobScheduler().isStarted();
        if( !schedulerStarted ){
            state.setStarted( false );
            return;
        }
        
        ScheduledJobState dbState = store.load( getId() );
        boolean started = isStarted();
        if( dbState.isStarted() && started ){
            state = getStore().setStarted( getId(), false );
            getJobScheduler().pauseJob( getId() );
            return;
        }
        else if( dbState.isStarted() && !started ){
            state = getStore().setStarted( getId(), false );
            return;
        }
        else if( !dbState.isStarted() && started ){
            getJobScheduler().pauseJob( getId() );
            return;
        }
        else if( !dbState.isStarted() && !started ){
            return;
        }
    }

    @Override
    public void refresh() {
        boolean schedulerReady = getJobScheduler().isReady();
        if( !schedulerReady ){
            state.setStarted( false );
            return;
        }
        
        ScheduledJobState dbState = store.load( getId() );
        boolean started = isStarted();
        if( getJobScheduler().jobExists( getId() ) ){
            boolean scheduleChanged = dbState.getSchedule().getScheduleVersion() > state.getSchedule().getScheduleVersion();
            if( dbState.isStarted() && started ){
                if( scheduleChanged ){
                    getJobScheduler().reschedule( dbState );
                }
            }
            else if( dbState.isStarted() && !started ){
                if( scheduleChanged ){
                    getJobScheduler().reschedule( dbState );
                }
                else{
                    getJobScheduler().schedule( dbState );
                }
            }
            else if( !dbState.isStarted() && started ){
                getJobScheduler().pauseJob( getId() );
            }
            else if( !dbState.isStarted() && !started ){
                
            }
        }
        else{
            if( dbState.isStarted() ){
                getJobScheduler().schedule( dbState );
            }
        }
        
        state = dbState;
    }

    @Override
    public boolean isStarted() {
        return getState().isStarted();
    }

}