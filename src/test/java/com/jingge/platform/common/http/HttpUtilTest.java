/**
 * Copyright (c) 2014, JingGe Technologies, Ltd. All rights reserved.
 */
package com.jingge.platform.common.http;

import org.testng.annotations.Test;

import com.jingge.platform.common.http.HttpUtil;
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
 * 	Mar 18, 2013	henryleu	Create the class
 * </dd>
 * 
 * </dl>
 * @author	henryleu Email/MSN: hongli_leu@126.com
 */
public class HttpUtilTest extends AbstractTest{

    @Override
    public void setUp() throws Exception {}

    @Override
    public void tearDown() throws Exception {}

    @Test( groups={ "all", "platform", "ut", "framework", "http" } )
    public void parseUrlToComponents(){
        String url = null;
        String[] urlComponents = null;
        
        url = "http://www.meituan.com/api/v2/beijing/deals";
        urlComponents = HttpUtil.parseUrlToComponents( url );
        
        assertEquals( urlComponents[0], "http" );
        assertEquals( urlComponents[1], "-1" );
        assertEquals( urlComponents[2], "www.meituan.com" );
        
        url = "https://www.meituan.com:80/api/v2/beijing/deals";
        urlComponents = HttpUtil.parseUrlToComponents( url );
        
        assertEquals( urlComponents[0], "https" );
        assertEquals( urlComponents[1], "80" );
        assertEquals( urlComponents[2], "www.meituan.com" );
        
        
    }
    
}
