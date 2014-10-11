/**
 * Copyright (c) 2012, RealPaaS Technologies, Ltd. All rights reserved.
 */
package com.realpaas.platform.core.file.service.impl;

import org.springframework.beans.factory.annotation.Autowired;

import com.realpaas.platform.core.file.dataobject.FileInfo;
import com.realpaas.platform.core.file.dataobject.FileStorage;
import com.realpaas.platform.core.file.service.AbstractFileInfoService;
import com.realpaas.platform.core.file.service.AbstractInboundFile;
import com.realpaas.platform.core.file.service.CommonFileService;
import com.realpaas.platform.core.file.service.FileStorageCenter;
import com.realpaas.platform.ssos.core.dataobject.id.GenericIdentifierInstantiator;

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
 * 	Feb 3, 2013	henryleu	Create the class
 * </dd>
 * 
 * </dl>
 * @author	henryleu Email/MSN: hongli_leu@126.com
 */
public abstract class AbstractCommonFileServiceImpl<FI extends FileInfo, IF extends AbstractInboundFile<FI>> implements CommonFileService<FI, IF>{

    @Autowired
    protected FileStorageCenter fileStorageCenter;
    
    @Autowired
    protected GenericIdentifierInstantiator identifierInstantiator;
    
    protected abstract AbstractFileInfoService<FI> getFileInfoService();

    @Override
    public FI create(IF inboundFile) {
        /*
         * Create File Storage record
         */
        FileStorage fileStorage = createFileStorage( inboundFile );
        
        /*
         * Create File Info record
         */
//        FI fileInfo = getFileInfoService().instantiate();
        FI fileInfo = inboundFile.buildFile();
        populateStorageInfo( fileInfo, fileStorage);
        return getFileInfoService().create( fileInfo );
    }

    @Override
    public FI loadById(String id) {
        FI fileInfo = getFileInfoService().loadById( identifierInstantiator.identifier( id ) );
        return fileInfo;
    }

    /**
     * Create File Storage record
     * @param inboundFile
     * @return
     */
    private FileStorage createFileStorage(IF inboundFile){
        FileStorage fileStorage = fileStorageCenter.storeFile( inboundFile );
        return fileStorage;
    }
    
    private void populateStorageInfo(FI fileInfo, FileStorage fileStorage){
//        fileInfo.setName( inboundFile.getName() );
//        fileInfo.setFileSize( inboundFile.getUploadedFile().getSize() );
//        fileInfo.setOriginalFileName( inboundFile.getUploadedFile().getOriginalFilename() );
//        fileInfo.setOriginalFileExt( inboundFile.getUploadedFile().getOriginalFileExtension() );
        fileInfo.setFileStorageId( fileStorage.getId() );
        fileInfo.setUrl( fileStorage.getAccessUrl() );
    }
    
}
