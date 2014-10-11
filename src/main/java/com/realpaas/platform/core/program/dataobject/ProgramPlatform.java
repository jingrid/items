/**
 * Copyright (c) 2012, RealPaaS Technologies, Ltd. All rights reserved.
 */
package com.realpaas.platform.core.program.dataobject;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import com.realpaas.platform.core.infras.dataobject.SystemEntity;
import com.realpaas.platform.framework.dataobject.PlatformTracking;

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
 * 	Apr 2, 2012	henry leu	Create the class
 * </dd>
 * 
 * </dl>
 * @author	henry leu Email/MSN: hongli_leu@126.com
 */

@Entity
@DiscriminatorValue( ProgramPlatform.OBJECT_TYPE_TREE_CODE )
@Cache( usage=CacheConcurrencyStrategy.READ_WRITE )
public class ProgramPlatform extends ProgramUnit{

    private static final long serialVersionUID = 5095671575935383302L;

    public static final String OBJECT_TYPE_TREE_CODE = "PLF";
    
    public static final String OBJECT_TYPE_NAME = "Platform";
    
    public static final String DEFAULT_CODE = "realpaas";

    public static final String DEFAULT_NAME = "RealPaaS Platform";

    private SystemEntity systemEntity;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = PlatformTracking.CLMN_TENANT_ID, insertable = false, updatable = false, nullable = true)
    public SystemEntity getSystemEntity() {
        return systemEntity;
    }

    public void setSystemEntity(SystemEntity systemEntity) {
        this.systemEntity = systemEntity;
    }
}
