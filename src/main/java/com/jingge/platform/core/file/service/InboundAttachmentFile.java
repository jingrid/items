/**
 * Copyright (c) 2014, JingGe Technologies, Ltd. All rights reserved.
 */
package com.jingge.platform.core.file.service;

import com.jingge.platform.common.file.UploadedFile;
import com.jingge.platform.core.file.dataobject.AttachmentInfo;

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
 * 	Feb 4, 2013	henryleu	Create the class
 * </dd>
 * 
 * </dl>
 * @author	henryleu Email/MSN: hongli_leu@126.com
 */
public class InboundAttachmentFile extends AbstractInboundFile<AttachmentInfo> {

    private static final long serialVersionUID = 2405698430395217560L;

    public InboundAttachmentFile(UploadedFile uploadedFile) {
        super( uploadedFile );
    }

    @Override
    public AttachmentInfo buildFile() {
        AttachmentInfo fileInfo = new AttachmentInfo();
        fileInfo.setName( getName() );
        fileInfo.setFileSize( getUploadedFile().getSize() );
        fileInfo.setOriginalFileName( getUploadedFile().getOriginalFilename() );
        fileInfo.setOriginalFileExt( getUploadedFile().getOriginalFileExtension() );
        return fileInfo ;
    }

}
