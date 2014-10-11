/**
 * Copyright (c) 2012, RealPaaS Technologies, Ltd. All rights reserved.
 */
package com.realpaas.platform.core.identity.dataobject;

import javax.persistence.Column;
import javax.persistence.DiscriminatorColumn;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import org.hibernate.annotations.Type;

import com.realpaas.platform.core.universal.dataobject.ObjectTypeTreeCoding;
import com.realpaas.platform.framework.dataobject.AbstractPlatformDataObject;
import com.realpaas.platform.ssos.core.dataobject.id.Identifier;
import com.realpaas.platform.ssos.core.dataobject.usertype.GenericIdentifierUserType;
import com.realpaas.platform.ssos.ext.dataobject.Lifecycling;
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
 * 	2012-7-28	henryleu	Create the class
 * </dd>
 * 
 * </dl>
 * @author	henryleu Email/MSN: hongli_leu@126.com
 */

@Entity
@Table( name="PLF_USER_ACCESS" )
@Inheritance( strategy = InheritanceType.SINGLE_TABLE )
@DiscriminatorColumn( name = ObjectTypeTreeCoding.CLMN_OBJECT_TYPE_TREE_CODE )
public abstract class UserAccess extends AbstractPlatformDataObject
    implements
        ObjectTypeTreeCoding,
        Lifecycling,
//        AccessTypeTracking,
        UserTracking{

    private static final long serialVersionUID = -7454091531140051033L;

    public static final String OBJECT_TYPE_TREE_CODE = "ROOT";
    
    public static final String OBJECT_TYPE_NAME = "User Access (Signin approach)";
    
    private String objectTypeTreeCode;
    
    private LifeFlag lifeFlag = LifeFlag.Active;
    
    private String typeCode;
    
//    private Identifier<?> accessTypeId;
//    
//    private UserAccessType accessType;
    
    private Identifier<?> userId;
    
    private User user;
    
    @Column(name = ObjectTypeTreeCoding.CLMN_OBJECT_TYPE_TREE_CODE, insertable = false, updatable = false, nullable = false, length = 10)
    public String getObjectTypeTreeCode() {
        return objectTypeTreeCode;
    }
    
    public void setObjectTypeTreeCode(String objectTypeTreeCode) {
        this.objectTypeTreeCode = objectTypeTreeCode;
    }

    @Override
    @Type(type=LifeFlagUserType.USER_TYPE_ID)
    @Column(name = "LIFE_FLAG", insertable = true, updatable = true, nullable = false)
    public LifeFlag getLifeFlag() {
        return lifeFlag;
    }

    @Override
    public void setLifeFlag(LifeFlag lifeFlag) {
        this.lifeFlag = lifeFlag;
    }

//    @Type(type = GenericIdentifierUserType.USER_TYPE_ID)
//    @Column(name = AccessTypeTracking.CLMN_ACCESS_TYPE_ID, insertable = true, updatable = true, nullable = false)
//    @Override
//    public Identifier<?> getAccessTypeId() {
//        return accessTypeId;
//    }
//
//    @Override
//    public void setAccessTypeId(Identifier<?> accessTypeId) {
//        this.accessTypeId = accessTypeId;
//    }
//
//    @ManyToOne(fetch = FetchType.LAZY)
//    @JoinColumn(name = AccessTypeTracking.CLMN_ACCESS_TYPE_ID, insertable = false, updatable = false, nullable = false)
//    @Override
//    public UserAccessType getAccessType() {
//        return accessType;
//    }
//
//    @Override
//    public void setAccessType(UserAccessType accessType) {
//        this.accessType = accessType;
//    }

    @Column(name = ObjectTypeTreeCoding.CLMN_OBJECT_TYPE_TREE_CODE, insertable = false, updatable = false, nullable = false, length = 10)
    public String getTypeCode() {
        return typeCode;
    }

    public void setTypeCode(String typeCode) {
        this.typeCode = typeCode;
    }
    
    @Type(type = GenericIdentifierUserType.USER_TYPE_ID)
    @Column(name = UserTracking.CLMN_USER_ID, insertable = true, updatable = true, nullable = false)
    @Override
    public Identifier<?> getUserId() {
        return userId;
    }

    @Override
    public void setUserId(Identifier<?> userId) {
        this.userId = userId;
    }

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = UserTracking.CLMN_USER_ID, insertable = false, updatable = false, nullable = false)
    @Override
    public User getUser() {
        return user;
    }

    @Override
    public void setUser(User user) {
        this.user = user;
    }

}
