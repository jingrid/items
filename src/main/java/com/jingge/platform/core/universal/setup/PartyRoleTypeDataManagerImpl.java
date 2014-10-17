/**
 * Copyright (c) 2014, JingGe Technologies, Ltd. All rights reserved.
 */
package com.jingge.platform.core.universal.setup;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Component;

import com.jingge.platform.core.universal.dataobject.PartyRole;
import com.jingge.platform.core.universal.dataobject.ProgramPartyRole;

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
 * 	Jul 29, 2012	henryleu	Create the class
 * </dd>
 * 
 * </dl>
 * @author	henryleu Email/MSN: hongli_leu@126.com
 */

@Component( "partyRoleTypeDataManager" )
public class PartyRoleTypeDataManagerImpl extends AbstractObjectTypeDataManagerImpl {

    @Override
    protected List<PlatformTypeDTO> getInitData() {
        List<PlatformTypeDTO> list = new ArrayList<PlatformTypeDTO>();
        PlatformTypeDTO dto = null;
        
        //Party Role
        dto = new PlatformTypeDTO();
        dto.setCode( PartyRole.class.getName() );
        dto.setName( PartyRole.OBJECT_TYPE_NAME );
        list.add( dto );
        
        //Program Party Role
        dto = new PlatformTypeDTO();
        dto.setCode( ProgramPartyRole.class.getName() );
        dto.setName( ProgramPartyRole.OBJECT_TYPE_NAME );
        list.add( dto );
        
        return list;
    }

}
