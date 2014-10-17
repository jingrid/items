/**
 * Copyright (c) 2014, JingGe Technologies, Ltd. All rights reserved.
 */
package com.jingge.platform.common.scheduling.impl;

import java.util.Iterator;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ScheduledThreadPoolExecutor;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.scheduling.TaskScheduler;
import org.springframework.scheduling.concurrent.ConcurrentTaskScheduler;

import com.jingge.platform.common.scheduling.JobSchedule;
import com.jingge.platform.common.scheduling.JobScheduleSchemer;
import com.jingge.platform.common.scheduling.JobScheduler;
import com.jingge.platform.common.scheduling.impl.quartz.JobScheduleImpl;
import com.jingge.platform.common.scheduling.impl.store.ScheduledJobSchemeStore;
import com.jingge.platform.common.scheduling.impl.store.ScheduledJobStore;
import com.jingge.platform.common.scheduling.state.ScheduledJobState;
import com.jingge.platform.common.scheduling.state.SchemeState;

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
 * 	Nov 15, 2012	henryleu	Create the class
 * </dd>
 * 
 * </dl>
 * @author	henryleu Email/MSN: hongli_leu@126.com
 */
public abstract class AbstractJobScheduleSchemer implements JobScheduleSchemer {

    private static final int MONITORING_JOB_POOL_SIZE = 2;
    
    private static final long MONITORING_INTERVAL = 60000; // one minute

    private Log logger = LogFactory.getLog( getClass() );
    
    protected final ConcurrentMap<String, JobSchedule> jobScheduleMap = new ConcurrentHashMap<String, JobSchedule>();

    protected String id = "default";
    
    protected SchemeState state;
    
    protected ScheduledJobSchemeStore store;
    
    private ScheduledJobStore scheduledJobStore;

    protected JobScheduler jobScheduler;
    
    protected JobScheduleImpl.Builder jobScheduleBuilder;
    
    protected int delayedSecondsToStart = 0;
    
    private TaskScheduler monitoringJobScheduler;
    
    private ScheduledExecutorService monitoringJobExecutor;
    
    private long monitoringInterval = MONITORING_INTERVAL;
    
    protected ScheduledJobSchemeStore getStore() {
        return store;
    }

    public void setStore(ScheduledJobSchemeStore jobScheduleSchemeStore) {
        this.store = jobScheduleSchemeStore;
    }

    public ScheduledJobStore getScheduledJobStore() {
        return scheduledJobStore;
    }

    public void setScheduledJobStore(ScheduledJobStore scheduledJobStore) {
        this.scheduledJobStore = scheduledJobStore;
    }

    protected JobScheduler getJobScheduler() {
        return jobScheduler;
    }

    public void setJobScheduler(JobScheduler jobScheduler) {
        this.jobScheduler = jobScheduler;
    }

    protected int getDelayedSecondsToStart() {
        return delayedSecondsToStart;
    }

    public void setDelayedSecondsToStart(int delayedSecondsToStart) {
        this.delayedSecondsToStart = delayedSecondsToStart;
    }

    protected long getMonitoringInterval() {
        return monitoringInterval;
    }

    public void setMonitoringInterval(long monitoringInterval) {
        this.monitoringInterval = monitoringInterval;
    }

    @Override
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    @Override
    public String getName() {
        return state.getName();
    }
    
    @Override
    public synchronized boolean isStarted() {
        return state.isStarted();
    }

    @Override
    public JobSchedule getJobSchedule(String id) {
        return jobScheduleMap.get( id );
    }

    public void init(){
        jobScheduleBuilder = JobScheduleImpl.Builder.newInstance( getJobScheduler(), scheduledJobStore );
        
        /*
         * Load and Check JobScheduleScheme's State from back-end store (DB)
         */
        state = store.load( getId() );
        if( state==null ){
            throw new IllegalStateException( "Fail to load ScheduledJobSchemeState object [" + getId() + "]" );
        }
        
        /*
         * Skip schedule jobs if scheme is set to stopped
         */
        if( !state.isStarted() ){
            return;
        }
        
        /*
         * Load and initialize all the JobSchedules of the scheme
         */
        List<ScheduledJobState> scheduledJobStateList = getStore().listScheduledJobs( getId() );
        for(Iterator<ScheduledJobState> iter = scheduledJobStateList.iterator(); iter.hasNext(); ){
            initJobSchedule( iter.next() );
        }
    }
    
