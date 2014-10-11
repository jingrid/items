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
import com.realpaas.platform.core.file.dataobject.FileInFileSystem;
import com.realpaas.platform.core.file.dataobject.FileInRdb;
import com.realpaas.platform.core.file.dataobject.FileStorage;
import com.realpaas.platform.core.file.dataobject.FileStorageStrategy;
import com.realpaas.platform.core.file.dataobject.type.ContentDisposition;
import com.realpaas.platform.key.Base62;
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
 * 	2013-2-6	henryleu	Create the class
 * </dd>
 * 
 * </dl>
 * @author	henryleu Email/MSN: hongli_leu@126.com
 */
public class FileStorageCenterTest extends AbstractTest{
    
    FileStorageCenter service;
    
    @Override
    public void setUp() throws Exception {
        service = (FileStorageCenter)BeanContainer.i().getBean( "fileStorageCenter" );
    }

    @Override
    public void tearDown() throws Exception {
        
    }
    
    @Test(groups = { "all", "platform", "file", "sit", "service" })
    public void saveAndGetFileForRdb() throws IOException{
        String filename = "fileupload-test-file.txt";
        String filepath = "/com/realpaas/platform/core/file/service/" + filename;
        File file = null;
        
        try {
            file = ClassUtil.getFile( filepath );
        }
        catch ( IOException e ) {
            throw new RuntimeException( e );
        }
        String name = "testfile";
        UploadedFile uploadedFile = new UploadedFileBackedByFile( file );
        InboundCommonFile inboundFile = new InboundCommonFile( uploadedFile );
        inboundFile.setContentDisposition( ContentDisposition.Inline );
        inboundFile.setName( name );
        inboundFile.setStrategyCode( FileStorageStrategy.RDB_CODE );
        FileStorage fileStorage = service.storeFile( inboundFile );
        assertNotNull( fileStorage );
        assertNotNull( fileStorage.getAccessUrl() );
        assertTrue( fileStorage instanceof FileInRdb );

        String code = Base62.encode( ((Long)fileStorage.getId().getValue()).longValue() );
        OutboundFile outboundFile = service.getFile( code );
        assertNotNull( outboundFile );
        assertEquals( outboundFile.getFileName(), name );
        assertEquals( outboundFile.getContentDisposition(), ContentDisposition.Inline );
        
        long originalLength = uploadedFile.getSize();
        long returnedLength = outboundFile.getInputStream().available();
        assertEquals( returnedLength, originalLength );
        
        int readLength = 10;
        byte[] originalData = new byte[readLength];
        int originalReadLength = uploadedFile.getInputStream().read( originalData );
        
        byte[] returnedData = new byte[readLength];
        int returnedReadLength = outboundFile.getInputStream().read( returnedData );
        
        assertEquals( returnedReadLength, originalReadLength );
        assertEquals( returnedData, originalData );
    }
    
    @Test(groups = { "all", "platform", "file", "sit", "service" })
    public void saveAndGetFileForFS() throws IOException{
        String filename = "fileupload-test-file.txt";
        String filepath = "/com/realpaas/platform/core/file/service/" + filename;
        File file = null;
        
        try {
            file = ClassUtil.getFile( filepath );
        }
        catch ( IOException e ) {
            throw new RuntimeException( e );
        }
        String name = "testfile";
        UploadedFile uploadedFile = new UploadedFileBackedByFile( file );
        InboundCommonFile inboundFile = new InboundCommonFile( uploadedFile );
        inboundFile.setContentDisposition( ContentDisposition.Inline );
        inboundFile.setName( name );
        inboundFile.setStrategyCode( FileStorageStrategy.FS_CODE );
        FileStorage fileStorage = service.storeFile( inboundFile );
        assertNotNull( fileStorage );
        assertNotNull( fileStorage.getAccessUrl() );
        assertTrue( fileStorage instanceof FileInFileSystem );

        String code = Base62.encode( ((Long)fileStorage.getId().getValue()).longValue() );
        OutboundFile outboundFile = service.getFile( code );
        assertNotNull( outboundFile );
        assertEquals( outboundFile.getFileName(), name );
        assertEquals( outboundFile.getContentDisposition(), ContentDisposition.Inline );
        
        long originalLength = uploadedFile.getSize();
        long returnedLength = outboundFile.getInputStream().available();
        assertEquals( returnedLength, originalLength );
        
        int readLength = 10;
        byte[] originalData = new byte[readLength];
        int originalReadLength = uploadedFile.getInputStream().read( originalData );
        
        byte[] returnedData = new byte[readLength];
        int returnedReadLength = outboundFile.getInputStream().read( returnedData );
        
        assertEquals( returnedReadLength, originalReadLength );
        assertEquals( returnedData, originalData );
    }    
}
