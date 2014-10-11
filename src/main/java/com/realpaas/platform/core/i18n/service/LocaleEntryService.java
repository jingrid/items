package com.realpaas.platform.core.i18n.service;

import com.realpaas.platform.core.i18n.dataobject.LocaleEntry;
import com.realpaas.platform.framework.service.PlatformService;
import com.realpaas.platform.framework.service.ServiceLoadingByCodeAndTenantId;
import com.realpaas.platform.ssos.core.service.ServiceInstantiating;
import com.realpaas.platform.ssos.ext.service.ServiceDeletingAll;
import com.realpaas.platform.ssos.ext.service.ServiceFindingByLifeFlag;
import com.realpaas.platform.ssos.ext.service.ServiceLoadingByCode;
import com.realpaas.platform.ssos.ext.service.ServiceUpdatingLifeFlag;

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
 *  Apr 2, 2012    henry leu   Create the class
 * </dd>
 * 
 * </dl>
 * @author  henry leu Email/MSN: hongli_leu@126.com
 */
public interface LocaleEntryService
    extends 
        PlatformService<LocaleEntry>,
        ServiceLoadingByCode<LocaleEntry>,
        ServiceLoadingByCodeAndTenantId<LocaleEntry>,
        ServiceUpdatingLifeFlag,
        ServiceFindingByLifeFlag<LocaleEntry>,
        ServiceDeletingAll,
        ServiceInstantiating<LocaleEntry>{

}
