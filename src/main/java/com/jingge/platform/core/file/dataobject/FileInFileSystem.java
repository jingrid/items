/**
 * Copyright (c) 2014, JingGe Technologies, Ltd. All rights reserved.
 */
package com.jingge.platform.core.file.dataobject;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;

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
 * 	2012-8-3	henryleu	Create the class
 * </dd>
 * 
 * </dl>
 * @author	henryleu Email/MSN: hongli_leu@126.com
 */

@Entity
@DiscriminatorValue( FileInFileSystem.OBJECT_TYPE_TREE_CODE )
public class FileInFileSystem extends FileStorage {

    private static final long serialVersionUID = 3229900706609687008L;

    public static final String OBJECT_TYPE_TREE_CODE = "FS";
    
    public static final String OBJECT_TYPE_NAME = "File Storage in File System";

    public FileInFileSystem() {
        super();
        setStrategyCode( FileStorageStrategy.FS_CODE );
    }

//    public FileInFileSystem(FileStorage fileStorage) {
//        super();
//        setStrategyCode( FileStorageStrategy.FS_CODE );
//        this.setId( fileStorage.getId() );
//        this.setVersion( fileStorage.getVersion() );
//        this.setOriginalFileName( fileStorage.getOriginalFileName() );
//        this.setOriginalFileExt( fileStorage.getOriginalFileExt() );
//        this.setFileSize( fileStorage.getFileSize() );
//        this.setStorageUrl( fileStorage.getStorageUrl() );
//        this.setTenantId( fileStorage.getTenantId() );
//        this.setLifeFlag( fileStorage.getLifeFlag() );
//        this.setCreatedTime( fileStorage.getCreatedTime() );        
//    }
    
}
