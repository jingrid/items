/**
 * Copyright (c) 2014, JingGe Technologies, Ltd. All rights reserved.
 */
package com.jingge.platform.core.infras.dao.hibernate;

import org.springframework.stereotype.Repository;

import com.jingge.platform.core.infras.dao.SystemEntityDao;
import com.jingge.platform.core.infras.dataobject.SystemEntity;
import com.realpaas.platform.framework.dao.hibernate.GenericDaoTemplate;
import com.realpaas.platform.ssos.ext.dao.hibernate.DaoDeletingAllDelegate;
import com.realpaas.platform.ssos.ext.dao.hibernate.DaoLoadingByUniquePropertyDelegate;
import com.realpaas.platform.ssos.ext.dataobject.Coding;

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
 * 	Jul 11, 2012	henryleu	Create the class
 * </dd>
 * 
 * </dl>
 * @author	henryleu Email/MSN: hongli_leu@126.com
 */

@Repository( "systemEntityDao" )
public class SystemEntityDaoImpl extends GenericDaoTemplate<SystemEntity> implements SystemEntityDao {

    private DaoLoadingByUniquePropertyDelegate<SystemEntity> loadingByUniquePropDelegate;

    private DaoDeletingAllDelegate deletingAllDelegate;
    
    @Override
    protected void initDao() throws Exception {
        super.initDao();
        loadingByUniquePropDelegate = new DaoLoadingByUniquePropertyDelegate<SystemEntity>( delegate );
        deletingAllDelegate = new DaoDeletingAllDelegate();
        deletingAllDelegate.setHibernateTemplate( getHibernateTemplate() );
        deletingAllDelegate.setDataObjectName( getDataObjectName() );
        deletingAllDelegate.setDataObjectClass( getDataObjectClass() );
    }

    @Override
    public com.jingge.platform.core.infras.dataobject.SystemEntity loadByCode(String code) {
        return loadingByUniquePropDelegate.loadByUniqueProperty( Coding.PROP_CODE, code, null );
    }

    @Override
    public int deleteAll() {
        return deletingAllDelegate.deleteAll();
    }

}
