package com.jingge.platform.core.file.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

import com.jingge.platform.core.file.dataobject.AttachmentInfo;
import com.jingge.platform.core.file.service.AbstractFileInfoService;
import com.jingge.platform.core.file.service.AttachmentFileService;
import com.jingge.platform.core.file.service.InboundAttachmentFile;

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
 *  <b>IMMUTABLE</b> and <b>MUTABLE</b>
 * </dd>
 * 
 * <p><dt><b>Thread Safety:</b></dt> 
 * <dd>
 *  <b>NOT-THREAD-SAFE</b> and <b>NOT-APPLICABLE</b> (for it will never be used on multi-thread occasion.)
 * </dd>
 * 
 * <p><dt><b>Serialization:</b></dt>
 * <dd>
 *  <b>NOT-SERIALIIZABLE</b> and <b>NOT-APPLICABLE</b> (for it have no need to be serializable.)
 * </dd>
 * 
 * <p><dt><b>Design Patterns:</b></dt>
 * <dd>
 *  
 * </dd>
 * 
 * <p><dt><b>Change History:</b></dt>
 * <dd>
 *  Date        Author      Action
 * </dd>
 * <dd>
 *  Feb 4, 2013 henryleu    Create the class
 * </dd>
 * 
 * </dl>
 * @author  henryleu Email/MSN: hongli_leu@126.com
 */
@Component( "attachmentFileService" )
public class AttachmentFileServiceImpl extends AbstractCommonFileServiceImpl<AttachmentInfo, InboundAttachmentFile> implements AttachmentFileService {

    @Qualifier( "attachmentInfoService" )
    @Autowired
    private AbstractFileInfoService<AttachmentInfo> fileInfoService;
    
    @Override
    protected AbstractFileInfoService<AttachmentInfo> getFileInfoService() {
        return fileInfoService;
    }

    @Override
    public AttachmentInfo create(InboundAttachmentFile commonFile) {
        return super.create( commonFile );
    }

    @Override
    public AttachmentInfo loadById(String id) {
        return super.loadById( id );
    }
}
