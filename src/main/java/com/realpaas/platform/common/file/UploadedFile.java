/**
 * Copyright (c) 2012, RealPaaS Technologies, Ltd. All rights reserved.
 */
package com.realpaas.platform.common.file;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;

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
public interface UploadedFile {
    /**
     * Return the original filename in the client's filesystem.
     * <p>This may contain path information depending on the browser used,
     * but it typically will not with any other than Opera.
     * @return the original filename, or the empty String if no file
     * has been chosen in the multipart form, or <code>null</code>
     * if not defined or not available
     */
    String getOriginalFilename();
    
    /**
     * Return the original extension which is parsed from original filename.
     * @return the original extension
     */
    String getOriginalFileExtension();
    
    /**
     * Return the content type of the file.
     * @return the content type, or <code>null</code> if not defined
     * (or no file has been chosen in the multipart form)
     */
    String getContentType();
    
    /**
     * Return whether the uploaded file is empty, that is, either no file has
     * been chosen in the multipart form or the chosen file has no content.
     */
    boolean isEmpty();

    /**
     * Return the size of the file in bytes.
     * @return the size of the file, or 0 if empty
     */
    long getSize();

    /**
     * Return the contents of the file as an array of bytes.
     * @return the contents of the file as bytes, or an empty byte array if empty
     * @throws IOException in case of access errors (if the temporary store fails)
     */
    byte[] getBytes() throws IOException;

    /**
     * Return an InputStream to read the contents of the file from.
     * The user is responsible for closing the stream.
     * @return the contents of the file as stream, or an empty stream if empty
     * @throws IOException in case of access errors (if the temporary store fails)
     */
    InputStream getInputStream() throws IOException;

    /**
     * Transfer the received file to the given destination file.
     * <p>This may either move the file in the filesystem, copy the file in the
     * filesystem, or save memory-held contents to the destination file.
     * If the destination file already exists, it will be deleted first.
     * <p>If the file has been moved in the filesystem, this operation cannot
     * be invoked again. Therefore, call this method just once to be able to
     * work with any storage mechanism.
     * @param dest the destination file
     * @throws IOException in case of reading or writing errors
     * @throws IllegalStateException if the file has already been moved
     * in the filesystem and is not available anymore for another transfer
     */
    void transferTo(File dest) throws IOException, IllegalStateException;
}
