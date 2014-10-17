/**
 * Copyright (c) 2014, JingGe Technologies, Ltd. All rights reserved.
 */
package com.jingge.platform.common.scheduling.impl.quartz;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.quartz.CalendarIntervalScheduleBuilder;
import org.quartz.CronScheduleBuilder;
import org.quartz.JobDetail;
import org.quartz.JobKey;
import org.quartz.ScheduleBuilder;
import org.quartz.Scheduler;
import org.quartz.SchedulerException;
import org.quartz.SimpleScheduleBuilder;
import org.quartz.Trigger;
import org.quartz.TriggerBuilder;
import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;

import com.jingge.platform.common.scheduling.Job;
import com.jingge.platform.common.scheduling.JobScheduler;
import com.jingge.platform.common.scheduling.impl.FrameworkJob;
import com.jingge.platform.common.scheduling.state.ScheduleState;
import com.jingge.platform.common.scheduling.state.ScheduledJobState;
import com.jingge.platform.common.scheduling.state.type.ScheduleMode;

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
public class JobSchedulerImpl implements JobScheduler, ApplicationContextAware {

    private final Log logger = LogFactory.getLog( getClass() );
    
    private Scheduler scheduler;
    
    private ApplicationContext ac;
    
    private FrameworkJob frameworkJob;
    
    public void setScheduler(Scheduler scheduler){
        this.scheduler = scheduler;
    }

    public Scheduler getScheduler() {
        return scheduler;
    }

    public FrameworkJob getFrameworkJob() {
        return frameworkJob;
    }

    public void setFrameworkJob(FrameworkJob frameworkJob) {
        this.frameworkJob = frameworkJob;
    }

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException{
        this.ac = applicationContext;
    }

    @Override
    public void schedule(ScheduledJobState state) {
        try {
            getScheduler().scheduleJob( createJobDetail( state ), createTrigger( state ) );
        }
        catch ( SchedulerException e ) {
            logger.error( "Fail to schedule job [" + state.getId() + ": " + state.getJob().getName() + "]", e );
            throw new RuntimeException( e );
        }
    }

    @Override
    public void reschedule(ScheduledJobState state) {
        try {
            getScheduler().deleteJob( new JobKey( state.getId() ) );
            getScheduler().scheduleJob( createJobDetail( state ), createTrigger( state ) );
        }
        catch ( SchedulerException e ) {
            logger.error( "Fail to reschedule job schedule [" + state.getId() + ": " + state.getJob().getName() + "]", e );
            throw new RuntimeException( e );
        }
    }

    @Override
    public boolean jobExists(String jobId) {
        boolean existed = false;
        try {
            existed = getScheduler().checkExists( new JobKey( jobId ) );
        }
        catch ( SchedulerException e ) {
            logger.error( "Fail to check job schedule [" + jobId + "] existence", e );
            throw new RuntimeException( e );
        }
        return existed;
    }

    @Override
    public void start() {
        try {
            getScheduler().start();
        }
        catch ( SchedulerException e ) {
            logger.error( "Fail to start quartz scheduler", e );
            throw new RuntimeException( e );
        }
    }

    @Override
    public void start(int delayedSeconds) {
        try {
            getScheduler().startDelayed( delayedSeconds );
        }
        catch ( SchedulerException e ) {
            logger.error( "Fail to start quartz scheduler", e );
            throw new RuntimeException( e );
        }
    }

    @Override
    public void shutdown() {
        try {
            getScheduler().shutdown();
        }
        catch ( SchedulerException e ) {
            logger.error( "Fail to shutdown quartz scheduler", e );
            throw new RuntimeException( e );
        }
    }

    @Override
    public void standby() {
        try {
            getScheduler().standby();
        }
        catch ( SchedulerException e ) {
            logger.error( "Fail to pause quartz scheduler", e );
            throw new RuntimeException( e );
        }
    }

