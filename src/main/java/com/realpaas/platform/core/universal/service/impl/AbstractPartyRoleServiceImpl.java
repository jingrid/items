/**
 * Copyright (c) 2012, RealPaaS Technologies, Ltd. All rights reserved.
 */
package com.realpaas.platform.core.universal.service.impl;

import com.realpaas.platform.core.universal.dao.AbstractPartyRoleDao;
import com.realpaas.platform.core.universal.dataobject.PartyRole;
import com.realpaas.platform.core.universal.service.AbstractPartyRoleService;
import com.realpaas.platform.framework.service.impl.PlatformServiceTemplate;
import com.realpaas.platform.ssos.core.dataobject.id.Identifier;
import com.realpaas.platform.ssos.ext.dataobject.enumtype.LifeFlag;

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

public abstract class AbstractPartyRoleServiceImpl<Do extends PartyRole, Dao extends AbstractPartyRoleDao<Do>> 
    extends PlatformServiceTemplate<Do, Dao>
    implements AbstractPartyRoleService<Do>{

    @Override
    public void updateLifeFlag(Identifier<?> id, LifeFlag lifeFlag) {
        getDao().updateLifeFlag( id, lifeFlag );
    }

    @Override
    public void markActive(Identifier<?> id) {
        getDao().markActive( id );
    }

    @Override
    public void markInactive(Identifier<?> id) {
        getDao().markInactive( id );
    }

    @Override
    public void markDeleted(Identifier<?> id) {
        getDao().markDeleted( id );
    }

}
