/**
 * Copyright (c) 2012, RealPaaS Technologies, Ltd. All rights reserved.
 */
package com.realpaas.platform.core.identity.setup;

import org.springframework.stereotype.Component;

import com.realpaas.platform.core.identity.dataobject.DefaultUserAccess;
import com.realpaas.platform.core.identity.dataobject.UserAccessType;
import com.realpaas.platform.core.identity.service.UserAccessTypeService;
import com.realpaas.platform.framework.setup.impl.AbstractGenericDataManagerImpl;

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
 *  <b>IMMUTABLE</b> and <b>MUTABLE</b>
 * </dd>
 * 
 * <p><dt><b>Thread Safety:</b></dt> 
 * <dd>
 *  <b>NOT-THREAD-SAFE</b> and <b>NOT-APPLICABLE</b> (for it will never be used on multi-thread occasion.)
 * </dd>
 * 
 * <p><dt><b>Serialization:</b></dt>
 * <dd>
 *  <b>NOT-SERIALIIZABLE</b> and <b>NOT-APPLICABLE</b> (for it have no need to be serializable.)
 * </dd>
 * 
 * <p><dt><b>Design Patterns:</b></dt>
 * <dd>
 *  
 * </dd>
 * 
 * <p><dt><b>Change History:</b></dt>
 * <dd>
 *  Date        Author      Action
 * </dd>
 * <dd>
 *  Aug 8, 2012    henry leu   Create the class
 * </dd>
 * 
 * </dl>
 * @author  henry leu Email/MSN: hongli_leu@126.com
 */

@Component( "userAccessTypeDataManager" )
public class UserAccessTypeDataManagerImpl extends AbstractGenericDataManagerImpl<UserAccessTypeService> {

    @Override
    protected void doTearDown() {
        service.deleteAll();
    }

    @Override
    protected void doSetUp() {
        UserAccessType type = null;

        /*
         * DefaultUserAccess
         */
        type = new UserAccessType();
        type.setCode( DefaultUserAccess.TYPE_CODE );
        type.setName( DefaultUserAccess.TYPE_NAME );
        type.setDescription( DefaultUserAccess.TYPE_NAME );
        service.create( type );
    }

    @Override
    protected void doValidate() {
        UserAccessType type = null;

        /*
         * DefaultUserAccess
         */
        type = service.loadByCode( DefaultUserAccess.TYPE_CODE );
        checkExistence( type, DefaultUserAccess.TYPE_NAME );
        
    }

    @Override
    protected void doUpdate() {
        UserAccessType type = null;

        /*
         * DefaultUserAccess
         */
        type = service.loadByCode( DefaultUserAccess.TYPE_CODE );
        if(type==null){
            type = new UserAccessType();
            type.setCode( DefaultUserAccess.TYPE_CODE );
            type.setName( DefaultUserAccess.TYPE_NAME );
            type.setDescription( DefaultUserAccess.TYPE_NAME );
            service.create( type );
        }
        
        doValidate();
    }
    
}
