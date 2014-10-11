/**
 * Copyright (c) 2012, RealPaaS Technologies, Ltd. All rights reserved.
 */
package com.realpaas.platform.core.infras.dataobject;

import javax.persistence.Column;
import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.SecondaryTable;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

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
 * 	Aug 5, 2012	henry leu	Create the class
 * </dd>
 * 
 * </dl>
 * @author	henry leu Email/MSN: hongli_leu@126.com
 */

@Entity
@DiscriminatorValue( SystemEntity.OBJECT_TYPE_TREE_CODE )
@SecondaryTable( name="PLF_SYSTEM_ENTITY" )
@Cache(usage=CacheConcurrencyStrategy.READ_WRITE)
public class SystemEntity extends AbstractEntity {

    private static final long serialVersionUID = -8787629443683242121L;
    
    public static final String OBJECT_TYPE_TREE_CODE = "SYSTEM";
    
    public static final String OBJECT_TYPE_NAME = "System Entity";

    private long serialNo;
    
    private int licenseVersion;

    @Column( table="PLF_SYSTEM_ENTITY", name = "SERIAL_NO", insertable = true, updatable = true, nullable = false )
    public long getSerialNo() {
        return serialNo;
    }

    public void setSerialNo(long serialNo) {
        this.serialNo = serialNo;
    }

    @Column( table="PLF_SYSTEM_ENTITY", name = "LICENSE_VERSION", insertable = true, updatable = true, nullable = false )
    public int getLicenseVersion() {
        return licenseVersion;
    }

    public void setLicenseVersion(int licenseVersion) {
        this.licenseVersion = licenseVersion;
    }

}
