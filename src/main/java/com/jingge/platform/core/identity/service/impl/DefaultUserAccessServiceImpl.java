/**
 * Copyright (c) 2014, JingGe Technologies, Ltd. All rights reserved.
 */
package com.jingge.platform.core.identity.service.impl;

import java.util.List;

import org.springframework.stereotype.Component;

import com.jingge.platform.core.identity.dao.DefaultUserAccessDao;
import com.jingge.platform.core.identity.dataobject.DefaultUserAccess;
import com.jingge.platform.core.identity.service.DefaultUserAccessService;

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

@Component( "defaultUserAccessService" )
public class DefaultUserAccessServiceImpl 
    extends AbstractUserAccessServiceImpl<DefaultUserAccess, DefaultUserAccessDao> 
    implements DefaultUserAccessService{

    @Override
    public List<String> listUsernames(List<String> userIds) {
        return null;
    }

    @Override
    public DefaultUserAccess loadByUsername(String username) {
        return getDao().loadByUsername( username );
    }

}
