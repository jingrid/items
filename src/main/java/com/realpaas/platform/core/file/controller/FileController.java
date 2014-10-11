/**
 * Copyright (c) 2012, RealPaaS Technologies, Ltd. All rights reserved.
 */
package com.realpaas.platform.core.file.controller;

import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;

import javax.servlet.http.HttpServletResponse;

import org.apache.commons.io.IOUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.servlet.ModelAndView;

import com.realpaas.platform.common.web.AbstractController;
import com.realpaas.platform.core.file.dataobject.type.ContentDisposition;
import com.realpaas.platform.core.file.service.FileStorageCenter;
import com.realpaas.platform.core.file.service.OutboundFile;

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
@RequestMapping(value = "/core/file")
public class FileController extends AbstractController{
    
    @Autowired
    private FileStorageCenter fileStorageCenter;
    
    @RequestMapping(value = "/{code}")
    public ModelAndView attachmentDownload(NativeWebRequest nativeWebRequest, @PathVariable("code") String code) {
//        HttpServletRequest request = nativeWebRequest.getNativeRequest( HttpServletRequest.class );
        HttpServletResponse response = nativeWebRequest.getNativeResponse( HttpServletResponse.class );
        
        if( code==null ){
            throw new RuntimeException(); //TODO:
        }
        
        OutboundFile outboundFile = fileStorageCenter.getFile( code );
        if( outboundFile==null ){
            throw new RuntimeException(); //TODO:
        }
        
        /*
         * it is specially for fixing IE6's downloading issue. 
         * another modern browsers have no need of it.  
         */
        response.setHeader("Pragma", "public");
        response.setHeader("Cache-Control", "must-revalidate, post-check=0, pre-check=0");
        
        String saveAsFilename = outboundFile.getFileName();
        try {
            saveAsFilename = new String( saveAsFilename.getBytes( "utf-8" ), "ISO_8859_1" );
        }
        catch ( UnsupportedEncodingException e ) {
            logger.error( e );
        }
        
        if( ContentDisposition.Attachment.equals( outboundFile.getContentDisposition() ) ){
            /*
             * application/octet-stream is right mime for download
             * in case the attachment is of exe/dll file, it maybe need application/x-msdownload mime
             */
            response.setContentType("application/octet-stream");
            response.setHeader( "Content-Transfer-Encoding", "binary" );
            response.setHeader( "Content-Disposition", "attachment;filename=\"" + saveAsFilename + "\"" );
        }
        else if( ContentDisposition.Inline.equals( outboundFile.getContentDisposition() ) ){
            response.setContentType( outboundFile.getMime() );
            response.setHeader( "Content-Disposition", "inline;filename=\"" + saveAsFilename + "\"" );
        }
     
        try {
            InputStream is = outboundFile.getInputStream();
            IOUtils.copy( is, response.getOutputStream() );
            is.close();
        }
        catch ( IOException e ) {
            logger.error( e );
        }
        catch ( Exception e ) {
            logger.error( e );
        }
        
        return null;
    }
}
