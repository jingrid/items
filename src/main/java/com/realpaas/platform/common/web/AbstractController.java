/**
 * Copyright (c) 2012, RealPaaS Technologies, Ltd. All rights reserved.
 */
package com.realpaas.platform.common.web;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;


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
public abstract class AbstractController implements WebContextual{
    
    private final WebContext.Builder contextBuilder = WebContext.Builder.i();
    
    private WebContext context = contextBuilder.build();
    
    protected Log logger = LogFactory.getLog( getClass() );

    @Override
    public WebContextual addPath(String path) {
        contextBuilder.addPath( path );
        return this;
    }

    @Override
    public void buildContext() {
        context = contextBuilder.build();
    }
    
    @Override
    public String renderTo(String viewName) {
        return context.renderTo( viewName );
    }

    @Override
    public String forwardTo(String actionName) {
        return context.forwardTo( actionName );
    }

    @Override
    public String redirectTo(String actionName) {
        return context.redirectTo( actionName );
    }

    @Override
    public String render(String viewPath) {
        return context.render( viewPath );
    }

    @Override
    public String forward(String actionPath) {
        return context.forward( actionPath );
    }

    @Override
    public String redirect(String actionPath) {
        return context.forward( actionPath );
    }
}
