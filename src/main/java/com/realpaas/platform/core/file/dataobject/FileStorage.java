/**
 * Copyright (c) 2012, RealPaaS Technologies, Ltd. All rights reserved.
 */
package com.realpaas.platform.core.file.dataobject;

import java.sql.Blob;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.DiscriminatorColumn;
import javax.persistence.Entity;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.Lob;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Transient;

import org.hibernate.annotations.Type;

import com.realpaas.platform.core.file.dataobject.type.ContentDisposition;
import com.realpaas.platform.core.file.dataobject.type.ContentDispositionUserType;
import com.realpaas.platform.core.universal.dataobject.ObjectTypeTreeCoding;
import com.realpaas.platform.framework.dataobject.AbstractPlatformDataObject;
import com.realpaas.platform.ssos.ext.dataobject.CreatedTimeTracking;
import com.realpaas.platform.ssos.ext.dataobject.Lifecycling;
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
 * 	2012-8-3	henryleu	Create the class
 * </dd>
 * 
 * </dl>
 * @author	henryleu Email/MSN: hongli_leu@126.com
 */
@org.hibernate.annotations.TypeDefs({
    @org.hibernate.annotations.TypeDef(name = LifeFlagUserType.USER_TYPE_ID, typeClass = LifeFlagUserType.class),
    @org.hibernate.annotations.TypeDef(name = ContentDispositionUserType.USER_TYPE_ID, typeClass = ContentDispositionUserType.class)
})

@Entity
@Table( name="PLF_FILE_STORAGE" )
@Inheritance( strategy = InheritanceType.SINGLE_TABLE )
@DiscriminatorColumn( name = ObjectTypeTreeCoding.CLMN_OBJECT_TYPE_TREE_CODE )
public class FileStorage 
    extends AbstractPlatformDataObject
    implements 
        ObjectTypeTreeCoding,
        FileStorageStrategyCoding,
        Lifecycling,
        CreatedTimeTracking{

    private static final long serialVersionUID = -8396917524344860784L;

    public static final String OBJECT_TYPE_TREE_CODE = "GENERIC";
    
    public static final String OBJECT_TYPE_NAME = "File Storage";
    
    private String objectTypeCode;

    private String strategyCode;
    
    private LifeFlag lifeFlag = LifeFlag.Active;

    private String mime;
    
    private ContentDisposition contentDisposition = ContentDisposition.Attachment;
    
    private String fileName;

    private String fileUrl; //could be a file-system path, cloud-storage URL, or others
    
    private Blob fileData;

    private Date createdTime;

    /*
     * It is a transient field, so it won't be persisted; 
     * {@code accessUrl} has contained information about whether it is attachment or inline 
     * (for Content-Disposition header)
     */
    private String accessUrl;

    @Override
    @Column(name = ObjectTypeTreeCoding.CLMN_OBJECT_TYPE_TREE_CODE, insertable = false, updatable = false, nullable = false, length = 50)
    public String getObjectTypeTreeCode() {
        return objectTypeCode;
    }

    public void setObjectTypeTreeCode(String objectTypeCode) {
        this.objectTypeCode = objectTypeCode;
    }
    
    @Override
    @Column(name = FileStorageStrategyCoding.CLMN_STRATEGY_CODE, insertable = true, updatable = true, nullable = false, length = 10)
    public String getStrategyCode() {
        return strategyCode;
    }

    @Override
    public void setStrategyCode(String strategyCode) {
        this.strategyCode = strategyCode;
    }

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
   
    @Column(name = "MIME", insertable = true, updatable = true, nullable = true, length=100)
    public String getMime() {
        return mime;
    }

    public void setMime(String mime) {
        this.mime = mime;
    }
    
    @Type(type=ContentDispositionUserType.USER_TYPE_ID)
    @Column(name = "CONTENT_DISPOSITION", insertable = true, updatable = true, nullable = false)
    public ContentDisposition getContentDisposition() {
        return contentDisposition;
    }

    public void setContentDisposition(ContentDisposition contentDisposition) {
        this.contentDisposition = contentDisposition;
    }

    @Column(name = "FILE_NAME", insertable = true, updatable = true, nullable = false, length=255)
    public String getFileName() {
        return fileName;
    }

    public void setFileName(String name) {
        this.fileName = name;
    }

    @Column(name = "File_URL", insertable = true, updatable = true, nullable = true, length=255)
    public String getFileUrl() {
        return fileUrl;
    }

    public void setFileUrl(String fileUrl) {
        this.fileUrl = fileUrl;
    }

    @Lob
    @Column(name = "FILE_DATA", insertable = true, updatable = false, nullable = true)
    public Blob getFileData() {
        return fileData;
    }

    public void setFileData(Blob fileData) {
        this.fileData = fileData;
    }
    
    @Override
    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "CREATED_TIME", insertable = true, updatable = false, nullable = false)
    public Date getCreatedTime() {
        return createdTime;
    }

    @Override
    public void setCreatedTime(Date createdTime) {
        this.createdTime = createdTime;
    }

    @Transient
    public String getAccessUrl() {
        return accessUrl;
    }

    public void setAccessUrl(String accessUrl) {
        this.accessUrl = accessUrl;
    }
}
