/**
 * Copyright (c) 2012, RealPaaS Technologies, Ltd. All rights reserved.
 */
package com.realpaas.platform.core.file.service;

import java.io.File;
import java.io.IOException;

import org.testng.annotations.Test;

import com.realpaas.commons.utils.ClassUtil;
import com.realpaas.platform.common.file.UploadedFile;
import com.realpaas.platform.common.file.UploadedFileBackedByFile;
import com.realpaas.platform.core.file.dataobject.ImageInfo;
import com.realpaas.platform.test.AbstractTest;
import com.realpaas.platform.test.BeanContainer;

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
public class ImageFileServiceTest extends AbstractTest{

    private ImageFileService service;
    
    @Override
    public void setUp() throws Exception {
        service = (ImageFileService)BeanContainer.i().getBean( "imageFileService" );
    }

    @Override
    public void tearDown() throws Exception {}

    @Test(groups = { "all", "platform", "file", "sit", "service" })
    public void maintainTest(){
        String filename = "fileupload-test-file.jpg";
        String filepath = "/com/realpaas/platform/core/file/service/" + filename;
        File file = null;
        
        try {
            file = ClassUtil.getFile( filepath );
        }
        catch ( IOException e ) {
            throw new RuntimeException( e );
        }
        
        UploadedFile uploadedFile = new UploadedFileBackedByFile( file );
        InboundImageFile imageFile = new InboundImageFile( uploadedFile );
        ImageInfo iamgeInfo = service.create( imageFile );
        assertNotNull( iamgeInfo );
        
        ImageInfo iamgeInfoLoaded = service.loadById( iamgeInfo.getId().toString() );
        assertNotNull( iamgeInfoLoaded );
        assertEquals( iamgeInfoLoaded.getOriginalFileName(), filename );
    }
}
