package com.jingge.platform.core.file.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

import com.jingge.platform.core.file.dataobject.ImageInfo;
import com.jingge.platform.core.file.service.AbstractFileInfoService;
import com.jingge.platform.core.file.service.ImageFileService;
import com.jingge.platform.core.file.service.InboundImageFile;

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
@Component( "imageFileService" )
public class ImageFileServiceImpl extends AbstractCommonFileServiceImpl<ImageInfo, InboundImageFile> implements ImageFileService {

    @Qualifier( "imageInfoService" )
    @Autowired
    private AbstractFileInfoService<ImageInfo> fileInfoService;
    
    @Override
    protected AbstractFileInfoService<ImageInfo> getFileInfoService() {
        return fileInfoService;
    }

    @Override
    public ImageInfo create(InboundImageFile inboundFile) {
        return super.create( inboundFile );
    }

    @Override
    public ImageInfo loadById(String id) {
        return super.loadById( id );
    }
}
