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
@DiscriminatorValue( FileInRdb.OBJECT_TYPE_TREE_CODE )
public class FileInRdb extends FileStorage {

    private static final long serialVersionUID = 1720928940982797807L;

    public static final String OBJECT_TYPE_TREE_CODE = "RDB";
    
    public static final String OBJECT_TYPE_NAME = "File Storage in RDBMS";

//    private Identifier<?> blobDataId;
//    
//    private FileBlobData blobData;
//    
//    private Blob fileData;
//
    public FileInRdb() {
        super();
        setStrategyCode( FileStorageStrategy.RDB_CODE );
    }
    
//    public FileInRdb(FileStorage fileStorage, Identifier<?> blobDataId) {
//        super();
//        setStrategyCode( FileStorageStrategy.RDB_CODE );
//        this.setBlobDataId( blobDataId );
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

//    @Type(type = GenericIdentifierUserType.USER_TYPE_ID)
//    @Column(name = "BLOB_DATA_ID", insertable = true, updatable = true, nullable = true)
//    public Identifier<?> getBlobDataId() {
//        return blobDataId;
//    }
//
//    public void setBlobDataId(Identifier<?> blobDataId) {
//        this.blobDataId = blobDataId;
//    }
//
//    @ManyToOne(fetch = FetchType.LAZY)
//    @JoinColumn(name = "BLOB_DATA_ID", insertable = false, updatable = false, nullable = true)
//    public FileBlobData getBlobData() {
//        return blobData;
//    }
//
//    public void setBlobData(FileBlobData blobData) {
//        this.blobData = blobData;
//    }
//
//    @Lob
//    @Column(name = "FILE_DATA", insertable = true, updatable = false, nullable = true)
//    public Blob getFileData() {
//        return fileData;
//    }
//
//    public void setFileData(Blob fileData) {
//        this.fileData = fileData;
//    }
}
