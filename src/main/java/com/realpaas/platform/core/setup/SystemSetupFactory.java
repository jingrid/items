/**
 * Copyright (c) 2012, RealPaaS Technologies, Ltd. All rights reserved.
 */
package com.realpaas.platform.core.setup;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.DisposableBean;
import org.springframework.beans.factory.FactoryBean;

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
 * 	Oct 1, 2012	henryleu	Create the class
 * </dd>
 * 
 * </dl>
 * @author	henryleu Email/MSN: hongli_leu@126.com
 */
public class SystemSetupFactory extends SystemSetupInputs implements FactoryBean<SystemSetup>, DisposableBean{
    
    private static final String MYSQL = "mysql";
    
    private static AbstractSystemSetup systemSetup;

    private Log logger = LogFactory.getLog( getClass() );

    private SystemSetupMode mode = SystemSetupMode.iu;
    
    private String dbms = MYSQL;

    public void setMode(SystemSetupMode mode) {
        this.mode = mode;
    }

    public void setDbms(String dbms) {
        if( !MYSQL.equals( dbms ) ){
            throw new IllegalArgumentException();
        }
        this.dbms = dbms;
    }
    
    public void init() throws Exception{
        if( MYSQL.equals( dbms ) ){
            logger.debug( "System is setting up on MySQL database" );
            systemSetup = new MySqlSystemSetup();
        }
        else{
            throw new UnsupportedOperationException( "Does not support other DBMS besides MySQL" );
        }
        systemSetup.setMode( mode );
        systemSetup.setSystemDbUrl( systemDbUrl );
        systemSetup.setSystemDbUser( systemDbUser );
        systemSetup.setSystemDbPassword( systemDbPassword );
        systemSetup.setDbCatalog( dbCatalog );
        systemSetup.setDbUser( dbUser );
        systemSetup.setDbPassword( dbPassword );
        systemSetup.setDbHost( dbHost );
        systemSetup.setDbCharset( dbCharset );
        systemSetup.setDbCollation( dbCollation );
        systemSetup.initialize();
        systemSetup.setUp();
    }

    @Override
    public void destroy() throws Exception {
        systemSetup.uninitialize();
    }

    @Override
    public SystemSetup getObject() throws Exception {
        return systemSetup;
    }

    @Override
    public Class<?> getObjectType() {
        return SystemSetup.class;
    }

    @Override
    public boolean isSingleton() {
        return true;
    }

}


