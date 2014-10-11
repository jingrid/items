/**
 * Copyright (c) 2012, RealPaaS Technologies, Ltd. All rights reserved.
 */
package com.realpaas.platform.core.identity.dataobject;

import javax.persistence.Column;
import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;

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
 * 	2012-7-28	henryleu	Create the class
 * </dd>
 * 
 * </dl>
 * @author	henryleu Email/MSN: hongli_leu@126.com
 */

@Entity
@DiscriminatorValue( DefaultUserAccess.OBJECT_TYPE_TREE_CODE )
@Cache( usage=CacheConcurrencyStrategy.READ_WRITE )
public class DefaultUserAccess extends UserAccess {

    private static final long serialVersionUID = -3564987895325777963L;

    public static final String OBJECT_TYPE_TREE_CODE = "DEFAULT";
    
    public static final String OBJECT_TYPE_NAME = "Default User Access";
    
    public static final String TYPE_CODE = "DEFAULT";
    
    public static final String TYPE_NAME = "Default User Access";
    
    private String username;
    
    private String password;
    
    private byte passwordStrength = 0;
    
    @Column(name = "USERNAME", insertable = true, updatable = true, nullable = false, length = 100)
    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    @Column(name = "PASSWORD", insertable = true, updatable = true, nullable = true, length = 100)
    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    @Column(name = "PASSWORD_STRENGTH", insertable = true, updatable = true, nullable = true)
    public byte getPasswordStrength() {
        return passwordStrength;
    }

    public void setPasswordStrength(byte passwordStrength) {
        this.passwordStrength = passwordStrength;
    }
}
