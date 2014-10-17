/**
 * Copyright (c) 2014, JingGe Technologies, Ltd. All rights reserved.
 */
package com.jingge.platform.core.file.service.impl;

import org.springframework.stereotype.Component;

import com.jingge.platform.core.file.dao.FileInRdbDao;
import com.jingge.platform.core.file.dataobject.FileInRdb;
import com.jingge.platform.core.file.service.FileInRdbService;

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
 * 	2012-8-8	henryleu	Create the class
 * </dd>
 * 
 * </dl>
 * @author	henryleu Email/MSN: hongli_leu@126.com
 */
@Component( "fileInRdbService" )
public class FileInRdbServiceImpl extends AbstractFileStorageServiceImpl<FileInRdb, FileInRdbDao> implements FileInRdbService{
//    @Autowired( required=true )
//    private FileBlobDataDao fileBlobDataDao;
//
//    @Override
//    public void storeFile(UploadedFile file, FileStorage fileStorage) {
//        FileBlobData fileBlobData = new FileBlobData();
//        Blob blob = null;
//        try {
//            blob = Hibernate.createBlob( file.getInputStream() ); //TODO: it needs to be replaced it soon
//        }
//        catch ( IOException e ) {
//            throw new RuntimeException( e );
//        }
//        
//        Identifier<?> fileId = new GenericIdentifier( sequenceGenerator.nextValue() );
//        fileBlobData.setId( fileId );
//        fileBlobData.setFileData( blob );
//        fileBlobDataDao.create( fileBlobData );
//        
//        fileStorage.setId( fileId );
//        FileInRdb fileInRdb = new FileInRdb( fileStorage, fileBlobData.getId() );
//        fileInRdb.setStrategyCode( FileInRdb.OBJECT_TYPE_TREE_CODE );
//        fileInRdb.setStorageUrl( "rdbms:file://" + fileBlobData.getId() );
//        getDao().create( fileInRdb );
//        
//        fileStorage.setStorageUrl( fileInRdb.getStorageUrl() );
//        fileStorage.setCreatedTime( fileInRdb.getCreatedTime() );
//        fileStorage.setTenantId( fileInRdb.getTenantId() );
//    }
}
