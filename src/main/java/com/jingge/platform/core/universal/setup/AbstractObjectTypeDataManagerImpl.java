/**
 * Copyright (c) 2014, JingGe Technologies, Ltd. All rights reserved.
 */
package com.jingge.platform.core.universal.setup;

import com.jingge.platform.core.universal.dataobject.ObjectType;
import com.jingge.platform.core.universal.service.ObjectTypeService;
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
 * 	Jul 22, 2012	henryleu	Create the class
 * </dd>
 * 
 * </dl>
 * @author	henryleu Email/MSN: hongli_leu@126.com
 */

public abstract class AbstractObjectTypeDataManagerImpl extends PlatformTypeDataManagerImpl<ObjectType, ObjectTypeService> {

    
//    @Override
//    protected List<PlatformTypeDTO> getInitData() {
//        List<PlatformTypeDTO> list = new ArrayList<PlatformTypeDTO>();
//        PlatformTypeDTO dto = null;
//        
//        dto = new PlatformTypeDTO();
//        dto.setCode( ProgramUnitType.CODE_PLATFORM );
//        dto.setName( ProgramUnitType.NAME_PLATFORM );
//        list.add( dto );
//        
//        dto = new PlatformTypeDTO();
//        dto.setCode( ProgramUnitType.CODE_APPLICATION );
//        dto.setName( ProgramUnitType.NAME_APPLICATION );
//        list.add( dto );
//        
//        return list;
//    }

//    @Autowired
//    @Qualifier( "objectTypeService" )
//    @Override
//    public void setService(ObjectTypeService service) {
//        super.setService( service );
//    }

    @Override
    protected ObjectType initDo(PlatformTypeDTO dto) {
        ObjectType dataObject = new ObjectType();
        dataObject.setCode( dto.getCode() );
        dataObject.setName( dto.getName() );
        dataObject.setDescription( dto.getName() );
        return dataObject;
    }

}
