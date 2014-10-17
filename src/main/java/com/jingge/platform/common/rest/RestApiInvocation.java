/**
 * Copyright (c) 2014, JingGe Technologies, Ltd. All rights reserved.
 */
package com.jingge.platform.common.rest;

import java.io.InputStream;
import java.io.Serializable;
import java.util.Date;

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
 * 	Feb 28, 2013	henryleu	Create the class
 * </dd>
 * 
 * </dl>
 * @author	henryleu Email/MSN: hongli_leu@126.com
 */
public class RestApiInvocation implements Serializable{
    
    private static final long serialVersionUID = 306158681309468945L;

    private Request request;
    
    private Response response;

    public Request getRequest() {
        return request;
    }

    public void setRequest(Request request) {
        this.request = request;
    }

    public Response getResponse() {
        return response;
    }

    public void setResponse(Response response) {
        this.response = response;
    }

    public static class Request implements Serializable{
        
        private static final long serialVersionUID = 3559737629895030136L;

        private Date callTime;
        
        private String method;
        
        private String url;
        
        public Date getCallTime() {
            return callTime;
        }

        public void setCallTime(Date callTime) {
            this.callTime = callTime;
        }

        public String getMethod() {
            return method;
        }

        public void setMethod(String method) {
            this.method = method;
        }

        public String getUrl() {
            return url;
        }

        public void setUrl(String url) {
            this.url = url;
        }

        @Override
        public String toString() {
            return method + " " + url + " on " + callTime;
        }
    }
    
    public static class Response implements Serializable{
        
        private static final long serialVersionUID = -2052166509567967231L;

        private static final String DEFAULT_MIME_TYPE = "application/xml";

        private static final String DEFAULT_CHARSET = "UTF-8";

        private Date finishedTime;
        
        private String mimeType = DEFAULT_MIME_TYPE;
        
        private String charset = DEFAULT_CHARSET;
        
        private int length = 0;
        
        private InputStream data;
        
        private boolean successful = false;
        
        private String errorMessage;

        public static Response newInstance(){
            return new Response();
        }
        
        public Date getFinishedTime() {
            return finishedTime;
        }

        public void setFinishedTime(Date finishedTime) {
            this.finishedTime = finishedTime;
        }


        public String getMimeType() {
            return mimeType;
        }

        public void setMimeType(String mimeType) {
            this.mimeType = mimeType;
        }

        public String getCharset() {
            return charset;
        }

        public void setCharset(String charset) {
            this.charset = charset;
        }

        public int getLength() {
            return length;
        }

        public void setLength(int length) {
            this.length = length;
        }

        public InputStream getData() {
            return data;
        }

        public void setData(InputStream data) {
            this.data = data;
        }

        public boolean isSuccessful() {
            return successful;
        }

        public void setSuccessful(boolean successful) {
            this.successful = successful;
        }

        public String getErrorMessage() {
            return errorMessage;
        }

        public void setErrorMessage(String errorMessage) {
            this.errorMessage = errorMessage;
        }
    }
}
