/**
 * Copyright (c) 2012, RealPaaS Technologies, Ltd. All rights reserved.
 */
package com.realpaas.platform.core.program.dao.hibernate;

import org.springframework.stereotype.Repository;

import com.realpaas.platform.core.program.dao.ProgramUnitDao;
import com.realpaas.platform.core.program.dataobject.ProgramUnit;
import com.realpaas.platform.framework.dao.hibernate.PlatformDaoTemplate;
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
 * 	Apr 3, 2012	henryleu	Create the class
 * </dd>
 * 
 * </dl>
 * @author	henryleu Email/MSN: hongli_leu@126.com
 */

@Repository( "programUnitDao" )
public class ProgramUnitDaoImpl<Do extends ProgramUnit>  extends PlatformDaoTemplate<Do> implements ProgramUnitDao<Do> {

    private DaoLoadingByUniquePropertyDelegate<Do> loadingByUniquePropDelegate;

    private DaoDeletingAllDelegate deletingAllDelegate;
    
//    private ProgramUnitTypeDao programUnitTypeDao;
//    
//    private String typeCode;
//
//    public void setTypeCode(String typeCode) {
//        this.typeCode = typeCode;
//    }
//
//    @Autowired( required=true )
//    public void setProgramUnitTypeDao(ProgramUnitTypeDao programUnitTypeDao) {
//        this.programUnitTypeDao = programUnitTypeDao;
//    }

    @Override
    protected void initDao() throws Exception {
        super.initDao();
        loadingByUniquePropDelegate = new DaoLoadingByUniquePropertyDelegate<Do>( delegate );
        deletingAllDelegate = new DaoDeletingAllDelegate();
        deletingAllDelegate.setHibernateTemplate( getHibernateTemplate() );
        deletingAllDelegate.setDataObjectName( getDataObjectName() );
        deletingAllDelegate.setDataObjectClass( getDataObjectClass() );
    }

    @Override
    public Do loadByCode(String code) {
        return loadingByUniquePropDelegate.loadByUniqueProperty( Coding.PROP_CODE, code, null );
    }

    @Override
    public int deleteAll() {
        return deletingAllDelegate.deleteAll();
    }

    @Override
    public Do create(Do dataObject) {
//        ProgramUnitType type = programUnitTypeDao.loadByCode( typeCode );
//        dataObject.setTypeId( type.getId() );
        return super.create( dataObject );
    }
    
}
