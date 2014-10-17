/**
 * Copyright (c) 2012, RealPaaS Technologies, Ltd. All rights reserved.
 */
package com.jingge.platform.core.i18n.dataobject;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

import org.hibernate.annotations.Type;

import com.realpaas.platform.framework.dataobject.AbstractPlatformDataObject;
import com.realpaas.platform.framework.dataobject.PlatformTracking;
import com.realpaas.platform.ssos.ext.dataobject.Coding;
import com.realpaas.platform.ssos.ext.dataobject.DescriptionTracking;
import com.realpaas.platform.ssos.ext.dataobject.Lifecycling;
import com.realpaas.platform.ssos.ext.dataobject.Naming;
import com.realpaas.platform.ssos.ext.dataobject.RankTracking;
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
 * 	Mar 31, 2012	henry leu	Create the class
 * </dd>
 * 
 * </dl>
 * @author	henry leu Email/MSN: hongli_leu@126.com
 */

@org.hibernate.annotations.TypeDefs({
    @org.hibernate.annotations.TypeDef(name = LifeFlagUserType.USER_TYPE_ID, typeClass = LifeFlagUserType.class)
})
@Entity
@Table(name="PLF_LOCALE")
public class LocaleEntry extends AbstractPlatformDataObject
    implements
        PlatformTracking,
        Coding,
        Naming,
        DescriptionTracking,
        Lifecycling,
        RankTracking{

    private static final long serialVersionUID = -6906402648406348989L;

    public static final String CODE_ZH_CN = "zh_CN";

    public static final String NAME_ZH_CN = "简体中文";

    public static final String CODE_EN = "en";

    public static final String NAME_EN = "English";

    private LifeFlag lifeFlag = LifeFlag.Active;

    private String code;

    private String name;

    private String description;

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
    @Column(name = "CODE", insertable = true, updatable = true, nullable = false, length = 100)
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
    @Column(name = "RANK", insertable = true, updatable = true, nullable = true)
    public double getRank() {
        return rank;
    }

    @Override
    public void setRank(double rank) {
        this.rank = rank;
    }

}
