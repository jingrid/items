/**
 * Copyright (c) 2012, RealPaaS Technologies, Ltd. All rights reserved.
 */
package com.realpaas.platform.core.setup;

import javax.servlet.ServletContext;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

import org.springframework.web.context.ConfigurableWebApplicationContext;
import org.springframework.web.context.support.XmlWebApplicationContext;

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
 * 	Sep 30, 2012	henryleu	Create the class
 * </dd>
 * 
 * </dl>
 * @author	henryleu Email/MSN: hongli_leu@126.com
 */
public class SystemSetupContextListener implements ServletContextListener{

    private static final String CONFIG_LOCATION_PARAM = "contextConfigLocation";
    
    private static final String SYSTEM_SETUP_CONTEXT_ID = "systemSetupContext";
    
    @Override
    public void contextInitialized(ServletContextEvent sce) {
        ConfigurableWebApplicationContext wac = new XmlWebApplicationContext();
        ServletContext sc = sce.getServletContext();
        
        wac.setId( SYSTEM_SETUP_CONTEXT_ID );
        wac.setConfigLocations( null );
        wac.setParent( null );
        wac.setServletContext( sce.getServletContext() );
        String initParameter = sc.getInitParameter(CONFIG_LOCATION_PARAM);
        if (initParameter != null) {
            wac.setConfigLocation(initParameter);
        }
        wac.refresh();        
    }

    @Override
    public void contextDestroyed(ServletContextEvent sce) {
        
    }

}
