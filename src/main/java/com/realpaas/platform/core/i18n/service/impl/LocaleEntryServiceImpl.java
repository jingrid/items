/**
 * Copyright (c) 2012, RealPaaS Technologies, Ltd. All rights reserved.
 */
package com.realpaas.platform.core.i18n.service.impl;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Component;

import com.realpaas.platform.core.i18n.dao.LocaleEntryDao;
import com.realpaas.platform.core.i18n.dataobject.LocaleEntry;
import com.realpaas.platform.core.i18n.service.LocaleEntryService;
import com.realpaas.platform.framework.service.impl.PlatformServiceTemplate;
import com.realpaas.platform.ssos.core.dataobject.id.Identifier;
import com.realpaas.platform.ssos.ext.dataobject.enumtype.LifeFlag;
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

@Component( "localeEntryService" )
public class LocaleEntryServiceImpl 
    extends PlatformServiceTemplate<LocaleEntry, LocaleEntryDao>
    implements LocaleEntryService {

    @Override
    public LocaleEntry instantiate() {
        return getDao().instantiate( null );
    }

    @Override
    public LocaleEntry instantiate(Map<String, Object> initParams) {
        return getDao().instantiate( initParams );
    }

    @Override
    public LocaleEntry loadByCode(String code) {
        return getDao().loadByCode( code );
    }

    @Override
    public LocaleEntry loadByCodeAndTenantId(String code, Identifier<?> tenantId) {
        return getDao().loadByCodeAndTenantId( code, tenantId );
    }

    @Override
    public List<LocaleEntry> findByLifeFlag(LifeFlagFilter filter) {
        return getDao().findByLifeFlag( filter );
    }

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

    @Override
    public int deleteAll() {
        return getDao().deleteAll();
    }
}
