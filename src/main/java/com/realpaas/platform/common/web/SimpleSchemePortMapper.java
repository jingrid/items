/**
 * Copyright (c) 2012, RealPaaS Technologies, Ltd. All rights reserved.
 */
package com.realpaas.platform.common.web;

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
 * 	Jan 9, 2013	henryleu	Create the class
 * </dd>
 * 
 * </dl>
 * @author	henryleu Email/MSN: hongli_leu@126.com
 */
public class SimpleSchemePortMapper implements SchemePortMapper, Serializable {

    private static final long serialVersionUID = -4067937039254348161L;
    
    public static final Integer DEFAULT_HTTP_PORT = Integer.valueOf( 80 );

    public static final String HTTP_SCHEME = "http";

    public static final Integer DEFAULT_HTTPS_PORT = Integer.valueOf( 443 );
    
    public static final String HTTPS_SCHEME = "https";

    private Integer httpPort = DEFAULT_HTTP_PORT;

    private Integer httpsPort = DEFAULT_HTTPS_PORT;
    
    @Override
    public Integer getPort(String scheme) {
        if( HTTP_SCHEME.equals( scheme ) ){
            return httpPort;
        }
        else if( HTTPS_SCHEME.equals( scheme ) ){
            return httpsPort;
        }
        else{
            return null;
        }
    }

    @Override
    public Integer getHttpPort() {
        return httpPort;
    }

    @Override
    public Integer getHttpsPort() {
        return httpsPort;
    }

    public void setHttpPort(Integer httpPort) {
        this.httpPort = httpPort;
    }

    public void setHttpsPort(Integer httpsPort) {
        this.httpsPort = httpsPort;
    }
}
