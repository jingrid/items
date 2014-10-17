/**
 * Copyright (c) 2014, JingGe Technologies, Ltd. All rights reserved.
 */
package com.jingge.platform.core.program.setup;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Component;

import com.jingge.platform.core.program.dataobject.ProgramApplication;
import com.jingge.platform.core.program.dataobject.ProgramFunction;
import com.jingge.platform.core.program.dataobject.ProgramModule;
import com.jingge.platform.core.program.dataobject.ProgramPlatform;
import com.jingge.platform.core.universal.setup.AbstractObjectTypeDataManagerImpl;

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
 * 	Apr 3, 2012	henryleu	Create the class
 * </dd>
 * 
 * </dl>
 * @author	henryleu Email/MSN: hongli_leu@126.com
 */

@Component( "programUnitTypeDataManager" )
public class ProgramUnitTypeDataManagerImpl extends AbstractObjectTypeDataManagerImpl {

    @Override
    protected List<PlatformTypeDTO> getInitData() {
        List<PlatformTypeDTO> list = new ArrayList<PlatformTypeDTO>();
        PlatformTypeDTO dto = null;
        
        dto = new PlatformTypeDTO();
        dto.setCode( ProgramPlatform.class.getName() );
        dto.setName( ProgramPlatform.OBJECT_TYPE_NAME );
        list.add( dto );
        
        dto = new PlatformTypeDTO();
        dto.setCode( ProgramApplication.class.getName() );
        dto.setName( ProgramApplication.OBJECT_TYPE_NAME );
        list.add( dto );
        
        dto = new PlatformTypeDTO();
        dto.setCode( ProgramModule.class.getName() );
        dto.setName( ProgramModule.OBJECT_TYPE_NAME );
        list.add( dto );
        
        dto = new PlatformTypeDTO();
        dto.setCode( ProgramFunction.class.getName() );
        dto.setName( ProgramFunction.OBJECT_TYPE_NAME );
        list.add( dto );
        
        return list;
    }

}
