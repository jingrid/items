/**
 * Copyright (c) 2014, JingGe Technologies, Ltd. All rights reserved.
 */
package com.jingge.platform.core.infras.dataobject;

import javax.persistence.Column;
import javax.persistence.DiscriminatorColumn;
import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.Table;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.hibernate.annotations.Type;

import com.jingge.platform.core.universal.dataobject.ObjectTypeTreeCoding;
import com.realpaas.platform.ssos.core.dataobject.GenericDataObject;
import com.realpaas.platform.ssos.ext.dataobject.Coding;
import com.realpaas.platform.ssos.ext.dataobject.DescriptionTracking;
import com.realpaas.platform.ssos.ext.dataobject.Lifecycling;
import com.realpaas.platform.ssos.ext.dataobject.Naming;
import com.realpaas.platform.ssos.ext.dataobject.enumtype.LifeFlag;
import com.realpaas.platform.ssos.ext.dataobject.usertype.LifeFlagUserType;

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
 * 	2012-8-5	henryleu	Create the class
 * </dd>
 * 
 * </dl>
 * @author	henryleu Email/MSN: hongli_leu@126.com
 */

@org.hibernate.annotations.TypeDefs({
    @org.hibernate.annotations.TypeDef(name = LifeFlagUserType.USER_TYPE_ID, typeClass = LifeFlagUserType.class)
})
@Entity
@Table(name="PLF_ENTITY")
@Inheritance( strategy = InheritanceType.SINGLE_TABLE )
@DiscriminatorColumn( name = ObjectTypeTreeCoding.CLMN_OBJECT_TYPE_TREE_CODE )
@DiscriminatorValue( AbstractEntity.OBJECT_TYPE_TREE_CODE )
@Cache(usage=CacheConcurrencyStrategy.READ_WRITE)
public class AbstractEntity extends GenericDataObject
    implements 
        ObjectTypeTreeCoding,
        Lifecycling,
        Coding,
        Naming,
        DescriptionTracking{

    private static final long serialVersionUID = 8967617662416773690L;
    
    public static final String OBJECT_TYPE_TREE_CODE = "ABSTRACT";
    
    public static final String OBJECT_TYPE_NAME = "Abstract Entity";
    
    private String objectTypeCode;
    
    private LifeFlag lifeFlag = LifeFlag.Active;

    private String code;
    
    private String name;

    private String description;
    
    @Override
    @Column(name = ObjectTypeTreeCoding.CLMN_OBJECT_TYPE_TREE_CODE, insertable = false, updatable = false, nullable = false, length = 50)
    public String getObjectTypeTreeCode() {
        return objectTypeCode;
    }

    public void setObjectTypeTreeCode(String objectTypeCode) {
        this.objectTypeCode = objectTypeCode;
    }
    
    @Override
    @Type(type=LifeFlagUserType.USER_TYPE_ID)
    @Column(name = Lifecycling.CLMN_LIFE_FLAG, insertable = true, updatable = true, nullable = false)
    public LifeFlag getLifeFlag() {
        return lifeFlag;
    }

    @Override
    public void setLifeFlag(LifeFlag lifeFlag) {
        this.lifeFlag = lifeFlag;
    }
    
    @Override
    @Column(name = Coding.CLMN_CODE, insertable = true, updatable = true, nullable = true, length = 100)
    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }
    
    @Override
    @Column(name = Naming.CLMN_NAME, insertable = true, updatable = true, nullable = false, length = 255)
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
    
    @Override
    @Column(name = "DESCRIPTION", insertable = true, updatable = true, nullable = true, length = 1000)
    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
