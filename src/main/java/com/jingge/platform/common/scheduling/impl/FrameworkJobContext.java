/**
 * Copyright (c) 2014, JingGe Technologies, Ltd. All rights reserved.
 */
package com.jingge.platform.common.scheduling.impl;

import java.util.Date;

import com.jingge.platform.common.scheduling.state.JobState;
import com.jingge.platform.common.scheduling.state.ScheduledJobState;


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
public class FrameworkJobContext {
    
    private String jobId;
    
    private String scheduledJobId;
    
    private ScheduledJobState scheduledJob;
    
    private JobState job;
    
    private Date scheduledFireTime;
    
    private Date fireTime;
    
    public String getJobId() {
        return jobId;
    }

    public void setJobId(String jobId) {
        this.jobId = jobId;
    }

    public String getScheduledJobId() {
        return scheduledJobId;
    }

    public void setScheduledJobId(String scheduledJobId) {
        this.scheduledJobId = scheduledJobId;
    }

    public ScheduledJobState getScheduledJob() {
        return scheduledJob;
    }

    public void setScheduledJob(ScheduledJobState scheduledJob) {
        this.scheduledJob = scheduledJob;
    }

    public JobState getJob() {
        return job;
    }

    public void setJob(JobState job) {
        this.job = job;
    }

    public Date getScheduledFireTime() {
        return scheduledFireTime;
    }

    public void setScheduledFireTime(Date scheduledFireTime) {
        this.scheduledFireTime = scheduledFireTime;
    }

    public Date getFireTime() {
        return fireTime;
    }

    public void setFireTime(Date fireTime) {
        this.fireTime = fireTime;
    }
}
