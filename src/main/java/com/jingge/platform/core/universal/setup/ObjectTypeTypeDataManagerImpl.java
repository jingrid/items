/**
 * Copyright (c) 2014, JingGe Technologies, Ltd. All rights reserved.
 */
package com.jingge.platform.core.universal.setup;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Component;

import com.jingge.platform.core.universal.dataobject.DataObjectType;
import com.jingge.platform.core.universal.dataobject.ObjectType;
import com.jingge.platform.core.universal.dataobject.ObjectTypeType;
import com.jingge.platform.core.universal.dataobject.PartyType;
import com.jingge.platform.core.universal.service.ObjectTypeTypeService;
import com.realpaas.platform.framework.setup.impl.PlatformTypeDataManagerImpl;

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

@Component( "objectTypeTypeDataManager" )
public class ObjectTypeTypeDataManagerImpl extends PlatformTypeDataManagerImpl<ObjectTypeType, ObjectTypeTypeService> {

    @Override
    protected List<PlatformTypeDTO> getInitData() {
        List<PlatformTypeDTO> list = new ArrayList<PlatformTypeDTO>();
        PlatformTypeDTO dto = null;
        
        dto = new PlatformTypeDTO();
        dto.setCode( ObjectType.TYPE_CODE );
        dto.setName( ObjectType.TYPE_NAME );
        list.add( dto );
        
        dto = new PlatformTypeDTO();
        dto.setCode( DataObjectType.TYPE_CODE );
        dto.setName( DataObjectType.TYPE_NAME );
        list.add( dto );
        
        dto = new PlatformTypeDTO();
        dto.setCode( PartyType.TYPE_CODE );
        dto.setName( PartyType.TYPE_NAME );
        list.add( dto );
        
        return list;
    }

    @Override
    protected ObjectTypeType initDo(PlatformTypeDTO dto) {
        ObjectTypeType dataObject = new ObjectTypeType();
        dataObject.setCode( dto.getCode() );
        dataObject.setName( dto.getName() );
        dataObject.setDescription( dto.getName() );
        return dataObject;
    }

}
