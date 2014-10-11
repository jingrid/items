/**
 * Copyright (c) 2012, RealPaaS Technologies, Ltd. All rights reserved.
 */
package com.realpaas.platform.core.file.dataobject;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.DiscriminatorColumn;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.hibernate.annotations.Type;

import com.realpaas.platform.core.universal.dataobject.ObjectTypeTreeCoding;
import com.realpaas.platform.framework.dataobject.AbstractPlatformDataObject;
import com.realpaas.platform.ssos.core.dataobject.id.Identifier;
import com.realpaas.platform.ssos.core.dataobject.usertype.GenericIdentifierUserType;
import com.realpaas.platform.ssos.ext.dataobject.CreatedTimeTracking;
import com.realpaas.platform.ssos.ext.dataobject.Lifecycling;
import com.realpaas.platform.ssos.ext.dataobject.UpdatedTimeTracking;
import com.realpaas.platform.ssos.ext.dataobject.enumtype.LifeFlag;
import com.realpaas.platform.ssos.ext.dataobject.usertype.LifeFlagUserType;

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
 * 	Aug 9, 2012	huangqr	Create the class
 * </dd>
 * 
 * </dl>
 * @author	huangqr Email/MSN: huangqingrong520@163.com
 */

@Entity
@Table( name="PLF_FILE_INFO" )
@Inheritance( strategy = InheritanceType.SINGLE_TABLE )
@DiscriminatorColumn( name = ObjectTypeTreeCoding.CLMN_OBJECT_TYPE_TREE_CODE )
@Cache( usage=CacheConcurrencyStrategy.READ_WRITE )
public class FileInfo 
    extends AbstractPlatformDataObject
    implements
        Lifecycling,
        CreatedTimeTracking,
        UpdatedTimeTracking{
    
    private static final long serialVersionUID = 2048676106175030077L;
    
    private LifeFlag lifeFlag = LifeFlag.Active;
    
    private String name;
    
    private String url;
    
    private String originalFileName;

    private String originalFileExt;

    private long fileSize;
    
    private Identifier<?> fileTypeId;
    
    private FileType fileType;
    
    private Identifier<?> fileStorageId;
    
    private FileStorage fileStorage;

    private Date createdTime;

    private Date updatedTime;
    
    @Override
    @Type(type=LifeFlagUserType.USER_TYPE_ID)
    @Column(name = "LIFE_FLAG", insertable = true, updatable = true, nullable = false)
    public LifeFlag getLifeFlag() {
        return lifeFlag;
    }

    @Override
    public void setLifeFlag(LifeFlag lifeFlag) {
        this.lifeFlag = lifeFlag;
    }

    @Column(name = "NAME", insertable = true, updatable = true, nullable = false, length=255)
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Column(name = "RUL", insertable = true, updatable = true, nullable = false, length=255)
    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    @Column(name = "ORIGINAL_FILE_NAME", insertable = true, updatable = true, nullable = false, length=255)
    public String getOriginalFileName() {
        return originalFileName;
    }

    public void setOriginalFileName(String originalFileName) {
        this.originalFileName = originalFileName;
    }

    @Column(name = "ORIGINAL_FILE_EXT", insertable = true, updatable = true, nullable = false, length=20)
    public String getOriginalFileExt() {
        return originalFileExt;
    }

    public void setOriginalFileExt(String originalFileExt) {
        this.originalFileExt = originalFileExt;
    }

    @Column(name = "FILE_SIZE", insertable = true, updatable = true, nullable = false)
    public long getFileSize() {
        return fileSize;
    }

    public void setFileSize(long fileSize) {
        this.fileSize = fileSize;
    }
    
    @Type(type = GenericIdentifierUserType.USER_TYPE_ID)
    @Column(name = "FILE_TYPE_ID", insertable = true, updatable = true, nullable = false)
    public Identifier<?> getFileTypeId() {
        return fileTypeId;
    }

    public void setFileTypeId(Identifier<?> fileTypeId) {
        this.fileTypeId = fileTypeId;
    }

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "FILE_TYPE_ID", insertable = false, updatable = false, nullable = false)
    public FileType getFileType() {
        return fileType;
    }

    public void setFileType(FileType fileType) {
        this.fileType = fileType;
    }

    @Type(type = GenericIdentifierUserType.USER_TYPE_ID)
    @Column(name = "FILE_STORAGE_ID", insertable = true, updatable = true, nullable = false)
    public Identifier<?> getFileStorageId() {
        return fileStorageId;
    }

    public void setFileStorageId(Identifier<?> fileStorageId) {
        this.fileStorageId = fileStorageId;
    }

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "FILE_STORAGE_ID", insertable = false, updatable = false, nullable = false)
    public FileStorage getFileStorage() {
        return fileStorage;
    }

    public void setFileStorage(FileStorage fileStorage) {
        this.fileStorage = fileStorage;
    }
    
    @Override
    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "UPDATED_TIME", insertable = true, updatable = true, nullable = false)
    public Date getUpdatedTime() {
        return updatedTime;
    }

    @Override
    public void setUpdatedTime(Date updatedTime) {
        this.updatedTime = updatedTime;
    }

    @Override
    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "CREATED_TIME", insertable = true, updatable = false, nullable = false)
    public Date getCreatedTime() {
        return createdTime;
    }

    @Override
    public void setCreatedTime(Date cTime) {
        this.createdTime = cTime;
    }
}
