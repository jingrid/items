/**
 * Copyright (c) 2012, RealPaaS Technologies, Ltd. All rights reserved.
 */
package com.realpaas.platform.common.http;

import org.apache.http.HttpHost;
import org.apache.http.client.HttpClient;
import org.apache.http.conn.params.ConnRouteParams;

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
 * 	Jun 15, 2012	henryleu	Create the class
 * </dd>
 * 
 * </dl>
 * @author	henryleu Email/MSN: hongli_leu@126.com
 */
public class SystemConnectionProxy extends AbstractConnectionProxy implements ConnectionProxy {

    public static final String DEFAULT_SCHEME = "http";
    
    public static final int DEFAULT_PORT = 8080;
    
    public static final String SYS_PROP_PROXY_SET = "proxySet";

    public static final String SYS_PROP_PROXY_HOST = "proxyHost";
    
    public static final String SYS_PROP_PROXY_PORT = "proxyPort";
    
    private String scheme;
    
    private String host;
    
    private int port;
    
    private HttpHost httpHost;
    
    public SystemConnectionProxy() {
        refresh();
    }
    
    public String getScheme() {
        return scheme;
    }

    public void setScheme(String scheme) {
        this.scheme = scheme;
    }

    public String getHost() {
        return host;
    }

    public int getPort() {
        return port;
    }

    @Override
    public void setProxy(Object anyHttpClient) {
        if( anyHttpClient instanceof HttpClient ){
            HttpClient httpClient = (HttpClient)anyHttpClient;
            httpClient.getParams().setParameter( ConnRouteParams.DEFAULT_PROXY, httpHost );
        }
        else{
            throw new IllegalArgumentException( anyHttpClient.getClass().getName() + "is not supported to set proxy" );
        }
    }
    
    public void refresh(){
        String proxySet = getSystemProxySet();
        String proxyPort = getSystemProxyPort();
        setEnabled( "true".equals( proxySet ) );
        
        this.host = getSystemProxyHost();
        this.port = proxyPort == null ? DEFAULT_PORT : Integer.parseInt( proxyPort ) ;
        
        if( scheme == null ){
            this.scheme = DEFAULT_SCHEME;
        }
        else{
            this.scheme = scheme.toLowerCase();
        }
        httpHost = new HttpHost( this.host, this.port, this.scheme );
    }
    
    private String getSystemProxySet(){
        return System.getProperty( SYS_PROP_PROXY_SET );
    }
    
    private String getSystemProxyHost(){
        return System.getProperty( SYS_PROP_PROXY_HOST );
    }
    
    private String getSystemProxyPort(){
        return System.getProperty( SYS_PROP_PROXY_PORT );
    }
    
}
