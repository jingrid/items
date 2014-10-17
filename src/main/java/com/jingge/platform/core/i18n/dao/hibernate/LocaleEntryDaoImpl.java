/**
 * Copyright (c) 2014, JingGe Technologies, Ltd. All rights reserved.
 */
package com.jingge.platform.core.i18n.dao.hibernate;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.jingge.platform.core.i18n.dao.LocaleEntryDao;
import com.jingge.platform.core.i18n.dataobject.LocaleEntry;
import com.realpaas.platform.framework.dao.hibernate.PlatformDaoTemplate;
import com.realpaas.platform.framework.dataobject.PlatformTracking;
import com.realpaas.platform.framework.util.CollectionUtil;
import com.realpaas.platform.ssos.core.dao.DaoQueryOptions;
import com.realpaas.platform.ssos.core.dataobject.id.Identifier;
import com.realpaas.platform.ssos.ext.dao.hibernate.DaoDeletingAllDelegate;
import com.realpaas.platform.ssos.ext.dao.hibernate.DaoFindingByUniquePropertiesDelegate;
import com.realpaas.platform.ssos.ext.dao.hibernate.DaoLifecyclingDelegate;
import com.realpaas.platform.ssos.ext.dataobject.Coding;
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
 *  Mar 31, 2012    henry leu   Create the class
 * </dd>
 * 
 * </dl>
 * @author  henry leu Email/MSN: hongli_leu@126.com
 */

@Repository( "localeEntryDao" )
public class LocaleEntryDaoImpl extends PlatformDaoTemplate<LocaleEntry> implements LocaleEntryDao{

    private DaoFindingByUniquePropertiesDelegate<LocaleEntry> findingByUniquePropertiesDelegate;
    
    private DaoDeletingAllDelegate deletingAllDelegate;

    private DaoLifecyclingDelegate<LocaleEntry> lifecyclingDelegate;
    
    @Override
    protected void initDao() throws Exception {
        super.initDao();
        findingByUniquePropertiesDelegate = new DaoFindingByUniquePropertiesDelegate<LocaleEntry>( delegate );
        lifecyclingDelegate = new DaoLifecyclingDelegate<LocaleEntry>( delegate );
        deletingAllDelegate = new DaoDeletingAllDelegate();
        deletingAllDelegate.setHibernateTemplate( getHibernateTemplate() );
        deletingAllDelegate.setDataObjectName( getDataObjectName() );
        deletingAllDelegate.setDataObjectClass( getDataObjectClass() );
    }

    @Override
    public LocaleEntry loadByCode(String code) {
        List<LocaleEntry> list = doFindByCode( code, getSystem().getId() );
        return CollectionUtil.uniqueItem( list );
    }

    @Override
    public LocaleEntry loadByCodeAndTenantId(String code, Identifier<?> tenantId) {
        List<LocaleEntry> list = doFindByCode( code, tenantId );
        return CollectionUtil.uniqueItem( list );
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
    
    @Override
    public List<LocaleEntry> findByLifeFlag(LifeFlagFilter filter) {
        return lifecyclingDelegate.findByLifeFlag( filter );
    }

    @Override
    public <O extends DaoQueryOptions> List<LocaleEntry> findByLifeFlag(LifeFlagFilter filter, O queryOption) {
        return lifecyclingDelegate.findByLifeFlag( filter, queryOption );
    }

    @Override
    public int deleteAll() {
        return deletingAllDelegate.deleteAll();
    }

    private List<LocaleEntry> doFindByCode(String code, Identifier<?> platformId) {
        return findingByUniquePropertiesDelegate.findByUniqueProperties( 
                new String[] { Coding.PROP_CODE, PlatformTracking.PROP_TENANT_ID }, 
                new Object[] { code, platformId }, 
                null );
    }
}
