/**
 * Copyright (c) 2012, RealPaaS Technologies, Ltd. All rights reserved.
 */
package com.realpaas.platform.core.universal.dao.hibernate;

import org.springframework.beans.factory.annotation.Autowired;

import com.realpaas.platform.core.universal.dao.AbstractObjectTypeDao;
import com.realpaas.platform.core.universal.dao.ObjectTypeTypeDao;
import com.realpaas.platform.core.universal.dataobject.AbstractObjectType;
import com.realpaas.platform.core.universal.dataobject.ObjectTypeType;
import com.realpaas.platform.core.universal.dataobject.ObjectTypeTypeCoding;
import com.realpaas.platform.framework.dao.hibernate.PlatformTypeDaoTemplate;

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
 * 	Jul 21, 2012	henryleu	Create the class
 * </dd>
 * 
 * </dl>
 * @author	henryleu Email/MSN: hongli_leu@126.com
 */

public abstract class AbstractObjectTypeDaoImpl<Do extends AbstractObjectType> 
    extends PlatformTypeDaoTemplate<Do> 
    implements 
        AbstractObjectTypeDao<Do>,
        ObjectTypeTypeCoding{

    private ObjectTypeTypeDao objectTypeTypeDao;
    
    protected String typeCode;

    @Override
    public String getTypeCode() {
        return typeCode;
    }
    
    @Override
    public void setTypeCode(String typeCode) {
        this.typeCode = typeCode;
    }

    @Autowired( required=true )
    public void setObjectTypeTypeDao(ObjectTypeTypeDao objectTypeTypeDao) {
        this.objectTypeTypeDao = objectTypeTypeDao;
    }

    @Override
    public Do create(Do dataObject) {
        ObjectTypeType type = objectTypeTypeDao.loadByCode( getTypeCode() );
        dataObject.setTypeId( type.getId() );
        return super.create( dataObject );
    }
    
}
