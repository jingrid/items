/**
 * Copyright (c) 2012, RealPaaS Technologies, Ltd. All rights reserved.
 */
package com.realpaas.platform.common.scheduling.state;

import java.io.Serializable;

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
 * 	Mar 27, 2013	henryleu	Create the class
 * </dd>
 * 
 * </dl>
 * @author	henryleu Email/MSN: hongli_leu@126.com
 */
public class ScheduledJobState implements Serializable{

    private static final long serialVersionUID = -5884739606628164273L;

    private String id;
    
    private JobState job;

    private ScheduleState schedule;

    private boolean started;

    private int version;
    
    public ScheduledJobState() {
        super();
        job = new JobState();
        schedule = new ScheduleState();
    }

    public synchronized String getId() {
        return id;
    }

    public synchronized void setId(String id) {
        this.id = id;
    }

    public synchronized boolean isStarted() {
        return started;
    }

    public synchronized void setStarted(boolean started) {
        this.started = started;
    }

    public synchronized JobState getJob() {
        return job;
    }

    public synchronized void setJob(JobState job) {
        this.job = job;
    }

    public synchronized ScheduleState getSchedule() {
        return schedule;
    }

    public synchronized void setSchedule(ScheduleState schedule) {
        this.schedule = schedule;
    }

    public synchronized int getVersion() {
        return version;
    }
    
    public synchronized void setVersion(int version) {
        this.version = version;
    }
    
}
