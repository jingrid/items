/**
 * Copyright (c) 2012, RealPaaS Technologies, Ltd. All rights reserved.
 */
package com.realpaas.platform.core.common;

import org.testng.annotations.Test;

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
 * 	Aug 8, 2012	henry leu	Create the class
 * </dd>
 * 
 * </dl>
 * @author	henry leu Email/MSN: hongli_leu@126.com
 */
public class MiscTest extends AbstractTest{

    @Override
    public void setUp() throws Exception {

    }

    @Override
    public void tearDown() throws Exception {

    }
    
    @Test(groups = { "all", "platform", "service", "ut" })
    public void testLongBitOperators(){
        long id = 1L;
        int bit = 16;
        long id1 = id<<bit;
        System.out.println( id + " is moved to left by " + bit + "bit : " + id1);
        
        id = 2L;
        long id2 = id<<bit;
        System.out.println( id + " is moved to left by " + bit + "bit : " + id2);
    }
    
}
