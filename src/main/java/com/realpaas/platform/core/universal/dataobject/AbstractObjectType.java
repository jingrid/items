/**
 * Copyright (c) 2012, RealPaaS Technologies, Ltd. All rights reserved.
 */
package com.realpaas.platform.core.universal.dataobject;

import javax.persistence.Column;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.MappedSuperclass;

import org.hibernate.annotations.Type;

import com.realpaas.platform.framework.dataobject.AbstractPlatformType;
import com.realpaas.platform.ssos.core.dataobject.id.Identifier;
import com.realpaas.platform.ssos.core.dataobject.usertype.GenericIdentifierUserType;

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

@MappedSuperclass
public abstract class AbstractObjectType extends AbstractPlatformType
    implements 
        ObjectTypeTypeCoding,
        ObjectTypeTyping{

    private static final long serialVersionUID = 3046868218065419661L;

    private String typeCode;

    private Identifier<?> typeId;

    private ObjectTypeType type;

    @Override
    @Column(name = ObjectTypeTypeCoding.CLMN_TYPE_CODE, insertable = false, updatable = false, nullable = false, length = 50)
    public String getTypeCode() {
        return typeCode;
    }

    public void setTypeCode(String typeCode) {
        this.typeCode = typeCode;
    }

    @Type(type = GenericIdentifierUserType.USER_TYPE_ID)
    @Column(name = "TYPE_ID", insertable = true, updatable = true, nullable = false)
    public Identifier<?> getTypeId() {
        return typeId;
    }

    public void setTypeId(Identifier<?> typeId) {
        this.typeId = typeId;
    }

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "TYPE_ID", insertable = false, updatable = false, nullable = false)
    public ObjectTypeType getType() {
        return type;
    }

    public void setType(ObjectTypeType type) {
        this.type = type;
    }
}
