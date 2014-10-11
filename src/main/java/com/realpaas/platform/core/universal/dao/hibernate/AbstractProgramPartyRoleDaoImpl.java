/**
 * Copyright (c) 2012, RealPaaS Technologies, Ltd. All rights reserved.
 */
package com.realpaas.platform.core.universal.dao.hibernate;

import java.util.List;

import com.realpaas.platform.core.universal.dao.AbstractProgramPartyRoleDao;
import com.realpaas.platform.core.universal.dataobject.ProgramPartyRole;
import com.realpaas.platform.ssos.core.dao.DaoQueryOptions;
import com.realpaas.platform.ssos.ext.dao.hibernate.DaoLifecyclingDelegate;
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
public abstract class AbstractProgramPartyRoleDaoImpl<Do extends ProgramPartyRole> 
    extends AbstractPartyRoleDaoImpl<Do>
    implements AbstractProgramPartyRoleDao<Do>{

    private DaoLifecyclingDelegate<Do> lifecyclingDelegate;
    
    @Override
    protected void initDao() throws Exception {
        super.initDao();
        lifecyclingDelegate = new DaoLifecyclingDelegate<Do>( delegate );
    }

    @Override
    public List<Do> findByLifeFlag(LifeFlagFilter filter) {
        return lifecyclingDelegate.findByLifeFlag( filter );
    }

    @Override
    public <O extends DaoQueryOptions> List<Do> findByLifeFlag(LifeFlagFilter filter, O queryOption) {
        return lifecyclingDelegate.findByLifeFlag( filter, queryOption );
    }

}
