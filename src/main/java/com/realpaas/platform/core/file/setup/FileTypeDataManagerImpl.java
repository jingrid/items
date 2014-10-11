/**
 * Copyright (c) 2012, RealPaaS Technologies, Ltd. All rights reserved.
 */
package com.realpaas.platform.core.file.setup;

import org.springframework.stereotype.Component;

import com.realpaas.platform.core.file.dataobject.FileType;
import com.realpaas.platform.core.file.service.FileTypeService;
import com.realpaas.platform.framework.setup.impl.AbstractGenericDataManagerImpl;

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
 *  Jan 3, 2013    henry leu   Create the class
 * </dd>
 * 
 * </dl>
 * @author  henry leu Email/MSN: hongli_leu@126.com
 */

@Component( "fileTypeDataManager" )
public class FileTypeDataManagerImpl extends AbstractGenericDataManagerImpl<FileTypeService> {

    @Override
    protected void doTearDown() {
        service.deleteAll();
    }

    @Override
    protected void doSetUp() {
        FileType type = null;

        /*
         * Attachment
         */
        type = new FileType();
        type.setCode( FileType.ATTACHMENT_CODE );
        type.setName( FileType.ATTACHMENT_NAME );
        type.setDescription( FileType.ATTACHMENT_NAME );
        service.create( type );

        /*
         * Image
         */
        type = new FileType();
        type.setCode( FileType.IMAGE_CODE );
        type.setName( FileType.IMAGE_NAME );
        type.setDescription( FileType.IMAGE_NAME );
        service.create( type );

        /*
         * Document
         */
        type = new FileType();
        type.setCode( FileType.DOC_CODE );
        type.setName( FileType.DOC_NAME );
        type.setDescription( FileType.DOC_NAME );
        service.create( type );

    }

    @Override
    protected void doValidate() {
        FileType type = null;

        /*
         * Attachment
         */
        type = service.loadByCode( FileType.ATTACHMENT_CODE );
        checkExistence( type, FileType.ATTACHMENT_NAME );
        
        /*
         * Image
         */
        type = service.loadByCode( FileType.IMAGE_CODE );
        checkExistence( type, FileType.IMAGE_NAME );
        
        /*
         * Document
         */
        type = service.loadByCode( FileType.DOC_CODE );
        checkExistence( type, FileType.DOC_NAME );
    }

    @Override
    protected void doUpdate() {
        FileType type = null;

        /*
         * Attachment
         */
        type = service.loadByCode( FileType.ATTACHMENT_CODE );
        if(type==null){
            type = new FileType();
            type.setCode( FileType.ATTACHMENT_CODE );
            type.setName( FileType.ATTACHMENT_NAME );
            type.setDescription( FileType.ATTACHMENT_NAME );
            service.create( type );
        }
        
        /*
         * Image
         */
        type = service.loadByCode( FileType.IMAGE_CODE );
        if(type==null){
            type = new FileType();
            type.setCode( FileType.IMAGE_CODE );
            type.setName( FileType.IMAGE_NAME );
            type.setDescription( FileType.IMAGE_NAME );
            service.create( type );
        }
        
        /*
         * Document
         */
        type = service.loadByCode( FileType.DOC_CODE );
        if(type==null){
            type = new FileType();
            type.setCode( FileType.DOC_CODE );
            type.setName( FileType.DOC_NAME );
            type.setDescription( FileType.DOC_NAME );
            service.create( type );
        }

        doValidate();
    }
    
}
