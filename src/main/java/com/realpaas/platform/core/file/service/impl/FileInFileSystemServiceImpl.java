/**
 * Copyright (c) 2012, RealPaaS Technologies, Ltd. All rights reserved.
 */
package com.realpaas.platform.core.file.service.impl;

import org.springframework.stereotype.Component;

import com.realpaas.platform.core.file.dao.FileInFileSystemDao;
import com.realpaas.platform.core.file.dataobject.FileInFileSystem;
import com.realpaas.platform.core.file.service.FileInFileSystemService;

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
 * 	2012-8-8	henryleu	Create the class
 * </dd>
 * 
 * </dl>
 * @author	henryleu Email/MSN: hongli_leu@126.com
 */
@Component( "fileInFileSystemService" )
public class FileInFileSystemServiceImpl extends AbstractFileStorageServiceImpl<FileInFileSystem, FileInFileSystemDao> implements FileInFileSystemService{

/*    
    @Value("#{sysProps['file.root.path']}")
    private String rootPath;
    
    @Value("#{sysProps['file.separator']}")
    private String fsSeparator;
    
    @Autowired
    private TimeGenerator timeGenerator;
    
    private final SimpleDateFormat dateFormat = new SimpleDateFormat("yyyyMMdd");
    
    @Override
    public void storeFile(UploadedFile file, FileStorage fileStorage) {
        String folderName = dateFormat.format( timeGenerator.currentTime() );
        String folderPath = rootPath + fsSeparator + folderName;

        String originalFilename = file.getOriginalFilename();
        String orginalExt = StringUtil.parseFileExtension( originalFilename );
        long storageId = sequenceGenerator.nextValue();
        String fileName = Base62.convert( storageId );
        String filePath = null;
        if( StringUtil.alignToBlank( orginalExt ).equals( "" ) ){
            
        }
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
            file.transferTo( targetFile ); //Copy to target directory with originalFilename
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
        
        Identifier<?> fileId = new GenericIdentifier( storageId );
        fileStorage.setId( fileId );
        FileInFileSystem fileInFileSystem = new FileInFileSystem( fileStorage );
        fileInFileSystem.setStorageUrl( filePath  );
        getDao().create( fileInFileSystem );
        
        fileStorage.setStorageUrl( fileInFileSystem.getStorageUrl() );
        fileStorage.setCreatedTime( fileInFileSystem.getCreatedTime() );
        fileStorage.setTenantId( fileInFileSystem.getTenantId() );
    }
*/
}
