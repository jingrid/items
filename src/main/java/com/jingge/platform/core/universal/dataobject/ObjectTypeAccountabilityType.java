/**
 * Copyright (c) 2014, JingGe Technologies, Ltd. All rights reserved.
 */
package com.jingge.platform.core.universal.dataobject;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.hibernate.annotations.Type;

import com.realpaas.platform.framework.dataobject.AbstractPlatformLink;
import com.realpaas.platform.ssos.ext.dataobject.Coding;
import com.realpaas.platform.ssos.ext.dataobject.CreatedTimeTracking;
import com.realpaas.platform.ssos.ext.dataobject.DescriptionTracking;
import com.realpaas.platform.ssos.ext.dataobject.Lifecycling;
import com.realpaas.platform.ssos.ext.dataobject.Naming;
import com.realpaas.platform.ssos.ext.dataobject.RankTracking;
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
 * 	Jul 21, 2012	henryleu	Create the class
 * </dd>
 * 
 * </dl>
 * @author	henryleu Email/MSN: hongli_leu@126.com
 */

@Entity
@Table(name="PLF_OBJECT_TYPE_ACC_TYPE")
@Cache(usage=CacheConcurrencyStrategy.READ_WRITE)
public class ObjectTypeAccountabilityType 
    extends AbstractPlatformLink<ObjectTypeType, ObjectTypeType>
    implements 
        Coding,
        Naming,
        DescriptionTracking,
        Lifecycling,
        CreatedTimeTracking,
        UpdatedTimeTracking,
        RankTracking{
    
    private static final long serialVersionUID = 5027586496878019556L;

    private LifeFlag lifeFlag = LifeFlag.Active;
    
    private String code;

    private String name;

    private String description;

    private Date createdTime;

    private Date updatedTime;
    
    private double rank;
    
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
    
    @Override
    @Column(name = "CODE", insertable = true, updatable = true, nullable = false, length = 50)
    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    @Override
    @Column(name = "NAME", insertable = true, updatable = true, nullable = false, length = 200)
    public String getName() {
        return name;
    }

    @Override
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

    @Override
    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "CREATED_TIME", insertable = true, updatable = false, nullable = false)
    public Date getCreatedTime() {
        return createdTime;
    }

    @Override
    public void setCreatedTime(Date createdTime) {
        this.createdTime = createdTime;
    }

    @Override
    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "UPDATED_TIME", insertable = true, updatable = true, nullable = false)
    public Date getUpdatedTime() {
        return updatedTime;
    }

    @Override
    public void setUpdatedTime(Date updatedTime) {
        this.updatedTime = updatedTime;
    }

    @Override
    @Column(name = "RANK", insertable = true, updatable = true, nullable = true)
    public double getRank() {
        return rank;
    }

    @Override
    public void setRank(double rank) {
        this.rank = rank;
    }
}
