/**
 * Copyright (c) 2014, JingGe Technologies, Ltd. All rights reserved.
 */
package com.jingge.platform.core.file.service;

import java.io.Serializable;

import com.jingge.platform.common.file.UploadedFile;
import com.jingge.platform.core.file.dataobject.FileInfo;
import com.jingge.platform.core.file.dataobject.FileStorageStrategy;
import com.jingge.platform.core.file.dataobject.type.ContentDisposition;

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
 * 	Feb 15, 2013	henryleu	Create the class
 * </dd>
 * 
 * </dl>
 * @author	henryleu Email/MSN: hongli_leu@126.com
 */
public abstract class AbstractInboundFile<F extends FileInfo> implements Serializable{

    private static final long serialVersionUID = -3791385101539081552L;

    private final UploadedFile uploadedFile;
    
    private String name;
    
    private ContentDisposition contentDisposition = ContentDisposition.Attachment;
    
    private String strategyCode;
    
    public abstract F buildFile();
    
    public AbstractInboundFile(UploadedFile uploadedFile){
        this.uploadedFile = uploadedFile;
        this.name = uploadedFile.getOriginalFilename();
        this.strategyCode = FileStorageStrategy.FS_CODE;
    }
    
    public UploadedFile getUploadedFile() {
        return uploadedFile;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public ContentDisposition getContentDisposition() {
        return contentDisposition;
    }

    public void setContentDisposition(ContentDisposition contentDisposition) {
        this.contentDisposition = contentDisposition;
    }

    public String getStrategyCode() {
        return strategyCode;
    }

    public void setStrategyCode(String strategyCode) {
        this.strategyCode = strategyCode;
    }
} 
