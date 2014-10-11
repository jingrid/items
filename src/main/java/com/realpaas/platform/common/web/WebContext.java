/**
 * Copyright (c) 2012, RealPaaS Technologies, Ltd. All rights reserved.
 */
package com.realpaas.platform.common.web;

import java.util.ArrayList;
import java.util.List;

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
 * 	Jan 30, 2013	henryleu	Create the class
 * </dd>
 * 
 * </dl>
 * @author	henryleu Email/MSN: hongli_leu@126.com
 */
public class WebContext implements WebViewBuilder{
    
    public static final String SEPARATOR = "/";
    
    public static final String FORWARD = "forward:";
    
    public static final String REDIRECT = "redirect:";
    
    private final String contextPath;

    private final String forwardContextPath;
    
    private final String redirectContextPath;
    
    public WebContext(List<String> pathChain){
        StringBuilder pathBuilder = new StringBuilder();
        for(String pathItem : pathChain){
            pathBuilder.append( pathItem ).append( SEPARATOR );
        }
        contextPath = pathBuilder.toString();
        forwardContextPath = FORWARD + SEPARATOR + contextPath;
        redirectContextPath = REDIRECT + SEPARATOR + contextPath;
    }

    @Override
    public String renderTo(String viewName) {
        return contextPath + viewName;
    }

    @Override
    public String forwardTo(String actionName) {
        return forwardContextPath + actionName;
    }

    @Override
    public String redirectTo(String actionName) {
        return redirectContextPath + actionName;
    }

    @Override
    public String render(String viewPath) {
        return viewPath;
    }

    @Override
    public String forward(String actionPath) {
        return FORWARD + actionPath;
    }

    @Override
    public String redirect(String actionPath) {
        return REDIRECT + actionPath;
    }
    
    public static class Builder {

        private List<String> pathChain = new ArrayList<String>(); 

        private Builder(){}
        
        public static Builder i(){
            return new Builder();
        }
        
        public Builder addPath(String path){
            pathChain.add( path );
            return this;
        }
        
        public WebContext build(){
            return new WebContext( pathChain );
        }
    }
}
