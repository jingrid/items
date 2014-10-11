/**
 * Copyright (c) 2012, RealPaaS Technologies, Ltd. All rights reserved.
 */
package com.realpaas.platform.core.universal.dataobject;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.hibernate.annotations.Type;

import com.realpaas.platform.framework.dataobject.AbstractPlatformAccountability;
import com.realpaas.platform.ssos.ext.dataobject.CreatedTimeTracking;
import com.realpaas.platform.ssos.ext.dataobject.Lifecycling;
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
@Table(name="PLF_OBJECT_TYPE_ACC")
@Cache(usage=CacheConcurrencyStrategy.READ_WRITE)
public class ObjectTypeAccountability 
    extends AbstractPlatformAccountability<ObjectType, ObjectTypeAccountabilityType>
    implements
        Lifecycling,
        CreatedTimeTracking,
        UpdatedTimeTracking,
        RankTracking{

    private static final long serialVersionUID = 6081967086178794673L;

    private LifeFlag lifeFlag = LifeFlag.Active;

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
