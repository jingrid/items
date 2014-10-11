/**
 * Copyright (c) 2012, RealPaaS Technologies, Ltd. All rights reserved.
 */
package com.realpaas.platform.common.scheduling.impl;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.Date;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;

import com.realpaas.platform.common.scheduling.Job;
import com.realpaas.platform.common.scheduling.Job.JobContext;
import com.realpaas.platform.common.scheduling.impl.store.ExectionRoundStore;
import com.realpaas.platform.common.scheduling.impl.store.ExecutionLogStore;
import com.realpaas.platform.common.scheduling.state.ExecutionLogState;
import com.realpaas.platform.common.scheduling.state.ExecutionRoundState;
import com.realpaas.platform.common.scheduling.state.type.ExecutionStatus;
import com.realpaas.platform.common.scheduling.state.type.ExecutionType;
import com.realpaas.platform.time.TimeGenerator;

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
 * 	Nov 26, 2012	henryleu	Create the class
 * </dd>
 * 
 * </dl>
 * @author	henryleu Email/MSN: hongli_leu@126.com
 */
public abstract class AbstractFrameworkJob implements FrameworkJob {

    protected Log logger = LogFactory.getLog( getClass() );
    
    @Autowired
    protected TimeGenerator timeGenerator;
    
    private ExectionRoundStore exectionRoundStore;
    
    private ExecutionLogStore executionLogStore;
    
    @Autowired
    public void setExectionRoundStore(ExectionRoundStore exectionRoundStore) {
        this.exectionRoundStore = exectionRoundStore;
    }
    
    @Autowired
    public void setExecutionLogStore(ExecutionLogStore executionLogStore) {
        this.executionLogStore = executionLogStore;
    }

    @Override
    public void execute(FrameworkJobContext context) {
        Job job = (Job)context.getJob().getBeanObject();
        Object params = context.getJob().getParam();
        boolean success = false;

        /*
         * Persist round object
         */
        String scheduledJobId = context.getScheduledJobId();
        ExecutionRoundState roundState = exectionRoundStore.instantiate();
        roundState.setStatus( ExecutionStatus.Running );
        roundState.setRunTimes( (short)1 );
        roundState.setScheduledFireTime( timeGenerator.currentTime() );
        String roundId = exectionRoundStore.save( roundState, scheduledJobId );
        
        /*
         * Init log object
         */
        Date startTime = timeGenerator.currentTime();
        Date endTime = null;
        ExecutionLogState executionLogState = executionLogStore.instantiate();
        executionLogState.setType( ExecutionType.Scheduled );
        executionLogState.setStartTime( startTime );
        
        try{
            JobContext jobCtx = new JobContext();
            jobCtx.setJobId( context.getJobId() );
            jobCtx.setJobRoundId( roundId );
            jobCtx.setJobParams( params );
            job.run( jobCtx );
            
            success = true;
        }
        catch(Exception e){
            success = false;
            StringWriter errors = new StringWriter();
            e.printStackTrace( new PrintWriter( errors ) );
            executionLogState.setErrorMessage( errors.toString() );
        }

        ExecutionStatus status = success ? ExecutionStatus.Successful : ExecutionStatus.Failed;
        
        /*
         * Create log object after finishing running the job
         */
        executionLogState.setStatus( status );
        endTime = timeGenerator.currentTime();
        executionLogState.setEndTime( endTime );
        int timeSpent = (int)((endTime.getTime()-startTime.getTime())/1000L); //the seconds it took to run the job
        executionLogState.setTimeSpent( timeSpent );
        String logId = executionLogStore.save( executionLogState, roundId );
     
        /*
         * Update round object after finishing running the job
         */
        exectionRoundStore.update( roundId, logId );
    }

    @Override
    public void interrupt() {
        // TODO It is supposed to be ignore in this implementation
        Thread.currentThread().interrupt();
    }

}
