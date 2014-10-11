/**
 * Copyright (c) 2012, RealPaaS Technologies, Ltd. All rights reserved.
 */
package com.realpaas.platform.common.io;

import java.io.ByteArrayInputStream;
import java.io.InputStream;

/**
 * <p>
 * A ByteArrayInputStream that implements the SharedInputStream interface,
 * allowing the underlying byte array to be shared between multiple readers.
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
public class SharedByteArrayInputStream extends ByteArrayInputStream implements SharedInputStream{

    /**
     * Position within shared buffer that this stream starts at.
     */
    protected int start = 0;

    /**
     * Create a SharedByteArrayInputStream representing the entire
     * byte array.
     *
     * @param   buf the byte array
     */
    public SharedByteArrayInputStream(byte[] buf) {
        super( buf );
    }

    /**
     * Create a SharedByteArrayInputStream representing the part
     * of the byte array from <code>offset</code> for <code>length</code>
     * bytes.
     *
     * @param   buf the byte array
     * @param   offset  offset in byte array to first byte to include
     * @param   length  number of bytes to include
     */
    public SharedByteArrayInputStream(byte[] buf, int offset, int length) {
        super( buf, offset, length );
        start = offset;
    }

    /**
     * Return the current position in the InputStream, as an
     * offset from the beginning of the InputStream.
     *
     * @return  the current position
     */
    public long getPosition() {
        return pos - start;
    }

    /**
     * Return a new InputStream representing a subset of the data
     * from this InputStream, starting at <code>start</code> (inclusive)
     * up to <code>end</code> (exclusive).  <code>start</code> must be
     * non-negative.  If <code>end</code> is -1, the new stream ends
     * at the same place as this stream.  The returned InputStream
     * will also implement the SharedInputStream interface.
     *
     * @param   start   the starting position
     * @param   end the ending position + 1
     * @return      the new stream
     */
    public InputStream newStream(long start, long end) {
        if( start < 0 )
            throw new IllegalArgumentException( "start < 0" );
        if( end == -1 )
            end = count - this.start;
        return new SharedByteArrayInputStream( buf, this.start + (int)start, (int)(end - start) );
    }
}
