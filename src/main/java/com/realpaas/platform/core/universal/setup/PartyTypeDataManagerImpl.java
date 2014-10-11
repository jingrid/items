/**
 * Copyright (c) 2012, RealPaaS Technologies, Ltd. All rights reserved.
 */
package com.realpaas.platform.core.universal.setup;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Component;

import com.realpaas.platform.core.universal.dataobject.Party;
import com.realpaas.platform.core.universal.dataobject.PartyInformalOrganization;
import com.realpaas.platform.core.universal.dataobject.PartyLegalOrganization;
import com.realpaas.platform.core.universal.dataobject.PartyOrganization;
import com.realpaas.platform.core.universal.dataobject.PartyPerson;

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

@Component( "partyTypeDataManager" )
public class PartyTypeDataManagerImpl extends AbstractObjectTypeDataManagerImpl {

    @Override
    protected List<PlatformTypeDTO> getInitData() {
        List<PlatformTypeDTO> list = new ArrayList<PlatformTypeDTO>();
        PlatformTypeDTO dto = null;
        
        //Party
        dto = new PlatformTypeDTO();
        dto.setCode( Party.class.getName() );
        dto.setName( Party.OBJECT_TYPE_NAME );
        list.add( dto );
        
        //Party Organization
        dto = new PlatformTypeDTO();
        dto.setCode( PartyOrganization.class.getName() );
        dto.setName( PartyOrganization.OBJECT_TYPE_NAME );
        list.add( dto );
        
        dto = new PlatformTypeDTO();
        dto.setCode( PartyLegalOrganization.class.getName() );
        dto.setName( PartyLegalOrganization.OBJECT_TYPE_NAME );
        list.add( dto );
        
        dto = new PlatformTypeDTO();
        dto.setCode( PartyInformalOrganization.class.getName() );
        dto.setName( PartyInformalOrganization.OBJECT_TYPE_NAME );
        list.add( dto );
        
        //Party Person
        dto = new PlatformTypeDTO();
        dto.setCode( PartyPerson.class.getName() );
        dto.setName( PartyPerson.OBJECT_TYPE_NAME );
        list.add( dto );
        
        return list;
    }

}
