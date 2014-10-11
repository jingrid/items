/**
 * Copyright (c) 2012, RealPaaS Technologies, Ltd. All rights reserved.
 */
package com.realpaas.platform.core.file.service.impl;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.sql.Blob;
import java.sql.SQLException;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.hibernate.Hibernate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import com.realpaas.platform.common.file.UploadedFile;
import com.realpaas.platform.core.file.dataobject.FileInRdb;
import com.realpaas.platform.core.file.dataobject.FileStorage;
import com.realpaas.platform.core.file.dataobject.FileStorageStrategyObject;
import com.realpaas.platform.core.file.service.AbstractInboundFile;
import com.realpaas.platform.core.file.service.FileStorageService;
import com.realpaas.platform.core.file.service.OutboundFile;
import com.realpaas.platform.key.Base62;
import com.realpaas.platform.key.OneSequenceGenerator;
import com.realpaas.platform.ssos.core.dataobject.id.GenericIdentifierInstantiator;
import com.realpaas.platform.ssos.core.dataobject.id.Identifier;

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
@Component( "fileInRdbStrategy" )
public class FileInRdbStrategyImpl implements FileStorageStrategyObject {

    private Log logger = LogFactory.getLog( getClass() );
    
    @Value("#{sysProps['file.rdb.access.url']}")
    private String accessUrl;
    
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

        Blob blobFileData = null;
        try {
            blobFileData = Hibernate.createBlob( uploadedFile.getInputStream() ); //TODO: it needs to be replaced it soon
        }
        catch ( IOException e ) {
            String errorMsg = "Fail to create Blob object from FileInputStream of " + uploadedFile.getOriginalFilename();
            logger.error( errorMsg, e );
            throw new RuntimeException( errorMsg, e );
        }

        long fileSequence = sequenceGenerator.nextValue();
        Identifier<?> fileStorageId = identifierInstantiator.identifier( fileSequence );

        FileStorage fileStorage = new FileInRdb();
        fileStorage.setId( fileStorageId );
        fileStorage.setFileName( inboundFile.getName() );
        fileStorage.setMime( uploadedFile.getContentType() );
        fileStorage.setContentDisposition( inboundFile.getContentDisposition() );
        fileStorage.setFileUrl( null ); // No storage URL to populate
        fileStorage.setFileData( blobFileData );
        fileStorage = fileStorageService.create( fileStorage );

        String fileName = Base62.encode( fileSequence );
        fileStorage.setAccessUrl( accessUrl + fileName );

        return fileStorage;
    }

    @Override
    public OutboundFile get(FileStorage fileStorage) {
        OutboundFile outboundFile = new OutboundFile();
        InputStream is = null;
        Blob fileData = fileStorage.getFileData();
        try {
            
            if( fileData==null ){
                is = new ByteArrayInputStream(new byte[0]);
            }
            else{
                is = fileData.getBinaryStream();
            }
            outboundFile.setInputStream( is );
        }
        catch ( SQLException e ) {
            String msg = "Fail to access blob object: " + e.getMessage();
            logger.error( msg );
            throw new RuntimeException( msg, e );
        }
        catch ( Exception e ) {
            String msg = "Fail to access blob object: " + e.getMessage();
            logger.error( msg );
            throw new RuntimeException( msg, e );
        }
        
        outboundFile.setMime( fileStorage.getMime() );
        outboundFile.setContentDisposition( fileStorage.getContentDisposition() );
        outboundFile.setFileName( fileStorage.getFileName() );
        
        return outboundFile;
    }
}
