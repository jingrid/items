/**
 * Copyright (c) 2011, Lenovo Group, Ltd. All rights reserved.
 */
package com.realpaas.platform.common.scheduling.impl.quartz;

import java.util.Date;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.quartz.InterruptableJob;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.quartz.UnableToInterruptJobException;

import com.realpaas.platform.common.scheduling.impl.FrameworkJob;
import com.realpaas.platform.common.scheduling.impl.FrameworkJobContext;
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
 * 	Jun 21, 2011	henry leu	Create the class
 * </dd>
 * 
 * </dl>
 * @author	henry leu
 * @see			
 * @see			
 */
public class AdaptiveJob implements InterruptableJob {
    
    private Log logger = LogFactory.getLog( getClass() );
    
    private FrameworkJob frameworkJob;
    
    private ScheduledJobState scheduledJobState;
    
    private boolean bypassDelayedJob = false;
    
    private long ignoreMillisDelayed = 1000;
    
    public AdaptiveJob() {
        super();
    }

    @Override
    public void execute(JobExecutionContext context) throws JobExecutionException {
        if( bypassDelayedJob ){
            Date scheduledFireTime = context.getScheduledFireTime();
            Date fireTime = context.getFireTime();
            if(scheduledFireTime!=null && fireTime!=null ){
                long diff = fireTime.getTime()-scheduledFireTime.getTime();
                if(diff>ignoreMillisDelayed){
                    logger.warn( "Bypass the job instance of job \"" + 
                            context.getJobDetail().getKey().getName() + "\" at fire time: " + 
                            fireTime.toString());
                    return;
                }
            }
        }
        FrameworkJobContext ctx = prepareContext( context );
        frameworkJob.execute( ctx );
    }
    
    @Override
    public void interrupt() throws UnableToInterruptJobException {
        try{
            frameworkJob.interrupt();
            if( logger.isInfoEnabled() ){
                logger.info( "Scheduled Job \"" + scheduledJobState.getId() + "\"'s interruption request is sent." );
            }
        }
        catch(Exception e){
            logger.error( "Fail to interrupt scheduled job " + scheduledJobState.getId() );
            throw new UnableToInterruptJobException( e );
        }
    }

    private FrameworkJobContext prepareContext( JobExecutionContext context ){
        frameworkJob = AdaptiveJobDetail.getFrameworkJob( context.getMergedJobDataMap() );
        scheduledJobState = AdaptiveJobDetail.getScheduledJob( context.getMergedJobDataMap() );
        
        FrameworkJobContext ctx = new FrameworkJobContext();
        ctx.setJobId( scheduledJobState.getJob().getId() );
        ctx.setScheduledJobId( scheduledJobState.getId() );
        ctx.setScheduledJob( scheduledJobState );
        ctx.setJob( scheduledJobState.getJob() );
        ctx.setScheduledFireTime( context.getScheduledFireTime() );
        ctx.setFireTime( context.getFireTime() );
        return ctx;
    }
}
