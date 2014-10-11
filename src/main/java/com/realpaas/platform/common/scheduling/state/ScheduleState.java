/**
 * Copyright (c) 2012, RealPaaS Technologies, Ltd. All rights reserved.
 */
package com.realpaas.platform.common.scheduling.state;

import com.realpaas.platform.common.scheduling.state.type.ScheduleMode;



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
 * 	Nov 20, 2012	henryleu	Create the class
 * </dd>
 * 
 * </dl>
 * @author	henryleu Email/MSN: hongli_leu@126.com
 */
public class ScheduleState {

    public static final int RUN_INFINITE_TIMES = -1;
    
    private String id;
    
    private String name;
    
    private int scheduleVersion;
    
    private ScheduleMode scheduleMode;

    private int interval; //unit is second; it means: 1: repeat interval; 2: delay interval;
    
    private String cronExpression; //standard cron expression
    
    private int runTimes; //-1: infinite, others: times

    public synchronized String getId() {
        return id;
    }

    public synchronized void setId(String id) {
        this.id = id;
    }

    public synchronized String getName() {
        return name;
    }

    public synchronized void setName(String name) {
        this.name = name;
    }

    /**
     * a number indicates that schedule's version. once the schedule is updated in DB, 
     * the version will increase one (4 - >6). and scheduler instance in app server will
     * sync it and re-apply the schedule updated in DB periodically.
     * @return
     */
    public int getScheduleVersion(){
        return scheduleVersion;
    }

    public void setScheduleVersion(int version){
        scheduleVersion = version;
    }
    
    public ScheduleMode getScheduleMode(){
        return scheduleMode;
    }
    
    public void setScheduleMode(ScheduleMode scheduleMode ){
        this.scheduleMode = scheduleMode;
    }

    public int getInterval() {
        return interval;
    }

    public void setInterval(int interval) {
        this.interval = interval;
    }

    public String getCronExpression() {
        return cronExpression;
    }

    public void setCronExpression(String cronExpression) {
        this.cronExpression = cronExpression;
    }

    public int getRunTimes() {
        return runTimes;
    }

    public void setRunTimes(int runTimes) {
        this.runTimes = runTimes;
    }

}
