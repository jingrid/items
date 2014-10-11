/**
 * Copyright (c) 2012, RealPaaS Technologies, Ltd. All rights reserved.
 */
package com.realpaas.platform.core.universal.dataobject;

import javax.persistence.Column;
import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.hibernate.annotations.Type;

import com.realpaas.platform.core.program.dataobject.ProgramUnit;
import com.realpaas.platform.core.program.dataobject.ProgramUnitTracking;
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
 * 	2012-7-28	henryleu	Create the class
 * </dd>
 * 
 * </dl>
 * @author	henryleu Email/MSN: hongli_leu@126.com
 */

@Entity
@DiscriminatorValue( ProgramPartyRole.OBJECT_TYPE_TREE_CODE )
@Cache(usage=CacheConcurrencyStrategy.READ_WRITE)
public class ProgramPartyRole extends PartyRole implements ProgramUnitTracking<ProgramUnit>{

    private static final long serialVersionUID = 7551626213206857568L;

    public static final String OBJECT_TYPE_TREE_CODE = "PRGM_PRTY_RLE";
    
    public static final String OBJECT_TYPE_NAME = "Program Party Role";
    
    private Identifier<?> programUnitId;

    private ProgramUnit programUnit;

    @Type(type = GenericIdentifierUserType.USER_TYPE_ID)
    @Column(name = ProgramUnitTracking.CLMN_PROGRAM_UNIT_ID, insertable = true, updatable = true, nullable = true)
    @Override
    public Identifier<?> getProgramUnitId() {
        return programUnitId;
    }

    @Override
    public void setProgramUnitId(Identifier<?> programUnitId) {
        this.programUnitId = programUnitId;
    }

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = ProgramUnitTracking.CLMN_PROGRAM_UNIT_ID, insertable = false, updatable = false, nullable = true)
    @Override
    public ProgramUnit getProgramUnit() {
        return programUnit;
    }

    @Override
    public void setProgramUnit(ProgramUnit programUnit) {
        this.programUnit = programUnit;
    }
    
}
