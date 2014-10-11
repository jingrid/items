/**
 * Copyright (c) 2012, RealPaaS Technologies, Ltd. All rights reserved.
 */
package com.realpaas.platform.core.i18n.dao;

import com.realpaas.platform.core.i18n.dataobject.LocaleEntry;
import com.realpaas.platform.framework.dao.DaoLoadingByCodeAndTenantId;
import com.realpaas.platform.framework.dao.PlatformDao;
import com.realpaas.platform.ssos.ext.dao.DaoDeletingAll;
import com.realpaas.platform.ssos.ext.dao.DaoFindingByLifeFlag;
import com.realpaas.platform.ssos.ext.dao.DaoLoadingByCode;
import com.realpaas.platform.ssos.ext.dao.DaoUpdatingLifeFlag;

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
 *  Mar 31, 2012    henry leu   Create the class
 * </dd>
 * 
 * </dl>
 * @author  henry leu Email/MSN: hongli_leu@126.com
 */

public interface LocaleEntryDao
    extends
        PlatformDao<LocaleEntry>,
        DaoLoadingByCode<LocaleEntry>,
        DaoLoadingByCodeAndTenantId<LocaleEntry>,
        DaoUpdatingLifeFlag,
        DaoFindingByLifeFlag<LocaleEntry>,
        DaoDeletingAll{
}
