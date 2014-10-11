
/**
 * Copyright (c) 2011, Lenovo Group, Ltd. All rights reserved.
 */
package com.realpaas.platform.common.scheduling.impl.quartz;

import org.quartz.JobDataMap;
import org.quartz.Scheduler;
import org.quartz.impl.JobDetailImpl;

import com.realpaas.platform.common.scheduling.impl.FrameworkJob;
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
public class AdaptiveJobDetail extends JobDetailImpl {

    public static final String FRAMEWORK_JOB_KEY = "frameworkJob";

    public static final String SCHEDULED_JOB_KEY = "scheduledJob";

    private static final long serialVersionUID = -7299780003219442952L;
    
    public AdaptiveJobDetail(ScheduledJobState state, FrameworkJob frameworkJob){
        super();
        setName( state.getId() );
        setJobClass( AdaptiveJob.class );
        setScheduledJob( state );
        setFrameworkJob( frameworkJob ); //custom method
        setGroup( Scheduler.DEFAULT_GROUP );
        setDurability(false);
    }
    
    private void setFrameworkJob(FrameworkJob frameworkJob){
        getJobDataMap().put(FRAMEWORK_JOB_KEY, frameworkJob);
    }
    
    private void setScheduledJob(ScheduledJobState state){
        getJobDataMap().put(SCHEDULED_JOB_KEY, state);
    }
    
    public static FrameworkJob getFrameworkJob(JobDataMap map){
        return (FrameworkJob)map.get(FRAMEWORK_JOB_KEY);
    }

    public static ScheduledJobState getScheduledJob(JobDataMap map){
        return (ScheduledJobState)map.get(SCHEDULED_JOB_KEY);
    }
    
}