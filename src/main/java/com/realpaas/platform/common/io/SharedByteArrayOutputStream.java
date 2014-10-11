/**
 * Copyright (c) 2012, RealPaaS Technologies, Ltd. All rights reserved.
 */
package com.realpaas.platform.common.io;

import java.io.ByteArrayOutputStream;
import java.io.InputStream;

/**
 * <p>
 * A ByteArrayOutputStream that allows us to share the byte array
 * rather than copy it.
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
 * 	Mar 20, 2013	henryleu	Create the class
 * </dd>
 * 
 * </dl>
 * @author	henryleu Email/MSN: hongli_leu@126.com
 */
public class SharedByteArrayOutputStream extends ByteArrayOutputStream {
    
    public SharedByteArrayOutputStream() {
        super();
    }

    public SharedByteArrayOutputStream(byte[] buf) {
        super( buf.length );
        this.buf = buf;
    }

    public SharedByteArrayOutputStream(int size) {
        super( size );
    }

    public InputStream toInputStream() {
        return new SharedByteArrayInputStream( buf, 0, count );
    }
}
