/**
 * Copyright (c) 2014, JingGe Technologies, Ltd. All rights reserved.
 */
package com.jingge.platform.core.universal.service.impl;

import java.util.List;

import com.jingge.platform.core.universal.dao.AbstractProgramPartyRoleDao;
import com.jingge.platform.core.universal.dataobject.ProgramPartyRole;
import com.jingge.platform.core.universal.service.AbstractProgramPartyRoleService;
import com.realpaas.platform.ssos.ext.dataobject.enumtype.LifeFlagFilter;

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

public abstract class AbstractProgramPartyRoleServiceImpl<Do extends ProgramPartyRole, Dao extends AbstractProgramPartyRoleDao<Do>> 
    extends AbstractPartyRoleServiceImpl<Do, Dao>
    implements AbstractProgramPartyRoleService<Do>{

    @Override
    public List<Do> findByLifeFlag(LifeFlagFilter filter) {
        return getDao().findByLifeFlag( filter );
    }

}
