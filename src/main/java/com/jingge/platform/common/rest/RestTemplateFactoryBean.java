/**
 * Copyright (c) 2014, JingGe Technologies, Ltd. All rights reserved.
 */
package com.jingge.platform.common.rest;

import java.util.List;

import org.apache.http.HttpHost;
import org.apache.http.auth.AuthScope;
import org.apache.http.auth.UsernamePasswordCredentials;
import org.apache.http.client.HttpClient;
import org.apache.http.impl.client.DefaultHttpClient;
import org.springframework.beans.factory.FactoryBean;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.web.client.RestTemplate;

import com.jingge.platform.common.security.BasicHttpComponentsClientHttpRequestFactory;
import com.jingge.platform.common.security.DigestHttpComponentsClientHttpRequestFactory;

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
 * 	Jun 13, 2012	henryleu	Create the class
 * </dd>
 * 
 * </dl>
 * @author	henryleu Email/MSN: hongli_leu@126.com
 */
public class RestTemplateFactoryBean implements FactoryBean<RestTemplate>, InitializingBean {

    private RestTemplate restTemplate;
    
    private List<HttpMessageConverter<?>> messageConverters;
    
    private HttpClient httpClient;
    
    private int timeout;
    
    private boolean basicAuth;
    
    private boolean digestAuth;

    private String host;

    private String scheme;
    
    private int port;
    
    private String username;
    
    private String password;
    
    
    public void setMessageConverters(List<HttpMessageConverter<?>> messageConverters) {
        this.messageConverters = messageConverters;
    }

    public void setHttpClient(HttpClient httpClient) {
        this.httpClient = httpClient;
    }

    public void setTimeout(int timeout) {
        this.timeout = timeout;
    }
    
    public boolean isBasicAuth() {
        return basicAuth;
    }
    
    public void setBasicAuth(boolean basicAuth) {
        this.basicAuth = basicAuth;
    }
    
    public boolean isDigestAuth() {
        return digestAuth;
    }
    
    public void setDigestAuth(boolean digestAuth) {
        this.digestAuth = digestAuth;
    }

    public void setHost(String host) {
        this.host = host;
    }

    public void setPort(int port) {
        this.port = port;
    }

    public void setScheme(String scheme) {
        this.scheme = scheme;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    @Override
    public void afterPropertiesSet() throws Exception {
//        final DefaultHttpClient httpClient = new DefaultHttpClient();
        final HttpComponentsClientHttpRequestFactory requestFactory;
System.out.println( "hello world!" );
        if( basicAuth ){
            requestFactory = new BasicHttpComponentsClientHttpRequestFactory( httpClient, new HttpHost( host, port, scheme ) );
        }
        else if( digestAuth ){
            requestFactory = new DigestHttpComponentsClientHttpRequestFactory( httpClient );
        }
        else{
            requestFactory = new HttpComponentsClientHttpRequestFactory( httpClient );
        }

        requestFactory.setReadTimeout( timeout );
        restTemplate = new RestTemplate( requestFactory );
        restTemplate.setMessageConverters( messageConverters );
        
        if( basicAuth ){
            basicAuth();
        }
        if( digestAuth ){
            digestAuth();
        }
        
//        restTemplate.getMessageConverters().add( marshallingHttpMessageConverter() );
    }

    @Override
    public RestTemplate getObject() throws Exception {
        return restTemplate;
    }

    @Override
    public Class<?> getObjectType() {
        return RestTemplate.class;
    }

    @Override
    public boolean isSingleton() {
        return true;
    }

    protected void basicAuth(){
        final HttpComponentsClientHttpRequestFactory requestFactory = (HttpComponentsClientHttpRequestFactory) restTemplate.getRequestFactory();
        final DefaultHttpClient httpClient = (DefaultHttpClient) requestFactory.getHttpClient();
        httpClient.getCredentialsProvider().setCredentials( new AuthScope( host, port, AuthScope.ANY_REALM ), new UsernamePasswordCredentials( username, password ) );
    }
    
    protected void digestAuth(){
        throw new UnsupportedOperationException();
    }
}
