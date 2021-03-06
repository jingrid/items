/**
 * Copyright (c) 2014, JingGe Technologies, Ltd. All rights reserved.
 */
package com.jingge.platform.core.file.service.impl;

import com.jingge.platform.core.file.dao.AbstractFileStorageDao;
import com.jingge.platform.core.file.dataobject.FileStorage;
import com.jingge.platform.core.file.service.AbstractFileStorageService;
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
 * 	2012-8-8	henryleu	Create the class
 * </dd>
 * 
 * </dl>
 * @author	henryleu Email/MSN: hongli_leu@126.com
 */

public abstract class AbstractFileStorageServiceImpl<Do extends FileStorage, Dao extends AbstractFileStorageDao<Do>> 
    extends PlatformServiceTemplate<Do, Dao>
    implements AbstractFileStorageService<Do>{

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
