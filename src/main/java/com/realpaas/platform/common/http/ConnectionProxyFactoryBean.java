/**
 * Copyright (c) 2012, RealPaaS Technologies, Ltd. All rights reserved.
 */
package com.realpaas.platform.common.http;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.FactoryBean;
import org.springframework.beans.factory.InitializingBean;

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
public class ConnectionProxyFactoryBean implements FactoryBean<ConnectionProxy>, InitializingBean{

    public static final String NONE_PROXY = "none";

    public static final String MANUAL_PROXY = "manual";

    public static final String SYSTEM_PROXY = "system";

    public static final String SCRIPT_PROXY = "script";
    
    public static final String DETECT_PROXY = "detect";
    
    public static final Map<String, String> typeEnumMap = new HashMap<String, String>();

    public static final int UNSET_PROXY_PORT = -1;
    
    private ConnectionProxy connectionProxy;
    
    private String type = NONE_PROXY;
    
    private String proxyHost;
    
    private int proxyPort = UNSET_PROXY_PORT;
    
    private String proxyScheme;
    
    @SuppressWarnings("unused")
    private String proxyScriptUrl;
    
    static {
        typeEnumMap.put( NONE_PROXY, NONE_PROXY );
        typeEnumMap.put( MANUAL_PROXY, MANUAL_PROXY );
        typeEnumMap.put( SYSTEM_PROXY, SYSTEM_PROXY );
        typeEnumMap.put( SCRIPT_PROXY, SCRIPT_PROXY );
        typeEnumMap.put( DETECT_PROXY, DETECT_PROXY );
    }
    
    public void setType(String type) {
        this.type = type;
    }

    public void setProxyHost(String proxyHost) {
        this.proxyHost = proxyHost;
    }

    public void setProxyPort(int proxyPort) {
        this.proxyPort = proxyPort;
    }

    public void setProxyScheme(String proxyScheme) {
        this.proxyScheme = proxyScheme;
    }

    public void setProxyScriptUrl(String proxyScriptUrl) {
        this.proxyScriptUrl = proxyScriptUrl;
    }

    @Override
    public void afterPropertiesSet() throws Exception {
        if( !typeEnumMap.containsKey( type ) ){
            throw new IllegalStateException( "Property \"type\": "  + type + " is not supported, use: " + typeEnumMap.toString() );
        }
        
        if( NONE_PROXY.equals( type ) ){
            connectionProxy = new NoneConnectionProxy();
        }
        else if( MANUAL_PROXY.equals( type ) ){
            if( proxyHost == null ){
                throw new IllegalStateException( "Property \"proxyHost\" is required" );
            }
            
            if( proxyPort == UNSET_PROXY_PORT ){
                throw new IllegalStateException( "Property \"proxyPort\" is required" );
            }
            connectionProxy = new BasicConnectionProxy( proxyHost, proxyPort, proxyScheme );
        }
        else if( SYSTEM_PROXY.equals( type ) ){
            connectionProxy = new SystemConnectionProxy();
        }
        else if( SCRIPT_PROXY.equals( type ) ){
            throw new UnsupportedOperationException( SCRIPT_PROXY + " type of proxy is not supported" );
        }
        else if( DETECT_PROXY.equals( type ) ){
            throw new UnsupportedOperationException( DETECT_PROXY + " type of proxy is not supported" );
        }
    }

    @Override
    public ConnectionProxy getObject() throws Exception {
        return connectionProxy;
    }

    @Override
    public Class<?> getObjectType() {
        return ConnectionProxy.class;
    }

    @Override
    public boolean isSingleton() {
        return true;
    }

}
