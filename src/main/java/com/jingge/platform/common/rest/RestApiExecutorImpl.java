/**
 * Copyright (c) 2014, JingGe Technologies, Ltd. All rights reserved.
 */
package com.jingge.platform.common.rest;

import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.Charset;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.http.HttpEntity;
import org.apache.http.HttpHost;
import org.apache.http.HttpRequest;
import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.ContentType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.jingge.platform.common.http.HttpClientFactory;
import com.jingge.platform.common.http.HttpUtil;
import com.jingge.platform.common.rest.RestApiInvocation.Response;

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
@Component( "restApiExecutor" )
public class RestApiExecutorImpl implements RestApiExecutor {
    
    private Log logger = LogFactory.getLog( getClass() );
    
    @Autowired
    private HttpClientFactory httpClientFactory;
    
    @Override
    public void execute(RestApiInvocation invocation) {
        Response response = Response.newInstance();
        invocation.setResponse( response );
        HttpClient httpclient = httpClientFactory.create();
        HttpHost targetHost = null;
        HttpRequest httpRequest = null;
        HttpResponse httpResponse = null;
        String errorMessage = null;
        
        String[] urlConponents = HttpUtil.parseUrlToComponents( invocation.getRequest().getUrl() );
        int port = 80;
        if( !urlConponents[1].equals( "-1" ) ){
            port = Integer.valueOf( urlConponents[1] );
        }
        targetHost = new HttpHost( urlConponents[2], port, urlConponents[0]);
        
        String uri = urlConponents[3];
        if( "GET".equalsIgnoreCase( invocation.getRequest().getMethod() ) ){
            httpRequest = new HttpGet( uri );
        }
        else if( "POST".equalsIgnoreCase( invocation.getRequest().getMethod() ) ){
            httpRequest = new HttpPost( uri );
        }
        else{
            errorMessage = "Restful API Executor only supports GET & POST as of now.";
            response.setErrorMessage( errorMessage );
            response.setSuccessful( false );
            return;
        }

        if( logger.isDebugEnabled() ){
            logger.debug( "Executing request: " + httpRequest.getRequestLine() + " to target: " + targetHost );
        }
        
        try{
            httpResponse = httpclient.execute(targetHost, httpRequest);
        }
        catch ( ClientProtocolException e ) {
            String msg = "Fail to request " + targetHost + " for " + httpRequest.getRequestLine();
            logger.error( msg, e );
            errorMessage = msg + " : " + e.getMessage();
            response.setErrorMessage( errorMessage );
            response.setSuccessful( false );
            return;
        }
        catch ( IOException e ) {
            String msg = "Fail to request " + targetHost + " for " + httpRequest.getRequestLine();
            logger.error( msg, e );
            errorMessage = msg + " : " + e.getMessage();
            response.setErrorMessage( errorMessage );
            response.setSuccessful( false );
            return;
        }
        
        if( logger.isDebugEnabled() ){
            logger.debug( httpResponse.getStatusLine() + " for Executing request: " + httpRequest.getRequestLine() + " to target: " + targetHost);
        }

        HttpEntity httpEntity = httpResponse.getEntity();
        if (httpEntity == null) {
            errorMessage = "Restful API Executor fail to get response entity.";
            response.setErrorMessage( errorMessage );
            response.setSuccessful( false );
            return;
        }
        else {
            InputStream contentInputStream = null;
            try {
                contentInputStream = httpEntity.getContent();
            }
            catch ( IOException e ) {
                String msg = "Fail to request " + targetHost + " for " + httpRequest.getRequestLine();
                logger.error( msg, e );
                errorMessage = msg + " : " + e.getMessage();
                response.setErrorMessage( errorMessage );
                response.setSuccessful( false );
            }
            catch ( Exception e ) {
                String msg = "Fail to request " + targetHost + " for " + httpRequest.getRequestLine();
                logger.error( msg, e );
                errorMessage = msg + " : " + e.getMessage();
                response.setErrorMessage( errorMessage );
                response.setSuccessful( false );
            }
            
            ContentType contentType = ContentType.get( httpEntity );
            String mimeType = null;
            if( contentType!=null ){
                mimeType = contentType.getMimeType();
                if( mimeType!=null ){
                    response.setMimeType( mimeType );
                }
                
                Charset charset = contentType.getCharset();
                if( charset!=null ){
                    response.setCharset( charset.toString() );
                }
            }
            long length = httpEntity.getContentLength();
            response.setLength( (int)length );
            response.setData( contentInputStream );
            response.setSuccessful( true );
        }
        
        if( logger.isDebugEnabled() ){
            logger.debug("Response content length: " + response.getLength());
            logger.debug("Response content mime type: " + response.getMimeType() );
            logger.debug("Response content charset: " + response.getCharset() );
        }
    }

}
