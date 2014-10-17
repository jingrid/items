/**
 * Copyright (c) 2014, JingGe Technologies, Ltd. All rights reserved.
 */
package com.jingge.platform.core.file.service;

import java.awt.image.BufferedImage;

import javax.imageio.ImageIO;

import com.jingge.platform.common.file.UploadedFile;
import com.jingge.platform.core.file.dataobject.ImageInfo;

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
public class InboundImageFile extends AbstractInboundFile<ImageInfo> {

    private static final long serialVersionUID = -8985800031953732996L;

    private short width = 0;
    
    private short height = 0;
    
    public InboundImageFile(UploadedFile uploadedFile) {
        super( uploadedFile );
    }
    
    @Override
    public ImageInfo buildFile() {
        ImageInfo fileInfo = new ImageInfo();
        fileInfo.setName( getName() );
        fileInfo.setFileSize( getUploadedFile().getSize() );
        fileInfo.setOriginalFileName( getUploadedFile().getOriginalFilename() );
        fileInfo.setOriginalFileExt( getUploadedFile().getOriginalFileExtension() );

        /*
         * Image file specific fields
         */
        init();
        fileInfo.setWidth( getWidth() );
        fileInfo.setHeight( getHeight() );
        return fileInfo ;
    }

    public short getWidth() {
        return width;
    }

    public void setWidth(short width) {
        this.width = width;
    }

    public short getHeight() {
        return height;
    }

    public void setHeight(short height) {
        this.height = height;
    }

    private void init(){
        try {
            BufferedImage bufImg = ImageIO.read( getUploadedFile().getInputStream() );
            if( bufImg!=null ){
                width = (short)bufImg.getWidth();
                height = (short)bufImg.getHeight();
            }
        }
        catch ( Exception e ) {
            e.printStackTrace();
        }
    }
}
