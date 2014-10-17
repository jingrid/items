/**
 * Copyright (c) 2014, JingGe Technologies, Ltd. All rights reserved.
 */
package com.jingge.platform.core.file.setup;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Component;

import com.jingge.platform.core.file.dataobject.FileInFileSystem;
import com.jingge.platform.core.file.dataobject.FileInRdb;
import com.jingge.platform.core.file.dataobject.FileStorage;
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
 * 	Aug 3, 2012	henryleu	Create the class
 * </dd>
 * 
 * </dl>
 * @author	henryleu Email/MSN: hongli_leu@126.com
 */

@Component( "fileStorageTypeDataManager" )
public class FileStorageTypeDataManagerImpl extends AbstractObjectTypeDataManagerImpl {

    @Override
    protected List<PlatformTypeDTO> getInitData() {
        List<PlatformTypeDTO> list = new ArrayList<PlatformTypeDTO>();
        PlatformTypeDTO dto = null;
        
        dto = new PlatformTypeDTO();
        dto.setCode( FileStorage.class.getName() );
        dto.setName( FileStorage.OBJECT_TYPE_NAME );
        list.add( dto );
        
        dto = new PlatformTypeDTO();
        dto.setCode( FileInFileSystem.class.getName() );
        dto.setName( FileInFileSystem.OBJECT_TYPE_NAME );
        list.add( dto );
        
        dto = new PlatformTypeDTO();
        dto.setCode( FileInRdb.class.getName() );
        dto.setName( FileInRdb.OBJECT_TYPE_NAME );
        list.add( dto );
        
        return list;
    }

}
