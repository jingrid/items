/**
 * Copyright (c) 2014, JingGe Technologies, Ltd. All rights reserved.
 */
package com.jingge.platform.common.scheduling.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.FactoryBean;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;

import com.jingge.platform.common.scheduling.JobDescriptor;
import com.jingge.platform.common.scheduling.JobDescriptorFinder;
import com.jingge.platform.common.scheduling.JobParameterFinder;
import com.jingge.platform.common.scheduling.annocation.JobBean;
import com.jingge.platform.common.scheduling.annocation.Parameterized;

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
 * 	Apr 6, 2013	henryleu	Create the class
 * </dd>
 * 
 * </dl>
 * @author	henryleu Email/MSN: hongli_leu@126.com
 */
public class JobDescriptorFinderFactoryBean 
    implements 
        JobDescriptorFinder, 
        FactoryBean<JobDescriptorFinder>, 
        ApplicationContextAware,
        InitializingBean {

    private final Log logger = LogFactory.getLog( getClass() );
    
    private ApplicationContext ac;
    
    private List<JobDescriptor> descriptorList;
    
    private Map<String, JobParameterFinder> parameterFinderMap;
    
    @Override
    public void afterPropertiesSet() throws Exception {
        final Map<String, Object> jobMap = ac.getBeansWithAnnotation( JobBean.class );
        descriptorList = new ArrayList<JobDescriptor>();
        parameterFinderMap = new HashMap<String, JobParameterFinder>();
        for(final Map.Entry<String, Object> jobEntry : jobMap.entrySet()) {
            final String jobBeanName = jobEntry.getKey();
            final Object jobBeanObject = jobEntry.getValue();
            final Class<? extends Object> jobBeanClass = jobBeanObject.getClass();
            final JobBean jobAnnotation = jobBeanClass.getAnnotation( JobBean.class );
            final Parameterized ParameterizedAnnotation = jobBeanClass.getAnnotation( Parameterized.class );
            boolean parameterized = ParameterizedAnnotation != null;
            JobParameterFinder parameterFinder = null;
            if( parameterized ) {
                if( jobBeanObject instanceof JobParameterFinder ) {
                    parameterFinder = (JobParameterFinder)jobBeanObject;
                    parameterFinderMap.put( jobBeanName, parameterFinder );
                }
                else {
                    logger.warn( "Parameterized Job " + jobBeanObject.getClass().getName() + " should implement " + JobParameterFinder.class.getName() );
                }
            }

            JobDescriptor jobDesc = new JobDescriptor();
            jobDesc.setBeanName( jobBeanName );
            jobDesc.setName( jobAnnotation.name() );
            jobDesc.setParameterized( parameterized );
            descriptorList.add( jobDesc );
        }
    }

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        this.ac = applicationContext;
    }

    @Override
    public JobDescriptorFinder getObject() throws Exception {
        return this;
    }

    @Override
    public Class<?> getObjectType() {
        return JobDescriptorFinder.class;
    }

    @Override
    public boolean isSingleton() {
        return true;
    }

    @Override
    public List<JobDescriptor> findDescriptors() {
        return descriptorList;
    }

}
