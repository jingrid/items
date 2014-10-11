/**
 * Copyright (c) 2012, RealPaaS Technologies, Ltd. All rights reserved.
 */
package com.realpaas.platform.core.setup;

import java.sql.SQLException;
import java.util.Properties;

import com.realpaas.commons.utils.StringUtil;
import com.realpaas.dbproxy.RdbmsConstants;
import com.realpaas.dbproxy.RdbmsProxy;
import com.realpaas.dbproxy.impl.mysql.MySqlProxy;

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
 * 	Oct 2, 2012	henryleu	Create the class
 * </dd>
 * 
 * </dl>
 * @author	henryleu Email/MSN: hongli_leu@126.com
 */
public class MySqlSystemSetup extends AbstractSystemSetup {

    private static final String CREATE_USER = "CREATE USER {0}@'{2}' IDENTIFIED BY '{1}';";
    
    private static final String GRANT_PRIVILEGES = "GRANT ALL PRIVILEGES ON {0}.* TO {1}@'{2}';";

    @Override
    protected RdbmsProxy createRdbmsProxy(){
        RdbmsProxy proxy = null;
        
        try {
            proxy = new MySqlProxy( systemDbUrl, systemDbUser, systemDbPassword );
        }
        catch (SQLException e) {
            logger.debug( e.getMessage() );
            throw new RuntimeException( "Fail to create MySQL proxy object", e );
        }
        
        return proxy;
    }

    @Override
    protected void dropDatabase() throws SQLException {
        boolean dbExisted = getRdbmsProxy().catalogExists( dbCatalog );
        if( dbExisted ){
            getRdbmsProxy().dropDatabase( dbCatalog );
        }
    }

    @Override
    protected void createDatabase() throws SQLException {
        Properties dbProperties = new Properties();
        dbProperties.setProperty( RdbmsConstants.P_CHARSET, dbCharset );
        dbProperties.setProperty( RdbmsConstants.P_COLLATE, dbCollation );
        getRdbmsProxy().createDatabase( dbCatalog, dbProperties );
    }

    @Override
    protected void createUser() throws SQLException {
        try{
            MySqlProxy mySqlProxy = (MySqlProxy)getRdbmsProxy();
            boolean userExisted = mySqlProxy.userExists( dbUser, dbHost );
            if( userExisted ){
                logger.debug( "User " + dbUser + "@" + dbHost + " existed" );
            }
            else{
                String sql = StringUtil.format( CREATE_USER, dbUser, dbPassword, dbHost );
                getRdbmsProxy().executeUpdate( sql );
            }
        }
        catch(SQLException e){
            logger.error( "Fail to create user " + dbUser + "@" + dbHost + ": ", e );
            throw e;
        }
    }

    @Override
    protected void grantPrivilegesToUser() throws SQLException {
        String sql = StringUtil.format( GRANT_PRIVILEGES, dbCatalog, dbUser, dbHost );
        logger.debug( sql );
        
        try{
            getRdbmsProxy().executeUpdate( sql );
        }
        catch(SQLException e){
            logger.error( "Fail to grant al privileges to user " + dbUser + "@" + dbHost + ": ", e );
            throw e;
        }
    }

}
