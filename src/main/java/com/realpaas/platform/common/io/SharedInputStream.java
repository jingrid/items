/**
 * Copyright (c) 2012, RealPaaS Technologies, Ltd. All rights reserved.
 */
package com.realpaas.platform.common.io;

import java.io.InputStream;

/**
 * <p>
 *  * An InputStream that is backed by data that can be shared by multiple
 * readers may implement this interface.  This allows users of such an
 * InputStream to determine the current position in the InputStream, and
 * to create new InputStreams representing a subset of the data in the
 * original InputStream.  The new InputStream will access the same
 * underlying data as the original, without copying the data. <p>
 *
 * Note that implementations of this interface must ensure that the
 * <code>close</code> method does not close any underlying stream
 * that might be shared by multiple instances of <code>SharedInputStream</code>
 * until all shared instances have been closed.
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
public interface SharedInputStream {
    
    /**
     * Return the current position in the InputStream, as an
     * offset from the beginning of the InputStream.
     *
     * @return  the current position
     */
    public long getPosition();

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
    public InputStream newStream(long start, long end);
}