    protected JobSchedule initJobSchedule(ScheduledJobState jobScheduleState){
        JobSchedule jobSchedule = jobScheduleBuilder.setState( jobScheduleState ).build();
        jobSchedule.refresh();
        jobScheduleMap.put( jobSchedule.getId(), jobSchedule );
        return jobSchedule;
    }
    
    @Override
    public synchronized void start() {
        /*
         * Load and Check JobScheduleScheme's State from back-end store (DB)
         */
        SchemeState state = store.load( getId() );
        if( state==null ){
            throw new IllegalStateException( "Fail to load JobScheduleSchemeState object [" + getId() + "]" );
        }
        
        boolean serviceStarted = jobScheduler.isStarted();
        boolean storeStarted = state.isStarted();
        
        if( storeStarted && serviceStarted ){
            return;
        }
        else if( storeStarted && !serviceStarted ){
            syncState( state );
            doStart();
            return;
        }
        else if( !storeStarted && !serviceStarted ){
            startAndSyncState();
            doStart();
            return;
        }
        else if( !storeStarted && serviceStarted ){
            startAndSyncState();
            return;
        }
    }
    
    @Override
    public synchronized void stop() {
        /*
         * Load and Check JobScheduleScheme's State from back-end store (DB)
         */
        SchemeState state = store.load( getId() );
        if( state==null ){
            throw new IllegalStateException( "Fail to load JobScheduleSchemeState object [" + getId() + "]" );
        }
        
        boolean serviceStarted = jobScheduler.isStarted();
        boolean storeStarted = state.isStarted();
        
        if( storeStarted && serviceStarted ){
            stopAndSyncState();
            doStop();
            return;
        }
        else if( storeStarted && !serviceStarted ){
            stopAndSyncState();
            return;
        }
        else if( !storeStarted && !serviceStarted ){
            return;
        }
        else if( !storeStarted && serviceStarted ){
            syncState( state );
            doStop();
            return;
        }
    }

    @Override
    public synchronized void refresh() {
        /*
         * Load and Check JobScheduleScheme's State from back-end store (DB)
         */
        SchemeState state = store.load( getId() );
        if( state==null ){
            throw new IllegalStateException( "Fail to load JobScheduleSchemeState object [" + getId() + "]" );
        }
        
        boolean serviceStarted = jobScheduler.isStarted();
        boolean storeStarted = state.isStarted();
        
        if( storeStarted != serviceStarted ){
            syncState( state );
            if( storeStarted ){
                doStart();
            }
            else{
                doStop();
            }
            return;
        }
    }
    
    protected void doStart(){
        jobScheduler.start();
    }
    
    protected void doStop(){
        jobScheduler.standby();
    }
    
    protected void syncState(SchemeState state){
        state.setState( state );
    }

    protected void startAndSyncState(){
        SchemeState state = store.setStarted( getId(), true );
        state.setState( state );
    }

    protected void stopAndSyncState(){
        SchemeState state = store.setStarted( getId(), false );
        state.setState( state );
    }
    
    /**
     * Start Monitoring job for syncing between the current scheduler in the current app server with DB.
     */
    protected void startMonitoringJob() {
        monitoringJobExecutor = new ScheduledThreadPoolExecutor( MONITORING_JOB_POOL_SIZE );
        monitoringJobScheduler = new ConcurrentTaskScheduler( monitoringJobExecutor );
        try {
            monitoringJobScheduler.scheduleWithFixedDelay( new ErrorHandlingTask(), getMonitoringInterval() );
        }
        catch (Exception e) {
            logger.error( "Fail to schedule monitoring job", e );
        }
    }

    class ErrorHandlingTask implements Runnable{

        @Override
        public void run() {
            logger.debug( "ErrorHandlingTask is running" );
        }
        
    }
    
}
