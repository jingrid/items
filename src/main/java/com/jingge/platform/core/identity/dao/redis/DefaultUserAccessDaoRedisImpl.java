/**
 * Copyright (c) 2014, JingGe Technologies, Ltd. All rights reserved.
 */
package com.jingge.platform.core.identity.dao.redis;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.stereotype.Repository;

import com.jingge.platform.common.dao.redis.AbstractStringDoRedisDaoImpl;
import com.jingge.platform.core.identity.dao.DefaultUserAccessDaoRedis;
import com.jingge.platform.core.identity.dataobject.DefaultUserAccess;

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
 * 	2012-7-29	henryleu	Create the class
 * </dd>
 * 
 * </dl>
 * @author	henryleu Email/MSN: hongli_leu@126.com
 */

@Repository( "defaultUserAccessDaoRedis" )
public class DefaultUserAccessDaoRedisImpl extends AbstractStringDoRedisDaoImpl<DefaultUserAccess> implements DefaultUserAccessDaoRedis{

    private static final Log logger = LogFactory.getLog( DefaultUserAccessDaoRedisImpl.class );
    
    public static final String ID_NAME = "userAccessId"; 
    
    public static final String USER_ACCESS_TYPE_CODE = "default";
    
    public static final String PROP_PASSWORD = "password";
    
    public static final String PROP_USER_ACCESS_ID = "userAccessId";
    
    private static final String userAccessIdKeyPrefix;
    
    private static final String passwordKeyPrefix;
    
    static {
        StringBuilder sbKey = new StringBuilder(100);
        sbKey.append( ID_NAME )
            .append( KEY_DELIMITER_CHAR )
            .append( USER_ACCESS_TYPE_CODE )
            .append( KEY_DELIMITER_CHAR )
            .append( PROP_USER_ACCESS_ID )
            .append( KEY_DELIMITER_CHAR );
        userAccessIdKeyPrefix = sbKey.toString();
        logger.debug( "UserAccessId Key Prefix - " + userAccessIdKeyPrefix );
        
        sbKey = new StringBuilder(100);
        sbKey.append( ID_NAME )
            .append( KEY_DELIMITER_CHAR )
            .append( USER_ACCESS_TYPE_CODE )
            .append( KEY_DELIMITER_CHAR )
            .append( PROP_PASSWORD )
            .append( KEY_DELIMITER_CHAR );
        passwordKeyPrefix = sbKey.toString();
        logger.debug( "Password Key Prefix - " + userAccessIdKeyPrefix );
    }
    
    @Override
    public String getPassword(String username) {
        return valueOperations.get( getPasswordKey( username ) );
    }

    @Override
    public void setPassword(String username, String password) {
        valueOperations.set( getPasswordKey( username ), password );
    }

    @Override
    public String getUserAccessId(String username) {
        return valueOperations.get( getUserAccessIdKey( username ) );
    }

    @Override
    public void setUserAccessId(String username, String userAccessId) {
        valueOperations.set( getUserAccessIdKey( username ), userAccessId );
    }

    @Override
    protected String getDoIdName() {
        return ID_NAME;
    }

    //userAccessId:username:userid
    protected String getUserAccessIdKey(String username){
        return userAccessIdKeyPrefix + username;
    }

    protected String getPasswordKey(String username){
        return passwordKeyPrefix + username;
    }
}
