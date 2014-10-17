/**
 * Copyright (c) 2014, JingGe Technologies, Ltd. All rights reserved.
 */
package com.jingge.platform.core.file.service.impl;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import com.jingge.platform.common.file.UploadedFile;
import com.jingge.platform.core.file.dataobject.FileInFileSystem;
import com.jingge.platform.core.file.dataobject.FileStorage;
import com.jingge.platform.core.file.dataobject.FileStorageStrategyObject;
import com.jingge.platform.core.file.service.AbstractInboundFile;
import com.jingge.platform.core.file.service.FileStorageService;
import com.jingge.platform.core.file.service.OutboundFile;
import com.realpaas.commons.utils.StringUtil;
import com.realpaas.platform.key.Base62;
import com.realpaas.platform.key.OneSequenceGenerator;
import com.realpaas.platform.ssos.core.dataobject.id.GenericIdentifierInstantiator;
import com.realpaas.platform.ssos.core.dataobject.id.Identifier;
import com.realpaas.platform.time.TimeGenerator;

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
 * 	Feb 5, 2013	henryleu	Create the class
 * </dd>
 * 
 * </dl>
 * @author	henryleu Email/MSN: hongli_leu@126.com
 */
@Component( "fileInFileSystemStrategy" )
public class FileInFileSystemStrategyImpl implements FileStorageStrategyObject {

    private final SimpleDateFormat dateFormat = new SimpleDateFormat("yyyyMMdd");

    private Log logger = LogFactory.getLog( getClass() );

    @Value("#{sysProps['file.root.path']}")
    private String rootPath;
    
    @Value("#{sysProps['file.separator']}")
    private String fsSeparator;
    
    @Value("#{sysProps['file.fs.access.url']}")
    private String accessUrl;
    
    @Autowired
    private TimeGenerator timeGenerator;
    
    @Qualifier( "fileSequenceGenerator" )
    @Autowired
    protected OneSequenceGenerator sequenceGenerator;
    
    @Autowired
    protected GenericIdentifierInstantiator identifierInstantiator;

    @Autowired
    private FileStorageService fileStorageService;
    
    @Override
    public FileStorage store(AbstractInboundFile<?> inboundFile) {
        UploadedFile uploadedFile = inboundFile.getUploadedFile();
        String folderName = dateFormat.format( timeGenerator.currentTime() );
        String folderPath = rootPath + fsSeparator + folderName;
        String orginalExt = uploadedFile.getOriginalFileExtension();
        long fileSequence = sequenceGenerator.nextValue();
        String fileName = Base62.encode( fileSequence );
        String filePath = null;
        if( StringUtil.alignToBlank( orginalExt ).equals( "" ) ){}
        else{
            fileName = fileName + "." + orginalExt;
        }
        filePath = folderPath + fsSeparator + fileName;
        
        //Ensure target file-system directory existed there.
        File targetFolder = new File( folderPath );
        if( !targetFolder.exists() ){
            targetFolder.mkdirs();
        }
        
        File targetFile = null;
        try {
            targetFile = new File( filePath ); //copied and uploaded file (to-be-renamed)
            uploadedFile.transferTo( targetFile ); //Copy to target directory with originalFilename
        }
        catch ( IllegalStateException e ) {
            String msg = "Fail to move file [" + filePath + "]: " + e.getMessage();
            logger.error( msg );
            throw new RuntimeException( msg, e );
        }
        catch ( IOException e ) {
            String msg = "Fail to move file [" + filePath + "]: " + e.getMessage();
            logger.error( msg );
            throw new RuntimeException( msg, e );
        }
        catch ( Exception e ) {
            String msg = "Fail to move file [" + filePath + "]: " + e.getMessage();
            logger.error( msg );
            throw new RuntimeException( msg, e );
        }
        
        Identifier<?> fileStorageId = identifierInstantiator.identifier( fileSequence );
        FileStorage fileStorage = new FileInFileSystem();
        fileStorage.setId( fileStorageId );
        fileStorage.setFileName( inboundFile.getName() );
        fileStorage.setMime( uploadedFile.getContentType() );
        fileStorage.setContentDisposition( inboundFile.getContentDisposition() );
        fileStorage.setFileUrl( filePath );
        fileStorage.setFileData( null );
        fileStorage = fileStorageService.create( fileStorage );
        fileStorage.setAccessUrl( accessUrl + fileName );
        
        return fileStorage;
    }

    @Override
    public OutboundFile get(FileStorage fileStorage) {
        OutboundFile outboundFile = new OutboundFile();
        FileInputStream fis = null;
        try {
            fis = new FileInputStream( fileStorage.getFileUrl() );
            outboundFile.setInputStream( fis );
        }
        catch ( IllegalStateException e ) {
            String msg = "Fail to move file [" + fileStorage.getFileUrl() + "]: " + e.getMessage();
            logger.error( msg );
            throw new RuntimeException( msg, e );
        }
        catch ( IOException e ) {
            String msg = "Fail to move file [" + fileStorage.getFileUrl() + "]: " + e.getMessage();
            logger.error( msg );
            throw new RuntimeException( msg, e );
        }
        catch ( Exception e ) {
            String msg = "Fail to move file [" + fileStorage.getFileUrl() + "]: " + e.getMessage();
            logger.error( msg );
            throw new RuntimeException( msg, e );
        }
        
        outboundFile.setMime( fileStorage.getMime() );
        outboundFile.setContentDisposition( fileStorage.getContentDisposition() );
        outboundFile.setFileName( fileStorage.getFileName() );
        
        return outboundFile;
    }

}
