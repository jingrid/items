/**
 * Copyright (c) 2012, RealPaaS Technologies, Ltd. All rights reserved.
 */
package com.realpaas.platform.common.scheduling.impl.quartz;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.quartz.Scheduler;
import org.quartz.SchedulerException;
import org.quartz.impl.StdSchedulerFactory;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.FactoryBean;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;

import com.realpaas.platform.common.scheduling.JobScheduler;
import com.realpaas.platform.common.scheduling.impl.FrameworkJob;

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
public class JobSchedulerFactoryBean implements FactoryBean<JobScheduler>, ApplicationContextAware, InitializingBean{
    
    private final Log logger = LogFactory.getLog( getClass() );

    public static final String DEFAULT_ID = "default";
    
    private ApplicationContext ac;

    private JobSchedulerImpl jobScheduler;
    
    private String schedulerId = DEFAULT_ID;
    
    private FrameworkJob frameworkJob;
    
    private boolean templatedInstance = true;
    
    private boolean startNow = false;
    
    public void setSchedulerId(String schedulerId) {
        this.schedulerId = schedulerId;
    }
    
    public void setFrameworkJob(FrameworkJob frameworkJob) {
        this.frameworkJob = frameworkJob;
    }

    public void setTemplatedInstance(boolean templatedInstance) {
        this.templatedInstance = templatedInstance;
    }
    
    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        ac = applicationContext;
    }

    public void setStartNow(boolean startNow) {
        this.startNow = startNow;
    }

    @Override
    public void afterPropertiesSet() throws Exception {
        jobScheduler = new JobSchedulerImpl();
        jobScheduler.setApplicationContext( ac );
        jobScheduler.setScheduler( getScheduler() );
        jobScheduler.setFrameworkJob( frameworkJob );
        if( startNow ){
            jobScheduler.start();
        }
    }

    @Override
    public JobScheduler getObject() throws Exception {
        return jobScheduler;
    }

    @Override
    public Class<?> getObjectType() {
        return JobScheduler.class;
    }

    @Override
    public boolean isSingleton() {
        return true;
    }

    private Scheduler getScheduler(){
        Scheduler schdlr = null;
        StdSchedulerFactory sf = null;
        
        if( templatedInstance ){
            sf = new NamedStdSchedulerFactory( schedulerId );;
            
        }
        else{
            sf = new StdSchedulerFactory();
        }
        
        try {
            sf.initialize();
            schdlr = sf.getScheduler();
        }
        catch (SchedulerException e) {
            String msg = "Fail to create Scheduler";
            logger.error( msg, e );
            throw new RuntimeException( msg, e );
        }
        
        return schdlr;
    }
}
