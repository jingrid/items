/**
 * Copyright (c) 2014, JingGe Technologies Ltd. All rights reserved.
 */
package com.jingge.items.tss.system.setup;

import org.testng.annotations.Test;

import com.realpaas.platform.framework.ioc.BeanContextLoader;
import com.realpaas.platform.framework.setup.SystemDataManager;
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
 * 	Jun 7, 2011	henry leu	Create the class
 * </dd>
 *
 * </dl>
 * @author	henry leu
 */
public class SystemDataManagerTest extends AbstractTest {
    private SystemDataManager systemDataManager;

    public static void main(String[] args){
        SystemDataManagerTest test = new SystemDataManagerTest();
        test.setUp();
        test.testUpdate();
        test.tearDown();
    }
    
    @Override
    public void setUp() {
        systemDataManager = (SystemDataManager)BeanContextLoader.i().getBeanFactory( "setupContext" ).getBean( "systemDataManager" );
        assertNotNull( systemDataManager );
    }

    @Override
    public void tearDown() {}

    @Test(groups = { "all", "setup" })
    public void testUpdate() {
        systemDataManager.update();
    }
    
}
