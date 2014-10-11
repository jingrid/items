/**
 * Copyright (c) 2012, RealPaaS Technologies, Ltd. All rights reserved.
 */
package com.realpaas.platform.core.setup;

import java.sql.SQLException;
import java.text.MessageFormat;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.realpaas.dbproxy.RdbmsProxy;

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
abstract class AbstractSystemSetup extends SystemSetupInputs implements SystemSetup {
    
    protected Log logger = LogFactory.getLog( getClass() );
    
    private boolean isSetUp = false;
    
    private RdbmsProxy rdbmsProxy;
    
    private SystemSetupMode mode = SystemSetupMode.iu;
    
    AbstractSystemSetup(){}
    
    abstract protected RdbmsProxy createRdbmsProxy();
    
    abstract protected void dropDatabase() throws SQLException;
    
    abstract protected void createDatabase() throws SQLException;
    
    abstract protected void createUser() throws SQLException;
    
    abstract protected void grantPrivilegesToUser() throws SQLException;
    
    public void setMode(SystemSetupMode mode) {
        this.mode = mode;
    }

    public void uninitialize() throws Exception {
        getRdbmsProxy().disconnect();
    }

    public void initialize() throws Exception {
        getRdbmsProxy();
    }

    @Override
    public void setUp() {
        try{
            setUpDatabase();
            setUpDatabaseObjects();
            setUpDatabaseData();
        }
        catch( SQLException sqlE ){
            logger.error( sqlE );
            throw new RuntimeException( "Fail to set up", sqlE );
        }
        isSetUp = true;
    }

    @Override
    public void tearDown() {
        try{
            tearDownDatabaseData();
            tearDownDatabaseObjects();
            tearDownDatabase();
        }
        catch( SQLException sqlE ){
            logger.error( sqlE );
            throw new RuntimeException( "Fail to tear down", sqlE );
        }
    }

    @Override
    public boolean isSetUp() {
        return isSetUp;
    }

    protected void setUpDatabase() throws SQLException {
        String msg = null;
        try{
            if( SystemSetupMode.ci.equals( mode ) ){
                dropDatabase();
                createDatabase();
                createUser();
                grantPrivilegesToUser();
                
            }
            else if( SystemSetupMode.iu.equals( mode ) ){
                boolean dbExisted = getRdbmsProxy().catalogExists( dbCatalog );
                if( dbExisted ){
                    msg = "Database {0} exists";
                    msg = MessageFormat.format( msg, dbCatalog );
                    logger.debug( msg );
                }
                else{
                    createDatabase();
                }
                createUser();
                grantPrivilegesToUser();
            }
            else{
                throw new UnsupportedOperationException( "SystemSetupMode only supports ci and iu" );
            }
        }
        catch( SQLException sqlE ){
            logger.error( sqlE );
            throw new RuntimeException( "Fail to setup database", sqlE );
        }
    }
    
    protected void setUpDatabaseObjects() throws SQLException {
        //TODO
    }
    
    protected void setUpDatabaseData() throws SQLException {
        //TODO
    }

    protected void tearDownDatabase() throws SQLException{
        //TODO
    }

    protected void tearDownDatabaseObjects() throws SQLException {
        //TODO
    }
    
    protected void tearDownDatabaseData() throws SQLException {
        //TODO
    }
    
    protected RdbmsProxy getRdbmsProxy(){
        if( rdbmsProxy==null ){
            rdbmsProxy = createRdbmsProxy();
        }
        return rdbmsProxy;
    }

}
