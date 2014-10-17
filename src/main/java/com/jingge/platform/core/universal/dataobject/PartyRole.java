/**
 * Copyright (c) 2014, JingGe Technologies, Ltd. All rights reserved.
 */
package com.jingge.platform.core.universal.dataobject;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.DiscriminatorColumn;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.hibernate.annotations.Type;

import com.realpaas.platform.framework.dataobject.AbstractPlatformDataObject;
import com.realpaas.platform.ssos.core.dataobject.id.Identifier;
import com.realpaas.platform.ssos.core.dataobject.usertype.GenericIdentifierUserType;
import com.realpaas.platform.ssos.ext.dataobject.CreatedTimeTracking;
import com.realpaas.platform.ssos.ext.dataobject.Lifecycling;
import com.realpaas.platform.ssos.ext.dataobject.Naming;
import com.realpaas.platform.ssos.ext.dataobject.UpdatedTimeTracking;
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
@Table( name="PLF_PARTY_ROLE" )
@Inheritance( strategy = InheritanceType.SINGLE_TABLE )
@DiscriminatorColumn( name = ObjectTypeTreeCoding.CLMN_OBJECT_TYPE_TREE_CODE )
public class PartyRole extends AbstractPlatformDataObject
    implements 
        ObjectTypeTreeCoding,
        Lifecycling,
        PartyTracking<Party>,
        Naming,
        CreatedTimeTracking,
        UpdatedTimeTracking{

    private static final long serialVersionUID = 1285699542247937278L;

    public static final String OBJECT_TYPE_TREE_CODE = "PARTY_ROLE";
    
    public static final String OBJECT_TYPE_NAME = "Party Role";
    
    private String objectTypeCode;
    
    private LifeFlag lifeFlag = LifeFlag.Active;

    private String name;
    
    private Identifier<?> partyId;

    private Party party;

    private Date createdTime;

    private Date updatedTime;
    
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
    
    @Type(type = GenericIdentifierUserType.USER_TYPE_ID)
    @Column(name = PartyTracking.CLMN_PARTY_ID, insertable = true, updatable = true, nullable = true)
    @Override
    public Identifier<?> getPartyId() {
        return partyId;
    }

    @Override
    public void setPartyId(Identifier<?> partyId) {
        this.partyId = partyId;
    }

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = PartyTracking.CLMN_PARTY_ID, insertable = false, updatable = false, nullable = true)
    @Override
    public Party getParty() {
        return party;
    }

    @Override
    public void setParty(Party party) {
        this.party = party;
    }

    @Override
    @Column(name = Naming.CLMN_NAME, insertable = true, updatable = true, nullable = false, length = 200)
    public String getName() {
        return name;
    }

    @Override
    public void setName(String name) {
        this.name = name;
    }

    @Override
    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = CreatedTimeTracking.CLMN_CREATED_TIME, insertable = true, updatable = false, nullable = false)
    public Date getCreatedTime() {
        return createdTime;
    }

    @Override
    public void setCreatedTime(Date createdTime) {
        this.createdTime = createdTime;
    }

    @Override
    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = UpdatedTimeTracking.CLMN_UPDATED_TIME, insertable = true, updatable = true, nullable = false)
    public Date getUpdatedTime() {
        return updatedTime;
    }

    @Override
    public void setUpdatedTime(Date updatedTime) {
        this.updatedTime = updatedTime;
    }

}
