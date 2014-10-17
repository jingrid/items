/**
 * Copyright (c) 2014, JingGe Technologies, Ltd. All rights reserved.
 */
package com.jingge.platform.core.identity.setup;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Component;

import com.jingge.platform.core.identity.dataobject.DefaultUserAccess;
import com.jingge.platform.core.identity.dataobject.User;
import com.jingge.platform.core.identity.dataobject.UserAccess;
import com.jingge.platform.core.identity.dataobject.UserAccessType;
import com.jingge.platform.core.universal.setup.AbstractObjectTypeDataManagerImpl;

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
 * 	Jul 29, 2012	henryleu	Create the class
 * </dd>
 * 
 * </dl>
 * @author	henryleu Email/MSN: hongli_leu@126.com
 */

@Component( "identityTypeDataManager" )
public class IdentityTypeDataManagerImpl extends AbstractObjectTypeDataManagerImpl {

    @Override
    protected List<PlatformTypeDTO> getInitData() {
        List<PlatformTypeDTO> list = new ArrayList<PlatformTypeDTO>();
        PlatformTypeDTO dto = null;
        
        //User
        dto = new PlatformTypeDTO();
        dto.setCode( User.class.getName() );
        dto.setName( User.OBJECT_TYPE_NAME );
        list.add( dto );
        
        //UserAccess
        dto = new PlatformTypeDTO();
        dto.setCode( UserAccess.class.getName() );
        dto.setName( UserAccess.OBJECT_TYPE_NAME );
        list.add( dto );
        
        dto = new PlatformTypeDTO();
        dto.setCode( DefaultUserAccess.class.getName() );
        dto.setName( DefaultUserAccess.OBJECT_TYPE_NAME );
        list.add( dto );
        
        dto = new PlatformTypeDTO();
        dto.setCode( UserAccessType.class.getName() );
        dto.setName( UserAccessType.OBJECT_TYPE_NAME );
        list.add( dto );
        
        return list;
    }

}