    @Override
    public boolean isStarted() {
        boolean started = false;
        try {
            started = getScheduler().isStarted() && !getScheduler().isInStandbyMode();
        }
        catch ( SchedulerException e ) {
            logger.error( "Fail to check quartz scheduler's status", e );
            throw new RuntimeException( e );
        }
        return started;
    }

    @Override
    public boolean isReady() {
        boolean ready = true;
        try {
            ready = !getScheduler().isShutdown();
        }
        catch ( SchedulerException e ) {
            logger.error( "Fail to check if quartz scheduler's started or in standby mode", e );
            throw new RuntimeException( e );
        }
        return ready;
    }

    @Override
    public void pauseJob(String jobId) {
        try {
            getScheduler().pauseJob( new JobKey( jobId ) );
        }
        catch ( SchedulerException e ) {
            logger.error( "Fail to pause job [" + jobId + "]", e );
            throw new RuntimeException( e );
        }
    }

    @Override
    public void resumeJob(String jobId) {
        try {
            getScheduler().resumeJob( new JobKey( jobId ) );
        }
        catch ( SchedulerException e ) {
            logger.error( "Fail to resume job [" + jobId + "]", e );
            throw new RuntimeException( e );
        }
    }
    
    /**
     * Create Quart JobDetail object from JobScheduleState object, the instance of which is of AdaptiveJobDetail class 
     * that can support get job object (which well injected properties in Spring Application Context)
     * instead of using getJobclass
     * @param state JobScheduleState object loaded from database
     * @return JobDetail object
     */
    protected JobDetail createJobDetail(ScheduledJobState state){
        Object beanObject = ac.getBean( state.getJob().getBeanName() );
        if( beanObject==null || !(beanObject instanceof Job ) ){
            throw new RuntimeException();
        }
        state.getJob().setBeanObject( beanObject );
        return new AdaptiveJobDetail( state, frameworkJob );
    }
    
    /**
     * Create Quart Trigger object from JobScheduleState object
     * @param state
     * @return
     */
    protected Trigger createTrigger(ScheduledJobState state){
        Trigger newTrigger = null;
        TriggerBuilder<Trigger> triggerBuilder = null;
        ScheduleBuilder<?> scheduleBuilder = null;

        /*
         * Create TriggerBuilder
         */
        ScheduleState scheduleState = state.getSchedule();
        ScheduleMode scheduleMode = scheduleState.getScheduleMode();
        triggerBuilder = TriggerBuilder.newTrigger()
            .withIdentity( state.getId() )
            .withPriority( Trigger.DEFAULT_PRIORITY )
            .withDescription( state.getJob().getName() );
        
        /*
         * Create ScheduleBuilder
         */
        if( ScheduleMode.CronExpression.equals( scheduleMode ) ){
            scheduleBuilder = CronScheduleBuilder.cronSchedule( scheduleState.getCronExpression() )
            .withMisfireHandlingInstructionIgnoreMisfires();
        }
        else if( ScheduleMode.RepeatInterval.equals( scheduleMode ) ){
            int runTimes = scheduleState.getRunTimes();
            if( ScheduleState.RUN_INFINITE_TIMES == runTimes ){
                scheduleBuilder = CalendarIntervalScheduleBuilder.calendarIntervalSchedule()
                .withIntervalInSeconds( (int)scheduleState.getInterval() )
                .withMisfireHandlingInstructionIgnoreMisfires();
            }
            else{
                scheduleBuilder = SimpleScheduleBuilder.simpleSchedule()
                .withIntervalInSeconds( (int)scheduleState.getInterval() )
                .withRepeatCount( scheduleState.getRunTimes()-1 )
                .withMisfireHandlingInstructionIgnoreMisfires();
            }
        }
        
        /*
         * Build Trigger object finally
         */
        newTrigger = triggerBuilder.withSchedule( scheduleBuilder ).build();
        
        return newTrigger;
    }
}
