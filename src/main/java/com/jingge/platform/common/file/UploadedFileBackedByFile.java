/**
 * Copyright (c) 2014, JingGe Technologies, Ltd. All rights reserved.
 */
package com.jingge.platform.common.file;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;

import com.jingge.platform.common.util.FileUtil;
import com.realpaas.commons.utils.StringUtil;

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
 * 	2012-8-9	henryleu	Create the class
 * </dd>
 * 
 * </dl>
 * @author	henryleu Email/MSN: hongli_leu@126.com
 */
public class UploadedFileBackedByFile implements UploadedFile{
    
    private final File file;
    
    public UploadedFileBackedByFile(File file) {
        super();
        this.file = file;
    }

    @Override
    public String getOriginalFilename() {
        return file.getName();
    }

    @Override
    public String getOriginalFileExtension() {
        return StringUtil.parseFileExtension( file.getName() );
    }

    @Override
    public String getContentType() {
        return ""; //TODO:
    }

    @Override
    public boolean isEmpty() {
        return file==null ? true : !file.exists();
    }

    @Override
    public long getSize() {
        return file.length();
    }

    @Override
    public byte[] getBytes() throws IOException {
        throw new UnsupportedOperationException();
    }

    @Override
    public InputStream getInputStream() throws IOException {
        return new FileInputStream( file );
    }

    @Override
    public void transferTo(File dest) throws IOException, IllegalStateException {
        if( !file.exists() ){
            throw new IllegalStateException("File " + file.getAbsolutePath() + "has already been moved - cannot be transferred again");
        }
        FileUtil.copyFile( file, dest.getParentFile(), dest.getName() );
    }
}
