/**
 * Copyright (c) 2014, JingGe Technologies, Ltd. All rights reserved.
 */
package com.jingge.platform.core.setup;

import org.springframework.beans.factory.BeanFactory;
import org.testng.annotations.Test;

import com.jingge.platform.core.setup.SystemSetup;
import com.realpaas.platform.framework.ioc.BeanContextLoader;
import com.realpaas.platform.test.AbstractTest;

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
 * 	Oct 2, 2012	henryleu	Create the class
 * </dd>
 * 
 * </dl>
 * @author	henryleu Email/MSN: hongli_leu@126.com
 */
public class SystemSetupTest extends AbstractTest{
    
    BeanFactory bf;
    
    SystemSetup systemSetup;
    
    @Override
    public void setUp() throws Exception {
        bf = BeanContextLoader.i().getBeanFactory( BeanContextLoader.SETUP_CONTEXT );
    }

    @Override
    public void tearDown() throws Exception {
        systemSetup = (SystemSetup)bf.getBean( "systemSetup" );
        systemSetup.tearDown();
    }

    @Test
    public void test(){
        
    }
}
