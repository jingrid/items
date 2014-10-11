/**
 * Copyright (c) 2012, RealPaaS Technologies, Ltd. All rights reserved.
 */
package com.realpaas.platform.core.file.dao.hibernate;

import com.realpaas.platform.core.file.dao.AbstractFileStorageDao;
import com.realpaas.platform.core.file.dataobject.FileStorage;
import com.realpaas.platform.framework.dao.hibernate.PlatformDaoTemplate;
import com.realpaas.platform.ssos.core.dataobject.id.Identifier;
import com.realpaas.platform.ssos.ext.dao.hibernate.DaoLifecyclingDelegate;
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
public abstract class AbstractFileStorageDaoImpl<Do extends FileStorage> extends PlatformDaoTemplate<Do>
    implements AbstractFileStorageDao<Do>{

    private DaoLifecyclingDelegate<Do> lifecyclingDelegate;
    
    @Override
    protected void initDao() throws Exception {
        super.initDao();
        lifecyclingDelegate = new DaoLifecyclingDelegate<Do>( delegate );
    }
    
    @Override
    public void updateLifeFlag(Identifier<?> id, LifeFlag lifeFlag) {
        lifecyclingDelegate.updateLifeFlag( id, lifeFlag );
    }

    @Override
    public void markActive(Identifier<?> id) {
        lifecyclingDelegate.markActive( id );
    }

    @Override
    public void markInactive(Identifier<?> id) {
        lifecyclingDelegate.markInactive( id );
    }

    @Override
    public void markDeleted(Identifier<?> id) {
        lifecyclingDelegate.markDeleted( id );
    }
    
}
